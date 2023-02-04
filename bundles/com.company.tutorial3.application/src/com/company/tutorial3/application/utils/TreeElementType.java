package com.company.tutorial3.application.utils;

import com.company.tutorial3.common.states.AppData;

public enum TreeElementType {
		NETWORK("/icons/transportation_net.png", AppData.messages.network),
		ARC("/icons/link.png", AppData.messages.obj_ARCS),
		NODE("/icons/location.png", AppData.messages.obj_NODES),
	SCENARIO("/icons/producte_structure.png", AppData.messages.obj_SCENARIO);
	
	private final String iconPath;
	private final String label;
	
	private TreeElementType(String iconPath, String label) {
		this.iconPath = iconPath;
		this.label = label;
	}

	public String getIconPath() {
		return iconPath;
	}

	public String getLabel() {
		return label;
	}			
	
}
	

