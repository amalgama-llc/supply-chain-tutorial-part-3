/**
 */
package com.company.tutorial3.datamodel.impl;

import com.amalgamasimulation.randomdatamodel.Distribution;
import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Scenario;

import com.company.tutorial3.datamodel.Store;
import com.company.tutorial3.datamodel.Truck;
import com.company.tutorial3.datamodel.Warehouse;
import java.time.LocalDateTime;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Scenario</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.company.tutorial3.datamodel.impl.ScenarioImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.ScenarioImpl#getBeginDate <em>Begin Date</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.ScenarioImpl#getEndDate <em>End Date</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.ScenarioImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.ScenarioImpl#getArcs <em>Arcs</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.ScenarioImpl#getWarehouses <em>Warehouses</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.ScenarioImpl#getStores <em>Stores</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.ScenarioImpl#getTrucks <em>Trucks</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.ScenarioImpl#getMaxDeliveryTimeHrs <em>Max Delivery Time Hrs</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.ScenarioImpl#getIntervalBetweenRequests <em>Interval Between Requests</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ScenarioImpl extends MinimalEObjectImpl.Container implements Scenario {
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
	 * The default value of the '{@link #getBeginDate() <em>Begin Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBeginDate()
	 * @generated
	 * @ordered
	 */
	protected static final LocalDateTime BEGIN_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBeginDate() <em>Begin Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBeginDate()
	 * @generated
	 * @ordered
	 */
	protected LocalDateTime beginDate = BEGIN_DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndDate()
	 * @generated
	 * @ordered
	 */
	protected static final LocalDateTime END_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndDate()
	 * @generated
	 * @ordered
	 */
	protected LocalDateTime endDate = END_DATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<Node> nodes;

	/**
	 * The cached value of the '{@link #getArcs() <em>Arcs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArcs()
	 * @generated
	 * @ordered
	 */
	protected EList<Arc> arcs;

	/**
	 * The cached value of the '{@link #getWarehouses() <em>Warehouses</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWarehouses()
	 * @generated
	 * @ordered
	 */
	protected EList<Warehouse> warehouses;

	/**
	 * The cached value of the '{@link #getStores() <em>Stores</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStores()
	 * @generated
	 * @ordered
	 */
	protected EList<Store> stores;

	/**
	 * The cached value of the '{@link #getTrucks() <em>Trucks</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrucks()
	 * @generated
	 * @ordered
	 */
	protected EList<Truck> trucks;

	/**
	 * The default value of the '{@link #getMaxDeliveryTimeHrs() <em>Max Delivery Time Hrs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxDeliveryTimeHrs()
	 * @generated
	 * @ordered
	 */
	protected static final double MAX_DELIVERY_TIME_HRS_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getMaxDeliveryTimeHrs() <em>Max Delivery Time Hrs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxDeliveryTimeHrs()
	 * @generated
	 * @ordered
	 */
	protected double maxDeliveryTimeHrs = MAX_DELIVERY_TIME_HRS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getIntervalBetweenRequests() <em>Interval Between Requests</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntervalBetweenRequests()
	 * @generated
	 * @ordered
	 */
	protected Distribution intervalBetweenRequests;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ScenarioImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DatamodelPackage.Literals.SCENARIO;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.SCENARIO__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalDateTime getBeginDate() {
		return beginDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBeginDate(LocalDateTime newBeginDate) {
		LocalDateTime oldBeginDate = beginDate;
		beginDate = newBeginDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.SCENARIO__BEGIN_DATE, oldBeginDate,
					beginDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalDateTime getEndDate() {
		return endDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEndDate(LocalDateTime newEndDate) {
		LocalDateTime oldEndDate = endDate;
		endDate = newEndDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.SCENARIO__END_DATE, oldEndDate,
					endDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Node> getNodes() {
		if (nodes == null) {
			nodes = new EObjectContainmentWithInverseEList<Node>(Node.class, this, DatamodelPackage.SCENARIO__NODES,
					DatamodelPackage.NODE__SCENARIO);
		}
		return nodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Arc> getArcs() {
		if (arcs == null) {
			arcs = new EObjectContainmentWithInverseEList<Arc>(Arc.class, this, DatamodelPackage.SCENARIO__ARCS,
					DatamodelPackage.ARC__SCENARIO);
		}
		return arcs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Warehouse> getWarehouses() {
		if (warehouses == null) {
			warehouses = new EObjectContainmentWithInverseEList<Warehouse>(Warehouse.class, this,
					DatamodelPackage.SCENARIO__WAREHOUSES, DatamodelPackage.WAREHOUSE__SCENARIO);
		}
		return warehouses;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Store> getStores() {
		if (stores == null) {
			stores = new EObjectContainmentWithInverseEList<Store>(Store.class, this, DatamodelPackage.SCENARIO__STORES,
					DatamodelPackage.STORE__SCENARIO);
		}
		return stores;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Truck> getTrucks() {
		if (trucks == null) {
			trucks = new EObjectContainmentWithInverseEList<Truck>(Truck.class, this, DatamodelPackage.SCENARIO__TRUCKS,
					DatamodelPackage.TRUCK__SCENARIO);
		}
		return trucks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getMaxDeliveryTimeHrs() {
		return maxDeliveryTimeHrs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxDeliveryTimeHrs(double newMaxDeliveryTimeHrs) {
		double oldMaxDeliveryTimeHrs = maxDeliveryTimeHrs;
		maxDeliveryTimeHrs = newMaxDeliveryTimeHrs;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.SCENARIO__MAX_DELIVERY_TIME_HRS,
					oldMaxDeliveryTimeHrs, maxDeliveryTimeHrs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Distribution getIntervalBetweenRequests() {
		return intervalBetweenRequests;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIntervalBetweenRequests(Distribution newIntervalBetweenRequests,
			NotificationChain msgs) {
		Distribution oldIntervalBetweenRequests = intervalBetweenRequests;
		intervalBetweenRequests = newIntervalBetweenRequests;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					DatamodelPackage.SCENARIO__INTERVAL_BETWEEN_REQUESTS, oldIntervalBetweenRequests,
					newIntervalBetweenRequests);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIntervalBetweenRequests(Distribution newIntervalBetweenRequests) {
		if (newIntervalBetweenRequests != intervalBetweenRequests) {
			NotificationChain msgs = null;
			if (intervalBetweenRequests != null)
				msgs = ((InternalEObject) intervalBetweenRequests).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - DatamodelPackage.SCENARIO__INTERVAL_BETWEEN_REQUESTS, null, msgs);
			if (newIntervalBetweenRequests != null)
				msgs = ((InternalEObject) newIntervalBetweenRequests).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - DatamodelPackage.SCENARIO__INTERVAL_BETWEEN_REQUESTS, null, msgs);
			msgs = basicSetIntervalBetweenRequests(newIntervalBetweenRequests, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.SCENARIO__INTERVAL_BETWEEN_REQUESTS,
					newIntervalBetweenRequests, newIntervalBetweenRequests));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case DatamodelPackage.SCENARIO__NODES:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getNodes()).basicAdd(otherEnd, msgs);
		case DatamodelPackage.SCENARIO__ARCS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getArcs()).basicAdd(otherEnd, msgs);
		case DatamodelPackage.SCENARIO__WAREHOUSES:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getWarehouses()).basicAdd(otherEnd, msgs);
		case DatamodelPackage.SCENARIO__STORES:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getStores()).basicAdd(otherEnd, msgs);
		case DatamodelPackage.SCENARIO__TRUCKS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getTrucks()).basicAdd(otherEnd, msgs);
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
		case DatamodelPackage.SCENARIO__NODES:
			return ((InternalEList<?>) getNodes()).basicRemove(otherEnd, msgs);
		case DatamodelPackage.SCENARIO__ARCS:
			return ((InternalEList<?>) getArcs()).basicRemove(otherEnd, msgs);
		case DatamodelPackage.SCENARIO__WAREHOUSES:
			return ((InternalEList<?>) getWarehouses()).basicRemove(otherEnd, msgs);
		case DatamodelPackage.SCENARIO__STORES:
			return ((InternalEList<?>) getStores()).basicRemove(otherEnd, msgs);
		case DatamodelPackage.SCENARIO__TRUCKS:
			return ((InternalEList<?>) getTrucks()).basicRemove(otherEnd, msgs);
		case DatamodelPackage.SCENARIO__INTERVAL_BETWEEN_REQUESTS:
			return basicSetIntervalBetweenRequests(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DatamodelPackage.SCENARIO__NAME:
			return getName();
		case DatamodelPackage.SCENARIO__BEGIN_DATE:
			return getBeginDate();
		case DatamodelPackage.SCENARIO__END_DATE:
			return getEndDate();
		case DatamodelPackage.SCENARIO__NODES:
			return getNodes();
		case DatamodelPackage.SCENARIO__ARCS:
			return getArcs();
		case DatamodelPackage.SCENARIO__WAREHOUSES:
			return getWarehouses();
		case DatamodelPackage.SCENARIO__STORES:
			return getStores();
		case DatamodelPackage.SCENARIO__TRUCKS:
			return getTrucks();
		case DatamodelPackage.SCENARIO__MAX_DELIVERY_TIME_HRS:
			return getMaxDeliveryTimeHrs();
		case DatamodelPackage.SCENARIO__INTERVAL_BETWEEN_REQUESTS:
			return getIntervalBetweenRequests();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case DatamodelPackage.SCENARIO__NAME:
			setName((String) newValue);
			return;
		case DatamodelPackage.SCENARIO__BEGIN_DATE:
			setBeginDate((LocalDateTime) newValue);
			return;
		case DatamodelPackage.SCENARIO__END_DATE:
			setEndDate((LocalDateTime) newValue);
			return;
		case DatamodelPackage.SCENARIO__NODES:
			getNodes().clear();
			getNodes().addAll((Collection<? extends Node>) newValue);
			return;
		case DatamodelPackage.SCENARIO__ARCS:
			getArcs().clear();
			getArcs().addAll((Collection<? extends Arc>) newValue);
			return;
		case DatamodelPackage.SCENARIO__WAREHOUSES:
			getWarehouses().clear();
			getWarehouses().addAll((Collection<? extends Warehouse>) newValue);
			return;
		case DatamodelPackage.SCENARIO__STORES:
			getStores().clear();
			getStores().addAll((Collection<? extends Store>) newValue);
			return;
		case DatamodelPackage.SCENARIO__TRUCKS:
			getTrucks().clear();
			getTrucks().addAll((Collection<? extends Truck>) newValue);
			return;
		case DatamodelPackage.SCENARIO__MAX_DELIVERY_TIME_HRS:
			setMaxDeliveryTimeHrs((Double) newValue);
			return;
		case DatamodelPackage.SCENARIO__INTERVAL_BETWEEN_REQUESTS:
			setIntervalBetweenRequests((Distribution) newValue);
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
		case DatamodelPackage.SCENARIO__NAME:
			setName(NAME_EDEFAULT);
			return;
		case DatamodelPackage.SCENARIO__BEGIN_DATE:
			setBeginDate(BEGIN_DATE_EDEFAULT);
			return;
		case DatamodelPackage.SCENARIO__END_DATE:
			setEndDate(END_DATE_EDEFAULT);
			return;
		case DatamodelPackage.SCENARIO__NODES:
			getNodes().clear();
			return;
		case DatamodelPackage.SCENARIO__ARCS:
			getArcs().clear();
			return;
		case DatamodelPackage.SCENARIO__WAREHOUSES:
			getWarehouses().clear();
			return;
		case DatamodelPackage.SCENARIO__STORES:
			getStores().clear();
			return;
		case DatamodelPackage.SCENARIO__TRUCKS:
			getTrucks().clear();
			return;
		case DatamodelPackage.SCENARIO__MAX_DELIVERY_TIME_HRS:
			setMaxDeliveryTimeHrs(MAX_DELIVERY_TIME_HRS_EDEFAULT);
			return;
		case DatamodelPackage.SCENARIO__INTERVAL_BETWEEN_REQUESTS:
			setIntervalBetweenRequests((Distribution) null);
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
		case DatamodelPackage.SCENARIO__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case DatamodelPackage.SCENARIO__BEGIN_DATE:
			return BEGIN_DATE_EDEFAULT == null ? beginDate != null : !BEGIN_DATE_EDEFAULT.equals(beginDate);
		case DatamodelPackage.SCENARIO__END_DATE:
			return END_DATE_EDEFAULT == null ? endDate != null : !END_DATE_EDEFAULT.equals(endDate);
		case DatamodelPackage.SCENARIO__NODES:
			return nodes != null && !nodes.isEmpty();
		case DatamodelPackage.SCENARIO__ARCS:
			return arcs != null && !arcs.isEmpty();
		case DatamodelPackage.SCENARIO__WAREHOUSES:
			return warehouses != null && !warehouses.isEmpty();
		case DatamodelPackage.SCENARIO__STORES:
			return stores != null && !stores.isEmpty();
		case DatamodelPackage.SCENARIO__TRUCKS:
			return trucks != null && !trucks.isEmpty();
		case DatamodelPackage.SCENARIO__MAX_DELIVERY_TIME_HRS:
			return maxDeliveryTimeHrs != MAX_DELIVERY_TIME_HRS_EDEFAULT;
		case DatamodelPackage.SCENARIO__INTERVAL_BETWEEN_REQUESTS:
			return intervalBetweenRequests != null;
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
		result.append(", beginDate: ");
		result.append(beginDate);
		result.append(", endDate: ");
		result.append(endDate);
		result.append(", maxDeliveryTimeHrs: ");
		result.append(maxDeliveryTimeHrs);
		result.append(')');
		return result.toString();
	}

} //ScenarioImpl
