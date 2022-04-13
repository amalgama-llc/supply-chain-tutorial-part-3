package com.company.tutorial3.graphicaleditor.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.gestures.ClickDragGesture;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnStrokeHandler;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.amalgamasimulation.emf.commands.CommandsManager;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import com.company.tutorial3.common.command.CommandFactory;
import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Scenario;
import com.company.tutorial3.graphicaleditor.parts.ScenarioPart;
import com.company.tutorial3.graphicaleditor.view.InfiniteCanvasViewer;

public class DeleteSelectedOnTypeHandler extends AbstractHandler implements IOnStrokeHandler {

	@Override
	public void initialPress(KeyEvent event) {
		if (!isDelete(event))	return;
		
		IViewer viewer = getHost().getRoot().getViewer();

		IVisualPart<? extends javafx.scene.Node> part = viewer.getRootPart().getChildrenUnmodifiable().get(0);
		ScenarioPart graphPart = (ScenarioPart)part;
		Scenario scenario = graphPart.getContent();
		
		List<IContentPart<? extends javafx.scene.Node>> selections = new ArrayList<>(viewer.getAdapter(SelectionModel.class).getSelectionUnmodifiable());

		List<EObject> removedObjects = new ArrayList<>();
		for (IContentPart<? extends javafx.scene.Node> selection : selections) {
			Object content = selection.getContent();
			if (content instanceof EObject) {
				EObject eObject = (EObject)content;
				removedObjects.add(eObject);
			} else {
				throw new RuntimeException("���������������� ��� ���������� ������� " + selection + " /DeleteSelectedOnTypeHandler");
			}
		}
		
		
		CompoundCommand compoundCommandRemoveArcs = new CompoundCommand();
		CompoundCommand compoundCommandRemoveNode = new CompoundCommand();
		
		Set<Arc> removeArcs = new HashSet<>();
		Set<Node> removeNodes = new HashSet<>();
		
		Map<Node, Set<Arc>> nodesByArcs = new HashMap<>();
		scenario.getArcs().forEach(a -> {
			nodesByArcs.computeIfAbsent(a.getSource(), k -> new HashSet<Arc>()).add(a);
			nodesByArcs.computeIfAbsent(a.getDest(), k -> new HashSet<Arc>()).add(a);
		});
		
		for (EObject eObject : removedObjects) {
			if(eObject instanceof Node) {
				Node node = (Node)eObject;
				Set<Arc> temp = nodesByArcs.get(node);
				if(temp != null) {
					removeArcs.addAll(temp);
				}

				if(!removeNodes.contains(node)) {
					removeNodes.add(node);
				}
			}else if(eObject instanceof Arc) {
				Arc wc = (Arc)eObject;
				if(!removeArcs.contains(wc)) {
					removeArcs.add(wc);
				}
			}
		}
		
		for(Arc l: removeArcs) {
			compoundCommandRemoveArcs.append(CommandFactory.remove(l, false));
		}
		
		for(Node l: removeNodes) {
			compoundCommandRemoveNode.append(CommandFactory.remove(l, false));
		}
			
		
		CompoundCommand compoundCommand = new CompoundCommand();
		if(!compoundCommandRemoveArcs.isEmpty()) {
			compoundCommand.append(compoundCommandRemoveArcs);
		}

		if(!compoundCommandRemoveNode.isEmpty()) {
			compoundCommand.append(compoundCommandRemoveNode);
		}
		
		
		CommandsManager.getEditingDomain().getCommandStack().execute(compoundCommand);
		if (viewer instanceof InfiniteCanvasViewer) {
			InfiniteCanvasViewer infiniteCanvasViewer = (InfiniteCanvasViewer)viewer;
			infiniteCanvasViewer.deleteObject();
		}
	}

	protected boolean isDelete(KeyEvent event) {
		// only delete on <DELETE> key
		if (event.getCode() != KeyCode.DELETE)	return false;

		// prevent deletion when other drag policies are running
		ClickDragGesture tool = getHost().getRoot().getViewer().getDomain().getAdapter(ClickDragGesture.class);
		if (tool != null && getHost().getRoot().getViewer().getDomain().isExecutionTransactionOpen(tool)) {
			return false;
		}
		return true;
	}

	@Override
	public void press(KeyEvent event) {}

	@Override
	public void release(KeyEvent event) {}

	@Override
	public void abortPress() {}

	@Override
	public void finalRelease(KeyEvent event) {}

	protected IViewer getContentViewer() {
		return getHost().getRoot().getViewer().getDomain().getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
	}

}


