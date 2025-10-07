package com.company.tutorial3.application.pages;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.FeaturePath;

import com.amalgamasimulation.desktop.binding.ValidationStrategies;
import com.amalgamasimulation.desktop.ui.editor.Buttons;
import com.amalgamasimulation.desktop.ui.editor.EmfPage;
import com.amalgamasimulation.desktop.ui.editor.common.IPageConfiguration;
import com.company.tutorial3.application.localization.Messages;
import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;

public class ArcPage extends EmfPage<Arc> {
	
	@SuppressWarnings("unchecked")
	private IObservableList<Node> nodeListObservable = EMFProperties.list(
				FeaturePath.fromList(DatamodelPackage.Literals.ARC__SCENARIO, DatamodelPackage.Literals.SCENARIO__NODES)
			).observeDetail(observable);

	
	public ArcPage() {
		super(Arc.class);
	}
	
	@Override
	protected String getTab() {
		return Messages.messages().tab_general;
	}
	
	@Override
	protected void createContentsInternal() {
		emfStringEditor()
			.feature(DatamodelPackage.Literals.ARC__ID)
			.label(Messages.messages().obj_ARC_col_ID)
			.validationStrategy(ValidationStrategies.stringIsNotEmpty())
			.create();
		emfStringEditor()
			.feature(DatamodelPackage.Literals.ARC__NAME)
			.label(Messages.messages().obj_ARC_col_NAME)
			.validationStrategy(ValidationStrategies.stringIsNotEmpty())
			.create();
		
		emfComboboxEditor(Node.class)
			.feature(DatamodelPackage.Literals.ARC__SOURCE)
			.label(Messages.messages().obj_ARC_col_SOURCE)
			.elements(nodeListObservable)
			.format(DatamodelPackage.Literals.NODE__NAME)
			.selectFromTableButton(table -> {
				table.column(node -> node.getName()).name(Messages.messages().obj_NODE_col_NAME).width(150);
			})
			.addButton(Buttons.showProperty())
			.create();
		emfComboboxEditor(Node.class)
			.feature(DatamodelPackage.Literals.ARC__DEST)
			.label(Messages.messages().obj_ARC_col_DEST)
			.elements(nodeListObservable)
			.format(DatamodelPackage.Literals.NODE__NAME)
			.selectFromTableButton(table -> {
				table.column(node -> node.getName()).name(Messages.messages().obj_NODE_col_NAME).width(150);
			})
			.addButton(Buttons.showProperty())
			.create();
	}
	
	@Override
	protected void configureInternal(IPageConfiguration<Arc> pageConfiguration) {
		// dynamic label
		pageConfiguration.objectDescription(obj -> obj.getName());
		// dynamic label should update whenever these features are updated
		pageConfiguration.features(DatamodelPackage.Literals.ARC__NAME);
		pageConfiguration.objectTypeName(obj -> {
			if (obj instanceof Arc) {
				return Messages.messages().obj_ARC;
			}
			return null;
		});
	}
}
