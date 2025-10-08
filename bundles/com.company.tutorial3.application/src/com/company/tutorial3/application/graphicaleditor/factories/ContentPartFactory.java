package com.company.tutorial3.application.graphicaleditor.factories;

import org.eclipse.gef.mvc.fx.parts.IContentPart;

import com.company.tutorial3.application.graphicaleditor.parts.ArcPart;
import com.company.tutorial3.application.graphicaleditor.parts.NodePart;
import com.company.tutorial3.application.graphicaleditor.parts.ScenarioPart;
import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.Scenario;

import javafx.scene.Node;

public class ContentPartFactory extends com.amalgamasimulation.graphicaleditor.factories.ContentPartFactory{
	
	@Override
	protected Class<? extends IContentPart<? extends Node>> getPartClass(Object content) {
		if (content instanceof Scenario) {
			return ScenarioPart.class;
		} else if (content instanceof com.company.tutorial3.datamodel.Node) {
			return NodePart.class;
		} else if (content instanceof Arc) {
			return ArcPart.class;
		}
		return null;
	}
}

