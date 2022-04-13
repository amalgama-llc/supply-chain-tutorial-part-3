package com.company.tutorial3.graphicaleditor.handlers;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.amalgamasimulation.emf.commands.AddCommand;

import javafx.scene.input.MouseEvent;
import com.company.tutorial3.common.command.CommandFactory;
import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.DatamodelFactory;
import com.company.tutorial3.graphicaleditor.parts.NodePart;
import com.company.tutorial3.graphicaleditor.parts.ScenarioPart;
import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel;
import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel.Type;
import com.company.tutorial3.graphicaleditor.view.InfiniteCanvasViewer;
import com.company.tutorial3.graphicaleditor.view.Storage;
public class CreateNewArcByClickHandler extends AbstractHandler implements IOnClickHandler {
	
	@Override
	public void click(MouseEvent e) {
		if (!e.isPrimaryButtonDown()) {
			return;
		}

		IViewer viewer = getHost().getRoot().getViewer();
		ItemCreationModel creationModel = viewer.getAdapter(ItemCreationModel.class);
		Arc arc = null;
		if (creationModel.getType() == Type.ARC) {
			if (getHost() instanceof NodePart) {
				NodePart clickedNode = (NodePart)getHost();
				if(creationModel.getSource() == null) {
					creationModel.setSource(clickedNode);
				}else {
					NodePart source = creationModel.getSource();
					NodePart target = clickedNode;
					if (source != target) {
						IVisualPart<? extends javafx.scene.Node> part = getHost().getRoot().getChildrenUnmodifiable().get(0);
						if (part instanceof ScenarioPart) {
							ScenarioPart scenarioPart = (ScenarioPart)part;
							AddCommand<Arc> addCom = CommandFactory.create(scenarioPart.getContent(), Storage.messages.obj_ARC, Storage.messages.obj_ARC, () -> {
								Arc link = DatamodelFactory.eINSTANCE.createArc();
								link.setSource(source.getContent());
								link.setDest(target.getContent());
								return link;
							}, true); 
							arc = addCom.getObject();
						}
						
						creationModel.setSource(null);
						creationModel.setType(ItemCreationModel.Type.NONE, getContentViewer());
						getContentViewer().onObjectClick(arc);
					}
				}
			}
		}
		
	}
	


	protected InfiniteCanvasViewer getContentViewer() {
		return (InfiniteCanvasViewer)getHost().getRoot().getViewer().getDomain().getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
	}
}


