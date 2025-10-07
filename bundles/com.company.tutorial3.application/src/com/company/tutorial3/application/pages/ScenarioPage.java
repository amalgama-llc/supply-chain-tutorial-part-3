package com.company.tutorial3.application.pages;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.FeaturePath;

import com.amalgamasimulation.desktop.binding.UpdateValueStrategyFactory;
import com.company.tutorial3.application.localization.Messages;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Scenario;

public class ScenarioPage extends AbstractPage<Scenario> {
	
	@SuppressWarnings("all")
	private IObservableList<Node> nodeListObservable = EMFProperties.list(DatamodelPackage.Literals.SCENARIO__NODES)
			.observeDetail(observable);
	
	public ScenarioPage(Messages messages) {
		super(messages, null);
	}

	@Override
	public boolean isVisible(Object selectedObject) {
		return selectedObject instanceof Scenario;
	}
	
	@Override
	protected String getNameClassObject() {
		return messages.object_scenario;
	}
	
	@Override
	protected String getObjectDisplayName() {
		return observable.getValue().getName();
	}
	
	@Override
	protected FeaturePath[] getUpdateListeners() {
		return new FeaturePath [] {FeaturePath.fromList(DatamodelPackage.Literals.SCENARIO__NAME)};
	}

	@Override
	protected void createControlsInternal() {
		addStringSection(messages.obj_SCENARIO_col_NAME, DatamodelPackage.Literals.SCENARIO__NAME)
			.addTextbox(UpdateValueStrategyFactory.stringIsNotEmpty());
		addDateTimeSection(messages.obj_SCENARIO_col_BEGIN_DATE, DatamodelPackage.Literals.SCENARIO__BEGIN_DATE)
			.addTextbox(UpdateValueStrategyFactory.localDateTime())
			.addLocalDateTimeEditorButton()
			.setEnabled(true);
		addDateTimeSection(messages.obj_SCENARIO_col_END_DATE, DatamodelPackage.Literals.SCENARIO__END_DATE)
			.addTextbox(UpdateValueStrategyFactory.localDateTime())
			.addLocalDateTimeEditorButton()
			.setEnabled(true);
		addNumericSection("Max delivery time, hrs", DatamodelPackage.Literals.SCENARIO__MAX_DELIVERY_TIME_HRS)
			.addTextbox(UpdateValueStrategyFactory.doublePositive());
		addDistributionSection("Interval between requests, hrs", DatamodelPackage.Literals.SCENARIO__INTERVAL_BETWEEN_REQUESTS_HRS)
			.addTextbox(UpdateValueStrategyFactory.distribution())
			.addDialogButton("...", DatamodelPackage.Literals.SCENARIO__INTERVAL_BETWEEN_REQUESTS_HRS)
			.setEnabled(false);
		
		addReferenceSection("Truck initial node", DatamodelPackage.Literals.SCENARIO__TRUCK_SITE)
				.addAutoCompleteTextbox(DatamodelPackage.Literals.NODE__NAME, nodeListObservable)
				.addSelectionDialogButton("a node", nodeListObservable, tableView -> {
					tableView.column(Node::getName).name("Name").width(150);
				}).addClearButton().setTextFieldCanBeEmpty(false);
	}
}
