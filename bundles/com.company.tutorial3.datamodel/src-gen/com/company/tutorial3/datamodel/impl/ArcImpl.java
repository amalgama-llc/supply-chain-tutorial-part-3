/**
 */
package com.company.tutorial3.datamodel.impl;

import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Point;
import com.company.tutorial3.datamodel.Scenario;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Arc</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.company.tutorial3.datamodel.impl.ArcImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.ArcImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.ArcImpl#getSource <em>Source</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.ArcImpl#getDest <em>Dest</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.ArcImpl#getPoints <em>Points</em>}</li>
 *   <li>{@link com.company.tutorial3.datamodel.impl.ArcImpl#getScenario <em>Scenario</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ArcImpl extends MinimalEObjectImpl.Container implements Arc {
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
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected Node source;

	/**
	 * The cached value of the '{@link #getDest() <em>Dest</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDest()
	 * @generated
	 * @ordered
	 */
	protected Node dest;

	/**
	 * The cached value of the '{@link #getPoints() <em>Points</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPoints()
	 * @generated
	 * @ordered
	 */
	protected EList<Point> points;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArcImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DatamodelPackage.Literals.ARC;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.ARC__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.ARC__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject) source;
			source = (Node) eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DatamodelPackage.ARC__SOURCE, oldSource,
							source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(Node newSource) {
		Node oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.ARC__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getDest() {
		if (dest != null && dest.eIsProxy()) {
			InternalEObject oldDest = (InternalEObject) dest;
			dest = (Node) eResolveProxy(oldDest);
			if (dest != oldDest) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DatamodelPackage.ARC__DEST, oldDest,
							dest));
			}
		}
		return dest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetDest() {
		return dest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDest(Node newDest) {
		Node oldDest = dest;
		dest = newDest;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.ARC__DEST, oldDest, dest));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Point> getPoints() {
		if (points == null) {
			points = new EObjectContainmentWithInverseEList<Point>(Point.class, this, DatamodelPackage.ARC__POINTS,
					DatamodelPackage.POINT__ARC);
		}
		return points;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Scenario getScenario() {
		if (eContainerFeatureID() != DatamodelPackage.ARC__SCENARIO)
			return null;
		return (Scenario) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetScenario(Scenario newScenario, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newScenario, DatamodelPackage.ARC__SCENARIO, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScenario(Scenario newScenario) {
		if (newScenario != eInternalContainer()
				|| (eContainerFeatureID() != DatamodelPackage.ARC__SCENARIO && newScenario != null)) {
			if (EcoreUtil.isAncestor(this, newScenario))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newScenario != null)
				msgs = ((InternalEObject) newScenario).eInverseAdd(this, DatamodelPackage.SCENARIO__ARCS,
						Scenario.class, msgs);
			msgs = basicSetScenario(newScenario, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.ARC__SCENARIO, newScenario,
					newScenario));
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
		case DatamodelPackage.ARC__POINTS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getPoints()).basicAdd(otherEnd, msgs);
		case DatamodelPackage.ARC__SCENARIO:
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
		case DatamodelPackage.ARC__POINTS:
			return ((InternalEList<?>) getPoints()).basicRemove(otherEnd, msgs);
		case DatamodelPackage.ARC__SCENARIO:
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
		case DatamodelPackage.ARC__SCENARIO:
			return eInternalContainer().eInverseRemove(this, DatamodelPackage.SCENARIO__ARCS, Scenario.class, msgs);
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
		case DatamodelPackage.ARC__ID:
			return getId();
		case DatamodelPackage.ARC__NAME:
			return getName();
		case DatamodelPackage.ARC__SOURCE:
			if (resolve)
				return getSource();
			return basicGetSource();
		case DatamodelPackage.ARC__DEST:
			if (resolve)
				return getDest();
			return basicGetDest();
		case DatamodelPackage.ARC__POINTS:
			return getPoints();
		case DatamodelPackage.ARC__SCENARIO:
			return getScenario();
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
		case DatamodelPackage.ARC__ID:
			setId((String) newValue);
			return;
		case DatamodelPackage.ARC__NAME:
			setName((String) newValue);
			return;
		case DatamodelPackage.ARC__SOURCE:
			setSource((Node) newValue);
			return;
		case DatamodelPackage.ARC__DEST:
			setDest((Node) newValue);
			return;
		case DatamodelPackage.ARC__POINTS:
			getPoints().clear();
			getPoints().addAll((Collection<? extends Point>) newValue);
			return;
		case DatamodelPackage.ARC__SCENARIO:
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
		case DatamodelPackage.ARC__ID:
			setId(ID_EDEFAULT);
			return;
		case DatamodelPackage.ARC__NAME:
			setName(NAME_EDEFAULT);
			return;
		case DatamodelPackage.ARC__SOURCE:
			setSource((Node) null);
			return;
		case DatamodelPackage.ARC__DEST:
			setDest((Node) null);
			return;
		case DatamodelPackage.ARC__POINTS:
			getPoints().clear();
			return;
		case DatamodelPackage.ARC__SCENARIO:
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
		case DatamodelPackage.ARC__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		case DatamodelPackage.ARC__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case DatamodelPackage.ARC__SOURCE:
			return source != null;
		case DatamodelPackage.ARC__DEST:
			return dest != null;
		case DatamodelPackage.ARC__POINTS:
			return points != null && !points.isEmpty();
		case DatamodelPackage.ARC__SCENARIO:
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
		result.append(')');
		return result.toString();
	}

} //ArcImpl
