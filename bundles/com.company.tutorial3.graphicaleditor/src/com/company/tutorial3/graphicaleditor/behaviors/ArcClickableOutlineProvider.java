package com.company.tutorial3.graphicaleditor.behaviors;

import org.eclipse.gef.common.adapt.IAdaptable;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.geometry.planar.ITranslatable;
import org.eclipse.gef.geometry.planar.Polyline;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.providers.ShapeOutlineProvider;

import com.company.tutorial3.graphicaleditor.parts.ArcPart;
import com.company.tutorial3.graphicaleditor.visuals.ArcVisual;
import com.google.inject.Provider;


/**
 * The {@link ShapeOutlineProvider} is a {@link Provider
 * Provider&lt;IGeometry&gt;} that returns an {@link IGeometry} that corresponds
 * to the shape outline of its host visual, i.e. it includes the geometric
 * outline and the stroke of the visual. The {@link IGeometry} is specified
 * within the local coordinate system of the host visual.
 *
 * @author mwienand
 *
 */
public class ArcClickableOutlineProvider extends IAdaptable.Bound.Impl<ArcPart> implements Provider<IGeometry> {

	@Override
	public IGeometry get() {
		ArcVisual mineArcVisual = (ArcVisual) getAdaptable().getVisual();
		Polyline geometry = mineArcVisual.getHandledOutline();
		if (geometry != null) {
			return ((ITranslatable<?>) geometry).getTranslated(-mineArcVisual.getLayoutX(), -mineArcVisual.getLayoutY());
		} else {
			return new Rectangle();
		}
	}
}


