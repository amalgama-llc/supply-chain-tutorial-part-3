package com.company.tutorial3.application.parts.simulation;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.swt.widgets.Composite;

import com.company.tutorial3.application.utils.Topics;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.simulation.Indicator;
import com.company.tutorial3.simulation.Mapping;
import com.company.tutorial3.simulation.model.Model;
import com.amalgamasimulation.desktop.ui.views.TableView;
import com.amalgamasimulation.desktop.utils.MessageManager;
import com.amalgamasimulation.viewupdater.service.IViewUpdaterService;

public class SimulationStatisticsPart {

	@Inject
	@Translation
	private Messages messages;
	
	@Inject
	private IViewUpdaterService viewUpdaterService;
	
	@Inject
	private MessageManager messageManager;	
	private TableView<Indicator> tableView;

	@PostConstruct
	public void createComposite(Composite parent) {
		tableView = new TableView<>(parent, Collections.emptyList(), false, true);
		tableView.addColumn(messages.SIMULATION_STATS_obj_INDICATOR_col_NAME, 	200, 
				indicator -> Mapping.INDICATOR_LABELS.apply(indicator.getType()));
		tableView.addColumn(messages.SIMULATION_STATS_obj_INDICATOR_col_VALUE, 	70, 
				indicator ->  Mapping.INDICATOR_FORMATTERS.apply(indicator.getType()).apply(indicator.getValue()));
		viewUpdaterService.getStatsUpdater().addView(tableView);
		messageManager.subscribe(Topics.SHOW_MODEL, this::onShowModel, true);
	}
	
	protected void onShowModel(Model model) {
		tableView.setData(model != null ? model.getIndicators() : Collections.emptyList());
	}
	
}

