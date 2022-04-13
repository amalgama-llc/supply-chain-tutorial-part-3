package com.company.tutorial3.application.parts.editor.treeelements;

import com.company.tutorial3.application.parts.editor.TreePart;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.datamodel.Scenario;

public class TreeElementArc extends TreeElement {
	private Scenario scenario;
	
	public TreeElementArc(Scenario scenario, Messages messages) {
		super(TreePart.TreeElementType.ARC, scenario, messages);
		this.scenario = scenario;
	}
	
	@Override
	public String getName() {
		return messages.obj_ARCS + " (" + scenario.getArcs().size() + ")";
	}

}


