package com.company.tutorial3.application.parts.editor;

import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;

import com.company.tutorial3.application.parts.editor.TreePart.TreeElementType;
import com.company.tutorial3.application.utils.IconsMapping;
import com.company.tutorial3.application.utils.Topics;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.common.states.AppData;
import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Scenario;
import com.company.tutorial3.graphicaleditor.view.GraphicalEditor;
import com.company.tutorial3.graphicaleditor.view.Storage;
import com.amalgamasimulation.desktop.properties.PropertyPage;
import com.amalgamasimulation.desktop.properties.PropertyPart;
import com.amalgamasimulation.desktop.utils.ToolbarUtils;
import com.amalgamasimulation.utils.Pair;

public class MapPart {

	private Consumer<Object> onObjectSelectedHandler;
	private Predicate<Object> onObjectSelectedFilter;
	@Inject
	private IEventBroker eventBroker;
	private GraphicalEditor editor;

	
	private Composite parent;
	
	@Inject
	@Translation
	public Messages messages;
	private boolean showMap = false;
	@Inject
	private AppData appData;
	

	@PostConstruct
	public void postConstruct(MPart mPart, Composite parent, Storage storage) {
		this.parent = parent;
		parent.setLayout(new FormLayout());
		parent.setEnabled(false);
		ToolBar toolBar = initToolBar(parent);
		editor = new GraphicalEditor(parent, storage);
		parent.setLayout(new FormLayout());
		FormData fdView = new FormData();
		fdView.top = new FormAttachment(toolBar, 0);
		fdView.left = new FormAttachment(0, 0);	
		fdView.right = new FormAttachment(100, 0);
		fdView.bottom = new FormAttachment(100, 0);
		editor.getCanvas().setLayoutData(fdView);	
		
		editor.addObjectClickListener(o -> {
			if (editor.isWaitingForSelection()) {
				if(o != null){
					if(onObjectSelectedFilter.test(o)) {
						onObjectSelectedHandler.accept(o);
					}
				}
			} else {
				if(o != null){
					TreeElementType treeElement = null;
					if( o instanceof Node) {
						treeElement = TreeElementType.NODE;
					}else if( o instanceof Arc) {
						treeElement = TreeElementType.ARC;
					}
					eventBroker.send(Topics.CHANGE_VISIBILITY_TABLE_PAGE, treeElement);
					eventBroker.send(Topics.CHANGE_SELECTED_TREE_ELEMENT, treeElement);
					eventBroker.send(Topics.CHANGE_SELECTED_OBJECT_IN_TABLE_PAGE, o);
					eventBroker.send(PropertyPart.PROPERTY_SELECTION_CHANGED, o);		
					editor.highlightDependencies(o);
				}
			}
		});
		
		eventBroker.subscribe(PropertyPage.WAITING_FOR_FILTERED_OBJECT_SELECTION, event -> {
			Pair<Predicate<Object>, Consumer<Object>> pair = (Pair<Predicate<Object>, Consumer<Object>>)event.getProperty(IEventBroker.DATA);
			this.onObjectSelectedFilter = pair.first;
			this.onObjectSelectedHandler = pair.second;
			editor.deselectAll();
			editor.setWaitingForSelection(onObjectSelectedFilter);
		});
		
		eventBroker.subscribe(Topics.NAVIGATE_TO, e -> { 
			Object o = e.getProperty(IEventBroker.DATA);
			editor.getContentViewer().navigateTo(o, false);
		});
		eventBroker.subscribe(Topics.ESC_KEY_TYPE, event -> editor.escKeyTyped());
		eventBroker.subscribe(Topics.NEW_SCENARIO, event -> onNewFileLoaded(editor));
		onNewFileLoaded(editor);
		editor.getContentViewer().setDoAfterRemoveObject( () -> eventBroker.send(PropertyPart.PROPERTY_SELECTION_CHANGED, null));
	}
	
	
	private ToolBar initToolBar( Composite parent ) {
		ToolBar toolBar = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);
		toolBar.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		FormData fdToolBar = new FormData();
		fdToolBar.top = new FormAttachment(0, 0);
		fdToolBar.left = new FormAttachment(0, 0);		
		toolBar.setLayoutData(fdToolBar);
		
		ToolbarUtils.addCheckItem(toolBar, messages.toolbar_map, state -> {
			if (editor != null) {
				editor.getContentViewer().setShowMap(state);
			}		
		}, showMap);
		
		ToolbarUtils.addCommandItem(toolBar, IconsMapping.CENTERING, messages.toolbar_centering, () -> editor.adjustWindow());
	
		return toolBar;
	}
	
	private void onNewFileLoaded(GraphicalEditor editor) {
		Scenario scenario = appData.getScenario();
		if (scenario != null) {
			parent.setEnabled(true);
			editor.setScenario(scenario);
			if(!scenario.getNodes().isEmpty()) {
				editor.getContentViewer().navigateTo(scenario.getNodes().get(0), false);
			}else {
				editor.adjustWindow();
			}
		}
	}
}



