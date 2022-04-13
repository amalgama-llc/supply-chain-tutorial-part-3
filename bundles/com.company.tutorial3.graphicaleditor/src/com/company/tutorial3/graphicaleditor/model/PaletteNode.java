package com.company.tutorial3.graphicaleditor.model;

import com.company.tutorial3.datamodel.Scenario;
import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel.Type;

public class PaletteNode {
	
    public enum PaletteNodeType{
    	//ASSET,
    	ARC		(Type.ARC),
    	NODE		(Type.NODE);
    	
    	private Type type;
    	
    	private PaletteNodeType(Type type) {
    		this.type = type;
    	}
    	
    	public Type getType() {
    		return type;
    	}
    };

    private PaletteNodeType paletteNodeType;
    private Scenario scenario;
    
	public PaletteNode(PaletteNodeType paletteNodeType, Scenario scenario) {
		super();
		this.paletteNodeType = paletteNodeType;
		this.scenario = scenario;
	}

	public PaletteNodeType getPaletteNodeType() {
		return paletteNodeType;
	}

	public Scenario getScenario() {
		return scenario;
	}
	
}


