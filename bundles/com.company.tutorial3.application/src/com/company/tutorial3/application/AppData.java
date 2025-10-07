package com.company.tutorial3.application;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.services.nls.Translation;

import com.company.tutorial3.application.localization.Messages;
import com.company.tutorial3.datamodel.Scenario;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
@Creatable
public class AppData {
	private Scenario scenario;
	private String filePath;

	@Inject
	@Translation
	public static Messages messages;
	
	public Scenario getScenario() {
		return scenario;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

}

