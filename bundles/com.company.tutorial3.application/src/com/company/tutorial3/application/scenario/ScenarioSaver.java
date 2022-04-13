package com.company.tutorial3.application.scenario;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.ecore.resource.Resource;
import com.amalgamasimulation.emf.commands.CommandsManager;
import com.amalgamasimulation.emf.excel.EMFExcelSaver;
import com.amalgamasimulation.emf.excel.EMFExcelTransform;

import com.company.tutorial3.application.utils.EMFExcelTransformFactory;
import com.company.tutorial3.datamodel.Scenario;

public class ScenarioSaver {
    private ScenarioSaver() {
    }
    
    public static class ScenarioSavingException extends RuntimeException {
		private static final long serialVersionUID = -7961780578467278337L;

		public ScenarioSavingException(List<String> errors) {
    		super(String.join(System.lineSeparator(), errors));
    	}
    	
    	public ScenarioSavingException(String ... errors) {
    		this(Arrays.asList(errors));
    	}
    }
    
    /**
     * Saves scenario to an excel file.
     * 
     * @param scenario the Scenario object to be saved
     * @param filePath full path to the file where the scenario will be saved
     * @throws ScenarioSavingException in case of any saving errors
     */
    public static void save(Scenario scenario, String filePath) throws ScenarioSavingException {
		saveToExcel(	scenario,
						ScenarioSaver.ensureFilePathEndsWith(filePath, ScenarioFileFormat.EXCEL.fileDotExtension),
						EMFExcelTransformFactory.createExcelTransform());
    }

    /**
     * Saves scenario to an EXCEL file
     * 
     * @param scenario the Scenario object to be saved
     * @param filePath full file path
     */
    private static void saveToExcel(Scenario scenario,
                                   String filePath,
                                   EMFExcelTransform<Scenario> emfExcelTransform) {
    	
        EMFExcelSaver<Scenario> saver = emfExcelTransform.createSaver(filePath, scenario);
		if (!saver.save()) {
			List<String> errors = saver		.getErrors()
											.stream()
											.map(Resource.Diagnostic::getMessage)
											.collect(Collectors.toList());
			throw new ScenarioSavingException(errors);
		}
    }

    private static String ensureFilePathEndsWith(String filePath,
                                                String shouldEndWith) {
        return filePath.endsWith(shouldEndWith) ? filePath : filePath.concat(shouldEndWith);
    }

	public static boolean isSaveNeeded() {
		BasicCommandStack b = (BasicCommandStack)CommandsManager.getEditingDomain().getCommandStack();	
		return b.isSaveNeeded();
	}
}



