package com.company.tutorial3.application.parts.simulation;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;

import com.company.tutorial3.application.animation.SimulationStatusShape;
import com.company.tutorial3.application.handlers.SwitchPerspectiveHandler;
import com.company.tutorial3.application.states.AppState;
import com.company.tutorial3.common.states.AppData;
import com.company.tutorial3.simulation.model.Model;
import com.company.tutorial3.application.utils.IconsMapping;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.application.utils.Topics;
import com.company.tutorial3.application.utils.PerspectiveUtils.Perspective;
import com.amalgamasimulation.desktop.ui.views.ToolBarComposite;
import com.amalgamasimulation.desktop.utils.MessageManager;
import com.amalgamasimulation.desktop.utils.ToolbarUtils;
import com.amalgamasimulation.engine.service.IEngineService;
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
	
	@Inject
	private EPartService partService;
	
	@Inject
	@Translation
	public Messages messages;

	@Inject
	private AppData appData;
	
	@Inject
	private IEventBroker eventBroker;
	
	@Inject
	private AppState appState;
	
	@Inject
	private EModelService modelService;
	
	@Inject
	private MApplication app;
	
	@Inject
	private IEngineService engineService;

	@PostConstruct
	public void createComposite(Composite parent) {
		new ToolBarComposite(parent, this::initializeToolBar, this::initContents);	
		
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
	
	private void initContents(Composite parent) {
		animationView = new SWT2DSimulationViewImpl(parent);
		animationView.setAllowPan(false);
		animationView.setAllowScale(false);
		messageManager.subscribe(Topics.SHOW_MODEL, this::onShowModel, true);
		viewUpdaterService.getDefaultUpdater().addView(animationView);
	}
	
	private void initializeToolBar(Composite parent) {
		ToolBar toolBar = new ToolBar(parent, SWT.HORIZONTAL);
		
		ToolbarUtils.addCommandItem(toolBar, IconsMapping.REFRESH, messages.button_reset, 
				() -> engineService.getEngine().reset())
			.setText(messages.button_reset);
		
		ToolbarUtils.addCommandItem(toolBar, IconsMapping.getImage("/icons/editing.png"), messages.button_editor, 
				() -> SwitchPerspectiveHandler.trySwitchToPerspective(Perspective.EDITOR, parent.getShell(), app, partService, modelService, messageManager, messages, appData, appState, engineService))	
			.setText(messages.button_editor);
	}
	
}

