/**
 */
package com.company.tutorial3.datamodel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Warehouse</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.company.tutorial3.datamodel.Warehouse#getScenario <em>Scenario</em>}</li>
 * </ul>
 *
 * @see com.company.tutorial3.datamodel.DatamodelPackage#getWarehouse()
 * @model
 * @generated
 */
public interface Warehouse extends Asset {
	/**
	 * Returns the value of the '<em><b>Scenario</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.company.tutorial3.datamodel.Scenario#getWarehouses <em>Warehouses</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scenario</em>' container reference.
	 * @see #setScenario(Scenario)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getWarehouse_Scenario()
	 * @see com.company.tutorial3.datamodel.Scenario#getWarehouses
	 * @model opposite="warehouses" transient="false"
	 * @generated
	 */
	Scenario getScenario();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Warehouse#getScenario <em>Scenario</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scenario</em>' container reference.
	 * @see #getScenario()
	 * @generated
	 */
	void setScenario(Scenario value);

} // Warehouse
