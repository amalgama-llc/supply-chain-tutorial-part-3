package com.company.tutorial3.graphicaleditor.visuals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.fx.nodes.GeometryNode;
import org.eclipse.gef.geometry.planar.Polyline;
import org.eclipse.gef.mvc.fx.parts.IBendableContentPart.BendPoint;

import com.company.tutorial3.graphicaleditor.parts.ArcPart;
import com.company.tutorial3.graphicaleditor.policies.MapZoom;
import com.amalgamasimulation.geometry.GeometryUtil;
import com.amalgamasimulation.geometry.Point;

import javafx.scene.paint.Color;

public class ArcVisual extends GeometryNode<Polyline> {
	
	private ArcPart linkPart;
	private com.amalgamasimulation.geometry.Polyline internalPolyline;
	private javafx.scene.shape.Polyline endDecoration;
	private boolean highlighted = false;
	private boolean highlightedAsDependency = false;
	protected Color baseColor = new Color(0, 0, 0, 1);
	private Color highlightedColor = new Color(1, 0, 0, 1);
	protected Color currentColor = baseColor;
	private double scale = 0;

	public ArcVisual(ArcPart mineArcPart, double scale) {
		this.linkPart = mineArcPart;
		this.scale = scale;
	}
	
	public void invalidateMineArc(List<BendPoint> visualBendpoints) {
		if (visualBendpoints.size() > 1) {
			List<Point> points = new ArrayList<>();
			visualBendpoints.forEach(vbp -> points.add(new Point(vbp.getPosition().x + (vbp.getContentAnchorage() != null ? (2 * MapZoom.getLocationVisualZoom()) : 0), vbp.getPosition().y + (vbp.getContentAnchorage() != null ? (2 * MapZoom.getLocationVisualZoom()) : 0))));
			internalPolyline = new com.amalgamasimulation.geometry.Polyline(points);
			double[] coordinates = new double[internalPolyline.getPoints().size() * 2];
			for (int i = 0; i < internalPolyline.getPoints().size(); i++) {
				coordinates[i * 2] = internalPolyline.getPoints().get(i).getX();
				coordinates[i * 2 + 1] = internalPolyline.getPoints().get(i).getY();
			}
			setGeometry(new Polyline(new double[] {coordinates[0], coordinates[1], coordinates[coordinates.length - 1], coordinates[coordinates.length - 2]}));
			setGeometry(new Polyline(coordinates));
			//arrows
			if (endDecoration != null) {
				getChildren().remove(endDecoration);
				endDecoration = null;
			}
			
			if (internalPolyline.getLength() > 6 * MapZoom.getLocationVisualZoom()) {
				com.amalgamasimulation.geometry.Polyline arrow = GeometryUtil.getArrowPolyline(internalPolyline.getSubPolylineByAbsoluteOffsets(3* MapZoom.getLocationVisualZoom(), internalPolyline.getLength() - 3* MapZoom.getLocationVisualZoom()).minus(new Point(getLayoutX(), getLayoutY())).getLastSegment(), 3* MapZoom.getLocationVisualZoom());
				double[] arrowCoordinates = new double[arrow.getPoints().size() * 2];
				for (int i = 0; i < arrow.getPoints().size(); i++) {
					arrowCoordinates[i * 2] = arrow.getPoints().get(i).getX();
					arrowCoordinates[i * 2 + 1] = arrow.getPoints().get(i).getY();
				}
				endDecoration = new javafx.scene.shape.Polyline(arrowCoordinates);
				getChildren().add(endDecoration);
			}
		}
		repaintStroke();
	}
	
	public Polyline getHandledOutline() {
		com.amalgamasimulation.geometry.Polyline internalHandledOutline = internalPolyline.getLength() > 6 ? internalPolyline.getSubPolylineByAbsoluteOffsets(3, internalPolyline.getLength() - 3) : internalPolyline;
		double[] coordinates = new double[internalHandledOutline.getPoints().size() * 2];
		for (int i = 0; i < internalHandledOutline.getPoints().size(); i++) {
			coordinates[i * 2] = internalHandledOutline.getPoints().get(i).getX();
			coordinates[i * 2 + 1] = internalHandledOutline.getPoints().get(i).getY();
		}
		return new Polyline(coordinates);
	}
	
	public void setScale(double scale) {
		this.scale = scale;
		setStrokeWidth(scale);
	}
	
	public org.eclipse.gef.geometry.planar.Point getPoint(int index) {
		return getGeometry().getPoints()[index];
	}
	
	private Color getColor() {
		return currentColor;
	}
	
	public void setColor(Color color) {
		this.currentColor = color;
		repaintStroke();
	}
	
	public void unHighlighted() {
		this.highlighted = false;
		this.currentColor = baseColor;
		repaintStroke();
	}
	
	public void highlighted() {
		this.highlighted = true;
		this.currentColor = highlightedColor;
		repaintStroke();
	}
	
	private void repaintStroke() {	
		setStroke(getColor());		
		double s = scale;
		if(highlighted) {
			s = scale * 3;
		}
		setStrokeWidth(s);
		if (endDecoration != null) {
			endDecoration.setStroke(getColor());
			endDecoration.setFill(null);
			endDecoration.setStrokeWidth(s);
		}
	}
	
	public ArcPart getLinkPart() {
		return linkPart;
	}
	
	public void setHighlighted(boolean highlighted, Color highlightColor, boolean highlightedAsDependency, Color dependencyColor) {
		this.highlighted = highlighted;
		this.highlightedAsDependency = highlightedAsDependency;
		repaintStroke();
	}
}


