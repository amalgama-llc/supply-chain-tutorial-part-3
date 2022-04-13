package com.company.tutorial3.graphicaleditor.behaviors;

import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.mvc.fx.providers.ShapeOutlineProvider;

public class ImageOutlineProvider extends ShapeOutlineProvider{
	
	@Override
	public IGeometry get() {
//		double scale = 1;
//		IVisualPart<?> p = getAdaptable();
//		Node n = p.getVisual();
//		if(p instanceof HighlightablePart) {
//
//			if(n instanceof AssetVisual) {				
//				scale = MapZoom.getHighlightableImageVisualZoom();
//				IGeometry node = NodeUtils.getShapeOutline(n);
//				Region reg = (Region)node;
//				reg.scale(scale);
//				return node;		
//			}
//		}
		
		return super.get();
	}
}


