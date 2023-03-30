package com.company.tutorial3.application.parts.editor.treeelements;

import java.util.List;

import com.company.tutorial3.application.utils.TreeElementType;
import com.company.tutorial3.datamodel.Scenario;

public class TreeElementAssets extends TreeElement {
	private Scenario scenario;
	
	public TreeElementAssets(Scenario scenario) {
		super(TreeElementType.ASSET, scenario);
		this.scenario = scenario;
	}
	
	@Override
	protected List<TreeElement> createChildElements() {
		return List.of(
				createLeaf(TreeElementType.WAREHOUSE, () -> scenario.getWarehouses().size()),
				createLeaf(TreeElementType.STORE, () -> scenario.getStores().size())
				);
	}
}
