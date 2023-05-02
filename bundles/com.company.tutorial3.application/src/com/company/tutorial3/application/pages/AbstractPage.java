package com.company.tutorial3.application.pages;

import java.util.function.Function;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.FeaturePath;
import org.eclipse.emf.ecore.EObject;


import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.datamodel.Scenario;
import com.amalgamasimulation.desktop.properties.PropertyPage;
import com.amalgamasimulation.desktop.properties.sections.PropertySection.ControlsMarginRule;

public abstract class AbstractPage<T extends EObject> extends PropertyPage<T>{

	protected Messages messages;
	protected IObservableValue<Scenario> scenarioObservable = new WritableValue<>();
	private Function<T, Scenario> scenarioExtractor;
	
	private static final int EDITOR_FIXED_WIDTH = 200;
	
	private IValueChangeListener<T> labelListener = l -> { 
			if (observable.getValue() != null) {
				refreshLabelTitle(); 
			}
		};

	protected AbstractPage(Messages messages, Function<T, Scenario> scenarioExtractor) {
		super(EDITOR_FIXED_WIDTH, ControlsMarginRule.DYNAMIC);
		this.messages = messages;
		this.scenarioExtractor = scenarioExtractor;
		addUpdateListeners(getUpdateListeners());
	}
	
	@SuppressWarnings("unchecked")
	private void addUpdateListeners(FeaturePath... featurePaths) {
		if (featurePaths == null)
			return;
		for (FeaturePath path: featurePaths) {
			IObservableValue<T> observableLabel = EMFProperties.value(path).observeDetail(observable);
			observableLabel.addValueChangeListener(labelListener);
		}
	}
	
	@Override
	protected final String getLabelTitle() {
		if (observable.getValue() == null) {
			return "";
		}
		// property part header contains object name and object type
		// if object name is empty, only object type is displayed
		String displayName = getObjectDisplayName();
		if (displayName == null || displayName.isEmpty()) {
			return getNameClassObject();
		}
		return displayName + " (" + getNameClassObject() + ")";
	}
	
	/**
	 * Object name, as displayed in the object properties page. Should not include object type.
	 * Can be null or empty string.
	 */
	protected String getObjectDisplayName() {
		return "";
	}
	
	/**
	 * Feature list which object name depends on (see {@link #getObjectDisplayName})
	 */
	protected FeaturePath[] getUpdateListeners() {
		return new FeaturePath[0];	// empty array instead of null
	}
	
	/**
	 * Returns the localized object class name for the object properties page heading.
	 */
	protected abstract String getNameClassObject();
	
	@Override
	public void setSelection(Object selectedObject) {
		if (isVisible(selectedObject)) {
			T wc = (T) selectedObject;
			if (scenarioExtractor != null) {
				Scenario scenario = scenarioExtractor.apply(wc);
				if (!scenario.equals(scenarioObservable.getValue())) {
					scenarioObservable.setValue(scenario);
				}
			}
			observable.setValue(wc);
		} else {
			observable.setValue(null);
		}
	}	
}

