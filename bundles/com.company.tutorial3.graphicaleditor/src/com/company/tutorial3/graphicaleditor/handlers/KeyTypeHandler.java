package com.company.tutorial3.graphicaleditor.handlers;

import java.util.Set;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnTypeHandler;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyTypeHandler extends AbstractHandler implements IOnTypeHandler {
	
	@Override
	public void type(KeyEvent keyEvent, Set<KeyCode> pressedKeys) {}

	protected IViewer getContentViewer() {
		return getHost().getRoot().getViewer().getDomain().getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
	}

}


