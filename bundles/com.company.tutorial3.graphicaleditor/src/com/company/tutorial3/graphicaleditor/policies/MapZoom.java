package com.company.tutorial3.graphicaleditor.policies;

public class MapZoom {
	
	private static final double[] zoomsMain =                     new double[] {8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
	private static final double[] zoomsHighlightableImageVisual = new double[] {0.5, 0.5, 1.0, 2.0, 4.0, 8.0, 20.0, 30.0, 40.0, 40.0, 40.0, 40.0, 40.0};
	private static final double[] zoomsLocationVisual =           new double[] {1.0, 1.0, 1.0, 2.0, 4.0, 8.0, 20.0, 30.0, 40.0, 40.0, 40.0, 40.0, 40.0};
	
	
	
	private static final int maxCount = zoomsMain.length - 1;
	private static final int middleCount = 3;
	private static final int minCount = 0;
	
	private static int currentCountZoom = MapZoom.middleCount;
	
	public static boolean addCountZoom() {
		currentCountZoom++;
		return checkCountZoom();
	}
	
	public static boolean minuseCountZoom() {
		currentCountZoom--;
		return checkCountZoom();
	}
	
	private static boolean checkCountZoom() {
		if(currentCountZoom > maxCount) {
			currentCountZoom = maxCount;
			return false;
		}
		if(currentCountZoom < minCount) {
			currentCountZoom = minCount;
			return false;
		}
		return true;
	}
	
	public static double getMainZoom() {
		return zoomsMain[currentCountZoom];
	}
	
	public static double getHighlightableImageVisualZoom() {
		return zoomsHighlightableImageVisual[currentCountZoom];
	}
	
	public static double getLocationVisualZoom() {
		return zoomsLocationVisual[currentCountZoom];
	}



}


