package com.company.tutorial3.application.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import com.company.tutorial3.application.AppInfo;
import com.company.tutorial3.application.dialogs.AboutDialog;

public class AboutHandler {

	@CanExecute
	private boolean canExecute() {
		return true;
	}

	@Execute
	public void execute(Shell shell) {
		new AboutDialog(shell, new AppInfo()).open();
	}
}

