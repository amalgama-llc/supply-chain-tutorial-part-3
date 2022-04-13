package com.company.tutorial3.application.pages;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.databinding.EMFProperties;

import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Point;
import com.amalgamasimulation.desktop.binding.UpdateValueStrategyFactory;
import com.amalgamasimulation.desktop.properties.sections.TableSection;
import com.amalgamasimulation.desktop.ui.views.TableView;


public class PointPage extends AbstractPage<Arc>{

	@SuppressWarnings("unchecked")
	private IObservableList<Point> observableBendPoints = EMFProperties.list(DatamodelPackage.Literals.ARC__POINTS).observeDetail(observable);
	
	public PointPage(Messages messages) {
		super(messages, Arc::getScenario);
	}

	@Override
	public boolean isVisible(Object selectedObject) {
		return selectedObject instanceof Arc;
	}
	
	@Override
	protected String getNameClassObject() {
		return messages.obj_POINT;
	}
	
	@Override
	protected String getObjectDisplayName() {
		return messages.obj_POINT;
	}
	
	
	@Override
	protected void createControlsInternal() {
		TableSection<Arc, Point> tableSection = addTableSection(observableBendPoints, DatamodelPackage.Literals.POINT__LONGITUDE, DatamodelPackage.Literals.POINT__LATITUDE);
		TableView<Point> tableView = tableSection.getTableView();
		
		tableView.addColumn(messages.obj_POINT_col_LATITUDE, 100, p -> p.getLatitude())
			.setTextEditingSupport(DatamodelPackage.Literals.POINT__LATITUDE, UpdateValueStrategyFactory.doubleBetweenValues(-90, 90, 10, true));
		tableView.addColumn(messages.obj_POINT_col_LONGITUDE, 100, p -> p.getLongitude())
			.setTextEditingSupport(DatamodelPackage.Literals.POINT__LONGITUDE, UpdateValueStrategyFactory.doubleBetweenValues(-180, 180, 10, true));
	}
}


