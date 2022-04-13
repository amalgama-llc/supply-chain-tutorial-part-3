package com.company.tutorial3.graphicaleditor.behaviors;

import org.eclipse.gef.fx.nodes.InfiniteCanvas;
import org.eclipse.gef.mvc.fx.behaviors.AbstractBehavior;

import com.company.tutorial3.graphicaleditor.view.InfiniteCanvasViewer;



public class GridBehavior extends AbstractBehavior {

	@Override
	protected void doActivate() {
		GridModel gridModel = getHost().getRoot().getViewer().getAdapter(GridModel.class);
		if (gridModel == null) {
			throw new IllegalStateException("Unable to retrieve GridModel viewer adapter. Please check your adapter bindings.");
		}
		InfiniteCanvas canvas = getCanvas();
		canvas.showGridProperty().bind(gridModel.showGridProperty());
		canvas.zoomGridProperty().bind(gridModel.zoomGridProperty());
		canvas.gridCellWidthProperty().bind(gridModel.gridCellWidthProperty());
		canvas.gridCellHeightProperty().bind(gridModel.gridCellHeightProperty());
	}

	@Override
	protected void doDeactivate() {
		InfiniteCanvas canvas = getCanvas();
		canvas.showGridProperty().unbind();
		canvas.zoomGridProperty().unbind();
		canvas.gridCellWidthProperty().unbind();
		canvas.gridCellHeightProperty().unbind();
	}

	protected InfiniteCanvas getCanvas() {
		return ((InfiniteCanvasViewer) getHost().getRoot().getViewer()).getCanvas();
	}

}


