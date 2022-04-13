package com.company.tutorial3.application.parts.editor;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.databinding.viewers.ObservableListTreeContentProvider;
import org.eclipse.jface.databinding.viewers.TreeStructureAdvisor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;

import com.company.tutorial3.application.parts.editor.treeelements.TreeElement;
import com.company.tutorial3.application.parts.editor.treeelements.TreeElementScenario;
import com.company.tutorial3.application.utils.EditorTreeLabelProvider;
import com.company.tutorial3.application.utils.MainWindowTitleUpdater;
import com.company.tutorial3.application.utils.Topics;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.common.states.AppData;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Scenario;
import com.amalgamasimulation.desktop.properties.PropertyPart;

public class TreePart {

	@Inject
	@Translation
	public Messages messages;

	@Inject
	private AppData appData;
	
	@Inject
	protected IEventBroker eventBroker;
	
	private IObservableValue<Scenario> currentScenarioObservable = new WritableValue<>();
	private IObservableList<TreeElement> rootTreeElementsObservable = new WritableList<>();
	private ObservableListTreeContentProvider<TreeElement> contentProvider;
	private TreeViewer treeViewer;
	private List<EStructuralFeature> dependentScenarioListFields = new ArrayList<>();
	
	@Inject
	protected  IWorkbench workbench;

	private final void refresh() {
		if (treeViewer != null && !treeViewer.getTree().isDisposed()) {
			for (TreeElement o : rootTreeElementsObservable) {
				treeViewer.refresh(o);
			}
		}
	}
	
	private void viewItem(TreeElementType treeElement) {
		treeViewer.expandAll();
		TreeItem treeItem = findTreeItem(treeElement, treeViewer.getTree().getItems());
		if(treeItem != null) {
			treeViewer.setSelection(new StructuredSelection(treeItem.getData()));
		}
	}
	
	private TreeItem findTreeItem(TreeElementType treeElement, TreeItem [] treeItems) {
		TreeItem res = null;
		for(TreeItem treeItem: treeItems) {
			TreeElement data = (TreeElement)treeItem.getData();
			if(data.getTreeElementType() == treeElement) {
				res = treeItem;
				break;
			}
			res = findTreeItem(treeElement, treeItem.getItems());
			if(res != null) {
				break;
			}
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void postConstruct(Composite parent) {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));
		initializeDependentScenarioListFields(dependentScenarioListFields);
		initializeTreeViewer(parent);
		
		treeViewer.setInput(rootTreeElementsObservable);
		treeViewer.getTree().setMenu(new MenuManager().createContextMenu(treeViewer.getTree()));
		eventBroker.subscribe(Topics.NEW_SCENARIO, event -> onNewFileLoaded());
		eventBroker.subscribe(Topics.CHANGE_SELECTED_TREE_ELEMENT, event -> viewItem((TreeElementType)event.getProperty(IEventBroker.DATA)));
		onNewFileLoaded();
		
		dependentScenarioListFields.stream().forEach(feature -> EMFProperties.list(feature).observeDetail(currentScenarioObservable).addListChangeListener(event -> refresh()));
		createLabelMainWindow();
	}
	
	private void initializeTreeViewer(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI) {
			@Override
			protected void handleDispose(DisposeEvent event) {
				try {
					contentProvider.dispose();
				} catch (Exception e) {
					System.out.println("contentTreeProvider exception");
				}
			}
		};
		contentProvider = new ObservableListTreeContentProvider<> (
				(Object target) -> {
					if (target instanceof IObservableList<?>) {
						return (IObservableList<TreeElement>) target;
					}
					if (target instanceof TreeElement) {
						return ((TreeElement) target).getChildElements();
					}
					return new WritableList<>();
				},
				new TreeStructureAdvisor<Object>() {
					@Override
					public Object getParent(Object element) {
						return super.getParent(element);
					}

					@Override
					public Boolean hasChildren(Object element) {
						return super.hasChildren(element);
					}
				}
		);
		treeViewer.setLabelProvider(new EditorTreeLabelProvider() {
			@Override
			public String getIconPath(Object object) {
				if (object instanceof TreeElement){
					TreeElementType treeElementType = ((TreeElement)object).getTreeElementType();
					return treeElementType.iconPath;
				}
				return "icons/dummy.png";
			}
			@Override
			public String getName(Object object) {
				if (object instanceof TreeElement){
					TreeElement treeElement = (TreeElement)object;
					return treeElement.getName();
				}
				return String.valueOf(object);
			}
		});
		
		treeViewer.getTree().addSelectionListener(SelectionListener.widgetSelectedAdapter(l -> {
			Object object = treeViewer.getStructuredSelection().getFirstElement();
			if (object instanceof TreeElement) {
				TreeElement treeElement = (TreeElement)object;
				TreeElementType treeElementType = treeElement.getTreeElementType();
				eventBroker.send(Topics.CHANGE_VISIBILITY_TABLE_PAGE, treeElementType);
				eventBroker.send(Topics.CHANGE_SELECTED_OBJECT_IN_TABLE_PAGE, null);
				if (treeElementType == TreeElementType.SCENARIO) {
					eventBroker.send(PropertyPart.PROPERTY_SELECTION_CHANGED, treeElement.getEObject());
				}
			}
		}));
		
		treeViewer.setContentProvider(contentProvider);
	}
	
	@SuppressWarnings({"all"})
	protected void createLabelMainWindow() {
		IValueChangeListener<Scenario> modelChangeListener = new IValueChangeListener<Scenario>() {
			@Override
			public void handleValueChange(ValueChangeEvent<? extends Scenario> event) {
				MainWindowTitleUpdater.updateMainWindowTitle(workbench, appData);
			}
		};
		IObservableValue<Scenario> detailedValue1 = EMFProperties.value(DatamodelPackage.Literals.SCENARIO__NAME).observeDetail(currentScenarioObservable);
		detailedValue1.addValueChangeListener(modelChangeListener);
		
		IObservableValue<Scenario> detailedValue2 = EMFProperties.value(DatamodelPackage.Literals.SCENARIO__BEGIN_DATE).observeDetail(currentScenarioObservable);
		detailedValue2.addValueChangeListener(modelChangeListener);
		
		IObservableValue<Scenario> detailedValue3 = EMFProperties.value(DatamodelPackage.Literals.SCENARIO__END_DATE).observeDetail(currentScenarioObservable);
		detailedValue3.addValueChangeListener(modelChangeListener);
		
		MainWindowTitleUpdater.updateMainWindowTitle(workbench, appData);
	}
		
	private void onNewFileLoaded() {
		rootTreeElementsObservable.clear();
		treeViewer.getTree().clearAll(true);
		
		refresh();
		Scenario scenario = appData.getScenario();	
		if (scenario != null) {
			rootTreeElementsObservable.add(new TreeElementScenario(scenario, messages));
			treeViewer.expandAll();
		}
		currentScenarioObservable.setValue(scenario);
		refresh();
	}
	
	private void initializeDependentScenarioListFields(List<EStructuralFeature> list) {
		list.add(DatamodelPackage.Literals.SCENARIO__NODES);
		list.add(DatamodelPackage.Literals.SCENARIO__ARCS);
	}

	public enum TreeElementType {
		NETWORK("/icons/transportation_net.png"),
		ARC("/icons/link.png"),
		NODE("/icons/location.png"),
		SCENARIO("/icons/producte_structure.png");
		
		private final String iconPath;
		
		private TreeElementType(String iconPath) {
			this.iconPath = iconPath;
		}

		public String getIconPath() {
			return iconPath;
		}		
		
	}
	
}



