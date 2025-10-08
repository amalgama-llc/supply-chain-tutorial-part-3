package com.company.tutorial3.application.states;

import org.eclipse.e4.core.di.annotations.Creatable;

import com.amalgamasimulation.engine.Engine;
import com.amalgamasimulation.engine.service.IEngineService;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
@Creatable
public class ModelState {
	
	@Inject
	private IEngineService engineservice;
	
	public Engine engine() {
		return engineservice.getEngine();
	}
	
}

