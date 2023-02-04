package com.company.tutorial3.application.graphicaleditor.parts;

import java.util.Comparator;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.ecore.EObject;
import com.amalgamasimulation.emf.commands.RemoveCommand;
import com.amalgamasimulation.graphicaleditor.parts.AbstractContainerPart;
import com.company.tutorial3.common.command.CommandFactory;
import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Scenario;

public class ScenarioPart extends AbstractContainerPart<Scenario>{

	@Override
	protected Comparator<Object> getChildsContentComparator() {
		return (o1, o2) -> {
			int firstObjectPriority = o1 instanceof Arc ? 0 : 1;
			int secondObjectPriority = o2 instanceof Arc ? 0 : 1;
			return Integer.compare(firstObjectPriority, secondObjectPriority);
		};
	}

	@Override
	protected void removeChildContent(EObject childContent) {
		new RemoveCommand<EObject>(childContent, CommandFactory.CROSS_REFERENCE_ADAPTER).executeInStack();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected IObservableList<Object> getChildContentsObservable(Scenario container) {
		return EMFProperties.multiList(	DatamodelPackage.Literals.SCENARIO__ARCS,
										DatamodelPackage.Literals.SCENARIO__NODES).observeDetail(containerObservable);
	}

}

