package com.company.tutorial3.simulation;

import java.time.temporal.ChronoUnit;

import org.eclipse.nebula.widgets.geomap.GeoMapUtil;
import org.eclipse.nebula.widgets.geomap.PointD;

import com.amalgamasimulation.animation.swt.TiledMapCoordinatesConverter;
import com.amalgamasimulation.geometry.Point;

public class CoordinatesConverter {
	private static final int BASE_MAP_ZOOM = 14;
	private static final org.eclipse.swt.graphics.Point MAP_POSITION = GeoMapUtil.computePosition(new PointD(-14.0, 26.0), BASE_MAP_ZOOM);
	private static final TiledMapCoordinatesConverter MAP_COORDINATES_CONVERTER = new TiledMapCoordinatesConverter(new Point(MAP_POSITION.x, MAP_POSITION.y), BASE_MAP_ZOOM);
	
	private double pixelsPerMeter;
	
	public static TiledMapCoordinatesConverter getMapCoordinatesConverter() {
		return MAP_COORDINATES_CONVERTER;
	}
	
	public Point lonLatToLogicalPoint(double longitude, double latitude) {
		return MAP_COORDINATES_CONVERTER.lonLatToLogicalPoint(new Point(longitude, latitude));
	}
	
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

