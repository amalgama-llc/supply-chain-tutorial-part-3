package com.company.tutorial3.application.pages;

import org.eclipse.emf.databinding.FeaturePath;

import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;
import com.amalgamasimulation.desktop.binding.UpdateValueStrategyFactory;


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
		return observable.getValue().getId() + " - " + observable.getValue().getName();
	}
	
	@Override
	protected final FeaturePath[] getUpdateListeners() {
		return new FeaturePath [] {
				FeaturePath.fromList(DatamodelPackage.Literals.NODE__ID),
				FeaturePath.fromList(DatamodelPackage.Literals.NODE__NAME)};
	}
	
	@Override
	protected void createControlsInternal() {
		addStringSection(messages.obj_NODE_col_ID, DatamodelPackage.Literals.NODE__ID)
			.addIdTextbox(scenarioObservable, DatamodelPackage.Literals.SCENARIO__NODES);
		addStringSection(messages.obj_NODE_col_NAME, DatamodelPackage.Literals.NODE__NAME)
			.addTextbox(UpdateValueStrategyFactory.stringIsNotEmpty());
		addNumericSection(messages.obj_NODE_col_LATITUDE, DatamodelPackage.Literals.NODE__LATITUDE)
			.addTextbox(UpdateValueStrategyFactory.doubleBetweenValues(-90, 90, 10, true));
		addNumericSection(messages.obj_NODE_col_LONGITUDE, DatamodelPackage.Literals.NODE__LONGITUDE)
			.addTextbox(UpdateValueStrategyFactory.doubleBetweenValues(-180, 180, 10, true));
	}
}


