package com.company.tutorial3.graphicaleditor.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.fx.swt.canvas.IFXCanvasFactory;
import org.eclipse.gef.mvc.fx.domain.HistoricizingDomain;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.google.inject.Inject;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import com.company.tutorial3.datamodel.Scenario;
import com.company.tutorial3.graphicaleditor.Activator;
import com.company.tutorial3.graphicaleditor.GraphEditorModule;
import com.company.tutorial3.graphicaleditor.model.PaletteNode;
import com.company.tutorial3.graphicaleditor.model.PaletteNode.PaletteNodeType;
import com.company.tutorial3.graphicaleditor.parts.ArcPart;
import com.company.tutorial3.graphicaleditor.parts.HighlightablePart;
import com.company.tutorial3.graphicaleditor.visuals.ArcVisual;

public class GraphicalEditor {
	
	@Inject
	private IDomain domain;
	
	@Inject 
	private IFXCanvasFactory canvasFactory;

	private FXCanvas canvas;
	
	public GraphicalEditor(Composite parent, Storage storage) {
		Activator.GetInjector().injectMembers(this);
		canvas = canvasFactory.createCanvas(parent, SWT.NONE);
		
		IViewer contentViewer = getContentViewer();
		IViewer paletteViewer = getPaletteViewer();
		((PaletteRootPart)getPaletteViewer().getRootPart()).setInfiniteCanvasViewer(getContentViewer());
		
		canvas.setScene(new Scene(new GraphEditorComposite(contentViewer, paletteViewer).getComposite()));

		storage.setHistoricizingDomain((HistoricizingDomain)domain);
		storage.setContentViewer(contentViewer);
		
		domain.activate();
	}
	
	public void deselectAll() {
		getContentViewer().deselectAll();
	}
	
	public void setWaitingForSelection(Predicate<Object> filter) {
		getContentViewer().setWaitingForSelection(filter);
	}
	
	public boolean isWaitingForSelection() {
		return getContentViewer().isWaitingForSelection();
	}
	
	
	public FXCanvas getCanvas() {
		return canvas;
	}
	
	public void adjustWindow() {
		getContentViewer().adjustWindow();
	}
	
	public void escKeyTyped() {
		getContentViewer().escKeyTyped();
	}
	
	public void addMouseMoveListener(BiConsumer<Double, Double> listener) {
		getContentViewer().addMouseMoveListener(listener);
	}
	
	public void addObjectClickListener(Consumer<Object> listener) {
		getContentViewer().addObjectClickListener(listener);
	}
	
	public void removeObjectClickListener(Consumer<Object> listener) {
		getContentViewer().removeObjectClickListener(listener);
	}
	
	public void addObjectsSelectionListener(Consumer<List<Object>> listener) {
		getContentViewer().addObjectsSelectionListener(listener);
	}
	
	public void removeObjectsSelectionListener(Consumer<List<Object>> listener) {
		getContentViewer().removeObjectsSelectionListener(listener);
	}
	
	public void setScenario(Scenario scenario) {
		if (scenario != null) {
			getContentViewer().getContents().setAll(scenario);
			getPaletteViewer().getContents().setAll(createPaletteViewerContents(scenario));
		} else {
			getContentViewer().getContents().clear();
			getPaletteViewer().getContents().clear();
		}
	}

	public static List<Object> createPaletteViewerContents(Scenario scenario) {
		List<Object> paletteContents = new ArrayList<>();
		
		paletteContents.add(new PaletteNode(PaletteNodeType.NODE, scenario));
		paletteContents.add(new PaletteNode(PaletteNodeType.ARC, scenario));
		
		return paletteContents;
	}
	
	private List<Object> highlightedContentParts = new ArrayList<>();
	private List<Object> highlightedAsDependencyContentParts = new ArrayList<>();
	

	
	public void dehighlightAllDependencies() {
		if (!highlightedAsDependencyContentParts.isEmpty()) {
			for(Object o: highlightedAsDependencyContentParts) {
				if(o instanceof HighlightablePart) {
					((HighlightablePart)o).highlightAsDependency(false, null);
				}
			}
			highlightedAsDependencyContentParts.clear();
		}
	}
	
	public void dehighlightAll() {
		if (!highlightedContentParts.isEmpty()) {
			for(Object o: highlightedContentParts) {
				if(o instanceof HighlightablePart) {
					((HighlightablePart)o).highlight(false);
				}else if(o instanceof ArcPart) {
					((ArcPart)o).getVisual().unHighlighted();
				}
			}
			highlightedContentParts.clear();
		}
	}
	
	
	public void highlight(List<Object> objects) {
		dehighlightAll();
		for (Object object : objects) {
			highlightInternal(object);			
		}
	}
	
	public void highlight(Object object) {
		dehighlightAll();
		highlightInternal(object);			
	}
	
	private void highlightInternal(Object object) {
		if (getContentViewer().getContentPartMap().containsKey(object)) {
			IContentPart<?> contentPart = getContentViewer().getContentPartMap().get(object);
			if (contentPart instanceof HighlightablePart) {
				HighlightablePart<?> highlightablePart = (HighlightablePart<?>)contentPart;
				highlightablePart.highlight(true);
				highlightedContentParts.add(highlightablePart);
			}else if(contentPart instanceof ArcPart) {
				ArcPart arcPart = (ArcPart)contentPart;
				((ArcVisual)arcPart.getVisual()).highlighted();
				highlightedContentParts.add(arcPart);
			}
		}
	}
	
	private void highlightAsDependencyInternal(Object object, Color color) {
		if (object != null && getContentViewer().getContentPartMap().containsKey(object)) {
			IContentPart<?> contentPart = getContentViewer().getContentPartMap().get(object);
			if (contentPart instanceof HighlightablePart) {
				HighlightablePart<?> highlightablePart = (HighlightablePart<?>)contentPart;
				highlightablePart.highlightAsDependency(true, color);
				highlightedAsDependencyContentParts.add(highlightablePart);
			}
		}
	}
	
	public void highlightDependencies(Object object) {
		highlightDependencies(object instanceof List ? (List<?>)object : Arrays.asList(new Object[] {object}));
	}
	
	public void highlightDependencies(List<?> objects) {
		dehighlightAllDependencies();
	}
	

	public InfiniteCanvasViewer getContentViewer() {
		return (InfiniteCanvasViewer)domain.getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
	}
	
	private IViewer getPaletteViewer() {
		return domain.getAdapter(AdapterKey.get(IViewer.class, GraphEditorModule.PALETTE_VIEWER_ROLE));
	}
}


