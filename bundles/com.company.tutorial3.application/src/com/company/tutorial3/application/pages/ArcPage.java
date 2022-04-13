package com.company.tutorial3.application.pages;


import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.FeaturePath;

import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;
import com.amalgamasimulation.desktop.binding.UpdateValueStrategyFactory;

public class ArcPage extends AbstractPage<Arc> {
	
	@SuppressWarnings("unchecked")
	private IObservableList<Node> nodeListObservable = EMFProperties.list(DatamodelPackage.Literals.SCENARIO__NODES).observeDetail(scenarioObservable);

	
	public ArcPage(Messages messages) {
		super(messages, Arc::getScenario);
	}
	
	@Override
	public boolean isVisible(Object selectedObject) {
		return selectedObject instanceof Arc;
	}
	
	@Override
	protected String getNameClassObject() {
		return messages.obj_ARC;
	}
	
	@Override
	protected String getObjectDisplayName() {
		return observable.getValue().getId() + " - " + observable.getValue().getName();
	}
	
	@Override
	protected final FeaturePath[] getUpdateListeners() {
		return new FeaturePath [] {
				FeaturePath.fromList(DatamodelPackage.Literals.ARC__ID),
				FeaturePath.fromList(DatamodelPackage.Literals.ARC__NAME)};
	}
	
	@Override
	protected void createControlsInternal() {
		addStringSection(messages.obj_ARC_col_ID, DatamodelPackage.Literals.ARC__ID)
			.addIdTextbox(scenarioObservable, DatamodelPackage.Literals.SCENARIO__ARCS);
		addStringSection(messages.obj_ARC_col_NAME, DatamodelPackage.Literals.ARC__NAME)
			.addTextbox(UpdateValueStrategyFactory.stringIsNotEmpty());
		addReferenceSection(messages.obj_ARC_col_SOURCE, DatamodelPackage.Literals.ARC__SOURCE)
			.addAutoCompleteTextbox(DatamodelPackage.Literals.NODE__ID, nodeListObservable)
			.addSelectionDialogButton(messages.object_for_select_dialog_node_source, nodeListObservable, tableView -> {
				tableView.addColumn(messages.obj_NODE_col_ID, 150, node -> node.getId());
				tableView.addColumn(messages.obj_NODE_col_NAME, 150, node -> node.getName());
			});
		addReferenceSection(messages.obj_ARC_col_DEST, DatamodelPackage.Literals.ARC__DEST)
			.addAutoCompleteTextbox(DatamodelPackage.Literals.NODE__ID, nodeListObservable)
			.addSelectionDialogButton(messages.object_for_select_dialog_node_dest, nodeListObservable, tableView -> {
				tableView.addColumn(messages.obj_NODE_col_ID, 150, node -> node.getId());
				tableView.addColumn(messages.obj_NODE_col_NAME, 150, node -> node.getName());
			});
	}
}


