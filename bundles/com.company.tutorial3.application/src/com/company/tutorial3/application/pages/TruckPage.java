package com.company.tutorial3.application.pages;

import com.amalgamasimulation.desktop.binding.ValidationStrategies;
import com.amalgamasimulation.desktop.ui.editor.EmfPage;
import com.company.tutorial3.application.localization.Messages;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.TruckType;

public class TruckPage extends EmfPage<TruckType> {

	public TruckPage() {
		super(TruckType.class);
	}
	
	@Override
	protected String getTab() {
		return Messages.messages().tab_general;
	}
	
	@Override
	protected void createContentsInternal() {
		emfStringEditor()
			.feature(DatamodelPackage.Literals.TRUCK_TYPE__NAME)
			.label("Name")
			.validationStrategy(ValidationStrategies.stringIsNotEmpty())
			.create();
		emfDoubleEditor()
			.feature(DatamodelPackage.Literals.TRUCK_TYPE__SPEED)
			.label("Speed")
			.validationStrategy(ValidationStrategies.doublePositive())
			.create();
		emfIntegerEditor()
			.feature(DatamodelPackage.Literals.TRUCK_TYPE__QUANTITY)
			.label("Quantity")
			.validationStrategy(ValidationStrategies.integerPositiveWithZero())
			.create();
		emfDoubleEditor()
			.feature(DatamodelPackage.Literals.TRUCK_TYPE__OWNERSHIP_COST_PER_HOUR)
			.label("Ownership cost per hour")
			.validationStrategy(ValidationStrategies.doublePositiveWithZero())
			.create();
		emfDoubleEditor()
			.feature(DatamodelPackage.Literals.TRUCK_TYPE__USAGE_COST_PER_HOUR)
			.label("Usage cost per hour")
			.validationStrategy(ValidationStrategies.doublePositiveWithZero())
			.create();
	}

}