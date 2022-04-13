package com.company.tutorial3.application.animation;

import com.amalgamasimulation.animation.shapes.shapes2d.CircleShape;
import com.amalgamasimulation.animation.shapes.shapes2d.GroupShape;
import com.amalgamasimulation.geometry.Point;
import com.amalgamasimulation.utils.Colors;
import com.company.tutorial3.simulation.model.Store;

public class StoreShape extends GroupShape {
    public StoreShape(Store store) {
        super(() -> store.getNode().getPoint());
        withShape(new CircleShape(new Point(0, 0), 15, Colors.red).withFillColor(Colors.red));
        withFixedScale(1);
    }
}