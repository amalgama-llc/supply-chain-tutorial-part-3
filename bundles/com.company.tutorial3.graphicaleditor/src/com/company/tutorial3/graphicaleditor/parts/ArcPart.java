package com.company.tutorial3.graphicaleditor.parts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.FeaturePath;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.Polyline;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;
import org.eclipse.gef.mvc.fx.parts.IBendableContentPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import com.amalgamasimulation.desktop.binding.ListChangeManager;
import com.amalgamasimulation.emf.commands.CommandsManager;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import javafx.scene.transform.Affine;
import javafx.scene.transform.Translate;
import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.DatamodelFactory;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Point;
import com.company.tutorial3.datamodel.Scenario;
import com.company.tutorial3.graphicaleditor.policies.MapZoom;
import com.company.tutorial3.graphicaleditor.view.InfiniteCanvasViewer;
import com.company.tutorial3.graphicaleditor.visuals.ArcVisual;


public class ArcPart extends AbstractContentPart<ArcVisual> implements IBendableContentPart<ArcVisual> {

	protected IObservableValue<Arc> linkObservable = new WritableValue<>();
	private Polyline bounds;
	@SuppressWarnings("unchecked")
	private IObservableValue<Node> observableSourceLocation = EMFProperties.value(DatamodelPackage.Literals.ARC__SOURCE).observeDetail(linkObservable);
	@SuppressWarnings("unchecked")
	private IObservableValue<Node> observableDestLocation = EMFProperties.value(DatamodelPackage.Literals.ARC__DEST).observeDetail(linkObservable);
	@SuppressWarnings("unchecked")
	private IObservableList<Point> observableBendPoint = EMFProperties.list(DatamodelPackage.Literals.ARC__POINTS).observeDetail(linkObservable);

	@SuppressWarnings("unchecked")
	private IObservableValue<Scenario> sourceLocationXObservable = EMFProperties.value(FeaturePath.fromList(DatamodelPackage.Literals.ARC__SOURCE, DatamodelPackage.Literals.NODE__LATITUDE)).observeDetail(linkObservable);
	@SuppressWarnings("unchecked")
	private IObservableValue<Scenario> sourceLocationYObservable = EMFProperties.value(FeaturePath.fromList(DatamodelPackage.Literals.ARC__SOURCE, DatamodelPackage.Literals.NODE__LONGITUDE)).observeDetail(linkObservable);
	@SuppressWarnings("unchecked")
	private IObservableValue<Scenario> destLocationXObservable = EMFProperties.value(FeaturePath.fromList(DatamodelPackage.Literals.ARC__DEST, DatamodelPackage.Literals.NODE__LATITUDE)).observeDetail(linkObservable);
	@SuppressWarnings("unchecked")
	private IObservableValue<Scenario> destLocationYObservable = EMFProperties.value(FeaturePath.fromList(DatamodelPackage.Literals.ARC__DEST, DatamodelPackage.Literals.NODE__LONGITUDE)).observeDetail(linkObservable);

	private IValueChangeListener<Object> valueChangeListener = event -> refreshVisual();
	private IListChangeListener<Object> listChangeListener = event -> refreshVisual();
	
	private ListChangeManager<Point> bendpointsManager = new ListChangeManager<Point>(
			observableBendPoint,c -> updateBendpoint(c.getObservable().getValue()),
			DatamodelPackage.Literals.POINT__LATITUDE,
			DatamodelPackage.Literals.POINT__LONGITUDE);

	public ArcPart() {
		observableSourceLocation.addValueChangeListener(valueChangeListener);
		observableDestLocation.addValueChangeListener(valueChangeListener);
		observableBendPoint.addListChangeListener(listChangeListener);
		sourceLocationXObservable.addValueChangeListener(valueChangeListener);
		sourceLocationYObservable.addValueChangeListener(valueChangeListener);
		destLocationXObservable.addValueChangeListener(valueChangeListener);
		destLocationYObservable.addValueChangeListener(valueChangeListener);
	}



	@Override
	protected ArcVisual doCreateVisual() {
		return new ArcVisual(this, MapZoom.getLocationVisualZoom());
	}
	
	@Override
	protected List<? extends Object> doGetContentChildren() {
		return Collections.emptyList();
	}

	@Override
	public Arc getContent() {
		return (Arc) super.getContent();
	}
	
	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return HashMultimap.create();
	}
	
	private void updateBendpoint(Point bp) {
		refreshVisual();
	}
	
	@Override
	public Dimension getContentSize() {
		return bounds == null ? new Dimension(0, 0) : bounds.getBounds().getSize();
	}

	@Override
	public Affine getContentTransform() {
		return new Affine(new Translate(bounds.getBounds().getX(), bounds.getBounds().getY()));
	}

	@Override
	public void setContent(Object model) {
		super.setContent(model);
		bendpointsManager.setPaused(true);	
		linkObservable.setValue((Arc)model);	
		bendpointsManager.setPaused(false);
	}
	
	@Override
	public void dispose() {
		bendpointsManager.dispose();
		super.dispose();
	}

	@Override
	public void setContentSize(Dimension size) {
		bounds.getBounds().setSize(size);
	}
	
	@Override
	public void setContentTransform(Affine totalTransform) {}

	@Override
	public Dimension getVisualSize() {
		return IBendableContentPart.super.getVisualSize();
	}

	@Override
	public void setVisualSize(Dimension totalSize) {
		IBendableContentPart.super.setVisualSize(totalSize);
	}
	

	public static int gapForCorrectVisualization = 5;
	@Override
	public List<BendPoint> getContentBendPoints() {
		Arc content = getContent();
		List<BendPoint> bendPoints = new ArrayList<>();
		if(getContentViewer() == null)return bendPoints;
		com.amalgamasimulation.geometry.Point p = getContentViewer().getInfiniteCanvas().lonLatToLogic(content.getSource().getLongitude() , content.getSource().getLatitude());
		bendPoints.add(new BendPoint(getContent().getSource(), new org.eclipse.gef.geometry.planar.Point(p.getX() - gapForCorrectVisualization, p.getY() - gapForCorrectVisualization)));
		
		for (Point bendpoint : content.getPoints()) {
			com.amalgamasimulation.geometry.Point bp = 
					getContentViewer().getInfiniteCanvas().lonLatToLogic(bendpoint.getLongitude() , bendpoint.getLatitude());
			bendPoints.add(new BendPoint(bendpoint, new org.eclipse.gef.geometry.planar.Point(bp.getX(), bp.getY())));
		}
		p = getContentViewer().getInfiniteCanvas().lonLatToLogic(content.getDest().getLongitude() , content.getDest().getLatitude());
		bendPoints.add(new BendPoint(getContent().getDest(), new org.eclipse.gef.geometry.planar.Point(p.getX() - gapForCorrectVisualization, p.getY() - gapForCorrectVisualization)));
		
		return bendPoints;
	}

	@Override
	protected void doRefreshVisual(ArcVisual visual) {
		Arc content = getContent();
		if (content != null && content.getSource() != null && content.getDest() != null && getContentViewer() != null) {			
			List<org.eclipse.gef.geometry.planar.Point> wayPoints = new ArrayList<>();
			com.amalgamasimulation.geometry.Point p = getContentViewer().getInfiniteCanvas().lonLatToLogic(content.getSource().getLongitude() , content.getSource().getLatitude());
			wayPoints.add(new org.eclipse.gef.geometry.planar.Point(p.getX() - gapForCorrectVisualization, p.getY() - gapForCorrectVisualization));
			bounds = new Polyline(wayPoints.toArray(new org.eclipse.gef.geometry.planar.Point[wayPoints.size()]));
			setVisualBendPoints(getContentBendPoints());		
		}
	}

	@Override
	public void setContentBendPoints(List<BendPoint> contentBendPoints) {
		if (	contentBendPoints.size() < 2 
				|| !contentBendPoints.get(0).isAttached() 
				|| !contentBendPoints.get(contentBendPoints.size() - 1).isAttached()) {
			doRefreshVisual(getVisual());
			return;
		}
		
		Arc mineArc = getContent();
		CompoundCommand compoundCommand = new CompoundCommand();
		List<Point> graphBendpoints = new ArrayList<>();
		for (int i = 0; i < contentBendPoints.size(); i++) {
			BendPoint bp = contentBendPoints.get(i);
			com.amalgamasimulation.geometry.Point p = getContentViewer().getInfiniteCanvas().logicToLonLat(bp.getPosition().x , bp.getPosition().y);
			if (i == 0) {
				if (mineArc.getSource() != bp.getContentAnchorage()) {
					compoundCommand.append(SetCommand.create(CommandsManager.getEditingDomain(), mineArc, DatamodelPackage.Literals.ARC__SOURCE, bp.getContentAnchorage()));
				}
			} else if (i == contentBendPoints.size() - 1) {
				if (mineArc.getDest() != bp.getContentAnchorage()) {
					compoundCommand.append(SetCommand.create(CommandsManager.getEditingDomain(), mineArc, DatamodelPackage.Literals.ARC__DEST, bp.getContentAnchorage()));
				}
			} else {
				Point bendpoint = DatamodelFactory.eINSTANCE.createPoint();
				bendpoint.setLongitude(p.getX());
				bendpoint.setLatitude(p.getY());
				graphBendpoints.add(bendpoint);
			}
		}
		if (!mineArc.getPoints().equals(graphBendpoints)) {
			compoundCommand.append(SetCommand.create(CommandsManager.getEditingDomain(), mineArc, DatamodelPackage.Literals.ARC__POINTS, graphBendpoints));
		}
		if (!compoundCommand.isEmpty()) {
			CommandsManager.getEditingDomain().getCommandStack().execute(compoundCommand);
			refreshVisual();
		}

	}
	
	protected InfiniteCanvasViewer getContentViewer() {
		if(getViewer() == null)return null;
		if(getViewer().getDomain() == null)return null;
		return (InfiniteCanvasViewer)getViewer().getDomain().getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
	}
	
	
	private List<BendPoint> visualBendpoints = new ArrayList<>();
	
	@Override
	public List<BendPoint> getVisualBendPoints() {
		return visualBendpoints;
	}
	
	
	@Override
	public void setVisualBendPoints(List<BendPoint> visualBendpoints) {
		this.visualBendpoints = visualBendpoints;
		getVisual().invalidateMineArc(visualBendpoints);
	}


}


