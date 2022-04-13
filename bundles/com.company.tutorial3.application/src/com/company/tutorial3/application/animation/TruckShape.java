package com.company.tutorial3.application.animation;

import com.amalgamasimulation.animation.shapes.shapes2d.GroupShape;
import com.amalgamasimulation.animation.shapes.shapes2d.RectangleShape;
import com.amalgamasimulation.geometry.Point;
import com.amalgamasimulation.utils.Colors;
import com.company.tutorial3.simulation.model.Truck;

public class TruckShape extends GroupShape {

    public TruckShape(Truck truck) {
        super(() -> truck.getCurrentAnimationPoint());
        withShape(new RectangleShape(() -> new Point(-5, -5), () -> 10.0, () -> 30.0).withLineColor(Colors.blue)
                .withFillColor(Colors.blue));
        withRotationAngle(() -> -truck.getHeading());
        withFixedScale(1);
    }
}