package com.company.tutorial3.application.parts.simulation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.amalgamasimulation.charts.TimeGanttChart;
import com.amalgamasimulation.charts.axes.ticks.AxisTimeStyle;
import com.amalgamasimulation.charts.swt.ChartEnvironmentSwt;
import com.amalgamasimulation.charts.utils.LabelSide;
import com.amalgamasimulation.charts.visualsets.GanttVisualSet;
import com.amalgamasimulation.desktop.utils.MessageManager;
import com.amalgamasimulation.engine.service.IEngineService;
import com.amalgamasimulation.utils.Colors;
import com.amalgamasimulation.utils.format.Formats;
import com.amalgamasimulation.viewupdater.service.IViewUpdaterService;
import com.company.tutorial3.application.utils.Topics;
import com.company.tutorial3.simulation.model.Model;
import com.company.tutorial3.simulation.model.TransportationTask;

public class GanttChartPart {
	
	@Inject
	private IEngineService engineservice;
	
	@Inject
	private IViewUpdaterService viewUpdaterService;
	private TimeGanttChart ganttChart;

	@Inject
	private MessageManager messageManager;
	
	@PostConstruct
	public void createComposite(Composite parent, Shell shell) {
		initializeGanttChart(parent);
	}
	
	private void initializeGanttChart( Composite parent ) {
		ganttChart = new TimeGanttChart( new ChartEnvironmentSwt( parent ), "Gantt chart", ChronoUnit.HOURS );
		viewUpdaterService.getDefaultUpdater().addView( () -> updateView(), () -> false );
		messageManager.subscribe(Topics.SHOW_MODEL, this::updateContent, true);
	}
	
	private void updateView() {
		ganttChart.redraw();
	}
	
	private void updateContent(Model model) {
		ganttChart.getVisualSetContainer().clear();
		ganttChart.getXAxis().setTimeStyle(AxisTimeStyle.getDefault(model.getBeginDate(), model.timeUnit()))
				.setDisplayedRange(0,  model.dateToTime(model.getEndDate()));
		model.getTrucks().forEach(truck -> {
			var visualSet = new GanttVisualSet<>(truck.getName(), () -> truck.getTaskHistory(), t -> t.getBeginTime(), t -> getTaskEndTime(t))
					.setBackgroundColor(t -> Colors.BLUE)
					.setLabelText( LabelSide.TOP_LEFT, this::getTopLeftText, s -> 9.0, s -> Colors.white )
					.setLabelText( LabelSide.TOP_CENTER, this::getTopCenterText, s -> 9.0, s -> Colors.white )
					.setLabelText( LabelSide.TOP_RIGHT, this::getTopRightText, s -> 9.0, s -> Colors.white )
					.setLabelText( LabelSide.MIDDLE_CENTER, this::getMiddleCenterText, s -> 12.0, s -> Colors.white );
			ganttChart.getVisualSetContainer().addVisualSet(visualSet);
		});
		ganttChart.redraw();
	}
	
	private double getTaskEndTime(TransportationTask task) {
		return task.getRequest().isCompleted() ? task.getRequest().getCompletedTime() : engineservice.getEngine().time();
	}
	
	private LocalDateTime timeToDate(double time) {
		return engineservice.getEngine().timeToDate(time);
	}
	
	private String getTopLeftText(TransportationTask task) {
		return Formats.getDefaultFormats().dayMonthLongYearHoursMinutes(timeToDate(task.getBeginTime()));
	}
	
	private String getTopRightText(TransportationTask task) {
		return Formats.getDefaultFormats().dayMonthLongYearHoursMinutes(timeToDate(getTaskEndTime(task)));
	}
	
	private String getTopCenterText(TransportationTask task) {
		return Formats.getDefaultFormats().duration(Duration.between(timeToDate(task.getBeginTime()), timeToDate(getTaskEndTime(task))));
	}
	
	private String getMiddleCenterText(TransportationTask task) {
		return "%s [%s -> %s]".formatted(task.getId(), task.getRequest().getSourceAsset().getName(), task.getRequest().getDestAsset().getName());
	}
}
