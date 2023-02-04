package com.company.tutorial3.application.parts.editor;

import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;

import com.company.tutorial3.application.utils.TreeElementType;
import com.company.tutorial3.application.utils.IconsMapping;
import com.company.tutorial3.application.graphicaleditor.GraphicalEditorModule;
import com.company.tutorial3.application.utils.Topics;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.common.states.AppData;
import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Scenario;
import com.company.tutorial3.common.command.CommandFactory;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.amalgamasimulation.desktop.properties.PropertyPage;
import com.amalgamasimulation.desktop.properties.PropertyPart;
import com.amalgamasimulation.desktop.utils.ToolbarUtils;
import com.amalgamasimulation.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.layout.FillLayout;
import com.google.inject.Guice;
import com.google.inject.util.Modules;
import com.amalgamasimulation.desktop.ui.views.ToolBarComposite;
import com.amalgamasimulation.graphicaleditor.GraphicalEditorUiModule;
import com.amalgamasimulation.graphicaleditor.palette.nodes.PaletteNode;
import com.amalgamasimulation.graphicaleditor.palette.nodes.PaletteNodesFactory;
import com.amalgamasimulation.graphicaleditor.views.GraphicalEditor;

public class MapPart {
	

	@Inject
	private IEventBroker eventBroker;


	private ToolBar toolBar;
	
	@Inject
	private EModelService modelService;
	
	@Inject
	private MApplication application;
	
	@Inject
	private EPartService partService;
	
	@Inject
	@Translation
	private Messages messages;

	@Inject
		private AppData appData;
		
		private GraphicalEditor<Scenario> editor;
		private Consumer<Object> onObjectSelectedHandler;
		private Predicate<Object> onObjectSelectedFilter;
		
		protected void initializaToolBar( Composite parent ) {
			toolBar = new ToolBar(parent, SWT.HORIZONTAL);	
			
			ToolbarUtils.addCommandItem(toolBar, IconsMapping.CENTERING, messages.toolbar_centering, () -> editor.adjustWindow()).setText(messages.toolbar_centering);	
		}
			
		@PostConstruct
		public void postConstruct(MPart mPart, Composite parent) {
			new ToolBarComposite(parent, this::initializaToolBar, p -> initContents(p, mPart));
		}
			
		private void initContents(Composite parent, MPart mPart) {
			parent.setLayout( new FillLayout() );
			editor = new GraphicalEditor<Scenario>(parent,CommandFactory.CROSS_REFERENCE_ADAPTER, 
					Guice.createInjector(Modules.override(new GraphicalEditorModule()).with(new GraphicalEditorUiModule()))) {
				@Override
				protected List<PaletteNode> createPaletteViewerContents(Scenario container) {
					List<PaletteNode> paletteNodes = new ArrayList<>();
					paletteNodes.add(PaletteNodesFactory.createNode("Node", DatamodelPackage.Literals.NODE));
					paletteNodes.add(PaletteNodesFactory.createArc("Arc", DatamodelPackage.Literals.ARC));
					return paletteNodes;
				}
				@Override
				public MApplication getApplication() {
					return application;
				}
				@Override
				public IEventBroker getEventBroker() {
					return eventBroker;
				}
				@Override
				public EModelService getModelService() {
					return modelService;
				}
				@Override
				public EPartService getPartService() {
					return partService;
				}
			};
			GraphicalEditor.invertYAxis = false;
			eventBroker.subscribe(Topics.NEW_SCENARIO, event -> onNewFileLoaded(editor));
			eventBroker.subscribe(Topics.ESC_KEY_TYPE, event -> editor.escKeyTyped());
			
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
					}
				}
			});
			
			eventBroker.subscribe(PropertyPage.WAITING_FOR_FILTERED_OBJECT_SELECTION, event -> {
				Pair<Predicate<Object>, Consumer<Object>> pair = (Pair<Predicate<Object>, Consumer<Object>>)event.getProperty(IEventBroker.DATA);
				this.onObjectSelectedFilter = pair.first;
				this.onObjectSelectedHandler = pair.second;
				editor.setWaitingForSelection(onObjectSelectedFilter);
	});
	
	eventBroker.subscribe(Topics.NAVIGATE_TO, e -> { 
		Object o = e.getProperty(IEventBroker.DATA);
		editor.getContentViewer().navigateTo(o);
		});
                           		
                           		
		onNewFileLoaded(editor);
	}
                           	
	private void onNewFileLoaded(GraphicalEditor<Scenario> editor) {
		Scenario scenario = appData.getScenario();
		editor.setContainer(scenario);
	}
}

