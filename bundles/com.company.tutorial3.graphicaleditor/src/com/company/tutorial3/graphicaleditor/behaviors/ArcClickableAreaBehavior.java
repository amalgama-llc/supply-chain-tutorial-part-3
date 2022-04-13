package com.company.tutorial3.graphicaleditor.behaviors;


import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.fx.nodes.GeometryNode;
import org.eclipse.gef.geometry.planar.Polyline;
import org.eclipse.gef.mvc.fx.behaviors.AbstractBehavior;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.company.tutorial3.graphicaleditor.parts.ArcPart;
import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel;
import com.company.tutorial3.graphicaleditor.policies.MapZoom;
import com.company.tutorial3.graphicaleditor.policies.ItemCreationModel.Type;
import com.company.tutorial3.graphicaleditor.view.InfiniteCanvasViewer;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ArcClickableAreaBehavior extends AbstractBehavior{

	public static final double ABSOLUTE_CLICKABLE_WIDTH = 4;
	private DoubleBinding clickableAreaBinding;

	private final ChangeListener<? super Number> scaleXListener = new ChangeListener<Number>() {
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			clickableAreaBinding.invalidate();
		}
	};
	
	private InfiniteCanvasViewer canvas;
	private ItemCreationModel creationModel;
	
	protected InfiniteCanvasViewer getContentViewer() {
		if(canvas == null) {
			canvas =  (InfiniteCanvasViewer)getHost().getRoot().getViewer().getDomain().getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
		}
		return canvas;
	}

	private ItemCreationModel getItemCreationModel() {
		if(creationModel == null) {
			creationModel = getContentViewer().getAdapter(ItemCreationModel.class);
		}
		return creationModel;
	}
	
	@Override
	protected void doActivate() {
		clickableAreaBinding = new DoubleBinding() {
			@Override
			protected double computeValue() {
				IViewer viewer = getContentViewer();
				if (viewer != null) {
					ItemCreationModel creationModel = getItemCreationModel();
					if (creationModel != null && creationModel.isContentPartClickRestricted(ArcPart.class)) {
						return 0;
					}
				}
				return ABSOLUTE_CLICKABLE_WIDTH * MapZoom.getLocationVisualZoom();
			}
		};
		getItemCreationModel().getTypeProperty().addListener(new ChangeListener<Type>() {
			@Override
			public void changed(ObservableValue<? extends Type> observable, Type oldValue, Type newValue) {
				clickableAreaBinding.invalidate();
			}
		});
		getHost().getVisual().clickableAreaWidthProperty().bind(clickableAreaBinding);
		getContentViewer().getCanvas().getContentTransform().mxxProperty().addListener(scaleXListener);
	}

	@Override
	protected void doDeactivate() {
		clickableAreaBinding.dispose();
		getContentViewer().getCanvas().getContentTransform().mxxProperty().removeListener(scaleXListener);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IVisualPart<GeometryNode<Polyline>> getHost() {
		return (IVisualPart<GeometryNode<Polyline>>) super.getHost();
	}
}


