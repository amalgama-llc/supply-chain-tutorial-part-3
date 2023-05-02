package com.company.tutorial3.application.pages;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.FeaturePath;

import com.amalgamasimulation.desktop.binding.UpdateValueStrategyFactory;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Truck;

public class TruckPage extends AbstractPage<Truck> {

	@SuppressWarnings("all")
	private IObservableList<Node> nodeListObservable = EMFProperties.list(DatamodelPackage.Literals.SCENARIO__NODES)
			.observeDetail(scenarioObservable);

	public TruckPage(Messages messages) {
		super(messages, Truck::getScenario);
	}

	@Override
	public boolean isVisible(Object selectedObject) {
		return selectedObject instanceof Truck;
	}

	@Override
	protected String getNameClassObject() {
		return "Truck";
	}

	@Override
	protected String getObjectDisplayName() {
		return observable.getValue().getId() + " - " + observable.getValue().getName();
	}

	@Override
	protected final FeaturePath[] getUpdateListeners() {
		return new FeaturePath[] {
				FeaturePath.fromList(DatamodelPackage.Literals.TRUCK__ID),
				FeaturePath.fromList(DatamodelPackage.Literals.TRUCK__NAME) };
	}

	@Override
	protected void createControlsInternal() {
		addStringSection("ID", DatamodelPackage.Literals.TRUCK__ID)
				.addTextbox(UpdateValueStrategyFactory.stringIsNotEmpty());
		addStringSection("Name", DatamodelPackage.Literals.TRUCK__NAME)
				.addTextbox(UpdateValueStrategyFactory.stringIsNotEmpty());
		addNumericSection("Speed", DatamodelPackage.Literals.TRUCK__SPEED)
				.addTextbox(UpdateValueStrategyFactory.doublePositive());
		addReferenceSection("Initial node", DatamodelPackage.Literals.TRUCK__INITIAL_NODE)
				.addAutoCompleteTextbox(DatamodelPackage.Literals.NODE__NAME, nodeListObservable)
				.addSelectionDialogButton("a node", nodeListObservable, tableView -> {
					tableView.addColumn("Name", 150, Node::getName);
				}).addClearButton().setTextFieldCanBeEmpty(false);
	}
}