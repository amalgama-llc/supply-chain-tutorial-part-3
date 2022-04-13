package com.company.tutorial3.graphicaleditor.parts;

import java.util.Collections;
import java.util.List;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.Ellipse;
import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;
import org.eclipse.gef.mvc.fx.parts.IResizableContentPart;
import org.eclipse.gef.mvc.fx.parts.ITransformableContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.company.tutorial3.graphicaleditor.model.PaletteNode;
import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel;
import com.company.tutorial3.graphicaleditor.view.PaletteRootPart;
import com.company.tutorial3.graphicaleditor.visuals.PaletteNodeVisual;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import javafx.scene.Node;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Translate;

public class PaletteNodePart extends AbstractContentPart<PaletteNodeVisual> implements ITransformableContentPart<PaletteNodeVisual>, IResizableContentPart<PaletteNodeVisual> {

	private Ellipse nodeBounds = new Ellipse(0, 0, 3, 3);
	
	
	@Override
	public void setParent(IVisualPart<? extends Node> newParent) {
		super.setParent(newParent);
		if (newParent != null) {
			ItemCreationModel itemCreationModel = ((PaletteRootPart)getParent()).getInfiniteCanvasViewer().getAdapter(ItemCreationModel.class);
			itemCreationModel.getTypeProperty().addListener(observable -> {
				if(getVisual() != null && getContent() != null) {
					getVisual().setDrawAsSelected(getContent().getPaletteNodeType().getType() == itemCreationModel.getTypeProperty().get());
				}
			});}
	}
	
	@Override
	protected PaletteNodeVisual doCreateVisual() {
		return new PaletteNodeVisual(this);
	}

	@Override
	protected List<? extends Object> doGetContentChildren() {
		// we don't have any children.
        return Collections.emptyList();
	}

	@Override
	public PaletteNode getContent() {
		return (PaletteNode)super.getContent();
	}
	
	@Override
	protected void doRefreshVisual(PaletteNodeVisual visual) {
		// updating the visuals position
		nodeBounds = new Ellipse(0, 0, 3, 3);
        
        setVisualTransform(getContentTransform());
	}
	
	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return HashMultimap.create();
	}

	
	@Override
	public Dimension getContentSize() {
		return nodeBounds.getSize();
	}

	@Override
	public Affine getContentTransform() {
		Ellipse bounds = nodeBounds;
		return new Affine(new Translate(bounds.getX(), bounds.getY()));
	}

	@Override
	public void setContent(Object model) {
		if (model != null && !(model instanceof PaletteNode)) {
			throw new IllegalArgumentException("Only PaletteNode models are supported.");
		}
		super.setContent(model);
	}

	@Override
	public void setContentSize(Dimension size) {
		nodeBounds.setSize(size);
	}

	@Override
	public void setContentTransform(Affine totalTransform) {}

	@Override
	public Dimension getVisualSize() {
		return IResizableContentPart.super.getVisualSize();
	}

	@Override
	public void setVisualSize(Dimension totalSize) {
		IResizableContentPart.super.setVisualSize(totalSize);
	}
}


