package com.company.tutorial3.application.utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MCompositePart;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;


public class PerspectiveUtils {
	
	private static final String PERSPECTIVE_STACK_ID = "com.company.tutorial3.application.perspectivestack.0";

	public enum Perspective {
		EDITOR("com.company.tutorial3.application.perspective.editor") {
			@Override
			public boolean beforeSwitch() {return true;}
		},
		SIMULATION("com.company.tutorial3.application.perspective.simulation") {
			@Override
			public boolean beforeSwitch() {return true;}
			
			@Override
			public boolean engineToolBarIsVisible() {
				return true;
			}
		},
		SCHEDULING("com.company.tutorial3.application.perspective.scheduling") {
			@Override
			public boolean beforeSwitch() {return true;}
		};

		public final String id;

		Perspective(String id) {
			this.id = id;
		}
		
		public abstract boolean beforeSwitch();
		
		public boolean engineToolBarIsVisible() {
			return false;
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
	
	public static void setVisibleForModelingToolBar(boolean visible, EModelService modelService, MWindow window, String[] toolbars) {
		for (String id : toolbars) {
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


