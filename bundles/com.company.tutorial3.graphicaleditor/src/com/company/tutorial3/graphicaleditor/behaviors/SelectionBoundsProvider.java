package com.company.tutorial3.graphicaleditor.behaviors;


import org.eclipse.gef.common.adapt.IAdaptable;
import org.eclipse.gef.geometry.convert.fx.FX2Geometry;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.inject.Provider;

import javafx.geometry.Bounds;
import javafx.scene.Node;

public class SelectionBoundsProvider extends IAdaptable.Bound.Impl<IVisualPart<? extends Node>> implements Provider<IGeometry> {

	@Override
	public IGeometry get() {
		SelectionModel selectionModel = getAdaptable().getViewer().getAdapter(SelectionModel.class);
		Rectangle bounds = null;
		for (IVisualPart<? extends Node> part : selectionModel.getSelectionUnmodifiable()) {
			//if (!(part instanceof MineArcPart)) {
				Bounds boundsInLocal = part.getVisual().getBoundsInLocal();
				Bounds boundsInScene = part.getVisual().localToScene(boundsInLocal);
	
				// transform to scene
				if (boundsInLocal != null) {
					if (bounds == null) {
						bounds = FX2Geometry.toRectangle(boundsInScene);
					} else {
						bounds.union(FX2Geometry.toRectangle(boundsInScene));
					}
				}
			//}
		}
		return bounds;
	}
}


