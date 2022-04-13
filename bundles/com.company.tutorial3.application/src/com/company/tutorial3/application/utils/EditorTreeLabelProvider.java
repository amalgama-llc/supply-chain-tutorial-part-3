package com.company.tutorial3.application.utils;

import java.net.URL;
import java.util.Set;

import org.eclipse.core.databinding.observable.map.IMapChangeListener;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.map.MapChangeEvent;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public abstract class EditorTreeLabelProvider extends StyledCellLabelProvider {

	private IMapChangeListener mapChangeListener = new IMapChangeListener() {
		@Override
		public void handleMapChange(MapChangeEvent event) {
			Set<?> affectedElements = event.diff.getChangedKeys();
			if (!affectedElements.isEmpty()) {
				fireLabelProviderChanged(new LabelProviderChangedEvent(EditorTreeLabelProvider.this, affectedElements.toArray()));
			}
		}
	};

	protected EditorTreeLabelProvider(IObservableMap... attributeMaps) {
		for (int i = 0; i < attributeMaps.length; i++) {
			attributeMaps[i].addMapChangeListener(mapChangeListener);
		}
	}

	public abstract String getIconPath(Object object);

	public abstract String getName(Object object);

	@Override
	public String getToolTipText(Object element) {
		return "#dummy#";
	}


	@Override
	public void update(ViewerCell cell) {
		StyledString styledString = new StyledString(getName(cell.getElement()), null);
		cell.setText(styledString.getString());
		cell.setStyleRanges(styledString.getStyleRanges());
		Image image = IconsMapping.getImage(getIconPath(cell.getElement()));
		cell.setImage(image);
	}

	private ImageDescriptor createImageDescriptor(String pathIcon) {
		Bundle bundle = FrameworkUtil.getBundle(EditorTreeLabelProvider.class);
		URL url = FileLocator.find(bundle, new Path(pathIcon), null);
		return ImageDescriptor.createFromURL(url);
	}
}



