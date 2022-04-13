package com.company.tutorial3.simulation;

import java.util.function.Supplier;

public class Indicator {
	public enum Type {
		ARCS, NODES, SERVICE_LEVEL, EXPENSES
	}
	
	private Type type;
	private Supplier<Double> value;
	
	public Indicator(Type type, Supplier<Double> value) {
		this.type = type;
		this.value = value;
	}

	public Type getType() {
		return type;
	}

	public double getValue() {
		return value.get();
	}
}

