/**
 */
package com.company.tutorial3.datamodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.company.tutorial3.datamodel.Node#getId <em>Id</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Node#getName <em>Name</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Node#getLatitude <em>Latitude</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Node#getLongitude <em>Longitude</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Node#getScenario <em>Scenario</em>}</li>
 * </ul>
 *
 * @see com.company.tutorial3.datamodel.DatamodelPackage#getNode()
 * @model
 * @generated
 */
public interface Node extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getNode_Id()
	 * @model id="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Node#getId <em>Id</em>}' attribute.
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
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getNode_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Node#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Latitude</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Latitude</em>' attribute.
	 * @see #setLatitude(double)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getNode_Latitude()
	 * @model
	 * @generated
	 */
	double getLatitude();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Node#getLatitude <em>Latitude</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Latitude</em>' attribute.
	 * @see #getLatitude()
	 * @generated
	 */
	void setLatitude(double value);

	/**
	 * Returns the value of the '<em><b>Longitude</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Longitude</em>' attribute.
	 * @see #setLongitude(double)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getNode_Longitude()
	 * @model
	 * @generated
	 */
	double getLongitude();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Node#getLongitude <em>Longitude</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Longitude</em>' attribute.
	 * @see #getLongitude()
	 * @generated
	 */
	void setLongitude(double value);

	/**
	 * Returns the value of the '<em><b>Scenario</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.company.tutorial3.datamodel.Scenario#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scenario</em>' container reference.
	 * @see #setScenario(Scenario)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getNode_Scenario()
	 * @see com.company.tutorial3.datamodel.Scenario#getNodes
	 * @model opposite="nodes" transient="false"
	 * @generated
	 */
	Scenario getScenario();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Node#getScenario <em>Scenario</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scenario</em>' container reference.
	 * @see #getScenario()
	 * @generated
	 */
	void setScenario(Scenario value);

} // Node
