package com.company.tutorial3.graphicaleditor.parts;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListDiffEntry;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.amalgamasimulation.emf.commands.RemoveCommand;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import javafx.scene.Group;
import javafx.scene.Node;

import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Scenario;

public class ScenarioPart extends AbstractContentPart<Group> {
	
	private boolean updateRestricted = false;
	private IObservableValue<Scenario> scenarioObservable = new WritableValue<>();
	
	public static Map<com.company.tutorial3.datamodel.Node, Set<Arc>> nodeByArcs = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	private IObservableList<com.company.tutorial3.datamodel.Node> nodes = EMFProperties.list(DatamodelPackage.Literals.SCENARIO__NODES).observeDetail(scenarioObservable);
	@SuppressWarnings("unchecked")
	private IObservableList<Arc> arcs = EMFProperties.list(DatamodelPackage.Literals.SCENARIO__ARCS).observeDetail(scenarioObservable);
	
	public ScenarioPart() {
		nodes.addListChangeListener(l -> {
			for (ListDiffEntry<? extends com.company.tutorial3.datamodel.Node> lde : l.diff.getDifferences()) {
				com.company.tutorial3.datamodel.Node node = lde.getElement();
				if (lde.isAddition()) {
					nodeByArcs.computeIfAbsent(node, p -> new HashSet<>());
				} else {
					nodeByArcs.remove(node);
				}
			}
		});
		
		IListChangeListener<Arc> listChangeListener = l -> {
			for (ListDiffEntry<? extends Arc> lde : l.diff.getDifferences()) {
				Arc arc = lde.getElement();
				if (lde.isAddition()) {					
					if(arc.getSource() != null) {
						nodeByArcs.get(arc.getSource()).add(arc);
					}
					if(arc.getDest() != null) {
						nodeByArcs.get(arc.getDest()).add(arc);
					}
				} else {
					if(arc.getSource() != null) {
						nodeByArcs.get(arc.getSource()).remove(arc);
					}
					if(arc.getDest() != null) {
						nodeByArcs.get(arc.getDest()).remove(arc);
					}
				}
			}
		};
		
		arcs.addListChangeListener(listChangeListener);
	}
	
	@Override
	protected void doAddChildVisual(IVisualPart<? extends Node> child, int index) {
		getVisual().getChildren().add(index, child.getVisual());
	}

	@Override
	protected void doAddContentChild(Object contentChild, int index) {}

	@Override
	protected Group doCreateVisual() {
		return new Group();
	}

	public void setUpdateRestricted(boolean updateRestricted) {
		this.updateRestricted = updateRestricted;
	}

	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return HashMultimap.create();
	}
	
	private IListChangeListener<Object> contentsChangeListener = l -> {
		if (!updateRestricted) {
			refreshContentChildren();
		}		
	};
	
	@Override
	public void reorderChild(IVisualPart<? extends Node> child, int index) {
		int oldIndex = getChildrenUnmodifiable().indexOf(child);
		if (oldIndex < 0) {
			throw new IllegalArgumentException("Cannot reorder child " + child + " because it is no child.");
		}
		removeChild(child);
		addChild(child, Math.min(getChildrenUnmodifiable().size(), oldIndex < index ? index - 1 : index));
	}
	
	@Override
	public void addChildren(List<? extends IVisualPart<? extends Node>> children, int index) {
		super.addChildren(children, getChildrenUnmodifiable().size() < index ? getChildrenUnmodifiable().size() : index);
	}
	
	@Override
	protected List<? extends Object> doGetContentChildren() {
		return observable;
	}

	@Override
	protected void doRefreshVisual(Group visual) {}

	@Override
	protected void doRemoveChildVisual(IVisualPart<? extends Node> child, int index) {
		getVisual().getChildren().remove(child.getVisual());
	}

	@Override
	protected void doRemoveContentChild(Object contentChild) {
		observable.remove(contentChild);
		if (contentChild instanceof EObject) {
			new RemoveCommand<EObject>((EObject)contentChild).executeInStack();
		} else {
			throw new IllegalArgumentException("contentChild has invalid type: " + contentChild.getClass());
		}
	}
	
	@SuppressWarnings("unchecked")
	private IObservableList<Object> observable = EMFProperties.multiList(	
			DatamodelPackage.Literals.SCENARIO__ARCS,
			DatamodelPackage.Literals.SCENARIO__NODES).observeDetail(scenarioObservable);
	
	@Override
	public void setContent(Object content) {
		super.setContent(content);
		updateRestricted = true;
		if (content instanceof Scenario) {
			observable.addListChangeListener(contentsChangeListener);			
			scenarioObservable.setValue((Scenario)content);			
		}
		updateRestricted = false;
		refreshContentChildren();
	}

	@Override
	public Scenario getContent() {
		return (Scenario) super.getContent();
	}
}


