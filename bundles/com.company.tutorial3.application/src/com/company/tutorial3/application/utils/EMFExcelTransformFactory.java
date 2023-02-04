package com.company.tutorial3.application.utils;

import com.amalgamasimulation.emf.excel.ColumnHeaderProvider;
import com.amalgamasimulation.emf.excel.EMFExcelTransform;
import com.company.tutorial3.application.utils.validation.ObjectType;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.common.states.AppData;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Scenario;
import static com.company.tutorial3.datamodel.DatamodelPackage.eINSTANCE;
import com.amalgamasimulation.ecoreutils.EcoreutilsPackage;
import com.amalgamasimulation.calendardatamodel.CalendardatamodelPackage;
import com.amalgamasimulation.randomdatamodel.RandomdatamodelPackage;
import com.amalgamasimulation.timeseriesdatamodel.TimeseriesdatamodelPackage;

public class EMFExcelTransformFactory {
	
	private EMFExcelTransformFactory() {}

	public static EMFExcelTransform<com.company.tutorial3.datamodel.Scenario> createExcelTransform() {
		EMFExcelTransform<Scenario> emfExcelTransform = new EMFExcelTransform<Scenario>().setRootClass(DatamodelPackage.eINSTANCE.getScenario())
				.addPackage(EcoreutilsPackage.eINSTANCE)
				.addPackage(CalendardatamodelPackage.eINSTANCE)
				.addPackage(RandomdatamodelPackage.eINSTANCE)
				.addPackage(TimeseriesdatamodelPackage.eINSTANCE)
				;
		emfExcelTransform.setColumnHeaderProvider(EMFExcelTransformFactory.createColumnHeaderProvider(AppData.messages));

		return emfExcelTransform;
	}

	private static ColumnHeaderProvider createColumnHeaderProvider(Messages messages) {
		var columnHeaderProvider = new ColumnHeaderProvider();
		for (ObjectType objectType : ObjectType.values()) {
			objectType.getFeatures().forEach(f -> columnHeaderProvider.addMapping(objectType.getEClass(), f, objectType.getColumnNameExcel(f)));
		}
		columnHeaderProvider
				.addMapping(eINSTANCE.getNode(), null, messages.obj_NODE_col_ID_excel)
				.addMapping(eINSTANCE.getArc(), null, messages.obj_ARC_col_ID_excel)
				;
		
		return columnHeaderProvider;
	}

}

