package com.company.tutorial3.graphicaleditor.handlers;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.amalgamasimulation.emf.commands.AddCommand;
import com.amalgamasimulation.geometry.Point;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import com.company.tutorial3.common.command.CommandFactory;
import com.company.tutorial3.datamodel.DatamodelFactory;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Scenario;
import com.company.tutorial3.graphicaleditor.parts.ScenarioPart;
import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel;
import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel.Type;
import com.company.tutorial3.graphicaleditor.view.InfiniteCanvasViewer;
import com.company.tutorial3.graphicaleditor.view.Storage;

public class CreateNewElementByClickHandler extends AbstractHandler implements IOnClickHandler {
	
	
	@Override
	public void click(MouseEvent e) {
		if (!e.isPrimaryButtonDown()) {
			return;
		}
		
		IViewer viewer = getHost().getRoot().getViewer();
		ItemCreationModel creationModel = viewer.getAdapter(ItemCreationModel.class);
		if (creationModel.getType() != Type.ARC  && creationModel.getType() != Type.NONE) {
			IVisualPart<? extends javafx.scene.Node> part = viewer.getRootPart().getChildrenUnmodifiable().get(0);
			if (part instanceof ScenarioPart) {
				ScenarioPart graphPart = (ScenarioPart)part;
				Scenario scenario = graphPart.getContent();
				Point2D mouseInLocal = part.getVisual().sceneToLocal(e.getSceneX(), e.getSceneY());
				Object newObject = null;
				switch (creationModel.getType()) {
				
				case NODE: {
					AddCommand<Node> addCom = CommandFactory.create(scenario, Storage.messages.obj_NODE, Storage.messages.obj_NODE, () -> {
						Node n = DatamodelFactory.eINSTANCE.createNode();
						Point p = getContentViewer().getInfiniteCanvas().logicToLonLat(mouseInLocal.getX(), mouseInLocal.getY());
						n.setLongitude(p.getX());
						n.setLatitude(p.getY());
						return n;
					}, true);
					newObject = addCom.getObject();
					getContentViewer().onObjectClick(newObject);
					break;
				}
					default: break;
				}
				
				
			}
		}
	}
	

	


	protected InfiniteCanvasViewer getContentViewer() {
		return (InfiniteCanvasViewer)getHost().getRoot().getViewer().getDomain().getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
	}
}


