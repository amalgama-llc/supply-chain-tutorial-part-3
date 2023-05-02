package com.company.tutorial3.application.graphicaleditor;

import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;

import com.amalgamasimulation.graphicaleditor.factories.ContentPartFactory;
import com.amalgamasimulation.graphicaleditor.handlers.CreateArcClickHandler;
import com.amalgamasimulation.graphicaleditor.handlers.CreateObjectClickHandler;
import com.amalgamasimulation.graphicaleditor.parts.AbstractArcPart;
import com.amalgamasimulation.graphicaleditor.parts.AbstractConnectionLinkPart;
import com.amalgamasimulation.graphicaleditor.parts.AbstractNodePart;
import com.company.tutorial3.application.graphicaleditor.handlers.CreateArcHandler;
import com.company.tutorial3.application.graphicaleditor.handlers.CreateObjectHandler;
import com.company.tutorial3.application.graphicaleditor.parts.ArcPart;
import com.company.tutorial3.application.graphicaleditor.parts.NodePart;

public class GraphicalEditorModule extends com.amalgamasimulation.graphicaleditor.Module{

	@Override
	protected Class<? extends ContentPartFactory> getContentPartFactoryType() {
		return com.company.tutorial3.application.graphicaleditor.factories.ContentPartFactory.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends AbstractArcPart<?, ?, ?, ?>>[] getArcPartTypes() {
		return new Class[] {ArcPart.class};
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends AbstractNodePart<?, ?>>[] getNodePartTypes() {
		return new Class[] {NodePart.class};
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends AbstractContentPart<?>>[] getAbstractPartTypes() {
		return new Class[] {};
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends AbstractConnectionLinkPart<?, ?, ?, ?>>[] getConnectionLinkPartTypes() {
		return new Class[] {};
	}

	@Override
	protected void configureInternal() {
		binder().bind(CreateObjectClickHandler.class).to(CreateObjectHandler.class);
		binder().bind(CreateArcClickHandler.class).to(CreateArcHandler.class);
	}
}

