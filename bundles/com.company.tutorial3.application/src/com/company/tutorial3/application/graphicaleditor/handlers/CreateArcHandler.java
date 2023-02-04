package com.company.tutorial3.application.graphicaleditor.handlers;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.amalgamasimulation.emf.commands.AddCommand;
import com.amalgamasimulation.graphicaleditor.factories.ContentPartFactory;
import com.amalgamasimulation.graphicaleditor.handlers.CreateArcClickHandler;
import com.company.tutorial3.application.states.AppState;
import com.company.tutorial3.common.command.UniqNamesManager;
import com.company.tutorial3.datamodel.DatamodelPackage;

public class CreateArcHandler extends CreateArcClickHandler{

	
	@Override
	public EObject executeCreateObject(ContentPartFactory contentPartFactory, EClass arcClass, EObject container,
			EObject sourceNode, EObject destNode) {
		AddCommand<? extends EObject> command = createArcCommand(contentPartFactory, arcClass, container, sourceNode, destNode);
		command.setActionBefore(() -> {
				command.getObject().eSet(DatamodelPackage.Literals.ARC__ID, UniqNamesManager.getInstance().generateUniqueId(container, command.getObject(), AppState.messages.obj_ARC));
		});
		command.executeInStack();
		return command.getObject();
	}
	
}

