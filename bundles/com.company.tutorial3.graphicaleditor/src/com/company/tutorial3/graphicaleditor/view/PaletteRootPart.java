package com.company.tutorial3.graphicaleditor.view;

import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.parts.LayeredRootPart;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class PaletteRootPart extends LayeredRootPart {

	@Override
	protected Group createContentLayer() {
		Group contentLayer = super.createContentLayer();
		VBox vbox = new VBox();
		vbox.setPickOnBounds(true);
		// define padding and spacing
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(10d);
		// fixed at top/right position
		vbox.setAlignment(Pos.TOP_LEFT);
		contentLayer.getChildren().add(vbox);
		return contentLayer;
	}
	
	private InfiniteCanvasViewer infiniteCanvasViewer;
	
	public void setInfiniteCanvasViewer(InfiniteCanvasViewer infiniteCanvasViewer) {
		this.infiniteCanvasViewer = infiniteCanvasViewer;
	}

	public InfiniteCanvasViewer getInfiniteCanvasViewer() {
		return infiniteCanvasViewer;
	}

	@Override
	protected void doAddChildVisual(IVisualPart<? extends Node> child, int index) {
		if (child instanceof IContentPart) {
			int contentLayerIndex = 0;
			for (int i = 0; i < index; i++) {
				if (i < getChildrenUnmodifiable().size() && getChildrenUnmodifiable().get(i) instanceof IContentPart) {
					contentLayerIndex++;
				}
			}
			((VBox) getContentLayer().getChildren().get(0)).getChildren().add(contentLayerIndex, new Group(child.getVisual()));
		} else {
			super.doAddChildVisual(child, index);
		}
	}

	@Override
	protected void doRemoveChildVisual(IVisualPart<? extends Node> child, int index) {
		if (child instanceof IContentPart) {
			((VBox) getContentLayer().getChildren().get(0)).getChildren().remove(index);
		} else {
			super.doRemoveChildVisual(child, index);
		}
	}
}


