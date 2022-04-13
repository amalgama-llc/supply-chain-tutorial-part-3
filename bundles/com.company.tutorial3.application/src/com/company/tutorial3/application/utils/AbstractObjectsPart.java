package com.company.tutorial3.application.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

import com.company.tutorial3.application.parts.editor.TreePart.TreeElementType;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.common.states.AppData;
import com.amalgamasimulation.desktop.ui.views.TableView;

public abstract class AbstractObjectsPart {

	@Inject
	protected AppData appData;
	@Inject
	@Translation
	protected Messages messages;

	@Inject
	protected IEventBroker eventBroker;
	
	@Inject
	protected EPartService fPartService;
	
	@Inject
	protected MPart part;
	protected Composite parent;
	
	private Composite contentsComposite;
	private int minWidth = 600;
	private int minHeight = 180;
	private List<ObjectsPage<?>> tablePages = new ArrayList<>();
	private Map<ObjectsPage<?>, ScrolledComposite> tablePageScrolledComposites = new HashMap<>();
	private Map<ObjectsPage<?>, Boolean> tablePagesVisibilityMap = new HashMap<>();
	
	protected abstract void registerPages();
	
	public Consumer<TableView<?>> addLabelProvider(EStructuralFeature structuralFeature, String columnName, Function<Object, ?> extractor) {	
		Consumer<TableView<?>> consumer = tableView -> {
		
			tableView.addColumn(columnName, 160, t -> extractor.apply(t));
			ObservableListContentProvider<?> contentProvider = new ObservableListContentProvider<>();
		
			tableView.setLabelProvider(new ObservableMapLabelProvider(EMFObservables.observeMaps(contentProvider.getKnownElements(), new EStructuralFeature[]{
					structuralFeature
				})));
			tableView.setContentProvider(contentProvider);
		};
		return consumer;
	}
	
	protected final void registerPage(ObjectsPage<?> tablePage) {
		tablePages.add(tablePage);
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(contentsComposite, SWT.NONE | SWT.V_SCROLL | SWT.H_SCROLL);
		scrolledComposite.setLayout(new FormLayout());
		FormData fdScrolledComposite = new FormData();
		fdScrolledComposite.top = new FormAttachment(0, 0);
		fdScrolledComposite.left = new FormAttachment(0, 0);
		fdScrolledComposite.right = new FormAttachment(100, 0);
		fdScrolledComposite.bottom = new FormAttachment(100, 0);
		scrolledComposite.setLayoutData(fdScrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setMinSize(minWidth, minHeight);
		
		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setLayout(new FormLayout());
		FormData fdParent = new FormData();
		fdParent.top = new FormAttachment(0, 0);
		fdParent.left = new FormAttachment(0, 0);
		fdParent.right = new FormAttachment(100, 0);
		fdParent.bottom = new FormAttachment(100, 0);
		composite.setLayoutData(fdParent);
		scrolledComposite.setContent(composite);
		scrolledComposite.setVisible(false);
		
		tablePageScrolledComposites.put(tablePage, scrolledComposite);
		tablePagesVisibilityMap.put(tablePage, false);
		tablePage.createControls(composite);
	}
	
	
	private void setSelectionPage(TreeElementType selectedTreeElement) {
		if (selectedTreeElement == null) {
			for (ObjectsPage<?> tablePage : tablePages) {
				tablePagesVisibilityMap.put(tablePage, false);
				tablePageScrolledComposites.get(tablePage).setVisible(false);
				tablePage.setContainer(null);
			}
			return;
		}
				
		for (ObjectsPage<?> tablePage : tablePages) {
			tablePagesVisibilityMap.put(tablePage, false);
			tablePageScrolledComposites.get(tablePage).setVisible(false);
			if(tablePage.getTreeElementType() == selectedTreeElement) {
				tablePagesVisibilityMap.put(tablePage, true);
				tablePageScrolledComposites.get(tablePage).setVisible(true);
				tablePage.setContainer(appData.getScenario());
			}
		}
	}
	
	
	private void setSelectionObjectInPage(Object selection) {
		if(selection == null) {
			for (ObjectsPage<?> tablePage : tablePages) {
				if (tablePagesVisibilityMap.get(tablePage)) {
					tablePage.selectedFirstElement();
				}
			}
			return;
		}
		for (ObjectsPage<?> tablePage : tablePages) {
			if (tablePagesVisibilityMap.get(tablePage)) {
				tablePage.setSelectionObject(selection, false);
			}
		}
	}

	
	@PostConstruct
	public void postConstruct(Composite parent, MPart mPart) {
		parent.setLayout(new FormLayout());
		this.parent = parent;
		contentsComposite = new Composite(parent, SWT.NONE);
		contentsComposite.setLayout(new FormLayout());
		contentsComposite.setBackground(new Color(null, 255, 255, 255));
		FormData contentsCompositeFormData = new FormData();
		contentsCompositeFormData.top = new FormAttachment(0, 0);
		contentsCompositeFormData.left = new FormAttachment(0, 0);
		contentsCompositeFormData.right = new FormAttachment(100, 0);
		contentsCompositeFormData.bottom = new FormAttachment(100, 0);
		contentsComposite.setLayoutData(contentsCompositeFormData);
		
		registerPages();
		
		eventBroker.subscribe(Topics.CHANGE_VISIBILITY_TABLE_PAGE, event -> setSelectionPage((TreeElementType)event.getProperty(IEventBroker.DATA)));
		eventBroker.subscribe(Topics.CHANGE_SELECTED_OBJECT_IN_TABLE_PAGE, event -> setSelectionObjectInPage((event.getProperty(IEventBroker.DATA))));
		eventBroker.subscribe(Topics.NEW_SCENARIO, event -> setSelectionPage(null));
	}
	
}



