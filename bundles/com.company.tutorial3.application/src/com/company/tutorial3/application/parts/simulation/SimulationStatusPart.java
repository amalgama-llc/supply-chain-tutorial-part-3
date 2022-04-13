package com.company.tutorial3.application.parts.simulation;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.swt.widgets.Composite;

import com.company.tutorial3.application.animation.SimulationStatusShape;
import com.company.tutorial3.application.utils.Topics;
import com.company.tutorial3.simulation.model.Model;
import com.amalgamasimulation.desktop.utils.MessageManager;
import com.amalgamasimulation.geometry.Point;
import com.amalgamasimulation.platform.animation.swt.SWT2DSimulationView;
import com.amalgamasimulation.platform.animation.swt.SWT2DSimulationViewImpl;
import com.amalgamasimulation.viewupdater.service.IViewUpdaterService;

public class SimulationStatusPart {

	@Inject
	private MessageManager messageManager;
	
	@Inject
	private IViewUpdaterService viewUpdaterService;
	
	private SWT2DSimulationView animationView;

	@PostConstruct
	public void createComposite(Composite parent) {
		animationView = new SWT2DSimulationViewImpl(parent);
		animationView.setAllowPan(false);
		animationView.setAllowScale(false);
		messageManager.subscribe(Topics.SHOW_MODEL, this::onShowModel, true);
		viewUpdaterService.getDefaultUpdater().addView(animationView);
	}
	
	private void onShowModel(Model model) {
		animationView.removeAllShapes();
		if (model != null) {
			animationView.addShape(new SimulationStatusShape(	model,
																partClass -> messageManager.activatePart((Class<?>)partClass),
																() -> animationView.updateView()
					).withPoint(new Point(5,5)).withFixedScale(1.25));
		}
		animationView.updateView();
	}
	
}

