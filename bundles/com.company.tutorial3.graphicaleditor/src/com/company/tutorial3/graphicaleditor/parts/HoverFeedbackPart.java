package com.company.tutorial3.graphicaleditor.parts;

import org.eclipse.gef.fx.nodes.GeometryNode;
import org.eclipse.gef.geometry.planar.ICurve;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.geometry.planar.Rectangle;

import com.company.tutorial3.graphicaleditor.policies.MapZoom;

import javafx.scene.shape.StrokeType;

public class HoverFeedbackPart extends org.eclipse.gef.mvc.fx.parts.HoverFeedbackPart{

	public HoverFeedbackPart() {}

	@Override
	public void doRefreshVisual(GeometryNode<IGeometry> visual) {
		if (getAnchoragesUnmodifiable().size() != 1) {
			return;
		}

		IGeometry feedbackGeometry = getFeedbackGeometry();
		if (feedbackGeometry == null) {
			return;
		}

		if (feedbackGeometry instanceof ICurve) {
			visual.setStrokeType(StrokeType.CENTERED);
			visual.setStrokeWidth(1 * MapZoom.getLocationVisualZoom());
		} else if(feedbackGeometry instanceof Rectangle) {
			visual.setStrokeType(StrokeType.OUTSIDE);
			visual.setStrokeWidth(0.5);
		}else {
			visual.setStrokeType(StrokeType.OUTSIDE);
			visual.setStrokeWidth(1 * MapZoom.getLocationVisualZoom());
		}
		
		visual.setGeometry(feedbackGeometry);
	}
}


