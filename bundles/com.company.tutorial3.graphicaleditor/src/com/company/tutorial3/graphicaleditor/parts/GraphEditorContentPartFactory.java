package com.company.tutorial3.graphicaleditor.parts;

import java.util.Map;

import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IContentPartFactory;

import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Scenario;
import com.company.tutorial3.graphicaleditor.model.PaletteNode;
import com.google.inject.Inject;
import com.google.inject.Injector;



public class GraphEditorContentPartFactory implements IContentPartFactory {

	@Inject
	private Injector injector;

	@Override
	public IContentPart<? extends javafx.scene.Node> createContentPart(Object content, Map<Object, Object> contextMap) {
		if (content instanceof Scenario) {
			return injector.getInstance(ScenarioPart.class);
		} else if (content instanceof PaletteNode) {
			return injector.getInstance(PaletteNodePart.class);
		} else if (content instanceof Node) {
			return injector.getInstance(NodePart.class);
		}else if (content instanceof Arc) {
			return injector.getInstance(ArcPart.class);
		}
		
		else {
			throw new IllegalArgumentException("Unknown content type <" + content + ">");
		}
	}
}


