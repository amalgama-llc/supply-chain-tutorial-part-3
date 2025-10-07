package com.company.tutorial3.application.pages;

import org.eclipse.emf.databinding.FeaturePath;

import com.amalgamasimulation.desktop.binding.UpdateValueStrategyFactory;
import com.company.tutorial3.application.localization.Messages;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;

public class NodePage extends AbstractPage<Node> {

	public NodePage(Messages messages) {
		super(messages, Node::getScenario);
	}

	@Override
	public boolean isVisible(Object selectedObject) {
		return selectedObject instanceof Node;
	}
	
	@Override
	protected String getNameClassObject() {
		return messages.obj_NODE;
	}
	
	@Override
	protected String getObjectDisplayName() {
		return observable.getValue().getName();
	}
	
	@Override
	protected final FeaturePath[] getUpdateListeners() {
		return new FeaturePath [] {
				FeaturePath.fromList(DatamodelPackage.Literals.NODE__NAME)};
	}
	
	@Override
	protected void createControlsInternal() {
		addStringSection(messages.obj_NODE_col_NAME, DatamodelPackage.Literals.NODE__NAME)
			.addIdTextbox(scenarioObservable, DatamodelPackage.Literals.SCENARIO__NODES);
		addNumericSection(messages.obj_NODE_col_X, DatamodelPackage.Literals.NODE__X)
			.addTextbox(UpdateValueStrategyFactory.doubleAny());
		addNumericSection(messages.obj_NODE_col_Y, DatamodelPackage.Literals.NODE__Y)
			.addTextbox(UpdateValueStrategyFactory.doubleAny());
	}
}

