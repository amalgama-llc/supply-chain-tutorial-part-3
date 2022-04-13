package com.company.tutorial3.graphicaleditor.handlers;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.policies.BendConnectionPolicy;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

public class GraphicalEditorBendConnectionPolicy extends BendConnectionPolicy{

	@Override
	public void move(Point initialMouseInScene, Point currentMouseInScene) {
		super.move(initialMouseInScene, currentMouseInScene);
	}
	
	@Override
	protected void insertExplicitAnchor(int insertionIndex, Point mouseInScene) {
		super.insertExplicitAnchor(insertionIndex, mouseInScene);
	}

	protected IViewer getContentViewer() {
		return getHost().getRoot().getViewer().getDomain().getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
	}
}


