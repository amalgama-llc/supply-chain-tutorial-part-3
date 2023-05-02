package com.company.tutorial3.application.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.swt.SWT;

import com.company.tutorial3.application.states.AppState;
import com.company.tutorial3.application.utils.MessageBoxFactory;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.common.states.AppData;

import org.eclipse.swt.widgets.Shell;

public class ExitHandler {

    @Inject
    private AppData appData;

    @Inject
    @Translation
    private Messages messages;

    @Inject
    private AppState appState;
    
    @Execute
	public void execute(IWorkbench workbench, Shell shell) {
		// ask if user wants to close the app
		if (SWT.OK != MessageBoxFactory.createMessageBox(shell,
				SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL | SWT.APPLICATION_MODAL, "",
				messages.message_create_new_scenario)) {
			return;
		}
		// ask if user wants the (changed) scenario to be saved
		if (appState.ensureCurrentScenarioIsSaved(shell, appData)) {
			workbench.close();
		}
	}
}

