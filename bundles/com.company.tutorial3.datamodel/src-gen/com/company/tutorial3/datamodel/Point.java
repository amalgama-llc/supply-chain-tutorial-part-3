/**
 */
package com.company.tutorial3.datamodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Point</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.company.tutorial3.datamodel.Point#getArc <em>Arc</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Point#getY <em>Y</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Point#getX <em>X</em>}</li>
 * </ul>
 *
 * @see com.company.tutorial3.datamodel.DatamodelPackage#getPoint()
 * @model
 * @generated
 */
public interface Point extends EObject {
	/**
	 * Returns the value of the '<em><b>Arc</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.company.tutorial3.datamodel.Arc#getPoints <em>Points</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arc</em>' container reference.
	 * @see #setArc(Arc)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getPoint_Arc()
	 * @see com.company.tutorial3.datamodel.Arc#getPoints
	 * @model opposite="points" transient="false"
	 * @generated
	 */
	Arc getArc();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Point#getArc <em>Arc</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arc</em>' container reference.
	 * @see #getArc()
	 * @generated
	 */
	void setArc(Arc value);

	/**
	 * Returns the value of the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #setY(double)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getPoint_Y()
	 * @model
	 * @generated
	 */
	double getY();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Point#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #getY()
	 * @generated
	 */
	void setY(double value);

	/**
	 * Returns the value of the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #setX(double)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getPoint_X()
	 * @model
	 * @generated
	 */
	double getX();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Point#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>X</em>' attribute.
	 * @see #getX()
	 * @generated
	 */
	void setX(double value);

} // Point
