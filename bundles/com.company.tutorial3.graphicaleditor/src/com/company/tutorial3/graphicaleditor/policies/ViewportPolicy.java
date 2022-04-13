package com.company.tutorial3.graphicaleditor.policies;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.eclipse.gef.fx.nodes.InfiniteCanvas;
import org.eclipse.gef.geometry.planar.AffineTransform;
import org.eclipse.gef.mvc.fx.operations.ChangeViewportOperation;
import org.eclipse.gef.mvc.fx.parts.IContentPart;

import com.company.tutorial3.graphicaleditor.parts.ArcPart;
import com.company.tutorial3.graphicaleditor.parts.HighlightablePart;
import com.company.tutorial3.graphicaleditor.view.CustomInfiniteCanvas;
import com.company.tutorial3.graphicaleditor.view.InfiniteCanvasViewer;
import com.company.tutorial3.graphicaleditor.visuals.NodeVisual;

import javafx.geometry.Point2D;

public class ViewportPolicy extends org.eclipse.gef.mvc.fx.policies.ViewportPolicy{
	
	private static final double MAX_ZOOM = 8;
	private static final double MIN_ZOOM = 0.0019;
	
	@Override
	protected void checkInitialized() {
		try {
			super.checkInitialized();
		}catch (Exception e) {
			System.out.println("Class Viewportpolicy has not been initialized");
			init();
		}
	}
	
	public void zoomAbsolute(double zoomAbsolute) {
		checkInitialized();
		CustomInfiniteCanvas infiniteCanvas = getCanvas();
		while (infiniteCanvas.getCurrentZoom() != zoomAbsolute) {
			double oldCurrentZoom = infiniteCanvas.getCurrentZoom();
			Point2D pivotPointInScene = infiniteCanvas.localToScene(infiniteCanvas.getWidth(), 0);
			zoomInternal(true, true, infiniteCanvas.getCurrentZoom() < zoomAbsolute ? 1.05 : 0.95, pivotPointInScene.getX(), pivotPointInScene.getY(), false, zoomAbsolute);
			if (oldCurrentZoom == infiniteCanvas.getCurrentZoom()) {
				break;
			}
		}
		locallyExecuteOperation();
	}

	private void zoomInternal(boolean concatenate, boolean discretize, double relativeZoom, double sceneX, double sceneY, boolean calculateScale, double zoomAbsolute) {
		if (relativeZoom > 1) {
			relativeZoom = 2;
			MapZoom.minuseCountZoom();
		} else if (relativeZoom < 1) {
			relativeZoom = 0.5;
			MapZoom.addCountZoom();
		}
		
		double discreteZoomLevel = 0d;
		if (discretize) {
			double oldZoomLevel = getChangeViewportOperation().getNewContentTransform().getScaleX();
			discreteZoomLevel = oldZoomLevel * relativeZoom;
			DecimalFormat df = new DecimalFormat("#.######");
			df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
			df.setRoundingMode(RoundingMode.HALF_EVEN);
			discreteZoomLevel = Double.parseDouble(df.format(discreteZoomLevel));
			int ozli = (int) oldZoomLevel;
			int nzli = (int) discreteZoomLevel;
			if (ozli != nzli && nzli != discreteZoomLevel && ozli != oldZoomLevel) {
				discreteZoomLevel = ozli < nzli ? nzli : ozli;
			}
		}
		if(discreteZoomLevel == 0.015624) {
			discreteZoomLevel = 0.015625;
		}
		if (discreteZoomLevel > MAX_ZOOM || discreteZoomLevel < MIN_ZOOM) {
			
			return;
		}
		zoom(concatenate, discreteZoomLevel, relativeZoom, sceneX, sceneY, calculateScale, zoomAbsolute);
	}
	
	private void zoom(boolean concatenate, double discreteZoomLevel, double relativeZoom, double sceneX, double sceneY, boolean calculateScale, double zoomAbsolute) {
		getCanvas().setCurrentZoom(discreteZoomLevel);

		Point2D contentGroupPivot = ((InfiniteCanvas) getHost().getRoot().getViewer().getCanvas()).getContentGroup().sceneToLocal(sceneX, sceneY);
		AffineTransform zoomTx = new AffineTransform()
				.translate(contentGroupPivot.getX(), contentGroupPivot.getY())
				.scale(relativeZoom, relativeZoom).translate(
						-contentGroupPivot.getX(), -contentGroupPivot.getY());
		if (concatenate) {
			getChangeViewportOperation().concatenateToNewContentTransform(zoomTx);
		} else {
			AffineTransform newTx = getChangeViewportOperation().getInitialContentTransform().getCopy().concatenate(zoomTx);
			getChangeViewportOperation().setNewContentTransform(newTx);
		}
		setZoom(discreteZoomLevel, sceneX, sceneY, calculateScale, zoomAbsolute);
	}
	
	private void setZoom(double zoom, double sceneX, double sceneY, boolean calculateScale, double zoomAbsolute) {
		super.setZoom(zoom);
		CustomInfiniteCanvas canvas = getCanvas();
		canvas.setCurrentZoom(zoom);
		getCanvas().onCurrentScaleChanged(getCanvasViewer(), canvas.getCurrentZoom(), sceneX, sceneY);
		
		if(calculateScale || zoom == zoomAbsolute) {
			for (IContentPart<?> contentPart : getCanvasViewer().getContentPartMap().values()) {
				if(contentPart.getVisual().isVisible()) {
					if(contentPart instanceof HighlightablePart<?>) {
						HighlightablePart<?> h = (HighlightablePart<?>)contentPart;
						Object obj = h.getVisual();
						if(obj instanceof NodeVisual) {
							NodeVisual wc = (NodeVisual)obj;
							wc.setSizeShape(MapZoom.getLocationVisualZoom());
						}
					}else if(contentPart instanceof ArcPart) {
						ArcPart arcPart = (ArcPart)contentPart;
						arcPart.getVisual().setScale(MapZoom.getLocationVisualZoom());
						arcPart.refreshVisual();
					}
				}
			}
		}
	}
	
	
	@Override
	public void zoom(boolean concatenate, boolean discretize, double relativeZoom, double sceneX, double sceneY) {
		checkInitialized();
		zoomInternal(concatenate, discretize, relativeZoom, sceneX, sceneY, true, 1);
		locallyExecuteOperation();
	}
	
	@Override
	public void scroll(boolean concatenate, double deltaTranslateX, double deltaTranslateY) {
		checkInitialized();
		ChangeViewportOperation operation = getChangeViewportOperation();
		operation.setNewHorizontalScrollOffset((concatenate ? operation.getNewHorizontalScrollOffset() : operation.getInitialHorizontalScrollOffset()) + deltaTranslateX);
		operation.setNewVerticalScrollOffset((concatenate ? operation.getNewVerticalScrollOffset() : operation.getInitialVerticalScrollOffset()) + deltaTranslateY);
		locallyExecuteOperation();
	}
	
	protected CustomInfiniteCanvas getCanvas() {
		return ((InfiniteCanvasViewer) getHost().getRoot().getViewer()).getCanvas();
	}
	
	protected InfiniteCanvasViewer getCanvasViewer() {
		return (InfiniteCanvasViewer) getHost().getRoot().getViewer();
	}
}


