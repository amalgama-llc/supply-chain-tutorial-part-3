/**
 */
package com.company.tutorial3.datamodel.impl;

import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Scenario;
import com.company.tutorial3.datamodel.TruckType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Truck Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.company.tutorial3.datamodel.impl.TruckTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.TruckTypeImpl#getSpeed <em>Speed</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.TruckTypeImpl#getQuantity <em>Quantity</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.TruckTypeImpl#getOwnershipCostPerHour <em>Ownership Cost Per Hour</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.TruckTypeImpl#getUsageCostPerHour <em>Usage Cost Per Hour</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.TruckTypeImpl#getScenario <em>Scenario</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TruckTypeImpl extends MinimalEObjectImpl.Container implements TruckType {
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
	 * The default value of the '{@link #getQuantity() <em>Quantity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQuantity()
	 * @generated
	 * @ordered
	 */
	protected static final int QUANTITY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getQuantity() <em>Quantity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQuantity()
	 * @generated
	 * @ordered
	 */
	protected int quantity = QUANTITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getOwnershipCostPerHour() <em>Ownership Cost Per Hour</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnershipCostPerHour()
	 * @generated
	 * @ordered
	 */
	protected static final double OWNERSHIP_COST_PER_HOUR_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getOwnershipCostPerHour() <em>Ownership Cost Per Hour</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnershipCostPerHour()
	 * @generated
	 * @ordered
	 */
	protected double ownershipCostPerHour = OWNERSHIP_COST_PER_HOUR_EDEFAULT;

	/**
	 * The default value of the '{@link #getUsageCostPerHour() <em>Usage Cost Per Hour</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsageCostPerHour()
	 * @generated
	 * @ordered
	 */
	protected static final double USAGE_COST_PER_HOUR_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getUsageCostPerHour() <em>Usage Cost Per Hour</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsageCostPerHour()
	 * @generated
	 * @ordered
	 */
	protected double usageCostPerHour = USAGE_COST_PER_HOUR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TruckTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DatamodelPackage.Literals.TRUCK_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.TRUCK_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getSpeed() {
		return speed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSpeed(double newSpeed) {
		double oldSpeed = speed;
		speed = newSpeed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.TRUCK_TYPE__SPEED, oldSpeed, speed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getQuantity() {
		return quantity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setQuantity(int newQuantity) {
		int oldQuantity = quantity;
		quantity = newQuantity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.TRUCK_TYPE__QUANTITY, oldQuantity,
					quantity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getOwnershipCostPerHour() {
		return ownershipCostPerHour;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOwnershipCostPerHour(double newOwnershipCostPerHour) {
		double oldOwnershipCostPerHour = ownershipCostPerHour;
		ownershipCostPerHour = newOwnershipCostPerHour;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.TRUCK_TYPE__OWNERSHIP_COST_PER_HOUR,
					oldOwnershipCostPerHour, ownershipCostPerHour));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getUsageCostPerHour() {
		return usageCostPerHour;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUsageCostPerHour(double newUsageCostPerHour) {
		double oldUsageCostPerHour = usageCostPerHour;
		usageCostPerHour = newUsageCostPerHour;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.TRUCK_TYPE__USAGE_COST_PER_HOUR,
					oldUsageCostPerHour, usageCostPerHour));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Scenario getScenario() {
		if (eContainerFeatureID() != DatamodelPackage.TRUCK_TYPE__SCENARIO)
			return null;
		return (Scenario) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetScenario(Scenario newScenario, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newScenario, DatamodelPackage.TRUCK_TYPE__SCENARIO, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setScenario(Scenario newScenario) {
		if (newScenario != eInternalContainer()
				|| (eContainerFeatureID() != DatamodelPackage.TRUCK_TYPE__SCENARIO && newScenario != null)) {
			if (EcoreUtil.isAncestor(this, newScenario))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newScenario != null)
				msgs = ((InternalEObject) newScenario).eInverseAdd(this, DatamodelPackage.SCENARIO__TRUCK_TYPES,
						Scenario.class, msgs);
			msgs = basicSetScenario(newScenario, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.TRUCK_TYPE__SCENARIO, newScenario,
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
		case DatamodelPackage.TRUCK_TYPE__SCENARIO:
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
		case DatamodelPackage.TRUCK_TYPE__SCENARIO:
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
		case DatamodelPackage.TRUCK_TYPE__SCENARIO:
			return eInternalContainer().eInverseRemove(this, DatamodelPackage.SCENARIO__TRUCK_TYPES, Scenario.class,
					msgs);
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
		case DatamodelPackage.TRUCK_TYPE__NAME:
			return getName();
		case DatamodelPackage.TRUCK_TYPE__SPEED:
			return getSpeed();
		case DatamodelPackage.TRUCK_TYPE__QUANTITY:
			return getQuantity();
		case DatamodelPackage.TRUCK_TYPE__OWNERSHIP_COST_PER_HOUR:
			return getOwnershipCostPerHour();
		case DatamodelPackage.TRUCK_TYPE__USAGE_COST_PER_HOUR:
			return getUsageCostPerHour();
		case DatamodelPackage.TRUCK_TYPE__SCENARIO:
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
		case DatamodelPackage.TRUCK_TYPE__NAME:
			setName((String) newValue);
			return;
		case DatamodelPackage.TRUCK_TYPE__SPEED:
			setSpeed((Double) newValue);
			return;
		case DatamodelPackage.TRUCK_TYPE__QUANTITY:
			setQuantity((Integer) newValue);
			return;
		case DatamodelPackage.TRUCK_TYPE__OWNERSHIP_COST_PER_HOUR:
			setOwnershipCostPerHour((Double) newValue);
			return;
		case DatamodelPackage.TRUCK_TYPE__USAGE_COST_PER_HOUR:
			setUsageCostPerHour((Double) newValue);
			return;
		case DatamodelPackage.TRUCK_TYPE__SCENARIO:
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
		case DatamodelPackage.TRUCK_TYPE__NAME:
			setName(NAME_EDEFAULT);
			return;
		case DatamodelPackage.TRUCK_TYPE__SPEED:
			setSpeed(SPEED_EDEFAULT);
			return;
		case DatamodelPackage.TRUCK_TYPE__QUANTITY:
			setQuantity(QUANTITY_EDEFAULT);
			return;
		case DatamodelPackage.TRUCK_TYPE__OWNERSHIP_COST_PER_HOUR:
			setOwnershipCostPerHour(OWNERSHIP_COST_PER_HOUR_EDEFAULT);
			return;
		case DatamodelPackage.TRUCK_TYPE__USAGE_COST_PER_HOUR:
			setUsageCostPerHour(USAGE_COST_PER_HOUR_EDEFAULT);
			return;
		case DatamodelPackage.TRUCK_TYPE__SCENARIO:
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
		case DatamodelPackage.TRUCK_TYPE__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case DatamodelPackage.TRUCK_TYPE__SPEED:
			return speed != SPEED_EDEFAULT;
		case DatamodelPackage.TRUCK_TYPE__QUANTITY:
			return quantity != QUANTITY_EDEFAULT;
		case DatamodelPackage.TRUCK_TYPE__OWNERSHIP_COST_PER_HOUR:
			return ownershipCostPerHour != OWNERSHIP_COST_PER_HOUR_EDEFAULT;
		case DatamodelPackage.TRUCK_TYPE__USAGE_COST_PER_HOUR:
			return usageCostPerHour != USAGE_COST_PER_HOUR_EDEFAULT;
		case DatamodelPackage.TRUCK_TYPE__SCENARIO:
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
		result.append(" (name: ");
		result.append(name);
		result.append(", speed: ");
		result.append(speed);
		result.append(", quantity: ");
		result.append(quantity);
		result.append(", ownershipCostPerHour: ");
		result.append(ownershipCostPerHour);
		result.append(", usageCostPerHour: ");
		result.append(usageCostPerHour);
		result.append(')');
		return result.toString();
	}

} //TruckTypeImpl
