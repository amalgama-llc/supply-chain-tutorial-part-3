/**
 */
package com.company.tutorial3.datamodel.impl;

import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Scenario;
import com.company.tutorial3.datamodel.Truck;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Truck</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.company.tutorial3.datamodel.impl.TruckImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.TruckImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.TruckImpl#getSpeed <em>Speed</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.TruckImpl#getInitialPosition <em>Initial Position</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.TruckImpl#getScenario <em>Scenario</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TruckImpl extends MinimalEObjectImpl.Container implements Truck {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSpeed() <em>Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpeed()
	 * @generated
	 * @ordered
	 */
	protected static final double SPEED_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getSpeed() <em>Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpeed()
	 * @generated
	 * @ordered
	 */
	protected double speed = SPEED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInitialPosition() <em>Initial Position</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialPosition()
	 * @generated
	 * @ordered
	 */
	protected Node initialPosition;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TruckImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DatamodelPackage.Literals.TRUCK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.TRUCK__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.TRUCK__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSpeed(double newSpeed) {
		double oldSpeed = speed;
		speed = newSpeed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.TRUCK__SPEED, oldSpeed, speed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getInitialPosition() {
		if (initialPosition != null && initialPosition.eIsProxy()) {
			InternalEObject oldInitialPosition = (InternalEObject) initialPosition;
			initialPosition = (Node) eResolveProxy(oldInitialPosition);
			if (initialPosition != oldInitialPosition) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DatamodelPackage.TRUCK__INITIAL_POSITION,
							oldInitialPosition, initialPosition));
			}
		}
		return initialPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetInitialPosition() {
		return initialPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitialPosition(Node newInitialPosition) {
		Node oldInitialPosition = initialPosition;
		initialPosition = newInitialPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.TRUCK__INITIAL_POSITION,
					oldInitialPosition, initialPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Scenario getScenario() {
		if (eContainerFeatureID() != DatamodelPackage.TRUCK__SCENARIO)
			return null;
		return (Scenario) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetScenario(Scenario newScenario, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newScenario, DatamodelPackage.TRUCK__SCENARIO, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScenario(Scenario newScenario) {
		if (newScenario != eInternalContainer()
				|| (eContainerFeatureID() != DatamodelPackage.TRUCK__SCENARIO && newScenario != null)) {
			if (EcoreUtil.isAncestor(this, newScenario))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newScenario != null)
				msgs = ((InternalEObject) newScenario).eInverseAdd(this, DatamodelPackage.SCENARIO__TRUCKS,
						Scenario.class, msgs);
			msgs = basicSetScenario(newScenario, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.TRUCK__SCENARIO, newScenario,
					newScenario));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case DatamodelPackage.TRUCK__SCENARIO:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetScenario((Scenario) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case DatamodelPackage.TRUCK__SCENARIO:
			return basicSetScenario(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
		case DatamodelPackage.TRUCK__SCENARIO:
			return eInternalContainer().eInverseRemove(this, DatamodelPackage.SCENARIO__TRUCKS, Scenario.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DatamodelPackage.TRUCK__ID:
			return getId();
		case DatamodelPackage.TRUCK__NAME:
			return getName();
		case DatamodelPackage.TRUCK__SPEED:
			return getSpeed();
		case DatamodelPackage.TRUCK__INITIAL_POSITION:
			if (resolve)
				return getInitialPosition();
			return basicGetInitialPosition();
		case DatamodelPackage.TRUCK__SCENARIO:
			return getScenario();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case DatamodelPackage.TRUCK__ID:
			setId((String) newValue);
			return;
		case DatamodelPackage.TRUCK__NAME:
			setName((String) newValue);
			return;
		case DatamodelPackage.TRUCK__SPEED:
			setSpeed((Double) newValue);
			return;
		case DatamodelPackage.TRUCK__INITIAL_POSITION:
			setInitialPosition((Node) newValue);
			return;
		case DatamodelPackage.TRUCK__SCENARIO:
			setScenario((Scenario) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case DatamodelPackage.TRUCK__ID:
			setId(ID_EDEFAULT);
			return;
		case DatamodelPackage.TRUCK__NAME:
			setName(NAME_EDEFAULT);
			return;
		case DatamodelPackage.TRUCK__SPEED:
			setSpeed(SPEED_EDEFAULT);
			return;
		case DatamodelPackage.TRUCK__INITIAL_POSITION:
			setInitialPosition((Node) null);
			return;
		case DatamodelPackage.TRUCK__SCENARIO:
			setScenario((Scenario) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case DatamodelPackage.TRUCK__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		case DatamodelPackage.TRUCK__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case DatamodelPackage.TRUCK__SPEED:
			return speed != SPEED_EDEFAULT;
		case DatamodelPackage.TRUCK__INITIAL_POSITION:
			return initialPosition != null;
		case DatamodelPackage.TRUCK__SCENARIO:
			return getScenario() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(", name: ");
		result.append(name);
		result.append(", speed: ");
		result.append(speed);
		result.append(')');
		return result.toString();
	}

} //TruckImpl
