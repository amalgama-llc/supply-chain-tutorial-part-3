package com.company.tutorial3.application.pages;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.databinding.EMFProperties;

import com.amalgamasimulation.desktop.binding.ValidationStrategies;
import com.amalgamasimulation.desktop.ui.editor.Buttons;
import com.amalgamasimulation.desktop.ui.editor.EmfPage;
import com.company.tutorial3.application.localization.Messages;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Scenario;

public class ScenarioPage extends EmfPage<Scenario> {
	
	@SuppressWarnings("all")
	private IObservableList<Node> nodeListObservable = EMFProperties.list(DatamodelPackage.Literals.SCENARIO__NODES)
			.observeDetail(observable);
	
	public ScenarioPage() {
		super(Scenario.class);
	}
	
	@Override
	protected String getTab() {
		return Messages.messages().tab_general;
	}
	
	@Override
	protected void createContentsInternal() {
		emfStringEditor()
			.feature(DatamodelPackage.Literals.SCENARIO__NAME)
			.label(Messages.messages().obj_SCENARIO_col_NAME)
			.validationStrategy(ValidationStrategies.stringIsNotEmpty())
			.create();
		emfLocalDateTimeEditor()
			.feature(DatamodelPackage.Literals.SCENARIO__BEGIN_DATE)
			.label(Messages.messages().obj_SCENARIO_col_BEGIN_DATE)
			.addButton(Buttons.localDateTime())
			.create();
		emfLocalDateTimeEditor()
			.feature(DatamodelPackage.Literals.SCENARIO__END_DATE)
			.label(Messages.messages().obj_SCENARIO_col_END_DATE)
			.addButton(Buttons.localDateTime())
			.create();
		
		emfDoubleEditor()
			.feature(DatamodelPackage.Literals.SCENARIO__MAX_DELIVERY_TIME_HRS)
			.label("Max delivery time, hrs")
			.validationStrategy(ValidationStrategies.doublePositive())
			.create();
		
		emfDistributionEditor()
			.feature(DatamodelPackage.Literals.SCENARIO__INTERVAL_BETWEEN_REQUESTS_HRS)
			.label("Interval between requests, hrs")
			.addButton(Buttons.distribution())
			.create();
		
		emfComboboxEditor(Node.class)
			.feature(DatamodelPackage.Literals.SCENARIO__TRUCK_SITE)
			.label("Truck initial node")
			.elements(nodeListObservable)
			.format(DatamodelPackage.Literals.NODE__NAME)
			.selectFromTableButton(table -> {
				table.column(node -> node.getName()).name(Messages.messages().obj_NODE_col_NAME).width(150);
			})
			.addButton(Buttons.showProperty())
			.create();
	}

}
