package com.company.tutorial3.application.parts.editor;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.emf.databinding.EMFProperties;

import com.company.tutorial3.application.utils.TreeElementType;
import com.company.tutorial3.application.utils.AbstractObjectsPart;
import com.company.tutorial3.application.utils.ObjectsPage;
import com.company.tutorial3.application.utils.Topics;
import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Scenario;
import com.amalgamasimulation.desktop.binding.UpdateValueStrategyFactory;

public class ObjectsPart extends AbstractObjectsPart{

	@SuppressWarnings("all")
	protected IObservableValue<Scenario> scenarioObservable = new WritableValue<>();
	@SuppressWarnings("all")
	private IObservableList<Node> nodeListObservable = EMFProperties.list(DatamodelPackage.Literals.SCENARIO__NODES).observeDetail(scenarioObservable);
	@SuppressWarnings("all")
	private IObservableList<Arc> arcListObservable = EMFProperties.list(DatamodelPackage.Literals.SCENARIO__ARCS).observeDetail(scenarioObservable);
	
	@Override
	protected void registerPages() {
		eventBroker.subscribe(Topics.NEW_SCENARIO, event -> scenarioObservable.setValue(appData.getScenario()));		
		new ObjectsPage<Node>(this, DatamodelPackage.Literals.SCENARIO__NODES, TreeElementType.NODE, null, null, null)
				.setTableRefreshBinding(
			DatamodelPackage.Literals.NODE__X,
					DatamodelPackage.Literals.NODE__ID,
					DatamodelPackage.Literals.NODE__NAME,
			DatamodelPackage.Literals.NODE__Y
											)
				.setAfterCreateTableElementAction(tableView -> {
					tableView.addColumn(messages.obj_NODE_col_ID, 120, Node::getId)
						.setIdTextEditingSupport(nodeListObservable, DatamodelPackage.Literals.NODE__ID);
					tableView.addColumn(messages.obj_NODE_col_NAME, 100, p -> p == null ? "" : p.getName())
						.setTextEditingSupport(DatamodelPackage.Literals.NODE__NAME, UpdateValueStrategyFactory.stringIsNotEmpty());
					tableView.addColumn(messages.obj_NODE_col_X, 100, Node::getX).setTextEditingSupport(
							DatamodelPackage.Literals.NODE__X,
							UpdateValueStrategyFactory.doubleAny());
					tableView.addColumn(messages.obj_NODE_col_Y, 100, Node::getY).setTextEditingSupport(
							DatamodelPackage.Literals.NODE__Y,
							UpdateValueStrategyFactory.doubleAny());
				});
		
		new ObjectsPage<Arc>(this, DatamodelPackage.Literals.SCENARIO__ARCS, TreeElementType.ARC, null, null,null)
				.setTableRefreshBinding(DatamodelPackage.Literals.ARC__SOURCE, DatamodelPackage.Literals.ARC__ID,DatamodelPackage.Literals.ARC__NAME,
					DatamodelPackage.Literals.ARC__DEST)
				.setAfterCreateTableElementAction(tableView -> {
					tableView.addColumn(messages.obj_ARC_col_ID, 100, Arc::getId)
						.setIdTextEditingSupport(arcListObservable, DatamodelPackage.Literals.ARC__ID);
					tableView.addColumn(messages.obj_ARC_col_NAME, 100, p -> p == null ? "" : p.getName())
						.setTextEditingSupport(DatamodelPackage.Literals.ARC__NAME, UpdateValueStrategyFactory.stringIsNotEmpty());
					tableView.addColumn(messages.obj_ARC_col_SOURCE, 100, Arc::getSource, p -> p == null ? "" : p.getId())
							.setAutoCompleteComboEditingSupport(nodeListObservable,
									DatamodelPackage.Literals.ARC__SOURCE, DatamodelPackage.Literals.NODE__ID);
					tableView.addColumn(messages.obj_ARC_col_DEST, 100, Arc::getDest, p -> p == null ? "" : p.getId())
							.setAutoCompleteComboEditingSupport(nodeListObservable,
									DatamodelPackage.Literals.ARC__DEST, DatamodelPackage.Literals.NODE__ID);
				});
		}
}

