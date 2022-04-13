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
 *   <li>{@link com.company.tutorial3.datamodel.Point#getLatitude <em>Latitude</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.Point#getLongitude <em>Longitude</em>}</li>
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
	 * Returns the value of the '<em><b>Latitude</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Latitude</em>' attribute.
	 * @see #setLatitude(double)
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getPoint_Latitude()
	 * @model
	 * @generated
	 */
	double getLatitude();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Point#getLatitude <em>Latitude</em>}' attribute.
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
	 * @see com.company.tutorial3.datamodel.DatamodelPackage#getPoint_Longitude()
	 * @model
	 * @generated
	 */
	double getLongitude();

	/**
	 * Sets the value of the '{@link com.company.tutorial3.datamodel.Point#getLongitude <em>Longitude</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Longitude</em>' attribute.
	 * @see #getLongitude()
	 * @generated
	 */
	void setLongitude(double value);

} // Point
