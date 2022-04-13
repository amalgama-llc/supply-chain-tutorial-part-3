package com.company.tutorial3.application.parts.editor.treeelements;

import java.util.List;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.emf.ecore.EObject;

import com.company.tutorial3.application.parts.editor.TreePart.TreeElementType;
import com.company.tutorial3.common.localization.Messages;


public abstract class TreeElement {

	protected final TreeElementType treeElementType;
	protected final EObject eObject;
	protected Messages messages;
	
	private IObservableList<TreeElement> childElementsCache = null;
	
	protected TreeElement(TreeElementType treeElementType, EObject eObject, Messages messages) {
		this.treeElementType = treeElementType;
		this.eObject = eObject;
		this.messages = messages;
	}
	
	public final TreeElementType getTreeElementType() {
		return treeElementType;
	}

	public final EObject getEObject() {
		return eObject;
	}
	
	public final IObservableList<TreeElement> getChildElements() {
		if (childElementsCache == null) {
			childElementsCache = new WritableList<>(createChildElements(), null);
		}
		return childElementsCache;
	}
	
	protected List<TreeElement> createChildElements() {
		return List.of();
	}
	
	public abstract String getName();
}



