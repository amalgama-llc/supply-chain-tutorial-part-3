package com.company.tutorial3.application.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Evaluate;

import com.company.tutorial3.application.states.AppState;


public class RecentFilesListMenuItemIsVisibleExpression {
	
	@Inject
	private AppState appState;
	
	@Evaluate
	public boolean evaluate() {
		return appState.isEditor() && appState.recentlyOpenedFilesManager.getLastFilePaths().length > 0;
	}
}

