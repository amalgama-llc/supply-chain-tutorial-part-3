package com.company.tutorial3.application.handlers;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import com.company.tutorial3.application.states.AppState;
import com.company.tutorial3.application.utils.MessageBoxFactory;
import com.company.tutorial3.application.utils.PerspectiveUtils;
import com.company.tutorial3.application.utils.Topics;
import com.company.tutorial3.application.utils.validation.ValidationManager;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.common.states.AppData;
import com.company.tutorial3.simulation.ExperimentRun;
import com.amalgamasimulation.desktop.utils.MessageManager;
import com.amalgamasimulation.engine.service.IEngineService;


public class SwitchPerspectiveHandler {
	
	private static String currentSelectedApplicationMode = "editor";
	
	@Inject
	private IEngineService engineService;
	
	@Inject
	private MessageManager messageManager;
	
	
	@Inject
	private AppState appState;
	
	@Inject
	@Translation
	private Messages messages;
	
	@Inject
	private AppData appData;
	
    @CanExecute
    private boolean canExecute() {
        return appState.isScenarioExist();
    }

	@Execute
	public void execute(@Named("com.company.tutorial3.application.commandparameter.switchPerspective") String applicationMode, Shell shell,
			MApplication app, EPartService partService, EModelService modelService, MWindow mainWindow, IEventBroker eventBroker,
			MHandledMenuItem item) {
		// Two events are coming here: 1) deselection of previous menu item and 2) selection of new menu item
		// We do not need the 1st event (deselection), so we ignore it here
		if (item != null && !item.isSelected()) {
			return;
		}
		ValidationManager validationManager = new ValidationManager(messages);
		validationManager.validate(eventBroker, appData.getScenario(), partService);
		if (validationManager.isErrorExist()) {
			MessageBoxFactory.createMessageBox(shell, SWT.ICON_ERROR | SWT.OK | SWT.APPLICATION_MODAL, 
					messages.title_check_data, messages.message_check_data_error);
			revertMenuItemSelection(modelService, mainWindow);
			return;
		}
		PerspectiveUtils.Perspective newPerspective = PerspectiveUtils.Perspective.valueOf(applicationMode);
		if (newPerspective == null) {
			revertMenuItemSelection(modelService, mainWindow);
			throw new RuntimeException("Unknown application mode: " + applicationMode);
		}
		else {
			appState.switchPerspective(newPerspective, app, partService, modelService, mainWindow);
			eventBroker.send(Topics.PERSPECTIVE_CHANGED, null);
			if (newPerspective == PerspectiveUtils.Perspective.SIMULATION) {
				engineService.getEngine().reset();
				ExperimentRun experimentRun = new ExperimentRun(appData.getScenario(), engineService.getEngine());
				appState.setCurrentExperiment(experimentRun);
				messageManager.post(Topics.SHOW_MODEL, experimentRun.getModel());
			}
			appState.setRunEnabled(true);
			currentSelectedApplicationMode = applicationMode;
		}
	}
	
	private void revertMenuItemSelection(EModelService modelService, MWindow mainWindow) {
		MMenu mainMenu = ((MTrimmedWindow)mainWindow).getMainMenu();
		MUIElement modeMenu = modelService.find("com.company.tutorial3.application.menu.mode", mainMenu);
		List<MHandledMenuItem> modeMenuItems = modelService.findElements(modeMenu, null/*id*/, MHandledMenuItem.class);
		modeMenuItems.forEach(item -> item.setSelected(item.getParameters().get(0).getValue().equals(currentSelectedApplicationMode)));
	}
}



