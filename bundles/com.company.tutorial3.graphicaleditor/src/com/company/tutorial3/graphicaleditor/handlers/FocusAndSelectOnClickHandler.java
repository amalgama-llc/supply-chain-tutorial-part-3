package com.company.tutorial3.graphicaleditor.handlers;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.models.FocusModel;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.operations.ChangeFocusOperation;
import org.eclipse.gef.mvc.fx.operations.ChangeSelectionOperation;
import org.eclipse.gef.mvc.fx.operations.DeselectOperation;
import org.eclipse.gef.mvc.fx.operations.ITransactionalOperation;
import org.eclipse.gef.mvc.fx.operations.SelectOperation;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.company.tutorial3.graphicaleditor.parts.ArcPart;
import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel;
import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel.Type;
import com.company.tutorial3.graphicaleditor.view.InfiniteCanvasViewer;
import com.google.common.reflect.TypeToken;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class FocusAndSelectOnClickHandler extends AbstractHandler implements IOnClickHandler {
	
	@SuppressWarnings("serial")
	@Override
	public void click(MouseEvent e) {
		// focus and select are only done on single click
		if (!isFocusAndSelect(e)) {
			return;
		}
		IVisualPart<? extends Node> host = getHost();
		IViewer viewer = host.getRoot().getViewer();
		ItemCreationModel itemCreationModel = viewer.getAdapter(ItemCreationModel.class);
		SelectionModel selectionModel = viewer.getAdapter(SelectionModel.class);
		if (itemCreationModel == null || isRestricted(viewer, host)) {
			return;
		}
		// determine if replacing or extending the selection
		boolean append = isAppend(e);

		// perform different changes depending on host type
		if (host instanceof IContentPart) {
			IContentPart<? extends Node> contentPart = (IContentPart<? extends Node>) host;
			if (viewer instanceof InfiniteCanvasViewer) {
				InfiniteCanvasViewer infiniteCanvasViewer = (InfiniteCanvasViewer)viewer;
				boolean waitingForSelection = infiniteCanvasViewer.isWaitingForSelection();
				Object selectedObject = contentPart.getContent();
				if (selectedObject != null) {
					if (itemCreationModel.isContentPartSelectable(contentPart)) {
						infiniteCanvasViewer.onObjectClick(selectedObject);
					} else {
						return;
					}
				}
				if (waitingForSelection) {
					return;
				}
			}
			//eventBroker.post("OBJECT_SELECTED", contentPart.getContent());

			// check if the host is the explicit event target
			if (isRegistered(e.getTarget()) && !isRegisteredForHost(e.getTarget())) {
				// do not process events for other parts
				return;
			}

			List<IContentPart<? extends Node>> singletonHostList = Collections.<IContentPart<? extends Node>> singletonList(contentPart);

			// create selection change operation(s)
			boolean wasDeselected = false;
			ITransactionalOperation selectionChangeOperation = null;
			if (selectionModel.isSelected(contentPart)) {
				if (append) {
					// deselect the host
					selectionChangeOperation = new DeselectOperation(viewer, singletonHostList);
					wasDeselected = true;
				}
			} else if (contentPart.isSelectable()) {
				if (append) {
					// prepend host to current selection (as new primary)
					selectionChangeOperation = new SelectOperation(viewer, singletonHostList);
				} else {
					// clear old selection, host becomes the only selected
					selectionChangeOperation = new ChangeSelectionOperation(viewer, singletonHostList);
				}
			}

			// execute selection changes
			if (selectionChangeOperation != null) {
				try {
					viewer.getDomain().execute(selectionChangeOperation, new NullProgressMonitor());
				} catch (ExecutionException e1) {
					throw new IllegalStateException(e1);
				}
			}

			// change focus depending on selection changes
			ChangeFocusOperation changeFocusOperation = null;
			ObservableList<IContentPart<? extends Node>> selection = selectionModel.getSelectionUnmodifiable();
			if (wasDeselected && selection.isEmpty()) {
				// unfocus when the only selected part was deselected
				changeFocusOperation = new ChangeFocusOperation(viewer, null);
			} else {
				// focus new primary selection
				IContentPart<? extends Node> primarySelection = selection.get(0);
				if (primarySelection.isFocusable()) {
					FocusModel focusModel = viewer.getAdapter(new TypeToken<FocusModel>() {});	
					if(focusModel != null) {
						if (focusModel.getFocus() == primarySelection) {
							primarySelection.getVisual().requestFocus();
						} else {
							changeFocusOperation = new ChangeFocusOperation(viewer, primarySelection);
						}
					}
				}
			}

			// execute focus change
			if (changeFocusOperation != null) {
				try {
					viewer.getDomain().execute(changeFocusOperation, new NullProgressMonitor());
				} catch (ExecutionException e1) {
					throw new IllegalStateException(e1);
				}
			}
		} else if (host instanceof IRootPart) {
			// check if click on background (either one of the root visuals, or
			// an unregistered visual)
			if (!isRegistered(e.getTarget()) || isRegisteredForHost(e.getTarget())) {
				// check if append-modifier is pressed
				if (viewer instanceof InfiniteCanvasViewer) {
					InfiniteCanvasViewer infiniteCanvasViewer = (InfiniteCanvasViewer)viewer;
					infiniteCanvasViewer.setWaitingForSelection(null);
				}
				if (append) {
					// do nothing
					return;
				}

				// unset focus and clear selection
				try {
					FocusModel focusModel = viewer.getAdapter(new TypeToken<FocusModel>() {});
					if (focusModel.getFocus() == null) {
						// no focus change needed, only update feedback
						viewer.getRootPart().getVisual().requestFocus();
					} else {
						// change focus, will update feedback via behavior
						viewer.getDomain().execute(new ChangeFocusOperation(viewer, null), new NullProgressMonitor());
					}
					viewer.getDomain().execute(new DeselectOperation(viewer, selectionModel .getSelectionUnmodifiable()), new NullProgressMonitor());
				} catch (ExecutionException e1) {
					throw new IllegalStateException(e1);
				}
			}
		}
	}
	
	protected boolean isRestricted(IViewer viewer, IVisualPart<? extends Node> host) {
		ItemCreationModel itemCreationModel = viewer.getAdapter(ItemCreationModel.class);
		if (host instanceof ArcPart) {
			return 	itemCreationModel.getType() != Type.NONE 
					&& itemCreationModel.getType() != Type.SELECTION;
		}
		return false;
	}

	protected boolean isAppend(MouseEvent e) {
		return e.isShortcutDown();
	}

	protected boolean isFocusAndSelect(MouseEvent event) {
		return event.getClickCount() <= 1;
	}
}


