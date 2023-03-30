package com.company.tutorial3.application.pages;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.FeaturePath;

import com.amalgamasimulation.desktop.binding.UpdateValueStrategyFactory;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Store;

public class StorePage extends AbstractPage<Store> {
	
	@SuppressWarnings("all")
	private IObservableList<Node> nodeListObservable = EMFProperties.list(DatamodelPackage.Literals.SCENARIO__NODES).observeDetail(scenarioObservable);
	
	public StorePage(Messages messages) {
		super(messages, Store::getScenario);
	}

	@Override
	public boolean isVisible(Object selectedObject) {
		return selectedObject instanceof Store;
	}
	
	@Override
	protected String getNameClassObject() {
		return "Store";
	}
	
	@Override
	protected String getObjectDisplayName() {
		return observable.getValue().getId() + " - " + observable.getValue().getName();
	}
	
	@Override
	protected final FeaturePath[] getUpdateListeners() {
		return new FeaturePath [] {
				FeaturePath.fromList(DatamodelPackage.Literals.ASSET__ID),
				FeaturePath.fromList(DatamodelPackage.Literals.ASSET__NAME)};
	}
	
	@Override
	protected void createControlsInternal() {
		addStringSection("ID", DatamodelPackage.Literals.ASSET__ID)
			.addTextbox(UpdateValueStrategyFactory.stringIsNotEmpty());
		addStringSection("Name", DatamodelPackage.Literals.ASSET__NAME)
			.addTextbox(UpdateValueStrategyFactory.stringIsNotEmpty());
		addReferenceSection("Node", DatamodelPackage.Literals.ASSET__NODE)
			.addAutoCompleteTextbox(DatamodelPackage.Literals.NODE__NAME, nodeListObservable)
			.addSelectionDialogButton("a node", nodeListObservable, tableView -> {
				tableView.addColumn("Name", 150, Node::getName);	
			})
			.addClearButton()
			.setTextFieldCanBeEmpty(false);
	}
}
