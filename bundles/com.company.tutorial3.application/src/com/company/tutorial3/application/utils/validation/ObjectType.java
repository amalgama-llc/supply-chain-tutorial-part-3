package com.company.tutorial3.application.utils.validation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.company.tutorial3.application.localization.Messages;
import com.company.tutorial3.application.utils.TreeElementType;
import com.company.tutorial3.datamodel.DatamodelPackage;


public enum ObjectType {
	
	SCENARIO	(TreeElementType.SCENARIO, Messages.messages().obj_SCENARIO,  DatamodelPackage.Literals.SCENARIO,
			FieldDescriptor.of(DatamodelPackage.Literals.SCENARIO__NAME,	Messages.messages().obj_SCENARIO_col_NAME,	Messages.messages().obj_SCENARIO_col_NAME_excel),
			FieldDescriptor.of(DatamodelPackage.Literals.SCENARIO__BEGIN_DATE,	Messages.messages().obj_SCENARIO_col_BEGIN_DATE,	Messages.messages().obj_SCENARIO_col_BEGIN_DATE_excel),
			FieldDescriptor.of(DatamodelPackage.Literals.SCENARIO__END_DATE,	Messages.messages().obj_SCENARIO_col_END_DATE,	Messages.messages().obj_SCENARIO_col_END_DATE_excel)
				)
	,
	
	NODE		(TreeElementType.NODE, Messages.messages().obj_NODE,			DatamodelPackage.Literals.NODE,
			FieldDescriptor.of(DatamodelPackage.Literals.NODE__NAME,	Messages.messages().obj_NODE_col_NAME,	Messages.messages().obj_NODE_col_NAME_excel),
			FieldDescriptor.of(DatamodelPackage.Literals.NODE__X, Messages.messages().obj_NODE_col_X, Messages.messages().obj_NODE_col_X_excel),
			FieldDescriptor.of(DatamodelPackage.Literals.NODE__Y,	Messages.messages().obj_NODE_col_Y, Messages.messages().obj_NODE_col_Y_excel)
				),
	
	ARC			(TreeElementType.ARC, Messages.messages().obj_ARC,					DatamodelPackage.Literals.ARC,
			FieldDescriptor.of(DatamodelPackage.Literals.ARC__ID, 	Messages.messages().obj_ARC_col_ID, 	Messages.messages().obj_ARC_col_ID_excel),
			FieldDescriptor.of(DatamodelPackage.Literals.ARC__NAME,	Messages.messages().obj_ARC_col_NAME,	Messages.messages().obj_SCENARIO_col_NAME_excel),
			FieldDescriptor.of(	DatamodelPackage.Literals.ARC__SOURCE,	Messages.messages().obj_ARC_col_SOURCE, 	Messages.messages().obj_ARC_col_SOURCE_excel),
			FieldDescriptor.of(	DatamodelPackage.Literals.ARC__DEST,	Messages.messages().obj_ARC_col_DEST, 	Messages.messages().obj_ARC_col_DEST_excel)
				),
	
	
	POINT		(TreeElementType.ARC, Messages.messages().obj_POINT,					DatamodelPackage.Literals.POINT,
			FieldDescriptor.of(	DatamodelPackage.Literals.POINT__ARC, Messages.messages().obj_POINT_col_ARC,	Messages.messages().obj_POINT_col_ARC_excel),
			FieldDescriptor.of(	DatamodelPackage.Literals.POINT__X, Messages.messages().obj_POINT_col_X,	Messages.messages().obj_POINT_col_X_excel),
			FieldDescriptor.of(	DatamodelPackage.Literals.POINT__Y, Messages.messages().obj_POINT_col_Y,	Messages.messages().obj_POINT_col_Y_excel)
			);
	
	
	private final String name;
	private final EClass eClass;
	private final TreeElementType treeElementType;
	private final Map<EStructuralFeature, FieldDescriptor> fieldDescriptors;
	
	private final String partID;
	
	private ObjectType(TreeElementType treeElementType, String name,  EClass eClass, FieldDescriptor...fieldDescriptors) {
		this.name = name;
		this.treeElementType = treeElementType;
		this.eClass = eClass;
		this.fieldDescriptors = Arrays.stream(fieldDescriptors).collect(Collectors.toMap(FieldDescriptor::getStructuralFeature, Function.identity()));
		this.partID = null;
	}
	
	public String getName() {
		return name;
	}
	
	public TreeElementType getTreeElementType() {
		return treeElementType;
	}

	public Collection<EStructuralFeature> getFeatures() {
		return fieldDescriptors.keySet();
	}
	
	public String getFeatureName(EStructuralFeature structuralFeature) {
		FieldDescriptor fd = fieldDescriptors.get(structuralFeature);
		if (fd != null) {
			return fd.getColumnName();
		}
		return "";
	}
	
	public String getColumnNameExcel(EStructuralFeature structuralFeature) {
		FieldDescriptor fd = fieldDescriptors.get(structuralFeature);
		if (fd != null) {
			return fd.getColumnNameExcel();
		}
		return "";
	}
	
	public EClass getEClass() {
		return eClass;
	}
	
	public String getPartID() {
		return partID;
	}
	
	public static ObjectType objectTypeByObject(Object object) {
		if (object instanceof EObject) {
			EClass eclass = ((EObject) object).eClass();
			for (ObjectType type : values()) {
				if (type.eClass.equals(eclass)) {
					return type;
				}
			}
		}
		return null;
	}
	
}





class FieldDescriptor {
	private final EStructuralFeature structuralFeature;
	private final String columnName;
	private final String columnNameExcel;
	
	private FieldDescriptor(EStructuralFeature structuralFeature, String columnName, String columnNameExcel) {
		this.structuralFeature = structuralFeature;
		this.columnName = columnName;
		this.columnNameExcel = columnNameExcel;
	}
	
	public static FieldDescriptor of(EStructuralFeature structuralFeature, String columnName, String columnNameExcel) {
		return new FieldDescriptor(structuralFeature, columnName, columnNameExcel);
	}
	
	public EStructuralFeature getStructuralFeature() {
		return this.structuralFeature;
	}
	
	public String getColumnName() {
		return this.columnName;
	}
	
	public String getColumnNameExcel() {
		return this.columnNameExcel;
	}
	
}

