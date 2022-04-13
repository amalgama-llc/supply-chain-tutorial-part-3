package com.company.tutorial3.graphicaleditor.handlers;


import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.parts.CircleSegmentHandlePart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.company.tutorial3.graphicaleditor.parts.ArcPart;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class BendPointDoubleClickHandler extends AbstractHandler implements IOnClickHandler {
	
	@Override
	public void click(MouseEvent e) {
		if (!isFocusAndSelect(e)) {
			return;
		}
		IVisualPart<? extends Node> host = getHost();
		if (host instanceof CircleSegmentHandlePart) {
			CircleSegmentHandlePart bendPointPart = (CircleSegmentHandlePart)host;
			for (IVisualPart<? extends Node> anchorage : bendPointPart.getAnchoragesUnmodifiable().keySet()) {
				if (anchorage instanceof ArcPart) {
					ArcPart mineArcPart = (ArcPart)anchorage;
					if (bendPointPart.getSegmentIndex() > 0 && bendPointPart.getSegmentParameter() != 1) {
//						CommandFactory.remove(mineArcPart.getContent().getPoints().get(bendPointPart.getSegmentIndex() - 1));
					}
				}
			}
		}
	}

	protected boolean isFocusAndSelect(MouseEvent event) {
		return event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY;
	}
}



