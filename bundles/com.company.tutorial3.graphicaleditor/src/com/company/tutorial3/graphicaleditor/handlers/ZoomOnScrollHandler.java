package com.company.tutorial3.graphicaleditor.handlers;

import org.eclipse.gef.fx.nodes.InfiniteCanvas;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnScrollHandler;

import com.company.tutorial3.graphicaleditor.policies.ViewportPolicy;

import javafx.geometry.Point2D;
import javafx.scene.input.ScrollEvent;

public class ZoomOnScrollHandler extends AbstractHandler implements IOnScrollHandler {

	private ViewportPolicy viewportPolicy;

	@Override
	public void abortScroll() {
		rollback(viewportPolicy);
		this.viewportPolicy = null;
	}

	/**
	 * Computes the zoom factor from the given {@link ScrollEvent}.
	 *
	 * @param event
	 *            The {@link ScrollEvent} from which to compute the zoom factor.
	 * @return The zoom factor according to the given {@link ScrollEvent}.
	 */
	protected double computeZoomFactor(ScrollEvent event) {
		return event.getDeltaY() > 0 ? 1.05 : 1 / 1.05;
	}

	/**
	 * Returns the {@link ViewportPolicy} that is to be used for changing the
	 * viewport. This method is called within {@link #startScroll(ScrollEvent)}
	 * where the resulting policy is cached for the scroll gesture.
	 *
	 * @return The {@link ViewportPolicy} that is to be used for changing the
	 *         viewport.
	 */
	protected ViewportPolicy determineViewportPolicy() {
		return getHost().getRoot().getAdapter(ViewportPolicy.class);
	}

	@Override
	public void endScroll() {
		commit(viewportPolicy);
		this.viewportPolicy = null;
	}

	/**
	 * Returns the {@link ViewportPolicy} that is used for changing the viewport
	 * within the current scroll gesture. This policy is set within
	 * {@link #startScroll(ScrollEvent)} to the value determined by
	 * {@link #determineViewportPolicy()}.
	 *
	 * @return The {@link ViewportPolicy} that is used for changing the viewport
	 *         within the current scroll gesture.
	 */
	protected final ViewportPolicy getViewportPolicy() {
		return viewportPolicy;
	}

	/**
	 * Returns <code>true</code> to signify that scrolling and zooming is
	 * restricted to the content bounds, <code>false</code> otherwise.
	 * <p>
	 * When content-restricted, the policy behaves texteditor-like, i.e. the
	 * pivot point for zooming is at the top of the viewport and at the left of
	 * the contents, and free space is only allowed to the right and to the
	 * bottom of the contents. Therefore, the policy does not allow panning or
	 * zooming if it would result in free space within the viewport at the top
	 * or left sides of the contents.
	 *
	 * @return <code>true</code> to signify that scrolling and zooming is
	 *         restricted to the content bounds, <code>false</code> otherwise.
	 */
	protected boolean isContentRestricted() {
		return false;
	}

	/**
	 * Returns <code>true</code> if the given {@link ScrollEvent} should trigger
	 * zooming. Otherwise returns <code>false</code>. Per default, either
	 * <code>&lt;Control&gt;</code> or <code>&lt;Alt&gt;</code> has to be
	 * pressed so that <code>true</code> is returned.
	 *
	 * @param event
	 *            The {@link ScrollEvent} in question.
	 * @return <code>true</code> if the given {@link ScrollEvent} should trigger
	 *         zooming, otherwise <code>false</code>.
	 */
	protected boolean isZoom(ScrollEvent event) {
		return event.isControlDown() || event.isAltDown();
	}

	@Override
	public void scroll(ScrollEvent event) {
		// each event is tested for suitability so that you can switch between
		// multiple scroll actions instantly when pressing/releasing modifiers
		if (isZoom(event)) {
			zoom(event);
		}
	}

	@Override
	public void startScroll(ScrollEvent event) {
		this.viewportPolicy = determineViewportPolicy();
		init(viewportPolicy);
		// delegate to scroll() to perform panning/zooming
		scroll(event);
	}

	/**
	 * Performs zooming according to the given {@link ScrollEvent}.
	 *
	 * @param event
	 *            The {@link ScrollEvent} according to which zooming is
	 *            performed.
	 */
	protected void zoom(ScrollEvent event) {
		// compute zoom factor from the given event
		double zoomFactor = computeZoomFactor(event);
		if (isContentRestricted()) {
			// calculate a pivot points to achieve a zooming similar to that of
			// a text editor (fix absolute content left in x-direction, fix
			// visible content top in y-direction)
			InfiniteCanvas infiniteCanvas = (InfiniteCanvas) getHost().getRoot().getViewer().getCanvas();
			// XXX: The pivot point computation needs to be done after free
			// space top/left is removed so that the content-bounds minX
			// coordinate is correct.
			Point2D pivotPointInScene = infiniteCanvas.localToScene(infiniteCanvas.getContentBounds().getMinX(), 0);
			// performing zooming
			viewportPolicy.zoom(true, true, zoomFactor, pivotPointInScene.getX(), pivotPointInScene.getY());
		} else {
			// zoom into/out-of the event location
			viewportPolicy.zoom(true, true, zoomFactor, event.getSceneX(), event.getSceneY());
		}
	}

}


