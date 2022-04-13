package com.company.tutorial3.graphicaleditor.handlers;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.company.tutorial3.graphicaleditor.parts.PaletteNodePart;
import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel;
import com.company.tutorial3.graphicaleditor.visuals.PaletteNodeVisual;

import javafx.scene.input.MouseEvent;

public class CreateObjectsFromPaletteHandler extends AbstractHandler implements IOnClickHandler {
	
	@Override
	public void click(MouseEvent e) {
		if (getHost() instanceof PaletteNodePart) {
			PaletteNodeVisual paletteNodeVisual = (PaletteNodeVisual)getHost().getVisual();
			setCreationModelType(e, paletteNodeVisual.getPaletteNodePart().getContent().getPaletteNodeType().getType());
		}
	}
	
	protected void setCreationModelTypeByDoubleClick(MouseEvent e, ItemCreationModel.Type type) {
		if (e.getClickCount() == 2) {
			ItemCreationModel creationModel = getContentViewer().getAdapter(ItemCreationModel.class);
			creationModel.setType(creationModel.getType() == type ? ItemCreationModel.Type.NONE : type, getContentViewer());
		}
	}
	
	private void setCreationModelType(MouseEvent e, ItemCreationModel.Type type) {
		ItemCreationModel creationModel = getContentViewer().getAdapter(ItemCreationModel.class);
		creationModel.setType(creationModel.getType() == type ? ItemCreationModel.Type.NONE : type, getContentViewer());
	}

	protected IViewer getContentViewer() {
		return getHost().getRoot().getViewer().getDomain().getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
	}
}


