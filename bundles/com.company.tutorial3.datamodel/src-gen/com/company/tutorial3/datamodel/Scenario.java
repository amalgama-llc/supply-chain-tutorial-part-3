/**
 */
package com.company.tutorial3.datamodel;

import com.amalgamasimulation.randomdatamodel.Distribution;
import java.time.LocalDateTime;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Scenario</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.company.tutorial3.datamodel.Scenario#getName <em>Name</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Scenario#getBeginDate <em>Begin Date</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Scenario#getEndDate <em>End Date</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Scenario#getNodes <em>Nodes</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Scenario#getArcs <em>Arcs</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Scenario#getWarehouses <em>Warehouses</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Scenario#getStores <em>Stores</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Scenario#getTrucks <em>Trucks</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Scenario#getMaxDeliveryTimeHrs <em>Max Delivery Time Hrs</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Scenario#getIntervalBetweenRequests <em>Interval Between Requests</em>}</li>
 * </ul>
 *
 * @see com.company.tutorial3.datamodel.DatamodelPackage#getScenario()
 * @model
 * @generated
 */
public interface Scenario extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getScenario_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Scenario#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Begin Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Begin Date</em>' attribute.
	 * @see #setBeginDate(LocalDateTime)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getScenario_BeginDate()
	 * @model dataType="com.amalgamasimulation.ecoreutils.LocalDateTime"
	 * @generated
	 */
	LocalDateTime getBeginDate();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Scenario#getBeginDate <em>Begin Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Begin Date</em>' attribute.
	 * @see #getBeginDate()
	 * @generated
	 */
	void setBeginDate(LocalDateTime value);

	/**
	 * Returns the value of the '<em><b>End Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Date</em>' attribute.
	 * @see #setEndDate(LocalDateTime)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getScenario_EndDate()
	 * @model dataType="com.amalgamasimulation.ecoreutils.LocalDateTime"
	 * @generated
	 */
	LocalDateTime getEndDate();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Scenario#getEndDate <em>End Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Date</em>' attribute.
	 * @see #getEndDate()
	 * @generated
	 */
	void setEndDate(LocalDateTime value);

	/**
	 * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link com.company.tutorial3.datamodel.Node}.
	 * It is bidirectional and its opposite is '{@link com.company.tutorial3.datamodel.Node#getScenario <em>Scenario</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nodes</em>' containment reference list.
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getScenario_Nodes()
	 * @see com.company.tutorial3.datamodel.Node#getScenario
	 * @model opposite="scenario" containment="true"
	 * @generated
	 */
	EList<Node> getNodes();

	/**
	 * Returns the value of the '<em><b>Arcs</b></em>' containment reference list.
	 * The list contents are of type {@link com.company.tutorial3.datamodel.Arc}.
	 * It is bidirectional and its opposite is '{@link com.company.tutorial3.datamodel.Arc#getScenario <em>Scenario</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arcs</em>' containment reference list.
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getScenario_Arcs()
	 * @see com.company.tutorial3.datamodel.Arc#getScenario
	 * @model opposite="scenario" containment="true"
	 * @generated
	 */
	EList<Arc> getArcs();

	/**
	 * Returns the value of the '<em><b>Warehouses</b></em>' containment reference list.
	 * The list contents are of type {@link com.company.tutorial3.datamodel.Warehouse}.
	 * It is bidirectional and its opposite is '{@link com.company.tutorial3.datamodel.Warehouse#getScenario <em>Scenario</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Warehouses</em>' containment reference list.
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getScenario_Warehouses()
	 * @see com.company.tutorial3.datamodel.Warehouse#getScenario
	 * @model opposite="scenario" containment="true"
	 * @generated
	 */
	EList<Warehouse> getWarehouses();

	/**
	 * Returns the value of the '<em><b>Stores</b></em>' containment reference list.
	 * The list contents are of type {@link com.company.tutorial3.datamodel.Store}.
	 * It is bidirectional and its opposite is '{@link com.company.tutorial3.datamodel.Store#getScenario <em>Scenario</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stores</em>' containment reference list.
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getScenario_Stores()
	 * @see com.company.tutorial3.datamodel.Store#getScenario
	 * @model opposite="scenario" containment="true"
	 * @generated
	 */
	EList<Store> getStores();

	/**
	 * Returns the value of the '<em><b>Trucks</b></em>' containment reference list.
	 * The list contents are of type {@link com.company.tutorial3.datamodel.Truck}.
	 * It is bidirectional and its opposite is '{@link com.company.tutorial3.datamodel.Truck#getScenario <em>Scenario</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trucks</em>' containment reference list.
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getScenario_Trucks()
	 * @see com.company.tutorial3.datamodel.Truck#getScenario
	 * @model opposite="scenario" containment="true"
	 * @generated
	 */
	EList<Truck> getTrucks();

	/**
	 * Returns the value of the '<em><b>Max Delivery Time Hrs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Delivery Time Hrs</em>' attribute.
	 * @see #setMaxDeliveryTimeHrs(double)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getScenario_MaxDeliveryTimeHrs()
	 * @model
	 * @generated
	 */
	double getMaxDeliveryTimeHrs();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Scenario#getMaxDeliveryTimeHrs <em>Max Delivery Time Hrs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Delivery Time Hrs</em>' attribute.
	 * @see #getMaxDeliveryTimeHrs()
	 * @generated
	 */
	void setMaxDeliveryTimeHrs(double value);

	/**
	 * Returns the value of the '<em><b>Interval Between Requests</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interval Between Requests</em>' containment reference.
	 * @see #setIntervalBetweenRequests(Distribution)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getScenario_IntervalBetweenRequests()
	 * @model containment="true"
	 * @generated
	 */
	Distribution getIntervalBetweenRequests();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Scenario#getIntervalBetweenRequests <em>Interval Between Requests</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interval Between Requests</em>' containment reference.
	 * @see #getIntervalBetweenRequests()
	 * @generated
	 */
	void setIntervalBetweenRequests(Distribution value);

} // Scenario
