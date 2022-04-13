package com.company.tutorial3.graphicaleditor.parts;


import java.util.List;
import java.util.Set;

import org.eclipse.gef.fx.nodes.GeometryNode;
import org.eclipse.gef.geometry.planar.ICurve;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.company.tutorial3.graphicaleditor.policies.MapZoom;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.shape.StrokeType;

public class SelectionFeedbackPart extends org.eclipse.gef.mvc.fx.parts.SelectionFeedbackPart{

	public SelectionFeedbackPart() {}

	ListChangeListener<Double> pointsListener = c -> refreshVisual();

	@Override
	protected void doAttachToAnchorageVisual(IVisualPart<? extends Node> anchorage, String role) {
		super.doAttachToAnchorageVisual(anchorage, role);
//		Node anchorageVisual = anchorage.getVisual();
//		System.out.println(this + "\t" + anchorage + "\t" + "ATTACHED");
//		if (anchorageVisual instanceof Traverse) {
//			((Traverse) anchorageVisual).getCurve().getPoints().addListener(pointsListener);
//		}
	}

	@Override
	protected void doDetachFromAnchorageVisual(IVisualPart<? extends Node> anchorage, String role) {
//		Node anchorageVisual = anchorage.getVisual();
//		System.out.println(this + "\t" + anchorage + "\t" + "DETACHED");
//		if (anchorageVisual instanceof Traverse) {
//			((Traverse) anchorageVisual).getCurve().getPoints().removeListener(pointsListener);
//		}
		super.doDetachFromAnchorageVisual(anchorage, role);
	}

	@Override
	public void doRefreshVisual(GeometryNode<IGeometry> visual) {
		Set<IVisualPart<? extends Node>> anchorages = getAnchoragesUnmodifiable().keySet();
		if (anchorages.isEmpty()) {
			return;
		}
		IVisualPart<? extends Node> anchorage = anchorages.iterator().next();
		IRootPart<? extends Node> root = anchorage.getRoot();
		if (root == null) {
			return;
		}
		IViewer viewer = anchorage.getRoot().getViewer();
		if (viewer == null) {
			return;
		}
		IGeometry feedbackGeometry = getFeedbackGeometry();
		if (feedbackGeometry == null) {
			return;
		}

		if (feedbackGeometry instanceof ICurve) {
			visual.setStrokeType(StrokeType.CENTERED);
			visual.setStrokeWidth(DEFAULT_STROKE_WIDTH * MapZoom.getLocationVisualZoom());
		} else {
			visual.setStrokeType(StrokeType.OUTSIDE);
			visual.setStrokeWidth(1 *  MapZoom.getLocationVisualZoom());
		}
		visual.setGeometry(feedbackGeometry);

		List<IContentPart<? extends Node>> selected = viewer.getAdapter(SelectionModel.class).getSelectionUnmodifiable();
		if (selected != null && selected.size() > 0 && selected.get(0) == anchorage) {
			visual.setStroke(getPrimarySelectionColor());
		} else {
			visual.setStroke(getSecondarySelectionColor());
		}
	}
}


