package com.company.tutorial3.application.pages;

import org.eclipse.emf.databinding.FeaturePath;

import com.amalgamasimulation.desktop.binding.UpdateValueStrategyFactory;
import com.company.tutorial3.application.localization.Messages;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.TruckType;

public class TruckPage extends AbstractPage<TruckType> {

	public TruckPage(Messages messages) {
		super(messages, TruckType::getScenario);
	}

	@Override
	public boolean isVisible(Object selectedObject) {
		return selectedObject instanceof TruckType;
	}

	@Override
	protected String getNameClassObject() {
		return "Truck";
	}

	@Override
	protected String getObjectDisplayName() {
		return observable.getValue().getName();
	}

	@Override
	protected final FeaturePath[] getUpdateListeners() {
		return new FeaturePath[] {
				FeaturePath.fromList(DatamodelPackage.Literals.TRUCK_TYPE__NAME) };
	}

	@Override
	protected void createControlsInternal() {
		addStringSection("Name", DatamodelPackage.Literals.TRUCK_TYPE__NAME)
				.addTextbox(UpdateValueStrategyFactory.stringIsNotEmpty());
		addNumericSection("Speed", DatamodelPackage.Literals.TRUCK_TYPE__SPEED)
				.addTextbox(UpdateValueStrategyFactory.doublePositive());
		addNumericSection("Quantity", DatamodelPackage.Literals.TRUCK_TYPE__QUANTITY)
				.addTextbox(UpdateValueStrategyFactory.integerPositiveWithZero());
		addNumericSection("Ownership cost per hour", DatamodelPackage.Literals.TRUCK_TYPE__OWNERSHIP_COST_PER_HOUR)
				.addTextbox(UpdateValueStrategyFactory.doublePositiveWithZero());
		addNumericSection("Usage cost per hour", DatamodelPackage.Literals.TRUCK_TYPE__USAGE_COST_PER_HOUR)
				.addTextbox(UpdateValueStrategyFactory.doublePositiveWithZero());
	}
}