package com.company.tutorial3.application.pages;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.FeaturePath;

import com.amalgamasimulation.desktop.binding.ValidationStrategies;
import com.amalgamasimulation.desktop.ui.editor.Buttons;
import com.amalgamasimulation.desktop.ui.editor.EmfPage;
import com.company.tutorial3.application.localization.Messages;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Store;

public class StorePage extends EmfPage<Store> {

	@SuppressWarnings("unchecked")
	private IObservableList<Node> nodeListObservable = EMFProperties.list(
				FeaturePath.fromList(DatamodelPackage.Literals.STORE__SCENARIO, DatamodelPackage.Literals.SCENARIO__NODES)
			).observeDetail(observable);

	public StorePage() {
		super(Store.class);
	}
	
	@Override
	protected String getTab() {
		return Messages.messages().tab_general;
	}
	
	@Override
	protected void createContentsInternal() {
		emfStringEditor()
			.feature(DatamodelPackage.Literals.ASSET__ID)
			.label("ID")
			.validationStrategy(ValidationStrategies.stringIsNotEmpty())
			.create();
		emfStringEditor()
			.feature(DatamodelPackage.Literals.ASSET__NAME)
			.label("Name")
			.validationStrategy(ValidationStrategies.stringIsNotEmpty())
			.create();
		emfComboboxEditor(Node.class)
			.feature(DatamodelPackage.Literals.ASSET__NODE)
			.label("Node")
			.elements(nodeListObservable)
			.format(DatamodelPackage.Literals.NODE__NAME)
			.selectFromTableButton(table -> {
				table.column(node -> node.getName()).name(Messages.messages().obj_NODE_col_NAME).width(150);
			})
			.addButton(Buttons.showProperty())
			.create();
	}
}
