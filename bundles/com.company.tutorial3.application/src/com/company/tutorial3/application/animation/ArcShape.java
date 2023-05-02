package com.company.tutorial3.application.animation;

import com.amalgamasimulation.animation.shapes.shapes2d.GroupShape;
import com.amalgamasimulation.animation.shapes.shapes2d.PolylineShape;
import com.amalgamasimulation.geometry.Point;
import com.company.tutorial3.simulation.model.Arc;
import com.amalgamasimulation.utils.Colors;


public class ArcShape extends GroupShape {
	
	public ArcShape(Arc road) {
		super(() -> new Point(0, 0));
		withShape(new PolylineShape(() -> road.getPolyline()).withLineColor(Colors.gray));
		asStatic(() -> true);
	}
}

