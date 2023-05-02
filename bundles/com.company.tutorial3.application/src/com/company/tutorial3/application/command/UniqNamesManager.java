package com.company.tutorial3.application.command;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

public class UniqNamesManager {
	private static UniqNamesManager instance;
	
	private UniqNamesManager() {}
	
	public static UniqNamesManager getInstance() {
		if (instance == null) {
			instance = new UniqNamesManager();
		}
		return instance;
	}	
	
	public <T extends EObject> String generateUniqueId(EObject container, T object, String patternId) {
		return UniqNamesManager.getInstance().generateUniqName(container, object, patternId);
	}
	
	@SuppressWarnings("unchecked")
	public <C extends EObject, T extends EObject> String generateUniqName(C container, T eObject, String pattern) {
		EReference containerFeature = getContainerFeature(eObject);
		if (container.eGet(containerFeature) instanceof EList) {
			pattern = parseToNameWithNumber(pattern).getKey();
			EList<T> collection = (EList<T>)container.eGet(containerFeature);

			long lastSeizedNumber = findLastNumber(collection, pattern);
			pattern = pattern.concat(""+(lastSeizedNumber + 1));
		}
		return pattern;
	}
	
	public EAttribute getIdAttribute(EClass eClass) {
		Optional<EAttribute> idAttribute = 
						eClass	.getEAllAttributes()
								.stream()
								.filter(EAttribute::isID)
								.filter(a -> a.getEAttributeType().equals(EcorePackage.Literals.ESTRING))
								.findFirst();			
		return idAttribute.orElse(null);
	}
	
	public String getId(EObject object) {
		if (object == null) {
			return null;
		}
		EAttribute idAttribute = getIdAttribute(object.eClass());
		if (idAttribute == null) {
			return null;
		}
		return (String) object.eGet(idAttribute);
	}
	
	public void setId(EObject object, String newId) {
		EAttribute idAttribute = getIdAttribute(object.eClass());
		if (idAttribute == null) {
			return;
		}
		object.eSet(idAttribute, newId);
	}

	public <T extends EObject> long findLastNumber(List<T> existingObjects, String objectIdPattern) {
		long lastSeizedNumber = 0;
		for (T existingObject : existingObjects) {
			Map.Entry<String, Long> nameWithNumber = parseToNameWithNumber(getId(existingObject));
			if (nameWithNumber.getKey().equals(objectIdPattern)) {
				lastSeizedNumber = Math.max(lastSeizedNumber, nameWithNumber.getValue());
			}
		}
		return lastSeizedNumber;
	}
	
	public <C extends EObject, T extends EObject> String getNameUniq(C container, T eObject, String oldName) {
		return generateUniqueId(container, eObject,  parseToNameWithNumber(oldName).getKey());
	}
	
	private EReference getContainerFeature(EObject eObject) {
		EReference result = eObject.eClass().getEAllReferences()	.stream()
																	.filter(EReference::isContainer)
																	.findFirst()
																	.orElse(null);
		if (result != null) {
			result = result.getEOpposite();
			return result;
		}
		return null;
	}
	
	// Parses an object 'full id' of 'ABC-123' format
	private Map.Entry<String, Long> parseToNameWithNumber(String objectFullID) {
		String name = "";
		long number = 0;
		if (objectFullID != null) {
			for (int i = objectFullID.length() - 1; i >= 0; i--) {
				if (objectFullID.length() - i - 1 < 18 && Character.isDigit(objectFullID.charAt(i))) {
					number += Character.getNumericValue(objectFullID.charAt(i))
							* getPower10(objectFullID.length() - i - 1);
				} else {
					name = objectFullID.substring(0, i + 1);
					break;
				}
			}
		}

		return new AbstractMap.SimpleEntry<>(name.stripTrailing(), number);
	}
	
	private Map<Integer, Long> multipliersCache = new HashMap<>();
	
	private long getPower10(int power) {
		if (power == 0) {
			return 1;
		}
		if (power == 1) {
			return 10;
		}
		if (!multipliersCache.containsKey(power)) {
			long multipriler10 = 1;
			for (int i = 0; i < power; i++) {
				multipriler10 = multipriler10 * 10;
			}
			multipliersCache.put(power, multipriler10);
		}
		return multipliersCache.get(power);
	}
	
}

