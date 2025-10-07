package com.company.tutorial3.simulation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import com.amalgamasimulation.engine.Engine;
import com.amalgamasimulation.graphagent.GeometricGraphPosition;
import com.amalgamasimulation.graphagent.GraphAgent;

public class Truck extends GraphAgent<Node, Arc> {
	private final String name;
	private final double speed;
	private final double ownershipCostPerHour;
	private final double usageCostPerHour;
	
	private record ActivePeriod(double startTime, double endTime) {}

	private List<ActivePeriod> activePeriods = new ArrayList<>();
	private Optional<Double> currentActivePeriodStartTime = Optional.empty();
	private TransportationTask currentTask;
	private List<TransportationTask> taskHistory = new ArrayList<>();
	private BiConsumer<Truck, GeometricGraphPosition<Node, Arc>> destinationReachedHandler;

	public Truck(String name, double speed,
			double ownershipCostPerHour, double usageCostPerHour,
			Engine engine) {
		super(engine);
		this.name = name;
		this.speed = speed;
		this.ownershipCostPerHour = ownershipCostPerHour;
		this.usageCostPerHour = usageCostPerHour;
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
		return ownershipDurationHours * ownershipCostPerHour + usageDurationHours * usageCostPerHour; 
	}

	private double getAllActivePeriodsDurationHrs() {
		double result = activePeriods.stream().mapToDouble(p -> p.endTime - p.startTime).sum();
		if (currentActivePeriodStartTime.isPresent()) {
			result += engine.time() - currentActivePeriodStartTime.get();
		}
		return result;
	}
	
	public void onTaskStarted(TransportationTask task,
			BiConsumer<Truck, GeometricGraphPosition<Node, Arc>> destinationReachedHandler) {
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
	public void onDestinationReached(GeometricGraphPosition<Node, Arc> destPosition) {
		super.onDestinationReached(destPosition);
		destinationReachedHandler.accept(this, destPosition);
	}
}
