package com.company.tutorial3.graphicaleditor.handlers;

import org.eclipse.gef.common.adapt.IAdaptable;
import org.eclipse.gef.fx.utils.NodeUtils;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel;
import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel.Type;
import com.google.inject.Provider;

import javafx.scene.Node;

public class SegmentGeometricOutlineProvider extends IAdaptable.Bound.Impl<IVisualPart<? extends Node>>implements Provider<IGeometry> {
	
	@Override
	public IGeometry get() {
		IViewer viewer = getAdaptable().getViewer();
		ItemCreationModel creationModel = viewer.getAdapter(ItemCreationModel.class);
		if (creationModel.getType() != Type.NONE) {
			return new Rectangle();
		}
		return NodeUtils.getGeometricOutline(getAdaptable().getVisual());
	}

}


