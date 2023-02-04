package com.company.tutorial3.application.handlers;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
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
import org.eclipse.e4.ui.model.application.commands.MParameter;
import org.eclipse.swt.widgets.Shell;

import com.company.tutorial3.application.states.AppState;
import com.company.tutorial3.application.utils.MessageBoxFactory;
import com.company.tutorial3.application.utils.PerspectiveUtils;
import com.company.tutorial3.application.utils.Topics;
import com.company.tutorial3.application.utils.validation.ValidationManager;
import com.amalgamasimulation.desktop.utils.MessageManager;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.common.states.AppData;
import com.company.tutorial3.application.utils.PerspectiveUtils.Perspective;
import com.company.tutorial3.simulation.ExperimentRun;
import com.amalgamasimulation.engine.service.IEngineService;


public class SwitchPerspectiveHandler {
	
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
	public void execute(@Named(PerspectiveUtils.COMMAND_PARAMETER_NAME) String newApplicationMode, 
						Shell shell,
						MApplication app, 
						EPartService partService, 
						EModelService modelService, 
						MWindow mainWindow) {
		PerspectiveUtils.Perspective newPerspective = PerspectiveUtils.Perspective.valueOf(newApplicationMode);
		if (newPerspective == null) {
			selectExactlyOneModeMenuItem(appState.getCurrentPerspective(), modelService, mainWindow);
			throw new RuntimeException("Unknown application mode: " + newApplicationMode);
		}
		trySwitchToPerspective(newPerspective, shell, app, partService, modelService, messageManager, messages, appData, appState
				, engineService
						);
	}
	
	public static void trySwitchToPerspective(	PerspectiveUtils.Perspective newPerspective, 
												Shell shell,
												MApplication app, 
												EPartService partService, 
												EModelService modelService, 
												MessageManager messageManager, 
												Messages messages,
												AppData appData,
												AppState appState
												,
												IEngineService engineService
											) {
		MWindow mainWindow = PerspectiveUtils.getMainWindow(modelService, app);
		if (newPerspective.requiresValidScenario) {
			ValidationManager validationManager = new ValidationManager(messages);
			// here, model's init descriptors get initialized
			validationManager.validate(messageManager, appData.getScenario(), partService);
			if (validationManager.isErrorExist()) {
				MessageBoxFactory.createMessageBox(shell, SWT.ICON_ERROR | SWT.OK | SWT.APPLICATION_MODAL, 
						messages.title_check_data, messages.message_check_data_error);
				selectExactlyOneModeMenuItem(appState.getCurrentPerspective(), modelService, mainWindow);
				return;
			}
		}
		if(appState.getCurrentPerspective() == Perspective.SIMULATION) {
			engineService.getEngine().reset();
		}
		if (newPerspective != appState.getCurrentPerspective()) {
			appState.switchPerspective(newPerspective, app, partService, modelService, mainWindow);
			messageManager.send(Topics.PERSPECTIVE_CHANGED, null);
			if (newPerspective == PerspectiveUtils.Perspective.SIMULATION) {
				ExperimentRun experimentRun = new ExperimentRun(appData.getScenario(), engineService.getEngine());
				appState.setCurrentExperiment(experimentRun);
				messageManager.post(Topics.SHOW_MODEL, experimentRun.getModel());
				appState.setRunEnabled(true);
			}
			selectExactlyOneModeMenuItem(appState.getCurrentPerspective(), modelService, mainWindow);
		}
	}
	
	public static void selectExactlyOneModeMenuItem(PerspectiveUtils.Perspective perspective, EModelService modelService, MWindow mainWindow) {
		MMenu mainMenu = ((MTrimmedWindow)mainWindow).getMainMenu();
		MUIElement modeMenu = modelService.find(PerspectiveUtils.MODE_MENU_ID, mainMenu);
		List<MHandledMenuItem> modeMenuItems = modelService.findElements(modeMenu, null/*id*/, MHandledMenuItem.class);
		for (var item : modeMenuItems) {
			for (MParameter parameter : item.getParameters()) {
	    		if (parameter.getName().equals(PerspectiveUtils.COMMAND_PARAMETER_NAME)) {
	    			// menu item parameter that contains the target application mode name (as a String)
	    			item.setSelected(parameter.getValue().equals(perspective.name()));
	    		}
			}
		}
	}
	 
}

