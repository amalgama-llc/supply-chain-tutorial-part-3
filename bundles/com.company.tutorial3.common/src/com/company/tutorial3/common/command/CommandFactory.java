package com.company.tutorial3.common.command;

import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

import com.amalgamasimulation.emf.commands.AddCommand;
import com.amalgamasimulation.emf.commands.RemoveCommand;


public class CommandFactory {
	
	public static final ECrossReferenceAdapter CROSS_REFERENCE_ADAPTER = new ECrossReferenceAdapter();
	
	private CommandFactory() {}
	
	public static <C extends EObject, T extends EObject> AddCommand<T> create(C container, String objectName,
			String patternId, Supplier<T> creator, boolean execute) {
		AddCommand<T> command = new AddCommand<>(container, () -> {
			T object = creator.get();
			object.eAdapters().add(CommandFactory.CROSS_REFERENCE_ADAPTER);

			// Object ID is set of there is a String-typed field with 'id' attribute set to true
			// Object name is updated if there is a String-type field 'name'
			EAttribute idAttribute = UniqNamesManager.getInstance().getIdAttribute(object.eClass());
			if (idAttribute != null) {
				String uniqId = UniqNamesManager.getInstance().generateUniqueId(container, object, patternId);
				UniqNamesManager.getInstance().setId(object, uniqId);
				EAttribute nameAttr = getNameAttribute(object.eClass());
				if (nameAttr != null) {
					object.eSet(nameAttr, uniqId);
				}
			}

			return object;
		});
		if (execute) {
			command.executeInStack();
		}
		return command;
	}
	
	private static EAttribute getNameAttribute(EClass eclass) {
		Optional<EAttribute> attribute = 
				eclass.getEAllAttributes()
						.stream().filter(attr -> attr.getName().equals("name"))
						.findFirst();
		return attribute.orElse(null);
	}
	
	public static <C extends EObject, T extends EObject> AddCommand<T> create(C container, Supplier<T> creator, boolean executeImmediately) {
		AddCommand<T> command = new AddCommand<>(container, creator::get);
		command.getObject().eAdapters().add(CROSS_REFERENCE_ADAPTER);
		if (executeImmediately) {
			command.executeInStack();
		}
		return command;
	}
	
	public static <T extends EObject> RemoveCommand<T> remove(T object, boolean executeImmediately) {
		RemoveCommand<T> command = new RemoveCommand<>(object, CROSS_REFERENCE_ADAPTER);
		if (executeImmediately) {
			command.executeInStack();
		}
		return command;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends EObject> AddCommand<T> copy(T object, int injectionIndex, boolean copyReferences, boolean execute) {
			AddCommand<T> command =  new AddCommand<>(object.eContainer(), injectionIndex, () -> {
			
			Copier copier = new Copier() {
				static final long serialVersionUID = 1;
				
				@Override
				protected void copyAttributeValue(EAttribute eAttribute, EObject eObject, Object value, Setting setting) {
					EAttribute idAttribute = UniqNamesManager.getInstance().getIdAttribute(object.eClass());
					if (eAttribute == idAttribute) {
						super.copyAttributeValue(eAttribute, eObject, 
								UniqNamesManager.getInstance().getNameUniq(eObject.eContainer(), eObject, value == null ? "" : String.valueOf(value)), setting);
					} else {
						super.copyAttributeValue(eAttribute, eObject, value, setting);
					}
				}
				@Override
				protected void copyReference(EReference eReference, EObject eObject, EObject copyEObject) {
					if (copyReferences){
						super.copyReference(eReference, eObject, copyEObject);
						if(eReference.getEOpposite() != null) {
							// case when the reference being copied has the opposite reference in another object
							copyEObject.eSet(eReference, eObject.eGet(eReference));						
						}
					}
				}
			};
			T copy = (T)copier.copy(object);
			copier.copyReferences();
			return copy;
		});
		command.getObject().eAdapters().add(CROSS_REFERENCE_ADAPTER);
		if(execute) {
			command.executeInStack();
		}
		return command;
	}
}

