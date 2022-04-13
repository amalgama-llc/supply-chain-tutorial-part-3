package com.company.tutorial3.application.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.dialogs.MessageDialog;

import com.company.tutorial3.common.localization.Messages;


public class ChangeLanguageManager {
	
	private Messages messages;
	
	public ChangeLanguageManager(Messages messages) {
		this.messages = messages;
	}

	public void setLanguage(IWorkbench application, String language) {
		try {
			File f = new File(System.getProperty("user.dir") + File.separator + System.getProperty("eclipse.launcher.name") + ".ini");			
			if (!f.exists()){
				f.createNewFile();
			}
			String buffer = "";
			BufferedReader reader = new BufferedReader(new FileReader(f.getAbsolutePath()));
			boolean containsNls = false;
			while (reader.ready()) {
				String string = reader.readLine();
				if (string.startsWith("-nl")) {
					buffer = buffer.concat(string + System.lineSeparator() + language + System.lineSeparator());
					reader.readLine();
					containsNls = true;
				} else {
					buffer = buffer.concat(string + System.lineSeparator());
				}
			}
			reader.close();
			if (!containsNls) {
				buffer = ("-nl" + System.lineSeparator() + language + System.lineSeparator()).concat(buffer);
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(f.getAbsolutePath()));
			writer.write(buffer);
			writer.close();
			if (MessageDialog.openConfirm(null, messages.label_set_language, messages.label_language_set_to + " '" + language + "'. " + messages.label_restart + "?")) {
				application.restart();
			}
		} catch (Exception e) {
			throw new RuntimeException(messages.label_unable_to_save_language_file + e.getLocalizedMessage());
		}
	}

}

