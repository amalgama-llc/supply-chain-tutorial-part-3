package com.company.tutorial3.application.parts.editor;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import com.company.tutorial3.application.pages.ArcPage;
import com.company.tutorial3.application.pages.NodePage;
import com.company.tutorial3.application.pages.PointPage;
import com.company.tutorial3.application.pages.ScenarioPage;
import com.company.tutorial3.common.localization.Messages;
import com.amalgamasimulation.desktop.properties.PropertyPart;

public class PropertiesPart extends PropertyPart {

	@Inject
	@Translation
	private Messages messages;
	
	@Override
	protected boolean isPartVisible() {
		return true;
	}

	@Override
	public void setPartVisible(boolean visible) {
	}
	
	@Override
	protected void setLabeledSelectedObject(IObservableValue<EObject> labeledSelectedObject, Object selectedObject) {
	}

	@Override
	protected boolean isAlternativeTitle() {
		return true;
	}

	@Override
	protected EStructuralFeature getLabeledField() {
		return null;
	}

	@Override
	protected void registerPages() {
		registerPage(new ScenarioPage 		(messages),  messages.tab_general);
		registerPage(new NodePage(messages),  							messages.tab_general);
		registerPage(new ArcPage(messages),  							messages.tab_general);
		registerPage(new PointPage(messages),  							messages.tab_bendpoint);
	}
	
	@Override
	protected String getPropertyTypeTitle(Object selectedObject) {
		return "";
	}
	
	@Override
	protected void changePropertyPerspective(Object selectedObject) {
		
	}
	
	@PreDestroy
	public void preDestroy() {
		eventBroker.unsubscribe(propertyPerspectiveChangedEventHandler);
		eventBroker.unsubscribe(propertySelectionChangedEventHandler);
	}
}



