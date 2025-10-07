package com.company.tutorial3.application.pages;

import com.amalgamasimulation.desktop.binding.ValidationStrategies;
import com.amalgamasimulation.desktop.ui.editor.EmfPage;
import com.amalgamasimulation.desktop.ui.editor.common.IPageConfiguration;
import com.company.tutorial3.application.localization.Messages;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;

public class NodePage extends EmfPage<Node> {

	public NodePage() {
		super(Node.class);
	}
	
	@Override
	protected String getTab() {
		return Messages.messages().tab_general;
	}
	
	@Override
	protected void createContentsInternal() {
		emfStringEditor()
			.feature(DatamodelPackage.Literals.NODE__NAME)
			.label(Messages.messages().obj_NODE_col_NAME)
			.validationStrategy(ValidationStrategies.stringIsNotEmpty())
			.create();
		
		emfDoubleEditor()
			.feature(DatamodelPackage.Literals.NODE__X)
			.label(Messages.messages().obj_NODE_col_X)
			.validationStrategy(ValidationStrategies.doubleAny())
			.create();
		emfDoubleEditor()
			.feature(DatamodelPackage.Literals.NODE__Y)
			.label(Messages.messages().obj_NODE_col_Y)
			.validationStrategy(ValidationStrategies.doubleAny())
			.create();
	}
	
	@Override
	protected void configureInternal(IPageConfiguration<Node> pageConfiguration) {
		// dynamic label
		pageConfiguration.objectDescription(obj -> obj.getName());
		// dynamic label should update whenever these features are updated
		pageConfiguration.features(DatamodelPackage.Literals.NODE__NAME);
		pageConfiguration.objectTypeName(obj -> {
			if (obj instanceof Node) {
				return Messages.messages().obj_NODE;
			}
			return null;
		});
	}
}

