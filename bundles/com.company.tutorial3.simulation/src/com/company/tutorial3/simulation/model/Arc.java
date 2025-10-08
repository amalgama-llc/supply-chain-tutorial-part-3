package com.company.tutorial3.simulation.model;

import com.amalgamasimulation.geometry.Polyline;
import com.amalgamasimulation.graphagent.AgentGraphArcImpl;

@SuppressWarnings("serial")
public class Arc extends AgentGraphArcImpl {
	public Arc(Polyline polyline) {
		super(polyline);
	}
}

