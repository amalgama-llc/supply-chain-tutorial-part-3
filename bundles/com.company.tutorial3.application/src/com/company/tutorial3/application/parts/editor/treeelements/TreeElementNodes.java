package com.company.tutorial3.application.parts.editor.treeelements;

import com.company.tutorial3.application.parts.editor.TreePart;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.datamodel.Scenario;

public class TreeElementNodes extends TreeElement {
	
	private Scenario scenario;
	
	public TreeElementNodes(Scenario scenario, Messages messages) {
		super(TreePart.TreeElementType.NODE, scenario, messages);
		this.scenario = scenario;
	}
	
	@Override
	public String getName() {
		return messages.obj_NODES + " (" + scenario.getNodes().size() + ")";
	}
}


