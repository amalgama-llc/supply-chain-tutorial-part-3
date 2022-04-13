package com.company.tutorial3.graphicaleditor.handlers;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.CursorSupport;
import org.eclipse.gef.mvc.fx.handlers.IOnDragHandler;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.company.tutorial3.graphicaleditor.policies.ViewportPolicy;
import com.company.tutorial3.graphicaleditor.view.InfiniteCanvasViewer;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class PanHandler extends AbstractHandler implements IOnDragHandler {

	private boolean invalidGesture = true;
	
	@Override
	public void abortDrag() {
		if (invalidGesture)	return;
	}

	@Override
	public void hideIndicationCursor() {}

	@Override
	public boolean showIndicationCursor(KeyEvent event) {
		return false;
	}

	@Override
	public boolean showIndicationCursor(MouseEvent event) {
		return false;
	}

	ViewportPolicy viewportPolicy = null;
	
	protected ViewportPolicy determineViewportPolicy() {
		return getHost().getRoot().getAdapter(ViewportPolicy.class);
	}
	
	@Override
	public void startDrag(MouseEvent event) {
		invalidGesture = !event.isSecondaryButtonDown();
		if ( invalidGesture ) {
			return;
		}
		
		this.viewportPolicy = determineViewportPolicy();
		init(viewportPolicy);
		getCursorSupport().storeAndReplaceCursor(Cursor.CLOSED_HAND);
	}
	
	@Override
	public void drag(MouseEvent e, Dimension delta) {
		if (invalidGesture) {
			return;
		}
		viewportPolicy.scroll(false, delta.width, delta.height);
	}
	
	@Override
	public void endDrag(MouseEvent e, Dimension delta) {
		if (invalidGesture) {
			return;
		}
		commit(viewportPolicy);
		this.viewportPolicy = null;
		CursorSupport cursorSupport = getCursorSupport();
		IViewer contentViewer = getContentViewer();
		if (contentViewer instanceof InfiniteCanvasViewer) {
			InfiniteCanvasViewer icv = (InfiniteCanvasViewer)contentViewer;
			if (icv.isWaitingForSelection()) {
				cursorSupport.setCursor(new ImageCursor(new Image("resources/selectionCursor.png")));
				return;
			}
		}
		cursorSupport.restoreCursor();
	}

	protected IViewer getContentViewer() {
		return getHost().getRoot().getViewer().getDomain().getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
	}
	
	protected CursorSupport getCursorSupport() {
		return getContentViewer().getAdapter(CursorSupport.class);
	}
}


