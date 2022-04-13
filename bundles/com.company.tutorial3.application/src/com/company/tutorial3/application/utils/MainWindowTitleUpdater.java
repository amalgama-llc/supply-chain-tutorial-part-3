package com.company.tutorial3.application.utils;

import java.time.LocalDateTime;
import java.util.List;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IWorkbench;

import com.company.tutorial3.application.AppInfo;
import com.company.tutorial3.common.states.AppData;
import com.company.tutorial3.datamodel.Scenario;
import com.amalgamasimulation.utils.format.Formats;


public class MainWindowTitleUpdater {
	
	private MainWindowTitleUpdater() {}

	public static void updateMainWindowTitle(IWorkbench workbench, AppData appData) {
		MApplication application = workbench.getApplication();
		List<MWindow> windows = application.getChildren();
		MWindow mainWindow = windows.get(0);
	
		Scenario scenario = appData.getScenario();
		String title = AppInfo.getFullProductName();
		if (scenario != null) {
			title = title + ",  " + scenario.getName() + ", " + formatDate(scenario.getBeginDate()) + " - " + formatDate(scenario.getEndDate());
		}
		mainWindow.setLabel(title);
	}
	
	private static String formatDate(LocalDateTime date) {
		return date == null ? "" : Formats.getDefaultFormats().dayMonthYear(date);
	}

}



