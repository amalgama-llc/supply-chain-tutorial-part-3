package com.company.tutorial3.graphicaleditor.behaviors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.IContentPart;

import com.company.tutorial3.graphicaleditor.view.InfiniteCanvasViewer;

import javafx.scene.Node;

public class GraphicalEditorSelectionModel extends SelectionModel {
	
	@Override
	public void appendToSelection(List<? extends IContentPart<? extends Node>> toBeAppended) {
		List<IContentPart<? extends Node>> result = new ArrayList<>();
		result.addAll(getSelectionUnmodifiable());
		result.addAll(toBeAppended);
		super.appendToSelection(getValidSelections(result));
		getInfiniteCanvasViewer().onObjectsSelection(new ArrayList<>(getSelectionUnmodifiable()));
	}
	
	@Override
	public void prependToSelection(List<? extends IContentPart<? extends Node>> toBePrepended) {
		List<IContentPart<? extends Node>> result = new ArrayList<>();
		result.addAll(toBePrepended);
		result.addAll(getSelectionUnmodifiable());
		super.prependToSelection(getValidSelections(result));
		getInfiniteCanvasViewer().onObjectsSelection(new ArrayList<>(getSelectionUnmodifiable()));
	}
	
	@Override
	public void setSelection(List<? extends IContentPart<? extends Node>> selection) {
		super.setSelection(getValidSelections(selection));
		getInfiniteCanvasViewer().onObjectsSelection(new ArrayList<>(getSelectionUnmodifiable()));
	}
	
	@Override
	public void removeFromSelection(Collection<? extends IContentPart<? extends Node>> contentParts) {
		super.removeFromSelection(contentParts);
		getInfiniteCanvasViewer().onObjectsSelection(new ArrayList<>(getSelectionUnmodifiable()));
	}
	
	@Override
	public void removeFromSelection(IContentPart<? extends Node> contentPart) {
		super.removeFromSelection(contentPart);
		getInfiniteCanvasViewer().onObjectsSelection(new ArrayList<>(getSelectionUnmodifiable()));
	}
	
	private List<? extends IContentPart<? extends Node>> getValidSelections(List<? extends IContentPart<? extends Node>> selections) {
		//TODO: filter 
		return selections;
	}
	
	private InfiniteCanvasViewer getInfiniteCanvasViewer() {
		return (InfiniteCanvasViewer)getAdaptable();
	}
}


