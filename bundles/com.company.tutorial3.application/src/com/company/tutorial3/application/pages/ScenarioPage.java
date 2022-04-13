package com.company.tutorial3.application.pages;

import org.eclipse.emf.databinding.FeaturePath;

import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Scenario;
import com.amalgamasimulation.desktop.binding.UpdateValueStrategyFactory;

public class ScenarioPage extends AbstractPage<Scenario>{
	
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
		addDistributionSection("Interval between requests, hrs", DatamodelPackage.Literals.SCENARIO__INTERVAL_BETWEEN_REQUESTS)
		    .addTextbox(UpdateValueStrategyFactory.distribution())
		    .addDialogButton("...", DatamodelPackage.Literals.SCENARIO__INTERVAL_BETWEEN_REQUESTS)
		    .setEnabled(false);
	}
}



