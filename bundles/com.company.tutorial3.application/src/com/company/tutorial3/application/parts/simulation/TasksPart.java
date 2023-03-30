package com.company.tutorial3.application.parts.simulation;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.swt.widgets.Composite;

import com.amalgamasimulation.desktop.utils.MessageManager;
import com.amalgamasimulation.desktop.ui.views.TableView;
import com.amalgamasimulation.utils.format.Formats;
import com.amalgamasimulation.viewupdater.service.IViewUpdaterService;
import com.company.tutorial3.application.utils.Topics;
import com.company.tutorial3.simulation.model.Model;
import com.company.tutorial3.simulation.model.TransportationTask;

public class TasksPart {

	@Inject
	private MessageManager messageManager;

	@Inject
	private IViewUpdaterService viewUpdaterService;

	private Model model;
	private TableView<TransportationTask> tableView;

	@PostConstruct
	public void createComposite(Composite parent) {
		tableView = new TableView<>(parent, Collections.emptyList());
		Function<LocalDateTime, String> labelExtractor = v -> v == null ? "" : Formats.getDefaultFormats().dayMonthLongYearHoursMinutes(v);
		tableView.addColumn("Id", t -> t.getId());
		tableView.addColumn("Truck", t -> t.getTruck().getName());
		tableView.addColumn("Source", t -> t.getRequest().getSourceAsset().getName());
		tableView.addColumn("Destination", t -> t.getRequest().getDestAsset().getName());
		tableView.addColumn("Created", t -> model.timeToDate(t.getRequest().getCreatedTime()))
				.setLabelExtractor(labelExtractor);
		tableView.addColumn("Deadline", t -> model.timeToDate(t.getRequest().getDeadlineTime()))
				.setLabelExtractor(labelExtractor);
		tableView
				.addColumn("Completed",
						t -> t.getRequest().isCompleted() ? model.timeToDate(t.getRequest().getCompletedTime()) : null)
				.setLabelExtractor(labelExtractor);
		tableView.addColumn("Status", this::getTaskStatus);

		messageManager.subscribe(Topics.SHOW_MODEL, this::onShowModel, true);
		viewUpdaterService.getDefaultUpdater().addView(tableView);
	}

	private void onShowModel(Model model) {
		this.model = model;
		tableView.setData(model.getTransportationTasks());
	}
	
	private String getTaskStatus(TransportationTask task) {
		var request = task.getRequest();
		if (request.isCompleted()) {
			return request.getCompletedTime() <= request.getDeadlineTime() ? "COMPLETED ON TIME" : "COMPLETED AFTER DEADLINE";
		}
		return "IN PROGRESS";
	}
}
