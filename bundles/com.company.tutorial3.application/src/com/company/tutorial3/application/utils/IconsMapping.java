package com.company.tutorial3.application.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Images cache
 *
 */
public class IconsMapping {

	private static Map<String, Image> images = new HashMap<>();

	// predefined commonly used images
	public static final Image ADD = getImage("/icons/add.png");
	public static final Image REMOVE = getImage("/icons/remove.png");
	public static final Image COPY = getImage("/icons/copy.png");
	public static final Image UP = IconsMapping.getImage("/icons/up.png");
	public static final Image DOWN = IconsMapping.getImage("/icons/down.png");

	public static final Image ZOOM_IN = getImage("/icons/zoomIn.png");
	public static final Image ZOOM_OUT = getImage("/icons/zoomOut.png");
	public static final Image CENTERING = getImage("/icons/centering.png");
	public static final Image MAP = getImage("/icons/map.png");
	public static final Image REFRESH = getImage("/icons/refresh.png");

	private IconsMapping() {
	}

	public static Image getImage(String path) {
		return images.computeIfAbsent(path, p -> new Image(Display.getCurrent(), FileUtils.getInputStream(p)));
	}

}

