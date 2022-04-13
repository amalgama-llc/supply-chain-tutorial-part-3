package com.company.tutorial3.graphicaleditor.parts;

import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public abstract class HighlightablePart<V extends Node> extends AbstractContentPart<V>{

	protected Color dependencyColor = new Color(1, 0, 0, 1);
	protected boolean highlightedAsDependency = false;
	protected boolean highlighted = false;

	public void highlightAsDependency(boolean highlightedAsDependency, Color dependencyColor) {
		this.dependencyColor = dependencyColor;
		this.highlightedAsDependency = highlightedAsDependency;
		refreshVisual();
	}
	
	public void highlight(boolean highlighted) {
		this.highlighted = highlighted;
		refreshVisual();
	}
	
	public boolean isHighlightedAsDependency() {
		return highlightedAsDependency;
	}
	
	public boolean isHighlighted() {
		return highlighted;
	}
}


