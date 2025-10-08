package com.company.tutorial3.simulation;

import com.company.tutorial3.datamodel.Scenario;
import com.company.tutorial3.simulation.model.Model;
import com.amalgamasimulation.engine.Engine;

public class ExperimentRun {
	private final Model model;
	private final Scenario scenario;
	private final Engine engine;
	
	public ExperimentRun(Scenario scenario, Engine engine) {
		this.scenario = scenario;
		this.engine = engine;
		this.model = new Model(engine, scenario);
	}

	public Model getModel() {
		return model;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public Engine getEngine() {
		return engine;
	}
}

