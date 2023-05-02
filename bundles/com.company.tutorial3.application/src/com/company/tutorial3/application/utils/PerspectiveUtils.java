package com.company.tutorial3.application.utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.commands.MParameter;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MCompositePart;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;


public class PerspectiveUtils {
	
	/**
	 * Name of the perspective name parameter in menu items that switch the app to another perspective. 
	 * This parameter contains the name of the perspective ('EDITOR', etc.).
	 * These names are also the String values of the Perspective enum.
	 */
	public static final String COMMAND_PARAMETER_NAME = "com.company.tutorial3.application.commandparameter.switchPerspective";
	
	public static final String MODE_MENU_ID = "com.company.tutorial3.application.menu.mode";
	
	private static final String PERSPECTIVE_STACK_ID = "com.company.tutorial3.application.perspectivestack.0";
	private static final String [] SIMULATION_TOOLBARS_IDS = new String [] {"com.amalgamasimulation.enginetoolbar", "com.amalgamasimulation.updatertoolbar"};

	public enum Perspective {
		EDITOR("com.company.tutorial3.application.perspective.editor", false, false),
		SIMULATION("com.company.tutorial3.application.perspective.simulation", true, true)
		;

		public final String id;
		public final boolean engineToolBarIsVisible;
		public final boolean requiresValidScenario;

		Perspective(String id, boolean engineToolBarIsVisible, boolean requiresValidScenario) {
			this.id = id;
			this.engineToolBarIsVisible = engineToolBarIsVisible;
			this.requiresValidScenario = requiresValidScenario;
		}
		
		public static Perspective fromPerspectiveId(String perspectiveId) {
			return Arrays.stream(values()).filter(p -> p.id.equals(perspectiveId)).findFirst().orElse(null);
		}
	}
	
	private PerspectiveUtils() {}

	private static MPerspectiveStack getPerspectiveStack(EModelService modelService, MApplication app) {
		return (MPerspectiveStack)modelService.find(PERSPECTIVE_STACK_ID, app);
	}
	
	private static MPerspective getPerspectiveById(String perspectiveId, MPerspectiveStack perspectiveStack) {
		return perspectiveStack.getChildren().stream().filter(p -> p.getElementId().equals(perspectiveId)).findFirst().orElse(null);
	}
	
	public static Perspective getVisiblePerspective(MWindow window) {	
		Perspective perspective = Perspective.values()[0];
		OUTER:
		for(MMenuElement menuElem: window.getMainMenu().getChildren()){
			if(menuElem.getElementId().equals(MODE_MENU_ID)) {
				MMenu menu = (MMenu)menuElem;
				for(MMenuElement itemMenu: menu.getChildren()) {
					MHandledMenuItem item = (MHandledMenuItem)itemMenu;
					if(item.isSelected()) {
						for(MParameter parameter: item.getParameters()) {
							perspective =  Perspective.valueOf(parameter.getValue());
							break OUTER;
						}
					}
				}
			}
		}
		return perspective;
	}

	public static void copyAllPerspectivesFromSnippets(EModelService modelService, MApplication app) {
		MPerspectiveStack perspectiveStack = getPerspectiveStack(modelService, app);
		for (MUIElement snippet : app.getSnippets()) {
			MPerspective perspectiveClone = (MPerspective) modelService.cloneSnippet(app, snippet.getElementId(), null);
			perspectiveStack.getChildren().add(perspectiveClone);
		}
	}
	
	public static MWindow getMainWindow(EModelService modelService, MApplication application) {
		return (MWindow) modelService.find("org.eclipse.e4.window.main", application);
	}

	public static void resetPerspectiveByTemplateAll(EModelService modelService, MApplication application, EPartService partService,
			PerspectiveUtils.Perspective currentPerspective) {
		
			MWindow mainWindow = getMainWindow(modelService, application);
			MPerspectiveStack perspectiveStack = getPerspectiveStack(modelService, application);
			MPerspective activePerspective = getPerspectiveById(currentPerspective.id, perspectiveStack);
			
			MPerspective perspectiveClone = (MPerspective) modelService.cloneSnippet(application, currentPerspective.id, null);
			perspectiveStack.getChildren().add(perspectiveClone);
						
			if (activePerspective != null) {
				for (String movedElementId : getPartTraverseOrder(perspectiveClone)) {
					MPart partInClonePerspective = (MPart) modelService.find(movedElementId, perspectiveClone);
					MPart partInActivePerspective = (MPart) modelService.find(movedElementId, activePerspective);
					boolean partInActivePerspectiveExists = partInActivePerspective != null;
					boolean partInActivePerspectiveNotClosed = partInActivePerspectiveExists && partInActivePerspective.isToBeRendered();
					if (partInClonePerspective != null && partInActivePerspectiveNotClosed) {
						partInClonePerspective.setContributionURI(null);
					}
				}
				
				perspectiveStack.setSelectedElement(perspectiveClone);
				
				for (String movedElementId : getPartTraverseOrder(perspectiveClone)) {
					MPart partInClonePerspective = (MPart) modelService.find(movedElementId, perspectiveClone);
					MPart partInActivePerspective = (MPart) modelService.find(movedElementId, activePerspective);
					if (partInActivePerspective != null 
							&& partInClonePerspective != null
							&& partInActivePerspective.getParent().getWidget() != null) {
						MElementContainer<MUIElement> sourceContainer = partInActivePerspective.getParent();
						MElementContainer<MUIElement> targetContainer = partInClonePerspective.getParent();
						final int elementIndexInTargetContainer = targetContainer.getChildren().indexOf(partInClonePerspective);
						partInClonePerspective.setParent(sourceContainer);
						partInActivePerspective.setParent(targetContainer);
						targetContainer.getChildren().add(elementIndexInTargetContainer, 
								targetContainer.getChildren().remove(targetContainer.getChildren().size() - 1));
					}
				}
				
				perspectiveStack.setSelectedElement(perspectiveClone);					
				modelService.removePerspectiveModel(activePerspective, mainWindow);
			} else {
				perspectiveStack.setSelectedElement(perspectiveClone);
			}
			
			activateFirstPartInStack(perspectiveClone, partService);
	}
	
	private static void activateFirstPartInStack(MElementContainer<?> container, EPartService partService) {		
		for(Object obj: container.getChildren()) {
			if(obj instanceof MElementContainer) {
				MElementContainer<?> mElementContainer = (MElementContainer<?>)obj;
				activateFirstPartInStack(mElementContainer, partService);
			}else if(obj instanceof MPart) {
				MPart part = (MPart)obj;
				partService.activate(part);
				break;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private static List<String> getPartTraverseOrder(MElementContainer<? extends MUIElement> perspective) {
		List<String> mPartMovingOrder = new ArrayList<>();
		for (MUIElement child: perspective.getChildren()) {
			if (child instanceof MPart 
					&&  !(child instanceof MCompositePart)) {
				mPartMovingOrder.add(child.getElementId());
			}
			if (child instanceof MElementContainer && !(child instanceof MCompositePart)) {
				mPartMovingOrder.addAll(getPartTraverseOrder((MElementContainer<? extends MUIElement>)child));
			}
		}
		return mPartMovingOrder;
	}
	
	public static void setVisibleForModelingToolBar(boolean visible, EModelService modelService, MWindow window) {
		for (String id : SIMULATION_TOOLBARS_IDS) {
			MToolBar toolBar1 = (MToolBar) modelService.find(id, window);
			if (toolBar1 != null) {
				for (MToolBarElement m : toolBar1.getChildren()) {
					m.setVisible(visible);
				}
				toolBar1.getTags().add(IPresentationEngine.NO_MOVE);
			}
		}
	}
}

