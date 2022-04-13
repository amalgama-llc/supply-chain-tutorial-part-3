package com.company.tutorial3.graphicaleditor.parts;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.parts.IResizableContentPart;
import org.eclipse.gef.mvc.fx.parts.ITransformableContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.amalgamasimulation.emf.commands.CommandsManager;
import com.amalgamasimulation.geometry.Point;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Translate;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel;
import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel.Type;
import com.company.tutorial3.graphicaleditor.view.InfiniteCanvasViewer;
import com.company.tutorial3.graphicaleditor.visuals.NodeVisual;

public class NodePart extends HighlightablePart<NodeVisual> implements ITransformableContentPart<NodeVisual>, IResizableContentPart<NodeVisual>{

	private IObservableValue<Node> observable = new WritableValue<>();
	private double nodeSize = 6;
	
	@SuppressWarnings("unchecked")
	public NodePart() {
		EMFProperties.value(DatamodelPackage.Literals.NODE__LONGITUDE).observeDetail(observable).addValueChangeListener(e -> refreshVisual());
		EMFProperties.value(DatamodelPackage.Literals.NODE__LATITUDE).observeDetail(observable).addValueChangeListener(e -> refreshVisual());	
	}
	
	@Override
	public void setParent(IVisualPart<? extends javafx.scene.Node> newParent) {
		super.setParent(newParent);
		if (newParent != null) {
			ItemCreationModel itemCreationModel = getParent().getViewer().getAdapter(ItemCreationModel.class);
			itemCreationModel.getTypeProperty().addListener(observable -> setDrawAsCreatedSegmentBeginNode((itemCreationModel.getType() == Type.ARC) && itemCreationModel.getSource() == this));
			itemCreationModel.getSourceProperty().addListener(observable -> setDrawAsCreatedSegmentBeginNode((itemCreationModel.getType() == Type.ARC) && itemCreationModel.getSource() == this));
		}
	}
	
	public void invalidateCreatedSegmentFeedback(double x, double y) {
		getVisual().invalidateCreatedSegmentFeedback(x, y);
	}
	
	@Override
	protected NodeVisual doCreateVisual() {
		return new NodeVisual();
	}

	@Override
	protected List<? extends Object> doGetContentChildren() {
		return Collections.emptyList();
	}


	@Override
	public void refreshContentChildren() {
		super.refreshContentChildren();
		refreshVisual();
	}

	@Override
	public NodeVisual getVisual() {
		return (NodeVisual) super.getVisual();
	}

	@Override
	public Node getContent() {
		return (Node) super.getContent();
	}

	@Override
	protected void doRefreshVisual(NodeVisual visual) {
		Node node = getContent();
		if (node != null) {
			visual.setHighlighted(highlighted, new Color(0, 0, 0, 1), highlightedAsDependency, dependencyColor);
			setVisualTransform(getContentTransform());
		}
	}
	
	private void setDrawAsCreatedSegmentBeginNode(boolean drawAsCreatedSegmentBeginNode) {
		boolean oldDrawAsCreatedSegmentBeginNode = getVisual().isDrawAsCreatedSegmentBeginNode();
		if (oldDrawAsCreatedSegmentBeginNode != drawAsCreatedSegmentBeginNode) {
			getVisual().setDrawAsCreatedSegmentBeginNode(drawAsCreatedSegmentBeginNode);
			if (!drawAsCreatedSegmentBeginNode) {
				doRefreshVisual(getVisual());
			}
		}
	}

	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return HashMultimap.create();
	}

	@Override
	protected void doRemoveContentChild(Object contentChild) {

	}

	@Override
	public void setContent(Object model) {
		observable.setValue((Node) model);
		super.setContent(model);
	}


	@Override
	public Affine getContentTransform() {
		if (getContent() != null && getContentViewer() != null) {
			Point p = getContentViewer().getInfiniteCanvas().lonLatToLogic(getContent().getLongitude() , getContent().getLatitude());
			return new Affine(new Translate(p.getX() - nodeSize, p.getY() - nodeSize));
		} else {
			return new Affine(new Translate(0, 0));
		}
	}

	@Override
	public Dimension getVisualSize() {
		return IResizableContentPart.super.getVisualSize();
	}
	@Override
	public void setVisualSize(Dimension totalSize) {
		IResizableContentPart.super.setVisualSize(totalSize);
	}

	@Override
	public void setContentTransform(Affine totalTransform) {
		if (getContent() != null) {
			Point p = getContentViewer().getInfiniteCanvas().logicToLonLat(totalTransform.getTx() + nodeSize, totalTransform.getTy() + nodeSize);		
			CompoundCommand compoundCommand = new CompoundCommand();
			compoundCommand.append(SetCommand.create(CommandsManager.getEditingDomain(), getContent(),DatamodelPackage.Literals.NODE__LONGITUDE, p.getX()));
			compoundCommand.append(SetCommand.create(CommandsManager.getEditingDomain(), getContent(),DatamodelPackage.Literals.NODE__LATITUDE, p.getY()));			
			CommandsManager.getEditingDomain().getCommandStack().execute(compoundCommand);
		}
	}

	@Override
	public Dimension getContentSize() {
		return new Dimension(nodeSize, nodeSize);
	}

	@Override
	public void setContentSize(Dimension totalSize) {
		
	}
	
	protected InfiniteCanvasViewer getContentViewer() {
		if(getViewer() == null)return null;
		if(getViewer().getDomain() == null)return null;
		return (InfiniteCanvasViewer)getViewer().getDomain().getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
	}
	
}


