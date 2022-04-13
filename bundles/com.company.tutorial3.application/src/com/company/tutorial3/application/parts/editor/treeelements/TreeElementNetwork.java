package com.company.tutorial3.application.parts.editor.treeelements;

import java.util.List;

import com.company.tutorial3.application.parts.editor.TreePart;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.datamodel.Scenario;


public class TreeElementNetwork extends TreeElement {
	private Scenario scenario;
	
	public TreeElementNetwork(Scenario scenario, Messages messages) {
		super(TreePart.TreeElementType.NETWORK, scenario, messages);
		this.scenario = scenario;
	}
	
	@Override
	public String getName() {
		return messages.network;
	}

	@Override
	protected List<TreeElement> createChildElements() {
		return List.of(new TreeElementNodes(scenario, messages), new TreeElementArc(scenario, messages));
	}
}


