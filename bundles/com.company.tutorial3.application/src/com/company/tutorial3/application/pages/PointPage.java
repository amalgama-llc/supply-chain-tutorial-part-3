package com.company.tutorial3.application.pages;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.databinding.EMFProperties;

import com.amalgamasimulation.desktop.binding.ValidationStrategies;
import com.amalgamasimulation.desktop.ui.editor.EmfPage;
import com.company.tutorial3.application.localization.Messages;
import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Point;

public class PointPage extends EmfPage<Arc> {

	@SuppressWarnings("unchecked")
	private IObservableList<Point> observableBendPoints = EMFProperties.list(DatamodelPackage.Literals.ARC__POINTS).observeDetail(observable);
	
	public PointPage() {
		super(Arc.class);
	}
	
	@Override
	protected String getTab() {
		return Messages.messages().tab_bendpoint;
	}
	
	@Override
	protected void createContentsInternal() {
		emfTableEditor(Point.class)
			.elementsFeature(DatamodelPackage.Literals.ARC__POINTS)
			.columns(table -> {
				table	.column(p -> p.getX())
							.name(Messages.messages().obj_POINT_col_X)
							.width(100)
							.emfTextEditor()
								.feature(DatamodelPackage.Literals.POINT__X)
								.strategy(ValidationStrategies.doubleAny())
								.build();
				table	.column(p -> p.getY())
						.name(Messages.messages().obj_POINT_col_Y)
						.width(100)
						.emfTextEditor()
							.feature(DatamodelPackage.Literals.POINT__Y)
							.strategy(ValidationStrategies.doubleAny())
							.build();
			})
			.observableFeatures(DatamodelPackage.Literals.POINT__X, DatamodelPackage.Literals.POINT__Y)
			.create();
	}
}

