package com.company.tutorial3.application.utils.validation;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemsContainer {

	// checked object type - checked object - error type - list of problems
	private Map<ObjectType, Map<Object, Map<ErrorType, List<Problem>>>> problems = new EnumMap<>(ObjectType.class);

	public ProblemsContainer() {
		for (ObjectType name : ObjectType.values()) {
			Map<Object, Map<ErrorType, List<Problem>>> m = new HashMap<>();
			problems.put(name, m);
		}
	}

	public boolean isErrorExist() {
		for (Map<Object, Map<ErrorType, List<Problem>>> m : problems.values()) {
			for (Map<ErrorType, List<Problem>> m1 : m.values()) {
				if (m1.get(ErrorType.ERROR) != null && !m1.get(ErrorType.ERROR).isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Map<ObjectType, Map<Object, Map<ErrorType, List<Problem>>>> getProblems() {
		return problems;
	}

	public void clear() {
		for (Map<Object, Map<ErrorType, List<Problem>>> m : problems.values()) {
			m.clear();
		}
	}

	public void ensureProblemsIsInitializedForObjectTypeAndObject(ObjectType objectType, Object object) {
		Map<Object, Map<ErrorType, List<Problem>>> map = problems.get(objectType);
		map.computeIfAbsent(object, obj -> {
			Map<ErrorType, List<Problem>> map1 = new EnumMap<>(ErrorType.class);
			map1.put(ErrorType.ERROR, new ArrayList<>());
			map1.put(ErrorType.WARNING, new ArrayList<>());
			return map1;
		});
	}

	public void addProblem(Problem problem) {
		ensureProblemsIsInitializedForObjectTypeAndObject(problem.getObjectType(), problem.getObject());
		problems.get(problem.getObjectType()).get(problem.getObject()).get(problem.getErrorType()).add(problem);
	}
}

