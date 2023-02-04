package com.company.tutorial3.simulation.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.math3.random.RandomGenerator;

import com.amalgamasimulation.engine.Engine;
import com.amalgamasimulation.geometry.Point;
import com.amalgamasimulation.geometry.Polyline;
import com.amalgamasimulation.graphagent.GraphEnvironment;
import com.amalgamasimulation.randomdatamodel.DistributionFactory;
import com.amalgamasimulation.utils.Utils;
import com.amalgamasimulation.utils.random.DefaultRandomGenerator;
import com.company.tutorial3.datamodel.Scenario;
import com.company.tutorial3.simulation.Mapping;

@SuppressWarnings("serial")
public class Model extends com.amalgamasimulation.engine.Model {
	private GraphEnvironment<Node, Arc, Object> graphEnvironment;
	private final Scenario scenario;
	private final Mapping mapping = new Mapping();
	
	private List<Arc> arcs = new ArrayList<>();
	
	private List<Truck> trucks = new ArrayList<>();
    private List<TransportationRequest> requests = new ArrayList<>();
    private Queue<TransportationRequest> waitingRequests = new LinkedList<>();
    private List<Warehouse> warehouses = new ArrayList<>();
    private List<Store> stores = new ArrayList<>();
    private final RandomGenerator randomGenerator = new DefaultRandomGenerator(1);
    private final Dispatcher dispatcher;
    private final Statistics statistics;

	public Model(Engine engine, Scenario scenario) {
		super(engine);
		engine().setTemporal(scenario.getBeginDate(), ChronoUnit.HOURS);
		engine().scheduleStop(dateToTime(scenario.getEndDate()), 0, "Experiment finished");
		this.scenario = scenario;
		graphEnvironment = new GraphEnvironment<>();
		
		initializeNodes();
		initializeModelObjects();
		
		var newRequestIntervalDistribution = DistributionFactory
		        .createDistribution(scenario.getIntervalBetweenRequests(), randomGenerator);
		RequestGenerator requestGenerator = new RequestGenerator(engine, this, newRequestIntervalDistribution,
		        scenario.getMaxDeliveryTimeHrs());
		requestGenerator.addNewRequestHandler(this::addRequest);
		this.dispatcher = new Dispatcher(engine, this);
		requestGenerator.addNewRequestHandler(dispatcher::onNewRequest);
		this.statistics = new Statistics(engine, this);
	}
	
	private void initializeModelObjects() {
		initializeGraph();
		initializeAssets();
		initializeTrucks();
	}
	
	private void initializeNodes() {
		for (var scenarioNode : scenario.getNodes()) {
			Node node = new Node(new Point(scenarioNode.getX(), scenarioNode.getY()));
			graphEnvironment.addNode(node);
			mapping.nodesMap.put(scenarioNode, node);
		}
	}
	

	private void initializeGraph() {
		for (var scenarioArc : scenario.getArcs()) {
			Polyline polyline = createPolyline(scenarioArc);
			if (polyline.getLength() != 0) {
				Node sourceNode = mapping.nodesMap.get(scenarioArc.getSource());
				Node destNode = mapping.nodesMap.get(scenarioArc.getDest());
				
				Arc forwardArc = new Arc(polyline);
				Arc backwardArc = new Arc(polyline.getReversed());

				forwardArc.setReverseArc(backwardArc);
				backwardArc.setReverseArc(forwardArc);
				graphEnvironment.addArc(sourceNode, destNode, forwardArc, backwardArc);
				mapping.forwardArcsMap.put(scenarioArc, forwardArc);
				this.arcs.add(forwardArc);
				this.arcs.add(backwardArc);
			}
		}
	}


	private Polyline createPolyline(com.company.tutorial3.datamodel.Arc dmArc) {
		List<Point> points = new ArrayList<>();
		points.add(new Point(dmArc.getSource().getX(), dmArc.getSource().getY()));
		dmArc.getPoints().forEach(
				bendpoint -> points.add(new Point(bendpoint.getX(), bendpoint.getY())));
		points.add(new Point(dmArc.getDest().getX(), dmArc.getDest().getY()));
		return new Polyline(Utils.toList(points.stream().distinct()));
	}
	

	public List<Arc> getArcs() {
		return arcs;
	}
	
	public GraphEnvironment<Node, Arc, ?> getGraphEnvironment() {
		return graphEnvironment;
	}
	
	public double getEndTime() {
		return dateToTime(scenario.getEndDate());
	}
	
	public List<Truck> getTrucks() {
		return trucks;
	}

	public List<TransportationRequest> getRequests() {
		return requests;
	}

	public void addRequest(TransportationRequest request) {
		requests.add(request);
	}

	public TransportationRequest getNextWaitingRequest() {
		return waitingRequests.poll();
	}

	public void addWaitingRequest(TransportationRequest request) {
		waitingRequests.add(request);
	}
	
	private void initializeAssets() {
		for (var scenarioWarehouse : scenario.getWarehouses()) {
			warehouses.add(new Warehouse(mapping.nodesMap.get(scenarioWarehouse.getNode()), scenarioWarehouse.getName()));
		}
		for (var scenarioStore : scenario.getStores()) {
			stores.add(new Store(mapping.nodesMap.get(scenarioStore.getNode()), scenarioStore.getName()));
		}
	}

	public List<Warehouse> getWarehouses() {
		return warehouses;
	}

	public List<Store> getStores() {
		return stores;
	}
	
	private void initializeTrucks() {
	    for (var scenarioTruck : scenario.getTrucks()) {
	        Truck truck = new Truck(scenarioTruck.getId(), scenarioTruck.getName(), scenarioTruck.getSpeed(), engine());
	        trucks.add(truck);
	        truck.setGraphEnvironment(graphEnvironment);
	        truck.jumpTo(mapping.nodesMap.value(scenarioTruck.getInitialPosition()));
	    }
	}
	
	public RandomGenerator getRandomGenerator() {
		return randomGenerator;
	}
	
	public Statistics getStatistics() {
	    return statistics;
	}
	
	public List<TransportationTask> getTransportationTasks() {
	    return dispatcher.getTransportationTasks();
	}

}

