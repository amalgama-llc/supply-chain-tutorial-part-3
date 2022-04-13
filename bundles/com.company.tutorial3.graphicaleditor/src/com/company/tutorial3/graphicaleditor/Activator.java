package com.company.tutorial3.graphicaleditor;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private static Injector injector;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		injector = Guice.createInjector(Modules.override(new GraphEditorModule()).with(new GraphEditorUiModule()));
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}
	
	public static Injector GetInjector() {
		return injector;
	}

}


