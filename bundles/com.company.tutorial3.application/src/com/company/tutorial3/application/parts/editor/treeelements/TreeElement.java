package com.company.tutorial3.application.parts.editor.treeelements;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.emf.ecore.EObject;

import com.company.tutorial3.application.utils.TreeElementType;


public abstract class TreeElement {

	protected final TreeElementType treeElementType;
	protected final EObject eObject;
	
	private IObservableList<TreeElement> childElementsCache = null;
	
	protected TreeElement(TreeElementType treeElementType, EObject eObject) {
		this.treeElementType = treeElementType;
		this.eObject = eObject;
	}
	
	public final TreeElementType getTreeElementType() {
		return treeElementType;
	}

	public final EObject getEObject() {
		return eObject;
	}
	
	public String getLabel() {
		return treeElementType.getLabel();
	}
	public boolean isEmptyElements() {return false;}
	
	public final IObservableList<TreeElement> getChildElements() {
		if (childElementsCache == null) {
			childElementsCache = new WritableList<>(createChildElements(), null);
		}
		return childElementsCache;
	}
	
	protected List<TreeElement> createChildElements() {
		return List.of();
	}
	
	protected TreeElement createLeaf(TreeElementType treeElementType, Supplier<? extends Number> countSupplier) {
		return createLeaf(treeElementType, () -> countSupplier.get().intValue() == 0, label -> getFormattedName(label, countSupplier));
	}
	
	protected TreeElement createLeaf(TreeElementType treeElementType, BooleanSupplier isEmptyElements, UnaryOperator<String> labelProdiver) {
		return new TreeElement(treeElementType, eObject) {
			@Override
			public String getLabel() {
				return labelProdiver.apply(treeElementType.getLabel());
			}
			
			@Override
			public boolean isEmptyElements() {
				return isEmptyElements.getAsBoolean();
			}
		};
	}
	
	private String getFormattedName(String baseName, Supplier<? extends Number> countSupplier) {
		return String.format("%s (%s entries)", baseName, countSupplier.get());
	}
	
}

