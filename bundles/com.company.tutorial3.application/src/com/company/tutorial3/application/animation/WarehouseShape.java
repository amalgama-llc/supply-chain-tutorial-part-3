package com.company.tutorial3.application.animation;

import com.amalgamasimulation.animation.shapes.shapes2d.CircleShape;
import com.amalgamasimulation.animation.shapes.shapes2d.GroupShape;
import com.amalgamasimulation.animation.shapes.shapes2d.TextShape;
import com.amalgamasimulation.geometry.Point;
import com.amalgamasimulation.utils.Colors;
import com.company.tutorial3.simulation.model.Warehouse;

public class WarehouseShape extends GroupShape {
	public WarehouseShape(Warehouse warehouse) {
		super(() -> warehouse.getNode().getPoint());
		withShape(new CircleShape(new Point(0, 0), 15, Colors.orange).withFillColor(Colors.orange));
		withShape(new TextShape(warehouse.getName()));
		withFixedScale(1);
	}
}
