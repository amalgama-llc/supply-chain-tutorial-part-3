package com.company.tutorial3.application.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import com.company.tutorial3.application.AppInfo;
import com.company.tutorial3.application.utils.MessageBoxFactory;
import com.company.tutorial3.common.localization.Messages;

public class AboutHandler {

	@Inject
	@Translation
	private Messages messages;

	@CanExecute
	private boolean canExecute() {
		return true;
	}

	@Execute
	public void execute(Shell shell) {
		MessageBoxFactory.createMessageBox(shell, SWT.ICON_INFORMATION | SWT.OK | SWT.APPLICATION_MODAL, "Info",
				String.format(messages.message_about, AppInfo.getProductID(), AppInfo.getVersionAsString()));
	}
}

