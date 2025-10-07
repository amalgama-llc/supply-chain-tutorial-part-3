package com.company.tutorial3.application.utils;

import com.amalgamasimulation.ecoreutils.EcoreutilsPackage;
import com.amalgamasimulation.emf.excel.EMFExcelTransform;
import com.amalgamasimulation.randomdatamodel.RandomdatamodelPackage;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Scenario;

public class EMFExcelTransformFactory {
	
	private EMFExcelTransformFactory() {}

	public static EMFExcelTransform<com.company.tutorial3.datamodel.Scenario> createExcelTransform() {
		EMFExcelTransform<Scenario> emfExcelTransform = new EMFExcelTransform<Scenario>().setRootClass(DatamodelPackage.eINSTANCE.getScenario())
				.addPackage(EcoreutilsPackage.eINSTANCE)
				.addPackage(RandomdatamodelPackage.eINSTANCE)
				;

		return emfExcelTransform;
	}
}
