package com.company.tutorial3.graphicaleditor.handlers;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel;

import javafx.scene.input.MouseEvent;


public class CreateNewArcFromPaletteHandler extends AbstractHandler implements IOnClickHandler {
	
	@Override
	public void click(MouseEvent e) {
		if (e.getClickCount() == 2) {
			ItemCreationModel creationModel = getContentViewer().getAdapter(ItemCreationModel.class);
			creationModel.setType(creationModel.getType() == ItemCreationModel.Type.ARC ? ItemCreationModel.Type.NONE : creationModel.getType(), getContentViewer());
		}
	}

	protected IViewer getContentViewer() {
		return getHost().getRoot().getViewer().getDomain().getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
	}
}


