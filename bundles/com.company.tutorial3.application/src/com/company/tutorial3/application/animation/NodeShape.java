package com.company.tutorial3.application.animation;

import com.amalgamasimulation.animation.shapes.shapes2d.CircleShape;
import com.amalgamasimulation.animation.shapes.shapes2d.GroupShape;
import com.amalgamasimulation.geometry.Point;
import com.amalgamasimulation.utils.Colors;
import com.company.tutorial3.simulation.model.Node;


public class NodeShape extends GroupShape {
	public NodeShape(Node node) {
		super(() -> node.getPoint());
		withShape(new CircleShape(new Point(0, 0), 5, Colors.black));
	}	
}

