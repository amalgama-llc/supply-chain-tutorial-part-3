package com.company.tutorial3.application.utils.validation;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.emf.ecore.EObject;

import com.company.tutorial3.application.utils.Topics;
import com.company.tutorial3.common.command.UniqNamesManager;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.datamodel.Arc;
import com.company.tutorial3.datamodel.DatamodelPackage;
import com.company.tutorial3.datamodel.Node;
import com.company.tutorial3.datamodel.Point;
import com.company.tutorial3.datamodel.Scenario;

public final class ValidationManager {

	public static final String VALIDATION_FINISHED = "VALIDATION_FINISHED";
	
	private ProblemsContainer problemsContainer = new ProblemsContainer();
	private BasicValidator basicValidator;
	
	private Messages messages;
	
	public ValidationManager(Messages messages) {
		this.messages = messages;
		basicValidator = new BasicValidator(this.problemsContainer, messages);
	}
		
	public boolean isErrorExist() {
		return problemsContainer.isErrorExist();
	}
	
	public void clear() {
		problemsContainer.clear();
	}
	
	public Map<ObjectType, Map<Object, Map<ErrorType, List<Problem>>>> getProblems() {
		return problemsContainer.getProblems();
	}
	
	public void validate(IEventBroker eventBroker, Scenario scenario, EPartService partService) {
		if (scenario != null) {
			clear();
			validateInternal(scenario);
			activateProblemPart(partService);
			eventBroker.send(Topics.PROBLEMS_PART, this);
		}
	}
	
	private void activateProblemPart(EPartService partService) {
		MPart part = partService.findPart("com.company.tutorial3.application.part.properties");
		partService.activate(part);
	}
	
	protected void validateInternal(Scenario scenario) {
		checkScenario(scenario);
		scenario.getNodes().stream().forEach(this::checkNode);
		scenario.getArcs().stream().forEach(this::checkArc);
		
	}
	
	private void checkArc(Arc arc) {
		basicValidator.checkNotEmpty(DatamodelPackage.Literals.ARC__NAME, arc, ErrorType.WARNING);
		basicValidator.checkNotEmpty(DatamodelPackage.Literals.ARC__SOURCE, arc, ErrorType.ERROR);
		basicValidator.checkNotEmpty(DatamodelPackage.Literals.ARC__DEST, arc, ErrorType.ERROR);
		if(arc.getSource() != null && arc.getDest() != null) {
			if(arc.getSource() == arc.getDest()) {
				addProblem(arc, ObjectType.ARC, messages.VALIDATION__error_source_equal_dest, ErrorType.ERROR);
			}
		}		
		arc.getPoints().forEach(this::checkPoint);
	}
	
	private void checkPoint(Point p) {
		basicValidator.checkLatitude(DatamodelPackage.Literals.POINT__LATITUDE, p);
		basicValidator.checkLongitude(DatamodelPackage.Literals.POINT__LONGITUDE, p);
	}
	
	private void checkNode(Node node) {
		basicValidator.checkNotEmpty(DatamodelPackage.Literals.NODE__NAME, node, ErrorType.WARNING);
		basicValidator.checkLatitude(DatamodelPackage.Literals.NODE__LATITUDE, node);
		basicValidator.checkLongitude(DatamodelPackage.Literals.NODE__LONGITUDE, node);
	}

	private void checkScenario(Scenario scenario) {
		basicValidator.checkNotEmpty(DatamodelPackage.Literals.SCENARIO__BEGIN_DATE, scenario, ErrorType.ERROR);
		basicValidator.checkNotEmpty(DatamodelPackage.Literals.SCENARIO__END_DATE, scenario, ErrorType.ERROR);
		
		basicValidator.checkDateFirstIsNotAfterDateSecond(
				DatamodelPackage.Literals.SCENARIO__BEGIN_DATE,  
				DatamodelPackage.Literals.SCENARIO__END_DATE, 
				scenario, ErrorType.ERROR);
	}
	
	
	private void addProblem(EObject own, ObjectType objectType, String message, ErrorType errorType) {
		addProblem(own, objectType, message, errorType, "");
	}
	
	private void addProblem(EObject own, ObjectType objectType, String message, ErrorType errorType, String extractorId) {
		addProblem(own, objectType, message, errorType, extractorId, Collections.emptyList());
	}
			
	private void addProblem(EObject own, ObjectType objectType, String message, ErrorType errorType, String extractorId, List<Object> otherObjects) {
		String id = UniqNamesManager.getId(own);
		if (id == null || id.length() == 0) {
			id = extractorId;
		}
		addProblem(new Problem(objectType, id, errorType, message, own, otherObjects));
	}
	
	public void addProblem(Problem problem) {
		problemsContainer.addProblem(problem);
	}
}



