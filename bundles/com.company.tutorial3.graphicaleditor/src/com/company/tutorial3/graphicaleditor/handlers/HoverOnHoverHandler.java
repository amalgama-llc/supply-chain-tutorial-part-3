package com.company.tutorial3.graphicaleditor.handlers;


import org.eclipse.gef.mvc.fx.models.HoverModel;
import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IHandlePart;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class HoverOnHoverHandler extends org.eclipse.gef.mvc.fx.handlers.HoverOnHoverHandler{

	protected HoverModel getHoverModel() {
		return getHost().getViewer().getAdapter(HoverModel.class);
	}

	@Override
	public void hover(MouseEvent e) {
		if (!isHover(e)) {
			getHoverModel().setHover(null);
			return;
		}
		getHoverModel().setHover(getHost());
	}

	@Override
	public void hoverIntent(Node hoverIntent) {
		HoverModel hoverModel = getHoverModel();
		if (!isRegistered(hoverIntent) && getHost() instanceof IRootPart) {
			hoverModel.setHoverIntent(null);
		} else if (isRegisteredForHost(hoverIntent)) {
			if (getHost() instanceof IHandlePart) {
				if (!getHost().getAnchoragesUnmodifiable().containsKey(hoverModel.getHoverIntent())) {
					hoverModel.setHoverIntent(null);
				}
			} else if (getHost() instanceof IContentPart) {
				hoverModel.setHoverIntent((IContentPart<? extends Node>) getHost());
			} else if (getHost() instanceof IRootPart) {
				hoverModel.setHoverIntent(null);
			}
		}
	}

	/**
	 * Returns <code>true</code> if the given {@link MouseEvent} should trigger
	 * hover. Otherwise returns <code>false</code>. Per default, returns
	 * <code>true</code> if the mouse target is not registered in the visual
	 * part map or it is registered for this {@link HoverOnHoverHandler}'s host.
	 *
	 * @param event
	 *            The {@link MouseEvent} in question.
	 * @return <code>true</code> to indicate that the given {@link MouseEvent}
	 *         should trigger hover, otherwise <code>false</code>.
	 */
	protected boolean isHover(MouseEvent event) {
		if (!isRegistered(event.getTarget()) || isRegisteredForHost(event.getTarget())) {
			IViewer viewer = getAdaptable().getViewer();
			if (viewer != null) {
				ItemCreationModel creationModel = viewer.getAdapter(ItemCreationModel.class);
				if (creationModel != null) {
					if (getHost() instanceof AbstractContentPart) {
						AbstractContentPart<?> acp = (AbstractContentPart<?>)getHost();
						if (creationModel.getSelectionFilter() != null) {
							return creationModel.getSelectionFilter().test(acp.getContent());
						}
					}
				}
			}
			return true;
		}
		return false;
	}
}

