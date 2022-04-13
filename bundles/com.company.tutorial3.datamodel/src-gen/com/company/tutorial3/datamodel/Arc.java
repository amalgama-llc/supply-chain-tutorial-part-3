/**
 */
package com.company.tutorial3.datamodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Arc</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.company.tutorial3.datamodel.Arc#getId <em>Id</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Arc#getName <em>Name</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Arc#getSource <em>Source</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Arc#getDest <em>Dest</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Arc#getPoints <em>Points</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Arc#getScenario <em>Scenario</em>}</li>
 * </ul>
 *
 * @see com.company.tutorial3.datamodel.DatamodelPackage#getArc()
 * @model
 * @generated
 */
public interface Arc extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getArc_Id()
	 * @model id="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Arc#getId <em>Id</em>}' attribute.
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
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getArc_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Arc#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(Node)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getArc_Source()
	 * @model required="true"
	 * @generated
	 */
	Node getSource();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Arc#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(Node value);

	/**
	 * Returns the value of the '<em><b>Dest</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dest</em>' reference.
	 * @see #setDest(Node)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getArc_Dest()
	 * @model required="true"
	 * @generated
	 */
	Node getDest();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Arc#getDest <em>Dest</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dest</em>' reference.
	 * @see #getDest()
	 * @generated
	 */
	void setDest(Node value);

	/**
	 * Returns the value of the '<em><b>Points</b></em>' containment reference list.
	 * The list contents are of type {@link com.company.tutorial3.datamodel.Point}.
	 * It is bidirectional and its opposite is '{@link com.company.tutorial3.datamodel.Point#getArc <em>Arc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Points</em>' containment reference list.
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getArc_Points()
	 * @see com.company.tutorial3.datamodel.Point#getArc
	 * @model opposite="arc" containment="true"
	 * @generated
	 */
	EList<Point> getPoints();

	/**
	 * Returns the value of the '<em><b>Scenario</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.company.tutorial3.datamodel.Scenario#getArcs <em>Arcs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scenario</em>' container reference.
	 * @see #setScenario(Scenario)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getArc_Scenario()
	 * @see com.company.tutorial3.datamodel.Scenario#getArcs
	 * @model opposite="arcs" transient="false"
	 * @generated
	 */
	Scenario getScenario();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Arc#getScenario <em>Scenario</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scenario</em>' container reference.
	 * @see #getScenario()
	 * @generated
	 */
	void setScenario(Scenario value);

} // Arc
