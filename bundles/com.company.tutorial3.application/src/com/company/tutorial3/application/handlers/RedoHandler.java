package com.company.tutorial3.application.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import com.amalgamasimulation.emf.commands.CommandsManager;
import com.company.tutorial3.application.states.AppState;

import jakarta.inject.Inject;

public class RedoHandler {
	
	@Inject
	private AppState appState;
	
	@CanExecute
	private boolean canExecute() {
		return appState.isEditor() && appState.isScenarioExist() && CommandsManager.getEditingDomain().getCommandStack().canRedo();
	}
	
	@Execute
	public void execute() {
		CommandsManager.getEditingDomain().getCommandStack().redo();
	}
}

