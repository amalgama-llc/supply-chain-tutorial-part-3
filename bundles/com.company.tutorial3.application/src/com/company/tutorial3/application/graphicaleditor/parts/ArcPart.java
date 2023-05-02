package com.company.tutorial3.application.graphicaleditor.parts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.amalgamasimulation.graphicaleditor.parts.AbstractArcPart;
import com.company.tutorial3.application.states.AppState;
import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.DatamodelFactory;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Point;
import com.company.tutorial3.datamodel.Scenario;

public class ArcPart extends AbstractArcPart<Arc, Scenario, Node, Point> {
	
	@Override
	public boolean isEndDecorationVisible() {
		return true;
	}
	
	@Override
	public EStructuralFeature getNameFeature() {
		return DatamodelPackage.Literals.ARC__NAME;
	}

	@Override
	public EStructuralFeature getArcBendpointsFeature() {
		return DatamodelPackage.Literals.ARC__POINTS;
	}

	@Override
	public EStructuralFeature getSourceNodeFeature() {
		return DatamodelPackage.Literals.ARC__SOURCE;
	}

	@Override
	public EStructuralFeature getDestNodeFeature() {
		return DatamodelPackage.Literals.ARC__DEST;
	}

	@Override
	public EStructuralFeature getNodeXFeature() {
			return DatamodelPackage.Literals.NODE__X;
	}

	@Override
	public EStructuralFeature getNodeYFeature() {
			return DatamodelPackage.Literals.NODE__Y;
	}

	@Override
	public EStructuralFeature getNodeZFeature() {
		return null;
	}

	@Override
	public EStructuralFeature getBendpointXFeature() {
			return DatamodelPackage.Literals.POINT__X;
	}

	@Override
	public EStructuralFeature getBendpointYFeature() {
			return DatamodelPackage.Literals.POINT__Y;
	}

	@Override
	public EStructuralFeature getBendpointZFeature() {
		return null;
	}

	@Override
	public Point createBendpoint() {
		return DatamodelFactory.eINSTANCE.createPoint();
	}

	@Override
	public EStructuralFeature getContainerStructuralFeature() {
		return DatamodelPackage.Literals.ARC__SCENARIO;
	}
	
	@Override
	public String getNewObjectName(EObject container, EObject eObject) {
		return AppState.messages.obj_ARC;
	}
	
}

