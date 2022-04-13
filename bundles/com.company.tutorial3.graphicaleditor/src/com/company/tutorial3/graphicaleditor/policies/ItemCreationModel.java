package com.company.tutorial3.graphicaleditor.policies;

import java.util.function.Predicate;

import org.eclipse.gef.mvc.fx.handlers.CursorSupport;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.company.tutorial3.graphicaleditor.parts.BendpointPart;
import com.company.tutorial3.graphicaleditor.parts.NodePart;
import com.company.tutorial3.graphicaleditor.view.InfiniteCanvasViewer;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;

public class ItemCreationModel {

	public enum Type {
		NONE, 
		SELECTION,
		NODE,
		ARC
    };
	
	private ObjectProperty<Type> typeProperty = new SimpleObjectProperty<ItemCreationModel.Type>(Type.NONE);
	private ObjectProperty<NodePart> sourceProperty = new SimpleObjectProperty<>();
	public ObjectProperty<Type> getTypeProperty() {
		return typeProperty;
	}

	public Type getType() {
		return typeProperty.getValue();
	}

	private IViewer contentViewer;
	
	private Predicate<Object> selectionFilter;
	
	public void setSelectionType(Predicate<Object> filter, InfiniteCanvasViewer contentViewer) {
		this.selectionFilter = filter;
		setType(Type.SELECTION, contentViewer);
	}
	
	public Predicate<Object> getSelectionFilter(){
		return selectionFilter;
	}
	
	public void setType(Type type, IViewer contentViewer) {
		this.contentViewer = contentViewer;
		if (this.typeProperty.getValue() != type) {
			if (this.typeProperty.getValue() != Type.NONE) {
				getCursorSupport(contentViewer).restoreCursor();
			}
			if (type == Type.NONE) {
				this.sourceProperty.setValue(null);
			}
			if (type != Type.SELECTION) {
				selectionFilter = null;
			}
			this.typeProperty.setValue(type);
		}
		updateCursor();
	}

	private void updateCursor() {
		switch (getType()) {
			case NONE: {
				getCursorSupport(contentViewer).setCursor(Cursor.DEFAULT);
			} break;
			case ARC: {
				if (getSource() == null) {
					getCursorSupport(contentViewer).setCursor(new ImageCursor(new Image("icons/createArcCursor.png")));
				} else {
					getCursorSupport(contentViewer).setCursor(new ImageCursor(new Image("icons/createArcCursor2.png")));
				}
			} break;
			case SELECTION: {
				getCursorSupport(contentViewer).setCursor(new ImageCursor(new Image("icons/selectionCursor.png"), 8, 8));
			} break;
			case NODE: {
				getCursorSupport(contentViewer).storeAndReplaceCursor(new ImageCursor(new Image("icons/createNodeCursor.png")));
			} break;
		}
	}
	public boolean isContentPartClickRestricted(Class<?> c) {
		Type currentType = getType();
		switch (currentType) {
		case NODE:
			return c.isAssignableFrom(BendpointPart.class);
		case ARC:
			return c.isAssignableFrom(NodePart.class);
		default:
			return false;
		}
	}
	
	public boolean isContentPartSelectable(IContentPart<? extends Node> contentPart) {
		return isContentPartSelectable(contentPart.getClass());
	}
	
	public boolean isContentPartSelectable(Class<?> c) {
		Type currentType = getType();
    	switch (currentType) {
    		case NONE:					return true;
    		case SELECTION:				return !isContentPartClickRestricted(c);
    		default:					return false;
    	}
	}
	
	public void setSource(NodePart nodePart) {
		this.sourceProperty.setValue(nodePart);
		updateCursor();
	}
	
	public NodePart getSource() {
		return sourceProperty.getValue();
	}
	
	public ObjectProperty<NodePart> getSourceProperty() {
		return sourceProperty;
	}
	
	
	protected CursorSupport getCursorSupport(IViewer contentViewer) {
		return contentViewer.getAdapter(CursorSupport.class);
	}
}


