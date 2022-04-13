package com.company.tutorial3.application.utils.validation;

import java.util.Collections;
import java.util.List;

public class Problem {
	private final ObjectType type;
	private final String id;
	private final ErrorType errorType;
	private final String description;
	private final Object object;
	private final List<Object> otherObjects;

	public Problem(String id, ErrorType errorType, String description, Object object) {
		this(ObjectType.objectTypeByObject(object), id, errorType, description, object, Collections.emptyList());
	}

	public Problem(ObjectType type, String id, ErrorType errorType, String description, Object object) {
		this(type, id, errorType, description, object, Collections.emptyList());
	}

	public Problem(ObjectType type, String id, ErrorType errorType, String description, Object object, List<Object> otherObjects) {
		super();
		this.type = type;
		this.id = id;
		this.errorType = errorType;
		this.description = description;
		this.object = object;
		this.otherObjects = otherObjects;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public String getDescription() {
		return description;
	}

	public Object getObject() {
		return object;
	}

	public ObjectType getObjectType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public List<Object> getOtherObjects() {
		return this.otherObjects;
	}
}



