package com.company.tutorial3.application.utils;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;


import com.company.tutorial3.application.parts.editor.TreePart.TreeElementType;
import com.amalgamasimulation.desktop.binding.ListChangeManager;
import com.amalgamasimulation.desktop.properties.PropertyPart;
import com.amalgamasimulation.desktop.ui.views.TableView;
import com.amalgamasimulation.desktop.ui.views.ToolBarComposite;
import com.amalgamasimulation.desktop.utils.ToolbarUtils;
public class ObjectsPage<T extends EObject> {

	private AbstractObjectsPart tablePart;
	private IObservableList<T> elementsList = new WritableList<>();
	private WritableValue<EObject> observableContainer = new WritableValue<>();
	private TableView<T> tableView;
	private TreeElementType treeElementType;
	private EReference contentsFeature;

	private Supplier<T> newElementSupplier;
	private Consumer<T> removeElement;
	private UnaryOperator<T> copyElement;
	
	public ObjectsPage(AbstractObjectsPart tablePart, EReference contentsFeature, TreeElementType treeElementType, 
			Supplier<T> newElementSupplier, Consumer<T> removeElement, UnaryOperator<T> copyElement) {
		this.tablePart = tablePart;
		this.contentsFeature = contentsFeature;
		this.treeElementType = treeElementType;
		this.newElementSupplier = newElementSupplier;
		this.removeElement = removeElement;
		this.copyElement = copyElement;
		tablePart.registerPage(this);
	}

	public ObjectsPage<T> setTableRefreshBinding(EStructuralFeature eStructuralFeature, EStructuralFeature... eStructuralFeatures) {
		new ListChangeManager<T>(elementsList, c -> tableView.refresh(c.getObservable().getValue(), true), eStructuralFeature, eStructuralFeatures);
		return this;
	}

	public void setContainer(EObject obj) {
		observableContainer.setValue(obj);
	}

	public void refresh() {
		if (tableView != null && elementsList != null) {
			for (Object o : elementsList) {
				tableView.refresh(o);
			}
			tableView.refresh();
		}
	}

	public void setSelectionObject(Object selection, boolean navigateTo) {
		if (selection != null) {
			tableView.setSelection(new StructuredSelection(selection), true);
			tablePart.eventBroker.send(PropertyPart.PROPERTY_SELECTION_CHANGED, selection);
			if(navigateTo) {
				tablePart.eventBroker.send(Topics.NAVIGATE_TO, selection);	
			}
		}
	}
	
	public void selectedFirstElement() {
		if (!elementsList.isEmpty()) {
			setSelectionObject(elementsList.get(0), true);
		}
	}

	public TreeElementType getTreeElementType() {
		return treeElementType;
	}

	protected void createControls(Composite parent) {
		new ToolBarComposite(parent, this::initializeToolBar, this::initContents);
	}

	@SuppressWarnings("unchecked")
	protected void initContents(Composite parent) {
		elementsList = EMFProperties.list(contentsFeature).observeDetail(observableContainer);

		tableView = new TableView<>(parent, elementsList);		
		tableView.setQuickFilterAllowed(true);
		ObservableListContentProvider<T> locationListContentProvider = new ObservableListContentProvider<>();
		tableView.setContentProvider(locationListContentProvider);
		tableView.setInput(elementsList);
		tableView.getTable().addSelectionListener(SelectionListener.widgetSelectedAdapter(c -> {
			Object selection = ((IStructuredSelection) tableView.getSelection()).getFirstElement();
			tablePart.eventBroker.send(PropertyPart.PROPERTY_SELECTION_CHANGED, selection);
			tablePart.eventBroker.send(Topics.NAVIGATE_TO, selection);	
		}));

	}

	public ObjectsPage<T> setAfterCreateTableElementAction(Consumer<TableView<T>> afterCreateTable) {
		afterCreateTable.accept(tableView);
		return this;
	}

	private void initializeToolBar(Composite parent) {
		if (newElementSupplier == null && removeElement == null && copyElement == null) {
			return;
		}
		ToolBar toolBar = new ToolBar(parent, SWT.HORIZONTAL);
		if (newElementSupplier != null) {
			ToolbarUtils.addCommandItem(toolBar, IconsMapping.ADD, tablePart.messages.button_add, this::addElement);
		}
		if (removeElement != null) {
			ToolbarUtils.addCommandItem(toolBar, IconsMapping.REMOVE, tablePart.messages.button_remove,
					this::removeElement);
		}
		if (copyElement != null) {
			ToolbarUtils.addCommandItem(toolBar, IconsMapping.COPY, tablePart.messages.button_copy, this::copyElement);
		}
		ToolbarUtils.addSeparator(toolBar);
	}

	private void addElement() {
		if (observableContainer.getValue() != null) {
			EObject obj = newElementSupplier.get();
			refresh();
			if (obj != null) {
				setSelectionObject(obj, true);
				refresh();
			}
		}
	}
	
	private void copyElement() {
		int rowIndex = tableView.getFirstSelectedRowIndex();
		if (rowIndex >= 0) {
			T element = copyElement.apply(tableView.getItem(rowIndex));
			if (element != null) {
				setSelectionObject(element,true);
				refresh();
			}
		}
	}
	
	private void removeElement() {
		int rowIndex = tableView.getFirstSelectedRowIndex();
		if (rowIndex >= 0) {
			T element = tableView.getItem(rowIndex);
			int newSelectedRowIndex = rowIndex > 0 ? rowIndex - 1 : 0;
			newSelectedRowIndex = com.amalgamasimulation.utils.Utils.limit(0, newSelectedRowIndex, tableView.getTable().getItemCount() - 1);
			if (tableView.getTable().getItemCount() > 0) {
				removeElement.accept(element);
				setSelectionObject(tableView.getElementAt(newSelectedRowIndex), true);
			}
			refresh();
		}
	}
}

