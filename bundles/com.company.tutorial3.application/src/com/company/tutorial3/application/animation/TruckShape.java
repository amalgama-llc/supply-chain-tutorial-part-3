package com.company.tutorial3.application.animation;

import com.amalgamasimulation.animation.shapes.shapes2d.GroupShape;
import com.amalgamasimulation.animation.shapes.shapes2d.RectangleShape;
import com.amalgamasimulation.geometry.Point;
import com.amalgamasimulation.utils.Colors;
import com.company.tutorial3.simulation.model.Truck;

public class TruckShape extends GroupShape {

    public TruckShape(Truck truck) {
        super(() -> truck.getCurrentAnimationPoint());
        withShape(new RectangleShape(() -> new Point(-5, -5), () -> 10.0, () -> 30.0).withLineColor(Colors.darkBlue)
                .withFillColor(() -> getTruckFillColor(truck)));
        withShape(new RectangleShape(() -> new Point(-4, -9), () -> 8.0, () -> 4.0).withLineColor(Colors.green)
                .withFillColor(Colors.green));
        withRotationAngle(() -> -truck.getHeading() + Math.PI);
        withFixedScale(1.3);
    }

    private java.awt.Color getTruckFillColor(Truck truck) {
        if (truck.getCurrentTask() == null) {
            return Colors.white;
        }
        return truck.getCurrentTask().isMovingWithCargo() ? Colors.blue : Colors.white;
    }
}