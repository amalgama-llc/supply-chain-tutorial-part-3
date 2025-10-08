package com.company.tutorial3.application.utils;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MessageBoxFactory {
	private MessageBoxFactory() {}

	public static int createMessageBox(Shell shell, int style, String title, String message) {
		MessageBox dialog =  new MessageBox(shell, style);
		dialog.setText(title);
		dialog.setMessage(message);
		return dialog.open();
	}
	
}

