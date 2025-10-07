package com.company.tutorial3.application.parts.editor;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.emf.databinding.EMFProperties;

import com.amalgamasimulation.desktop.binding.ValidationStrategies;
import com.company.tutorial3.application.localization.Messages;
import com.company.tutorial3.application.utils.AbstractObjectsPart;
import com.company.tutorial3.application.utils.ObjectsPage;
import com.company.tutorial3.application.utils.Topics;
import com.company.tutorial3.application.utils.TreeElementType;
import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.Asset;
import com.company.tutorial3.datamodel.DatamodelFactory;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Scenario;
import com.company.tutorial3.datamodel.Store;
import com.company.tutorial3.datamodel.TruckType;
import com.company.tutorial3.datamodel.Warehouse;

public class ObjectsPart extends AbstractObjectsPart {

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
			DatamodelPackage.Literals.NODE__NAME,
			DatamodelPackage.Literals.NODE__Y
											)
				.setAfterCreateTableElementAction(tableView -> {
					tableView	.column(Node::getName)
								.name(Messages.messages().obj_NODE_col_NAME)
								.width(120)
								.emfTextIdEditor()
								.elements(nodeListObservable)
								.idFeature(DatamodelPackage.Literals.NODE__NAME)								
								.build();
					tableView	.column(Node::getX)
								.name(Messages.messages().obj_NODE_col_X)
								.width(100)
								.emfTextEditor()
								.feature(DatamodelPackage.Literals.NODE__X)
								.strategy(ValidationStrategies.doubleAny())
								.build();
					tableView	.column(Node::getY)
								.name(Messages.messages().obj_NODE_col_Y)
								.width(100)
								.emfTextEditor()
								.feature(DatamodelPackage.Literals.NODE__Y)
								.strategy(ValidationStrategies.doubleAny())
								.build();
				});
		
		new ObjectsPage<Arc>(this, DatamodelPackage.Literals.SCENARIO__ARCS, TreeElementType.ARC, null, null, null)
				.setTableRefreshBinding(DatamodelPackage.Literals.ARC__SOURCE, DatamodelPackage.Literals.ARC__ID, DatamodelPackage.Literals.ARC__NAME,
					DatamodelPackage.Literals.ARC__DEST)
				.setAfterCreateTableElementAction(tableView -> {
					tableView	.column(Arc::getId)
								.name(Messages.messages().obj_ARC_col_ID)
								.width(120)
								.emfTextIdEditor()
								.elements(arcListObservable)
								.idFeature(DatamodelPackage.Literals.ARC__ID)								
								.build();
					tableView	.column(Arc::getName)
								.name(Messages.messages().obj_ARC_col_NAME)
								.width(100)
								.emfTextEditor()
								.feature(DatamodelPackage.Literals.ARC__NAME)
								.strategy(ValidationStrategies.stringIsNotEmpty())
								.build();
					tableView	.column(Arc::getSource)
								.name(Messages.messages().obj_ARC_col_SOURCE)
								.width(100)
								.format(Node::getName)
								.emfAutoCompleteComboEditor()
								.elements(nodeListObservable)
								.feature(DatamodelPackage.Literals.ARC__SOURCE)
								.nameFeature(DatamodelPackage.Literals.NODE__NAME)
								.build();
					tableView	.column(Arc::getDest)
								.name(Messages.messages().obj_ARC_col_DEST)
								.width(100)
								.format(Node::getName)
								.emfAutoCompleteComboEditor()
								.elements(nodeListObservable)
								.feature(DatamodelPackage.Literals.ARC__DEST)
								.nameFeature(DatamodelPackage.Literals.NODE__NAME)
								.build();
				});
		
		new ObjectsPage<Warehouse>(this, DatamodelPackage.Literals.SCENARIO__WAREHOUSES, TreeElementType.WAREHOUSE, 
				()-> {
					var wh = DatamodelFactory.eINSTANCE.createWarehouse();
					wh.setScenario(scenarioObservable.getValue());
					return wh;
				}, null, null)
				.setTableRefreshBinding(DatamodelPackage.Literals.ASSET__ID, DatamodelPackage.Literals.ASSET__NAME, DatamodelPackage.Literals.ASSET__NODE)
				.setAfterCreateTableElementAction(tableView -> {
					tableView.column(Asset::getId).name("ID").width(100);
					tableView.column(Asset::getName).name("Name").width(100);
					tableView.column(Asset::getNode).name("Node").width(100).format(Node::getName);
				});
		
		new ObjectsPage<Store>(this, DatamodelPackage.Literals.SCENARIO__STORES, TreeElementType.STORE, 
				() -> {
					var store = DatamodelFactory.eINSTANCE.createStore();
					store.setScenario(scenarioObservable.getValue());
					return store;
				}, 
				null, null)
				.setTableRefreshBinding(DatamodelPackage.Literals.ASSET__ID, DatamodelPackage.Literals.ASSET__NAME, DatamodelPackage.Literals.ASSET__NODE)
				.setAfterCreateTableElementAction(tableView -> {
					tableView.column(Asset::getId).name("ID").width(100);
					tableView.column(Asset::getName).name("Name").width(100);
					tableView.column(Asset::getNode).name("Node").width(100).format(Node::getName);
				});
		
		new ObjectsPage<TruckType>(this, DatamodelPackage.Literals.SCENARIO__TRUCK_TYPES, TreeElementType.TRUCK_TYPE, 
				() -> {
					var truck = DatamodelFactory.eINSTANCE.createTruckType();
					truck.setScenario(scenarioObservable.getValue());
					return truck;
				},
				null, null)
				.setTableRefreshBinding(DatamodelPackage.Literals.TRUCK_TYPE__NAME,
						DatamodelPackage.Literals.TRUCK_TYPE__SPEED, 
						DatamodelPackage.Literals.TRUCK_TYPE__QUANTITY)
				.setAfterCreateTableElementAction(tableView -> {
					tableView.column(TruckType::getName).name("Name").width(100);
					tableView.column(TruckType::getSpeed).name("Speed").width(100);
					tableView.column(TruckType::getQuantity).name("Quantity").width(100);
				});
	}
	
}

