package com.company.tutorial3.common;

import java.time.temporal.ChronoUnit;



public class CoordinatesConverter {
	
	private double pixelsPerMeter;
	
	
	
	public void setPixelsPerMeter(double pixelsPerMeter) {
		this.pixelsPerMeter = pixelsPerMeter;
	}
	
	public double metersToLogicalPixels(double meters) {
		return meters * pixelsPerMeter;
	}
	
	public double pixelsToKm(double pixels) {
		return pixelsToMeters(pixels) / 1_000;
	}
	
	public double pixelsToMeters(double pixels) {
		return pixels / pixelsPerMeter;
	}
	
	public double kphToLogicalPixelsPerUnit(double kph, ChronoUnit chronoUnit) {
		final double METERS_PER_SECOND_TO_KM_PER_HOUR = 3.6;
		double metersPerSecond = kph / METERS_PER_SECOND_TO_KM_PER_HOUR;
		double metersPerChronoUnit = metersPerSecond * chronoUnit.getDuration().dividedBy(ChronoUnit.SECONDS.getDuration());
		return metersToLogicalPixels(metersPerChronoUnit);
	}
}

