package com.company.tutorial3.graphicaleditor.handlers;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel;
import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel.Type;
import com.company.tutorial3.graphicaleditor.view.InfiniteCanvasViewer;

import javafx.scene.input.MouseEvent;

public class FocusAndSelectArcOnClickHandler extends FocusAndSelectOnClickHandler {

	@Override
	public void click(MouseEvent e) {
		if (!e.isPrimaryButtonDown()) {
			return;
		}

		super.click(e);
	}
	
	protected InfiniteCanvasViewer getContentViewer() {
		return (InfiniteCanvasViewer)getHost().getRoot().getViewer().getDomain().getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
	}
	
	protected boolean isRestricted(IViewer viewer) {
		ItemCreationModel itemCreationModel = viewer.getAdapter(ItemCreationModel.class);
		return 	itemCreationModel.getType() != Type.NONE ;
	}

}


