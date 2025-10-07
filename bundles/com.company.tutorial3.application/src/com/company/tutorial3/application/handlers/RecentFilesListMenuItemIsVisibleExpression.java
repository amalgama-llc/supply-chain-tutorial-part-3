package com.company.tutorial3.application.handlers;

import org.eclipse.e4.core.di.annotations.Evaluate;

import com.company.tutorial3.application.states.AppState;

import jakarta.inject.Inject;


public class RecentFilesListMenuItemIsVisibleExpression {
	
	@Inject
	private AppState appState;
	
	@Evaluate
	public boolean evaluate() {
		return appState.isEditor() && appState.getRecentlyOpenedFilesManager().getLastFilePaths().length > 0;
	}
}

