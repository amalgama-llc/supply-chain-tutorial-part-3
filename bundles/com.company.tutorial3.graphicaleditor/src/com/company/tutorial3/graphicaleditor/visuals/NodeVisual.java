package com.company.tutorial3.graphicaleditor.visuals;

import org.eclipse.gef.fx.nodes.GeometryNode;
import org.eclipse.gef.geometry.planar.Ellipse;
import org.eclipse.gef.geometry.planar.Point;

import com.company.tutorial3.graphicaleditor.policies.MapZoom;

import javafx.scene.paint.Color;

public class NodeVisual extends  GeometryNode<Ellipse> {
	
	private boolean drawAsCreatedSegmentBeginNode = false;
	private Ellipse ellipse;
	private double scale = 1;
	
	public NodeVisual( ) {
		this.scale = MapZoom.getLocationVisualZoom();
		this.ellipse = new Ellipse(0, 0, 4, 4);
		setGeometry(ellipse);
		setSizeShape(scale);
	}
	
	private javafx.scene.shape.Polyline createdSegmentFeedback = null;
	
	public void invalidateCreatedSegmentFeedback(double x, double y) {
		if (createdSegmentFeedback != null) {
			getChildren().remove(createdSegmentFeedback);
		}
		if (drawAsCreatedSegmentBeginNode) {
			com.amalgamasimulation.geometry.Polyline internalPolyline = new com.amalgamasimulation.geometry.Polyline(	new com.amalgamasimulation.geometry.Point(getLayoutX() + getWidth() / 2, getLayoutY() + getHeight() / 2), 
																														new com.amalgamasimulation.geometry.Point(sceneToLocal(x, y).getX(), sceneToLocal(x, y).getY()));
			if (internalPolyline.getLength() > 6) {
				com.amalgamasimulation.geometry.Polyline internalHandledOutline = internalPolyline.getSubPolylineByAbsoluteOffsets(3, internalPolyline.getLength() - 3);
				double[] coordinates = new double[internalHandledOutline.getPoints().size() * 2];
				for (int i = 0; i < internalHandledOutline.getPoints().size(); i++) {
					coordinates[i * 2] = internalHandledOutline.getPoints().get(i).getX();
					coordinates[i * 2 + 1] = internalHandledOutline.getPoints().get(i).getY();
				}
				createdSegmentFeedback = new javafx.scene.shape.Polyline(coordinates);
				createdSegmentFeedback.getStrokeDashArray().addAll(2d);
				createdSegmentFeedback.setStrokeWidth(MapZoom.getLocationVisualZoom());
				getChildren().add(createdSegmentFeedback);
			}
		}
	}
	
	
	public Point getCenterOfBoundsInParent() {
		return new Point(getBoundsInParent().getMinX() + getWidth() / 2, getBoundsInParent().getMinY() + getHeight() / 2);
	}
	
	public void setHighlighted(boolean highlighted, Color highlightColor, boolean highlightedAsDependency, Color dependencyColor) {
		if (!drawAsCreatedSegmentBeginNode) {
			setFill(highlightedAsDependency ? dependencyColor : highlighted ? highlightColor : new Color(0, 0, 1, 1));
			setStroke(highlighted ? Color.RED : Color.BLACK);
		}
	}
	
	public void setDrawAsCreatedSegmentBeginNode(boolean drawAsCreatedSegmentBeginNode) {
		this.drawAsCreatedSegmentBeginNode = drawAsCreatedSegmentBeginNode;
		if (drawAsCreatedSegmentBeginNode) {
			setFill(new Color(0, 0.5, 0, 1));
			setStroke(new Color(0, 0.5, 0, 1));
		} else {
			setFill(new Color(0, 0, 1, 1));
			setStroke(Color.BLACK);
			invalidateCreatedSegmentFeedback(getLayoutX() + getWidth() / 2, getLayoutY() + getHeight() / 2);
		}
	}

	public boolean isDrawAsCreatedSegmentBeginNode() {
		return drawAsCreatedSegmentBeginNode;
	}
	
	public double getScale() {
		return scale;
	}


	public void setSizeShape(double scale) {
		this.scale = scale;
		setWidth(6 * scale);
		setHeight(6 * scale);
	}
}


