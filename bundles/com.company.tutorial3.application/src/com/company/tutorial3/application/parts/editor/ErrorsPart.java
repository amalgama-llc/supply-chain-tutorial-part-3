package com.company.tutorial3.application.parts.editor;


import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.databinding.viewers.ObservableListTreeContentProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import com.amalgamasimulation.desktop.utils.MessageManager;

import com.company.tutorial3.application.utils.IconsMapping;
import com.company.tutorial3.application.utils.Topics;
import com.company.tutorial3.application.utils.validation.ErrorType;
import com.company.tutorial3.application.utils.validation.ObjectType;
import com.company.tutorial3.application.utils.validation.Problem;
import com.company.tutorial3.application.utils.validation.ValidationManager;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.common.states.AppData;
import com.amalgamasimulation.desktop.properties.PropertyPart;
import com.amalgamasimulation.desktop.ui.views.TreeView;
import com.amalgamasimulation.desktop.ui.views.TreeViewerColumnDescriptor;
import com.amalgamasimulation.desktop.utils.ToolbarUtils;
public class ErrorsPart {

	@Inject
	@Translation
	private Messages messages;
	
	@Inject
	private MessageManager messageManager;
	
	@Inject
	private EPartService partService;
	
	@Inject
	private AppData appData;
	
	@Inject
	private MPart part;
	
	private TreeView<ErrorTreeItem> treeViewer;
	private static final int MAX_CHILD_ROWS = 100;	
	private IObservableList<ErrorTreeItem> mainParts = new WritableList<>();

	@SuppressWarnings("all")
	private ObservableListTreeContentProvider contentProvider;


	protected void setNewScenario() {
		mainParts.clear();
		treeViewer.setData(mainParts);
		treeViewer.updateContent();
		treeViewer.updateView();
	}

	private void updateTable(ValidationManager validationManager) {
		mainParts.clear();
		Map<ErrorType, List<ErrorTreeItem>> mapOfSecondLevelElements = new EnumMap<>(ErrorType.class);
		for (Map.Entry<ObjectType, Map<Object, Map<ErrorType, List<Problem>>>> pair : validationManager.getProblems().entrySet()) {
			ObjectType objectType = pair.getKey();
			Map<Object, Map<ErrorType, List<Problem>>> map = pair.getValue();
			for (ErrorType errorType : ErrorType.values()) {
				ErrorTreeObjTypeItem treeItemSecondLevel = new ErrorTreeObjTypeItem(objectType);
				for (Map<ErrorType, List<Problem>> mmm : map.values()) {
					List<Problem> problems = mmm.get(errorType);
					if (problems != null && !problems.isEmpty()) {
						problems.stream().map(ErrorTreeLeafItem::new).forEach(treeItemSecondLevel.childItems::add);
					}
				}
				if (!treeItemSecondLevel.childItems.isEmpty()) {
					mapOfSecondLevelElements.computeIfAbsent(errorType, e -> new ArrayList<>()).add(treeItemSecondLevel);
				}
			}
		}
		
		// Errors go before warnings
		for (ErrorType errorType : List.of(ErrorType.ERROR, ErrorType.WARNING)) {
			List<ErrorTreeItem> listOfSecondLevelItems = mapOfSecondLevelElements.get(errorType);
			String label = errorType == ErrorType.ERROR ? messages.label_error : messages.label_warning;
			if (listOfSecondLevelItems != null && !listOfSecondLevelItems.isEmpty()) {
				ErrorTreeItem mainPart = new ErrorTreeErrorTypeItem(errorType, label, listOfSecondLevelItems);
				mainParts.add(mainPart);
			}
		}
		treeViewer.setData(mainParts);
		treeViewer.expandAll();
	}

	private void refreshErrors() {
		ValidationManager validationManager = new ValidationManager(messages);
		validationManager.validate(messageManager, appData.getScenario(), partService);
		updateTable(validationManager);
	}
	
	private  void activatePart(String partID) {
		MPart mPart = partService.findPart(partID);
		if (mPart != null) {
			partService.activate(mPart);
		}
	}

	@SuppressWarnings("all")
	@PostConstruct
	private void postConstruct(Composite parent) {
		messageManager.subscribe(Topics.NEW_SCENARIO, s -> setNewScenario());
		messageManager.subscribe(Topics.PROBLEMS_PART, s -> {
			activatePart(part.getElementId());
			updateTable((ValidationManager)s);
		});
		ToolBar toolBar = new ToolBar(parent, SWT.FLAT | SWT.HORIZONTAL);
		ToolbarUtils.addCommandItem(toolBar, IconsMapping.REFRESH, messages.toolbar_update, () -> refreshErrors()).setText(messages.toolbar_update);
		treeViewer = new TreeView<ErrorTreeItem>(parent, t -> getSublist(t.getChildItems(), MAX_CHILD_ROWS), mainParts);
		treeViewer.addDoubleClickListener(e -> {
			ErrorTreeItem it = (ErrorTreeItem) ((StructuredSelection) treeViewer.getSelection()).getFirstElement();
			if (it instanceof ErrorTreeLeafItem) {
				activatePartAndViewObject((ErrorTreeLeafItem)it);
			}
		});

		TreeViewerColumnDescriptor<ErrorTreeItem, ErrorTreeItem> des = treeViewer.addColumn(messages.column_description, 700, e -> e);
		des.setLabelExtractor(e -> getStyledTextForViewLabelProvider(e).toString());
		des.getViewerColumn().setLabelProvider(new DelegatingStyledCellLabelProvider(new ViewLabelProvider()));
		treeViewer.addColumn(messages.column_object, 150, treeItem -> treeItem instanceof ErrorTreeLeafItem ? ((ErrorTreeLeafItem)treeItem).id : "");
		treeViewer.setData(mainParts);
		setNewScenario();
	}

	private <T> List<T> getSublist(List<T> list, int maxElements) {
		return list.size() <= maxElements ? list : list.subList(0, maxElements);
	}

	private class ViewLabelProvider extends LabelProvider implements IStyledLabelProvider {

		public ViewLabelProvider() {}

		@Override
		public StyledString getStyledText(Object e) {
			return getStyledTextForViewLabelProvider(e);
		}

		@Override
		public Image getImage(Object e) {
			if (e instanceof ErrorTreeItem) {
				return ((ErrorTreeItem) e).getImage();
			}
			return null;
		}

		@Override
		public void dispose() {
		}
	}

	private StyledString getStyledTextForViewLabelProvider(Object e) {
		if (e instanceof ErrorTreeItem) {
			return ((ErrorTreeItem) e).getStyledText();
		}
		return null;
	}


	private abstract static class ErrorTreeItem {
		public ObjectType objectType;
		public List<ErrorTreeItem> childItems = new ArrayList<>();

		protected ErrorTreeItem() {}
		
		public List<ErrorTreeItem> getChildItems() {
			return childItems;
		}
		
		public abstract StyledString getStyledText();
		public abstract Image getImage();
	}
	
	private class ErrorTreeErrorTypeItem extends ErrorTreeItem {
		private String str;
		public ErrorType type;

		public ErrorTreeErrorTypeItem(ErrorType type, String str, List<ErrorTreeItem> childItems) {
			this.type = type;
			this.str = str;
			this.childItems = childItems;
		}
		
		public StyledString getStyledText() {
			return new StyledString(str);
		}
		
		public Image getImage() {
			return IconsMapping.getImage(type == ErrorType.ERROR ? "/icons/errors.png" : "/icons/warning.png");
		}		
	}
	
	private class ErrorTreeObjTypeItem extends ErrorTreeItem {
		public ErrorTreeObjTypeItem(ObjectType name) {
			this.objectType = name;
		}
		
		public StyledString getStyledText() {
			int problemsSize = childItems.size();
			return new StyledString(objectType.getName() + " : " + (problemsSize <= MAX_CHILD_ROWS ? problemsSize : (MAX_CHILD_ROWS + " из " + problemsSize)));
		}
		
		public Image getImage() {
			return IconsMapping.getImage(objectType.getTreeElementType().getIconPath());
		}
	}
	
	private class ErrorTreeLeafItem extends ErrorTreeItem {
		public Object object;
		public String id;
		public String description;
		public ErrorType type;
		public List<Object> otherObjects;
				
		public ErrorTreeLeafItem(Problem problem) {
			this.objectType = problem.getObjectType();
			this.object = problem.getObject();
			this.id = problem.getId();
			this.description = problem.getDescription();
			this.type = problem.getErrorType();
			this.otherObjects = problem.getOtherObjects();
		}
		
		public StyledString getStyledText() {
			return new StyledString(description);
		}
		
		public Image getImage() {
			return IconsMapping.getImage(type == ErrorType.ERROR ? "/icons/errors.png" : "/icons/warning.png");
		}
	}

	private void activatePartAndViewObject(ErrorTreeLeafItem treeItem) {
		Object o = treeItem.object;			
		messageManager.send(Topics.CHANGE_VISIBILITY_TABLE_PAGE, treeItem.objectType.getTreeElementType());
		messageManager.send(Topics.CHANGE_SELECTED_TREE_ELEMENT, treeItem.objectType.getTreeElementType());
		messageManager.send(Topics.CHANGE_SELECTED_OBJECT_IN_TABLE_PAGE, o);
		messageManager.send(PropertyPart.PROPERTY_SELECTION_CHANGED, o);			
	}
}

