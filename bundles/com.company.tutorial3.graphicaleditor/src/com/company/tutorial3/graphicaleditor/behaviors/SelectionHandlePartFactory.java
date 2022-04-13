package com.company.tutorial3.graphicaleditor.behaviors;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.geometry.planar.BezierCurve;
import org.eclipse.gef.geometry.planar.Polyline;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionHandlePartFactory;
import org.eclipse.gef.mvc.fx.parts.IHandlePart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.company.tutorial3.graphicaleditor.parts.BendpointPart;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

import javafx.scene.Node;

public class SelectionHandlePartFactory extends DefaultSelectionHandlePartFactory{

	@Inject
	private Injector injector;

	@Override
	protected List<IHandlePart<? extends Node>> createSingleSelectionHandlePartsForCurve(IVisualPart<? extends Node> target, Map<Object, Object> contextMap, Provider<BezierCurve[]> segmentsProvider) {
		List<IHandlePart<? extends Node>> hps = new ArrayList<>();
		BezierCurve[] segments = segmentsProvider.get();

		// generate vertex based handles
		for (int i = 0; i < segments.length; i++) {
			// create handle for the start point of a segment
			BendpointPart part = injector.getInstance(BendpointPart.class);
			part.setSegmentsProvider(segmentsProvider);
			part.setSegmentIndex(i);
			part.setSegmentParameter(0.0);
			hps.add(part);

			double segmentLength = new Polyline(segments[i].getPoints()).getLength();
			if (segmentLength >= BENDPOINT_CREATE_HANDLE_MINIMUM_SEGMENT_LENGTH) {
				// create handle for the middle of a segment
				part = injector.getInstance(BendpointPart.class);
				part.setSegmentsProvider(segmentsProvider);
				part.setSegmentIndex(i);
				part.setSegmentParameter(0.5);
				hps.add(part);
			}

			// create handle for the end point of the curve
			if (i == segments.length - 1) {
				part = injector.getInstance(BendpointPart.class);
				part.setSegmentsProvider(segmentsProvider);
				part.setSegmentIndex(i);
				part.setSegmentParameter(1.0);
				hps.add(part);
			}
		}

		return hps;
	}
}


