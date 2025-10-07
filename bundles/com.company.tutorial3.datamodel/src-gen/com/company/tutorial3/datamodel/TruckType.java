/**
 */
package com.company.tutorial3.datamodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Truck Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.company.tutorial3.datamodel.TruckType#getName <em>Name</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.TruckType#getSpeed <em>Speed</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.TruckType#getQuantity <em>Quantity</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.TruckType#getOwnershipCostPerHour <em>Ownership Cost Per Hour</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.TruckType#getUsageCostPerHour <em>Usage Cost Per Hour</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.TruckType#getScenario <em>Scenario</em>}</li>
 * </ul>
 *
 * @see com.company.tutorial3.datamodel.DatamodelPackage#getTruckType()
 * @model
 * @generated
 */
public interface TruckType extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getTruckType_Name()
	 * @model id="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.TruckType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Speed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Speed</em>' attribute.
	 * @see #setSpeed(double)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getTruckType_Speed()
	 * @model
	 * @generated
	 */
	double getSpeed();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.TruckType#getSpeed <em>Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Speed</em>' attribute.
	 * @see #getSpeed()
	 * @generated
	 */
	void setSpeed(double value);

	/**
	 * Returns the value of the '<em><b>Quantity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Quantity</em>' attribute.
	 * @see #setQuantity(int)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getTruckType_Quantity()
	 * @model
	 * @generated
	 */
	int getQuantity();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.TruckType#getQuantity <em>Quantity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Quantity</em>' attribute.
	 * @see #getQuantity()
	 * @generated
	 */
	void setQuantity(int value);

	/**
	 * Returns the value of the '<em><b>Ownership Cost Per Hour</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ownership Cost Per Hour</em>' attribute.
	 * @see #setOwnershipCostPerHour(double)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getTruckType_OwnershipCostPerHour()
	 * @model
	 * @generated
	 */
	double getOwnershipCostPerHour();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.TruckType#getOwnershipCostPerHour <em>Ownership Cost Per Hour</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ownership Cost Per Hour</em>' attribute.
	 * @see #getOwnershipCostPerHour()
	 * @generated
	 */
	void setOwnershipCostPerHour(double value);

	/**
	 * Returns the value of the '<em><b>Usage Cost Per Hour</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Usage Cost Per Hour</em>' attribute.
	 * @see #setUsageCostPerHour(double)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getTruckType_UsageCostPerHour()
	 * @model
	 * @generated
	 */
	double getUsageCostPerHour();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.TruckType#getUsageCostPerHour <em>Usage Cost Per Hour</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Usage Cost Per Hour</em>' attribute.
	 * @see #getUsageCostPerHour()
	 * @generated
	 */
	void setUsageCostPerHour(double value);

	/**
	 * Returns the value of the '<em><b>Scenario</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.company.tutorial3.datamodel.Scenario#getTruckTypes <em>Truck Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scenario</em>' container reference.
	 * @see #setScenario(Scenario)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getTruckType_Scenario()
	 * @see com.company.tutorial3.datamodel.Scenario#getTruckTypes
	 * @model opposite="truckTypes" transient="false"
	 * @generated
	 */
	Scenario getScenario();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.TruckType#getScenario <em>Scenario</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scenario</em>' container reference.
	 * @see #getScenario()
	 * @generated
	 */
	void setScenario(Scenario value);

} // TruckType
