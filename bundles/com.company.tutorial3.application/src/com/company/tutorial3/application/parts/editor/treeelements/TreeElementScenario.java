package com.company.tutorial3.application.parts.editor.treeelements;

import java.util.List;

import com.company.tutorial3.application.parts.editor.TreePart;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.datamodel.Scenario;


public class TreeElementScenario extends TreeElement {
	private Scenario scenario;

	public TreeElementScenario(Scenario scenario, Messages messages) {
		super(TreePart.TreeElementType.SCENARIO, scenario, messages);
		this.scenario = scenario;
	}

	@Override
	public String getName() {
		return messages.obj_SCENARIO;
	}

	@Override
	protected List<TreeElement> createChildElements() {
		return List.of(new TreeElementNetwork(scenario, messages));
	}

}



