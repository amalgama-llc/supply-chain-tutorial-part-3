package com.company.tutorial3.application.parts.editor.treeelements;

import java.util.List;

import com.company.tutorial3.application.utils.TreeElementType;
import com.company.tutorial3.datamodel.Scenario;


public class TreeElementNetwork extends TreeElement {
	private Scenario scenario;
	
	public TreeElementNetwork(Scenario scenario) {
		super(TreeElementType.NETWORK, scenario);
		this.scenario = scenario;
	}
	
	@Override
	protected List<TreeElement> createChildElements() {
		return List.of(
				createLeaf(TreeElementType.NODE, () -> scenario.getNodes().size()),
				createLeaf(TreeElementType.ARC, () -> scenario.getArcs().size())
				);
	}
}

