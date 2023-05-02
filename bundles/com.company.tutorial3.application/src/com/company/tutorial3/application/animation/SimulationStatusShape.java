package com.company.tutorial3.application.animation;

import java.awt.Color;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.swt.widgets.Display;

import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.common.states.AppData;
import com.company.tutorial3.simulation.model.Model;
import com.amalgamasimulation.animation.shapes.shapes2d.GroupShape;
import com.amalgamasimulation.animation.shapes.shapes2d.RectangleShape;
import com.amalgamasimulation.animation.shapes.shapes2d.RoundedRectangleShape;
import com.amalgamasimulation.animation.shapes.shapes2d.TextShape;
import com.amalgamasimulation.geometry.Point;
import com.amalgamasimulation.utils.Utils;
import com.amalgamasimulation.utils.format.Formats;

public class SimulationStatusShape extends GroupShape {
	
	private Messages messages = AppData.messages;
	
	private static double systemFontScaling() {
		final double defaultScaling = 96.0;
		return Display.getDefault().getDPI().x / defaultScaling;
	}
	private static final Supplier<Integer> FONT_SIZE_SMALL = () -> ((int)(9.0 / systemFontScaling()));
	private static final Supplier<Integer> FONT_SIZE_LARGE = () -> ((int)(14.0 / systemFontScaling()));

	public SimulationStatusShape(Model model, Consumer<Object> partActivator, Runnable updater) {
		withShape(new RoundedRectangleShape()
				.withRoundingRadius(7.901)
				.withTopLeftCorner(new Point(0, 0))
				.withWidth(169.24)
				.withLength(23.704)
				.withLineColor((Color)null)
				.withFillColor(new Color(200, 215, 229)));
			withShape(new TextShape(() -> Formats.getDefaultFormats().dayMonthLongYearHoursMinutes(model.date()))
				.withPoint(new Point(170 / 2, 0))
				.withFontColor(new Color(0, 0, 0))
				.withFontSize(FONT_SIZE_LARGE)
				.withHorizontalAlignment(HorizontalAlignment.CENTER)
				.withVerticalAlignment(VerticalAlignment.BOTTOM));
			withShape(new RoundedRectangleShape()
				.withRoundingRadius(7.901)
				.withTopLeftCorner(new Point(0.392, 28.895))
				.withWidth(169.24)
				.withLength(23.704)
				.withLineColor((Color)null)
				.withFillColor(new Color(200, 215, 229)));
			withShape(new RectangleShape()
				.withTopLeftCorner(new Point(3.205, 32.103))
				.withWidth(() -> 161.0 * Math.min(1, Utils.zidz(model.time(), model.getEndTime())))
				.withLength(17.235)
				.withLineColor((Color)null)
				.withFillColor(new Color(89, 132, 174)));
			withShape(new TextShape(() -> String.format(messages.SIMULATION_STATUS_MODELLED_DURATION, 
														Formats.getDefaultFormats().noDecimals(model.time() / model.day()),
														Formats.getDefaultFormats().noDecimals(model.getEndTime() / model.day())))
				.withPoint(new Point(170 / 2, 33.297))
				.withFontColor(new Color(0, 0, 0))
				.withFontSize(FONT_SIZE_SMALL)
				.withHorizontalAlignment(HorizontalAlignment.CENTER)
				.withVerticalAlignment(VerticalAlignment.BOTTOM));
	}
}

