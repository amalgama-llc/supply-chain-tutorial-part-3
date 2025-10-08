package com.company.tutorial3.application.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import com.company.tutorial3.application.AppData;
import com.company.tutorial3.application.states.AppState;

import jakarta.inject.Inject;

public class SaveHandler {

	@Inject
	private AppData appData;

	@Inject
	private AppState appState;

	@CanExecute
	private boolean canExecute() {
		return appState.isEditor() && appState.isScenarioExist();
	}

	@Execute
	public void execute(Shell shell) {
		appState.saveScenario(appData.getFilePath(), appData);
	}
}

