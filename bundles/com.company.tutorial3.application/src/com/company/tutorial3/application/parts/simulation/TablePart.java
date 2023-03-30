package com.company.tutorial3.application.parts.simulation;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.swt.widgets.Composite;

import com.amalgamasimulation.desktop.utils.MessageManager;
import com.amalgamasimulation.desktop.ui.views.TableView;
import com.amalgamasimulation.utils.format.Formats;
import com.amalgamasimulation.viewupdater.service.IViewUpdaterService;
import com.company.tutorial3.application.utils.Topics;
import com.company.tutorial3.simulation.model.Model;
import com.company.tutorial3.simulation.model.Truck;

public class TablePart {

	@Inject
	private MessageManager messageManager;
	
	@Inject
	private IViewUpdaterService viewUpdaterService;
	
	private TableView<Truck> tableView;
	
	private Model model;
	
	@PostConstruct
	public void createComposite(Composite parent) {
		tableView = new TableView<>(parent, Collections.emptyList(), false, true);
		tableView.addColumn("ID", t -> t.getId());
		tableView.addColumn("Name", t -> t.getName());
		tableView.addColumn("Expenses", t -> t.getExpenses()).setLabelExtractor(Formats.getDefaultFormats()::dollarTwoDecimals);
		tableView.addColumn("Distance traveled, km", 125, t -> getDistanceKm(t.getDistanceTraveled())).setLabelExtractor(Formats.getDefaultFormats()::twoDecimals);
		
		messageManager.subscribe(Topics.SHOW_MODEL, this::onShowModel, true);
		viewUpdaterService.getStatsUpdater().addView(tableView);
	}
	
	private void onShowModel(Model model) {
		this.model = model;
		tableView.setData(model.getTrucks());
	}
	
	private double getDistanceKm(double distancePx) {
		if (model == null) {
			return 0;
		}
		return distancePx;
	}
}
