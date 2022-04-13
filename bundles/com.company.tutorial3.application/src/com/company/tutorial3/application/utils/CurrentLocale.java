package com.company.tutorial3.application.utils;

import org.eclipse.e4.core.contexts.IEclipseContext;

public class CurrentLocale {

	public static String getCurrentLocale(IEclipseContext context) {
		String locale = "ru";
		if (context.getLocal("org.eclipse.e4.core.locale") != null) {
			locale = context.getLocal("org.eclipse.e4.core.locale").toString();
		}
		return locale;
	}
}



