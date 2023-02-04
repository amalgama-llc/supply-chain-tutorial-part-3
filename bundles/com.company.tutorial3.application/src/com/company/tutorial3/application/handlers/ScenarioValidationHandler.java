package com.company.tutorial3.application.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import com.amalgamasimulation.utils.format.Formats;
import com.amalgamasimulation.desktop.utils.MessageManager;
import com.company.tutorial3.application.states.AppState;
import com.company.tutorial3.application.utils.MessageBoxFactory;
import com.company.tutorial3.application.utils.validation.ValidationManager;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.common.states.AppData;

public class ScenarioValidationHandler {

	@Inject
	@Translation
	private Messages messages;

	@Inject
	private MessageManager messageManager;

	@Inject
	private AppData appData;

	@Inject
	private AppState appState;

	@CanExecute
	private boolean canExecute() {
		return appState.isEditor() && appState.isScenarioExist();
	}

	@Execute
	public void execute(EPartService fPartService, Shell shell) {
		if (appData.getScenario() == null) {
			return;
		}
		ValidationManager validationManager = new ValidationManager(messages);
		validationManager.validate(messageManager, appData.getScenario(), fPartService);
		if (!validationManager.isErrorExist()) {
			MessageBoxFactory.createMessageBox(shell, SWT.ICON_INFORMATION | SWT.OK | SWT.APPLICATION_MODAL, messages.title_check_data,
					String.format(messages.message_check_data_ok,
							Formats.getDefaultFormats().dayMonthLongYearHoursMinutes(appData.getScenario().getBeginDate()),
							Formats.getDefaultFormats().dayMonthLongYearHoursMinutes(appData.getScenario().getEndDate())));
		} else {
			MessageBoxFactory.createMessageBox(shell, SWT.ICON_ERROR | SWT.OK | SWT.APPLICATION_MODAL, messages.title_check_data,
					messages.message_check_data_error);
		}
	}

}

