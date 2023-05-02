package com.company.tutorial3.application.utils;
import java.util.Objects;

import org.eclipse.e4.core.contexts.IEclipseContext;

public class CurrentLocale {
	
	private static final String LOCALE_KEY = "org.eclipse.e4.core.locale";
	private static final String DEFAULT_LOCALE = "en";
	
	private CurrentLocale() {}

	public static String getCurrentLocale(IEclipseContext context) {
		Object localeInContext = context.get(LOCALE_KEY);
		return Objects.requireNonNullElse(localeInContext, DEFAULT_LOCALE).toString();
	}
}

