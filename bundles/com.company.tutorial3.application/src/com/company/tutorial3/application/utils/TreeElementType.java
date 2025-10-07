package com.company.tutorial3.application.utils;

import com.company.tutorial3.application.localization.Messages;

public enum TreeElementType {
		NETWORK("/icons/transportation_net.png", Messages.messages().network),
		ARC("/icons/link.png", Messages.messages().obj_ARCS),
		NODE("/icons/location.png", Messages.messages().obj_NODES),
		SCENARIO("/icons/producte_structure.png", Messages.messages().obj_SCENARIO),
		ASSET("/icons/object.png", "Assets"),
		WAREHOUSE("/icons/object.png", "Warehouses"),
		STORE("/icons/object.png", "Stores"),
		TRUCK_TYPE("/icons/object.png", "Trucks"),
	;
	
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
	

