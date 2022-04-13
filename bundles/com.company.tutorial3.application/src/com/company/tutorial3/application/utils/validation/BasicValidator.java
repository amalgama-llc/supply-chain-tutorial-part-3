package com.company.tutorial3.application.utils.validation;

import java.awt.Color;
import java.time.LocalDateTime;
import java.util.Collection;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.company.tutorial3.common.command.UniqNamesManager;
import com.company.tutorial3.common.localization.Messages;

public class BasicValidator {
	private Messages messages;	
	private final ProblemsContainer problemsContainer;
	
	public BasicValidator(ProblemsContainer problemsContainer, Messages messages) {
		this.problemsContainer = problemsContainer;
		this.messages = messages;
	}
	
	public void checkNotEmpty(EObject checkedObject, EObject own, ObjectType objectType, String columnName, ErrorType errorType) {
		checkNotEmpty((Object)checkedObject, own, objectType, columnName, errorType);
	}
	
	public void checkNotEmpty(Color checkedObject, EObject own, ObjectType objectType, String columnName, ErrorType errorType) {
		checkNotEmpty((Object)checkedObject, own, objectType, columnName, errorType);
	}
	
	public void checkNotEmpty(LocalDateTime checkedObject, EObject own, ObjectType objectType, String columnName, ErrorType errorType) {
		checkNotEmpty((Object)checkedObject, own, objectType, columnName, errorType);
	}
	
	public void checkNotEmpty(EStructuralFeature checkedStructuralFeature, EObject own, ErrorType errorType) {
		ObjectType objectType = ObjectType.objectTypeByObject(own);
		checkNotEmpty(own.eGet(checkedStructuralFeature), own, objectType, objectType.getFeatureName(checkedStructuralFeature), errorType);
	}
	
	public void checkNotEmpty(Enumerator checkedObject, EObject own, ObjectType objectType, String columnName, ErrorType errorType) {
		checkNotEmpty((Object)checkedObject, own, objectType, columnName, errorType);
	}
	
	private void checkNotEmpty(Object checkedObject, EObject own, ObjectType objectType, String columnName, ErrorType errorType) {
		if (checkedObject == null) {
			addProblem(own, objectType, String.format(messages.VALIDATION__error_column_not_filled, columnName), errorType);
		}
	}
	
	public void checkNotEmpty(String checkedObject, EObject own, ObjectType objectType, String columnName, ErrorType errorType) {
		if (checkedObject == null || checkedObject.length() == 0) {
			addProblem(own, objectType, String.format(messages.VALIDATION__error_column_not_filled, columnName), errorType);
		}
	}
	
	public void checkNotEmpty(Collection<?> checkedObject, EObject own, ObjectType objectType, String columnName, ErrorType errorType) {
		if (checkedObject == null || checkedObject.isEmpty()) {
			addProblem(own, objectType, String.format(messages.VALIDATION__error_cannot_be_empty, columnName), errorType);
		}
	}
	
	public void checkNotEmpty(Collection<?> checkedObject, EObject own, ObjectType objectType, ErrorType errorType, String customErrorMessage) {
		if (checkedObject == null || checkedObject.isEmpty()) {
			addProblem(own, objectType, customErrorMessage, errorType);
		}
	}
	
	public void checkDateFirstIsNotAfterDateSecond(EStructuralFeature dateFirstFeature, EStructuralFeature dateSecondFeature, 
			EObject own, ErrorType errorType) {
		LocalDateTime dateFirst = (LocalDateTime)own.eGet(dateFirstFeature);
		LocalDateTime dateSecond = (LocalDateTime)own.eGet(dateSecondFeature);
		if (dateFirst != null && dateSecond != null && dateFirst.isAfter(dateSecond)) {
			ObjectType objectType = ObjectType.objectTypeByObject(own);
			String columnNameDateFirst = objectType.getFeatureName(dateFirstFeature);
			String columnNameDateSecond = objectType.getFeatureName(dateSecondFeature);
			addProblem(own, objectType, String.format(messages.VALIDATION__error_begin_date_after_end_date, columnNameDateFirst, columnNameDateSecond), errorType);
		}
	}
	
	public void checkPercent(double d, EObject own, ObjectType objectType, String message, String column) {
		if (d < 0 || d > 100) {
			addProblem(own, objectType, String.format(message, column), ErrorType.ERROR);
		}
	}
	
	public void checkNumberPositive(double checkedNumber, EObject own, ObjectType objectType, String message, String column) {
		checkNumberPositive(checkedNumber, own, objectType, message, column, "");
	}
	
	public void checkNumberPositive(double checkedNumber, EObject own, ObjectType objectType, String message, String column, String idExtractor) {
		final double MIN_VALUE = 0.001;
		if (checkedNumber < MIN_VALUE) {
			addProblem(own, objectType, String.format(message, column, MIN_VALUE), ErrorType.ERROR, idExtractor);
		}
	}
	
	public void checkNumberNonNegative(double checkedNumber, EObject own, ObjectType objectType, String message, String column) {
		if (checkedNumber < 0) {
			addProblem(own, objectType, String.format(message, column), ErrorType.ERROR);
		}
	}
	
	public void checkNumberNonNegativeDouble(EStructuralFeature structuralFeature, EObject own) {
		Double checkedNumber = (Double)own.eGet(structuralFeature);
		if (checkedNumber != null && checkedNumber.doubleValue() < 0) {
			ObjectType objectType = ObjectType.objectTypeByObject(own);
			String columnName = objectType.getFeatureName(structuralFeature);
			addProblem(own, objectType, String.format(messages.VALIDATION__error_non_negative, columnName), ErrorType.ERROR);
		}
	}
	
	public void checkNumberNonNegativeInteger(EStructuralFeature structuralFeature, EObject own) {
		Integer checkedNumber = (Integer)own.eGet(structuralFeature);
		if (checkedNumber != null && checkedNumber.intValue() < 0) {
			ObjectType objectType = ObjectType.objectTypeByObject(own);
			String columnName = objectType.getFeatureName(structuralFeature);
			addProblem(own, objectType, String.format(messages.VALIDATION__error_non_negative, columnName), ErrorType.ERROR);
		}
	}
	
	public void checkNumberPositiveDouble(EStructuralFeature structuralFeature, EObject own) {
		Double checkedNumber = (Double)own.eGet(structuralFeature);
		if (checkedNumber != null && checkedNumber.doubleValue() <= 0) {
			ObjectType objectType = ObjectType.objectTypeByObject(own);
			String columnName = objectType.getFeatureName(structuralFeature);
			addProblem(own, objectType, String.format(messages.VALIDATION__error_positive_number, columnName), ErrorType.ERROR);
		}
	}
	
	public void checkNumberPositiveInteger(EStructuralFeature structuralFeature, EObject own) {
		Integer checkedNumber = (Integer)own.eGet(structuralFeature);
		if (checkedNumber != null && checkedNumber.intValue() <= 0) {
			ObjectType objectType = ObjectType.objectTypeByObject(own);
			String columnName = objectType.getFeatureName(structuralFeature);
			addProblem(own, objectType, String.format(messages.VALIDATION__error_positive_number, columnName), ErrorType.ERROR);
		}
	}
	
	public void checkLatitude(EStructuralFeature structuralFeature, EObject own) {
		Double checkedNumber = (Double)own.eGet(structuralFeature);
		if (checkedNumber != null && (checkedNumber.doubleValue() < -90 || checkedNumber.doubleValue() > 90)) {
			ObjectType objectType = ObjectType.objectTypeByObject(own);
			String columnName = objectType.getFeatureName(structuralFeature);
			addProblem(own, objectType, String.format(messages.VALIDATION__error_latitude, columnName), ErrorType.ERROR);
		}
	}
	
	public void checkLongitude(EStructuralFeature structuralFeature, EObject own) {
		Double checkedNumber = (Double)own.eGet(structuralFeature);
		if (checkedNumber != null && (checkedNumber.doubleValue() < -180 || checkedNumber.doubleValue() > 180)) {
			ObjectType objectType = ObjectType.objectTypeByObject(own);
			String columnName = objectType.getFeatureName(structuralFeature);
			addProblem(own, objectType, String.format(messages.VALIDATION__error_longitude, columnName), ErrorType.ERROR);
		}
	}
	
	private void addProblem(EObject own, ObjectType objectType, String message, ErrorType errorType) {
		addProblem(own, objectType, message, errorType, objectType.getName());
	}
	
	private void addProblem(EObject own, ObjectType objectType, String message, ErrorType errorType, String extractorId) {
		String id = UniqNamesManager.getId(own);
		if (id == null || id.length() == 0) {
			id = extractorId;
		}
		addProblem(new Problem(objectType, id, errorType, message, own));
	}
	
	private void addProblem(Problem problem) {
		problemsContainer.addProblem(problem);
	}
}

