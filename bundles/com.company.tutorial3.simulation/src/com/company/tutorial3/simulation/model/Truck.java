package com.company.tutorial3.simulation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import com.amalgamasimulation.engine.Engine;
import com.amalgamasimulation.graphagent.GraphAgent;
import com.amalgamasimulation.graphagent.GraphAgentPosition;

public class Truck extends GraphAgent<Node, Arc> {
	private final double OWNERSHIP_COST_PER_HOUR = 10;
	private final double USAGE_COST_PER_HOUR = 25;

	private final String id;
	private final String name;
	private final double speed;
	
	private record ActivePeriod(double startTime, double endTime) {}

	private List<ActivePeriod> activePeriods = new ArrayList<>();
	private Optional<Double> currentActivePeriodStartTime = Optional.empty();
	private TransportationTask currentTask;
	private List<TransportationTask> taskHistory = new ArrayList<>();
	private BiConsumer<Truck, GraphAgentPosition<Node, Arc>> destinationReachedHandler;

	public Truck(String id, String name, double speed, Engine engine) {
		super(engine);
		this.id = id;
		this.name = name;
		this.speed = speed;
	}

	public String getId() {
		return id;
	}
	
	@Override
	public String getName() {
		return name;
	}

	public double getSpeed() {
		return speed;
	}

	public TransportationTask getCurrentTask() {
		return currentTask;
	}
	
	public List<TransportationTask> getTaskHistory() {
		return taskHistory;
	}
	
	public boolean isIdle() {
		return currentTask == null;
	}

	public double getExpenses() {
		double ownershipDurationHours = engine.time() / engine.hour();
		double usageDurationHours = getAllActivePeriodsDurationHrs();
		return ownershipDurationHours * OWNERSHIP_COST_PER_HOUR + usageDurationHours * USAGE_COST_PER_HOUR; 
	}

	private double getAllActivePeriodsDurationHrs() {
		double result = activePeriods.stream().mapToDouble(p -> p.endTime - p.startTime).sum();
		if (currentActivePeriodStartTime.isPresent()) {
			result += engine.time() - currentActivePeriodStartTime.get();
		}
		return result;
	}
	
	public void onTaskStarted(TransportationTask task,
			BiConsumer<Truck, GraphAgentPosition<Node, Arc>> destinationReachedHandler) {
		currentActivePeriodStartTime = Optional.of(engine.time());
		currentTask = task;
		taskHistory.add(currentTask);
		this.destinationReachedHandler = destinationReachedHandler;
	}

	public void onTaskCompleted() {
		activePeriods.add(new ActivePeriod(currentActivePeriodStartTime.get(), engine.time()));
		currentActivePeriodStartTime = Optional.empty();
		currentTask = null;
		destinationReachedHandler = null;
	}

	@Override
	public void onDestinationReached(GraphAgentPosition<Node, Arc> destPosition) {
		super.onDestinationReached(destPosition);
		destinationReachedHandler.accept(this, destPosition);
	}
}
