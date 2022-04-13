package com.company.tutorial3.common.command;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
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
	
	public static <T extends EObject> String generateUniqueId(EObject container, T object, String patternId) {
		return UniqNamesManager.getInstance().generateUniqName(container, object, patternId);
	}
	
	@SuppressWarnings("unchecked")
	public static String generateUniqNameForThirdType(IObservableValue<? extends EObject> container, EStructuralFeature [] listObjectsExtractor, 
			EStructuralFeature idExtractor, String pattern) {
		List<EObject> collection = new ArrayList<>();
		EList<EObject> collection1 = (EList<EObject>)container.getValue().eGet(listObjectsExtractor[0]);
		for (EObject value1 : collection1) {
			EObject obj = (EObject)value1.eGet(listObjectsExtractor[1]);
			if(obj != null) {
				Object obj1 = obj.eGet(listObjectsExtractor[2]);
				if(obj1 instanceof EList) {
					collection.addAll((EList<EObject>) obj1);		
				}
			}
		}						
		long lastSeizedNumber = getLastNumber(collection, idExtractor, pattern);
		pattern = pattern.concat("" + (lastSeizedNumber + 1));	
		return pattern;
		
	}
	
	public static long getLastNumber(List<? extends EObject> collection, EStructuralFeature idExtractor, String pattern) {
		long lastSeizedNumber = 0;		
		for (EObject value : collection) {
			Map.Entry<String, Long> nameWithNumber = UniqNamesManager.getInstance().parseToNameWithNumber(value.eGet(idExtractor).toString());			
			if (nameWithNumber.getKey().equals(pattern)) {
				lastSeizedNumber = Math.max(lastSeizedNumber, nameWithNumber.getValue());
			}
		}
		return lastSeizedNumber;
	}

	@SuppressWarnings("unchecked")
	public static <C extends EObject, T extends EObject>String generateUniqNameForObjectFromList(C container, EStructuralFeature nestingListObjects, String pattern, EStructuralFeature nestingObject) {
		if (container.eGet(nestingListObjects) instanceof EList) {
			pattern = UniqNamesManager.getInstance().parseToNameWithNumber(pattern).getKey();
			List<T> collection = new ArrayList<>();
			
			for(T t: (EList<T>)container.eGet(nestingListObjects)) {
				if(t.eGet(nestingObject) != null) {
					collection.add((T)t.eGet(nestingObject));
				}
			}

			long lastSeizedNumber = UniqNamesManager.getInstance().findLastNumber(collection, pattern);
			pattern = pattern.concat(""+(lastSeizedNumber + 1));
		}
		return pattern;
	}

	public <T extends EObject> String generateUniqName(List<T> collection, T eObject, String pattern) {
		long lastSeizedNumber = findLastNumber(collection, pattern);
		pattern = pattern.concat(""+(lastSeizedNumber + 1));
		return pattern;
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
	
	@SuppressWarnings("unchecked")
	public <T extends EObject> String generateUniqNameDoubleNesting(EObject container, T eObject, String pattern) {
		EReference containerFromContainerFeature = getContainerFromObjectFeature(container);
		EObject containerOfContainer = (EObject)container.eGet(containerFromContainerFeature);

		EReference listObjectFromContainerFeature = getListObjectFromContainerFeature(eObject);
		EReference listObjectFromContainerOfContainerFeature = getListObjectFromContainerFeature(container);
				
		if (container.eGet(listObjectFromContainerFeature) instanceof EList && containerOfContainer.eGet(listObjectFromContainerOfContainerFeature) instanceof EList) {
			pattern = parseToNameWithNumber(pattern).getKey();
			EList<T> collectionContainers = (EList<T>)containerOfContainer.eGet(listObjectFromContainerOfContainerFeature);

			long lastSeizedNumber = 0;			
			for (EObject containerObject : collectionContainers) {
				EList<T> collection = (EList<T>)containerObject.eGet(listObjectFromContainerFeature);
				for (T existedObject : collection) {
					Map.Entry<String, Long> nameWithNumber = parseToNameWithNumber(getId(existedObject));
					if (nameWithNumber.getKey().equals(pattern)) {
						lastSeizedNumber = Math.max(lastSeizedNumber, nameWithNumber.getValue());
					}
				}			
			}			
			pattern = pattern.concat("" + (lastSeizedNumber + 1));
		}
		return pattern;
	}
	
	public static EAttribute getIdAttribute(EClass eClass) {
		Optional<EAttribute> idAttribute = 
						eClass	.getEAllAttributes()
								.stream()
								.filter(EAttribute::isID)
								.filter(a -> a.getEAttributeType().equals(EcorePackage.Literals.ESTRING))
								.findFirst();			
		return idAttribute.orElse(null);
	}
	
	public static String getId(EObject object) {
		if (object == null) {
			return null;
		}
		EAttribute idAttribute = getIdAttribute(object.eClass());
		if (idAttribute == null) {
			return null;
		}
		return (String) object.eGet(idAttribute);
	}
	
	public static void setId(EObject object, String newId) {
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
	
	private EReference getContainerFromObjectFeature(EObject eObject) {
		return eObject.eClass().getEAllReferences().stream().filter(r -> r.isContainer()).findFirst().orElse(null);
	}
	
	private EReference getListObjectFromContainerFeature(EObject eObject) {
		EReference result = eObject.eClass().getEAllReferences().stream().filter(EReference::isContainer).findFirst().orElse(null);
		if( result != null ) {
			result = result.getEOpposite();
			return result;
		}
		return null;
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

