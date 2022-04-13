package com.company.tutorial3.graphicaleditor.parts;


import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.fx.nodes.GeometryNode;
import org.eclipse.gef.geometry.planar.BezierCurve;
import org.eclipse.gef.geometry.planar.Ellipse;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.AbstractSegmentHandlePart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.company.tutorial3.graphicaleditor.policies.MapZoom;
import com.google.common.collect.SetMultimap;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

public class BendpointPart extends AbstractSegmentHandlePart<GeometryNode<Ellipse>> {
	
	protected static final double RADIUS = 1.4;
	protected static final double STROKE_WIDTH = 0.4;

	/**
	 * Creates the visual representation of this selection handle.
	 *
	 * @return {@link Node} representing the handle visually
	 */
	@Override
	protected GeometryNode<Ellipse> doCreateVisual() {
		GeometryNode<Ellipse> geometryNode = new GeometryNode<>();
		Ellipse circle = new Ellipse(0, 0, RADIUS * 2 * MapZoom.getLocationVisualZoom(), RADIUS * 2 * MapZoom.getLocationVisualZoom());
		geometryNode.setStroke(new Color(0, 0, 0, 1));
		geometryNode.setFill(new Color(1, 1, 1, 1));
		geometryNode.setStrokeWidth(STROKE_WIDTH * MapZoom.getLocationVisualZoom());
		geometryNode.setStrokeType(StrokeType.CENTERED);
		geometryNode.setGeometry(circle);
		return geometryNode;
	}

	@Override
	public void doRefreshVisual(GeometryNode<Ellipse> visual) {
		super.doRefreshVisual(visual);
		updateColor();
	}

	/**
	 * Updates the color of this part's visualization. If this handle part
	 * represents a way or end point of an {@link Connection}, it's color will
	 * be set to indicate whether the handle is connected to another part or
	 * not.
	 */
	protected void updateColor() {
		// only update when bound to anchorage
		SetMultimap<IVisualPart<? extends Node>, String> anchorages = getAnchoragesUnmodifiable();
		if (getRoot() == null || anchorages.keySet().size() != 1) {
			return;
		}

		GeometryNode<Ellipse> visual = getVisual();
		// no need to update the color if we are invisible
		if (!visual.isVisible()) {
			return;
		}
		if (getSegmentParameter() != 0.0 && getSegmentParameter() != 1.0) {
			visual.getGeometry().setWidth(RADIUS * 0.8 * 2 * MapZoom.getLocationVisualZoom());
			visual.getGeometry().setHeight(RADIUS * 0.8 * 2 * MapZoom.getLocationVisualZoom());
			visual.setStrokeWidth(STROKE_WIDTH * MapZoom.getLocationVisualZoom());
			visual.setClickableAreaWidth(4);
			visual.setFill(new Color(1, 1, 1, 1));
		} else {
			//visual.setFill(new Color(0, 0, 0, 1));
			visual.setFill(new Color(0.8, 0.8, 0.8, 1));
			visual.setClickableAreaWidth(2);
			IVisualPart<? extends Node> targetPart = anchorages.keySet().iterator().next();
			if (targetPart != null) {
				if (getSegmentIndex() + getSegmentParameter() == 0.0) {
					// handle at start point
					//visual.setRadius(RADIUS * 0.8);
				} else if (getSegmentParameter() + getSegmentIndex() == getSegmentsInScene().length) {
					// handle at end point
					//visual.setRadius(RADIUS * 0.8);
				}
				//return;
			}
			visual.getGeometry().setWidth(RADIUS * 2 * MapZoom.getLocationVisualZoom());
			visual.getGeometry().setHeight(RADIUS * 2 * MapZoom.getLocationVisualZoom());
			visual.setStrokeWidth(STROKE_WIDTH * MapZoom.getLocationVisualZoom());
		}
		updateLocation(visual);
	}
	
	@Override
	protected void updateLocation(GeometryNode<Ellipse> visual) {
		// only update when bound to anchorage
		SetMultimap<IVisualPart<? extends Node>, String> anchorages = getAnchoragesUnmodifiable();
		if (anchorages.keySet().size() < 1) {
			return;
		}

		BezierCurve segmentInParent = getBezierSegmentInParent();
		if (segmentInParent == null) {
			// hide those that have "invalid" index. (this may happen during
			// life-feedback, when a way-point is removed)
			visual.setVisible(false);
		} else {
			visual.setVisible(true);
			Point positionInParent = getPosition(segmentInParent);
			// transform to handle space
			visual.relocate(
					positionInParent.x + visual.getLayoutBounds().getMinX() - visual.getGeometry().getWidth() / 2 - STROKE_WIDTH / 2,
					positionInParent.y + visual.getLayoutBounds().getMinY() - visual.getGeometry().getHeight() / 2 - STROKE_WIDTH / 2);
		}
	}
}


