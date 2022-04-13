package com.company.tutorial3.graphicaleditor.behaviors;

import org.eclipse.gef.mvc.fx.behaviors.FocusBehavior;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

public class PaletteFocusBehavior extends FocusBehavior {

	public static final String FOCUSED_STYLE = "-fx-background-insets: 0; -fx-padding: 0; -fx-background-color: rgba(240, 240, 240, 1); -fx-border-color: #8ec0fc; -fx-border-width: 1;";
	public static final String DEFAULT_STYLE = "-fx-background-insets: 0; -fx-padding: 0; -fx-background-color: rgba(240, 240, 240, 1); -fx-border-color: rgba(240, 240, 240, 1); -fx-border-width: 1;";

	@Override
	protected void addViewerFocusedFeedback() {
		// XXX: super call is necessary so that state is correctly maintained.
		super.addViewerFocusedFeedback();
		IViewer viewer = getHost().getRoot().getViewer();
		viewer.getCanvas().setStyle(FOCUSED_STYLE);
	}

	@Override
	protected void removeViewerFocusedFeedback() {
		// XXX: super call is necessary so that state is correctly maintained.
		super.removeViewerFocusedFeedback();
		IViewer viewer = getHost().getRoot().getViewer();
		viewer.getCanvas().setStyle(DEFAULT_STYLE);
	}

}


