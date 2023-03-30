/**
 */
package com.company.tutorial3.datamodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Truck</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.company.tutorial3.datamodel.Truck#getId <em>Id</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Truck#getName <em>Name</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Truck#getSpeed <em>Speed</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Truck#getInitialNode <em>Initial Node</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Truck#getScenario <em>Scenario</em>}</li>
 * </ul>
 *
 * @see com.company.tutorial3.datamodel.DatamodelPackage#getTruck()
 * @model
 * @generated
 */
public interface Truck extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getTruck_Id()
	 * @model id="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Truck#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getTruck_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Truck#getName <em>Name</em>}' attribute.
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
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getTruck_Speed()
	 * @model
	 * @generated
	 */
	double getSpeed();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Truck#getSpeed <em>Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Speed</em>' attribute.
	 * @see #getSpeed()
	 * @generated
	 */
	void setSpeed(double value);

	/**
	 * Returns the value of the '<em><b>Initial Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial Node</em>' reference.
	 * @see #setInitialNode(Node)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getTruck_InitialNode()
	 * @model
	 * @generated
	 */
	Node getInitialNode();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Truck#getInitialNode <em>Initial Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial Node</em>' reference.
	 * @see #getInitialNode()
	 * @generated
	 */
	void setInitialNode(Node value);

	/**
	 * Returns the value of the '<em><b>Scenario</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.company.tutorial3.datamodel.Scenario#getTrucks <em>Trucks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scenario</em>' container reference.
	 * @see #setScenario(Scenario)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getTruck_Scenario()
	 * @see com.company.tutorial3.datamodel.Scenario#getTrucks
	 * @model opposite="trucks" transient="false"
	 * @generated
	 */
	Scenario getScenario();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Truck#getScenario <em>Scenario</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scenario</em>' container reference.
	 * @see #getScenario()
	 * @generated
	 */
	void setScenario(Scenario value);

} // Truck
