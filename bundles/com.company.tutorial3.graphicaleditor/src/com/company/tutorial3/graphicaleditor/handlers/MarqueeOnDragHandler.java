package com.company.tutorial3.graphicaleditor.handlers;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MarqueeOnDragHandler extends org.eclipse.gef.mvc.fx.handlers.MarqueeOnDragHandler {
	
	@Override
	protected boolean isMarquee(MouseEvent event) {
		return super.isMarquee(event) && event.getButton() == MouseButton.PRIMARY;
	}
}


