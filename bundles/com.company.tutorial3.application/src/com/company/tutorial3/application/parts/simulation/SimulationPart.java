package com.company.tutorial3.application.parts.simulation;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.nebula.widgets.geomap.TileServer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;

import com.company.tutorial3.application.animation.ArcShape;
import com.company.tutorial3.application.animation.NodeShape;
import com.company.tutorial3.application.animation.StoreShape;
import com.company.tutorial3.application.animation.TruckShape;
import com.company.tutorial3.application.animation.WarehouseShape;
import com.company.tutorial3.application.utils.IconsMapping;
import com.company.tutorial3.application.utils.Topics;
import com.company.tutorial3.simulation.model.Model;
import com.company.tutorial3.simulation.model.Node;
import com.amalgamasimulation.desktop.ui.views.ToolBarComposite;
import com.amalgamasimulation.desktop.utils.MessageManager;
import com.amalgamasimulation.desktop.utils.ToolbarUtils;
import com.amalgamasimulation.geometry.Point;
import com.amalgamasimulation.platform.animation.swt.SWT2DSimulationView;
import com.amalgamasimulation.platform.animation.swt.SWT2DSimulationViewImpl;
import com.amalgamasimulation.viewupdater.service.IViewUpdaterService;


public class SimulationPart {

	@Inject
	private MessageManager messageManager;
	
	@Inject
	private IViewUpdaterService viewUpdaterService;
	
	private SWT2DSimulationView animationView;
	private Model model;
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		new ToolBarComposite(parent, this::createToolBar, this::createContent);
	}
	
	private void createContent(Composite parent) {
		animationView = new SWT2DSimulationViewImpl(parent);
		messageManager.subscribe(Topics.SHOW_MODEL, this::onShowModel, true);
		viewUpdaterService.getDefaultUpdater().addView(animationView);
	}
	private ToolBar createToolBar(Composite parent) {
 		ToolBar toolBar = new ToolBar(parent, SWT.HORIZONTAL);
		ToolbarUtils.addCommandItem(toolBar, IconsMapping.CENTERING, "Center", () -> showAllMap()).setText("Center");
 		return toolBar;
	}
	
	private void onShowModel(Model model) {
		this.model = model;
		animationView.removeAllShapes();
		
		model.getGraphEnvironment().getNodeValues().stream().forEach(n -> animationView.addShape(new NodeShape(n)));
		model.getArcs().forEach(r -> animationView.addShape(new ArcShape(r)));	
		
		model.getWarehouses().forEach(w -> animationView.addShape(new WarehouseShape(w)));
		model.getStores().forEach(w -> animationView.addShape(new StoreShape(w)));
		model.getTrucks().forEach(t -> animationView.addShape(new TruckShape(t)));

		showAllMap();
	}
	
	private void showAllMap() {
		if (model != null) {
			List<Node> assets = model.getGraphEnvironment().getNodeValues();
			if (!assets.isEmpty()) {
				double minX = Double.POSITIVE_INFINITY;
				double minY = Double.POSITIVE_INFINITY;
				double maxX = Double.NEGATIVE_INFINITY;
				double maxY = Double.NEGATIVE_INFINITY;
				for (Node asset : assets) {
					Point point = asset.getPoint();
					minX = Math.min(point.getX(), minX);
					minY = Math.min(point.getY(), minY);
					maxX = Math.max(point.getX(), maxX);
					maxY = Math.max(point.getY(), maxY);
				}
				animationView.navigateTo(new Point(minX, minY), new Point(maxX, maxY));
				animationView.updateView();
			}
		}
	}
}

