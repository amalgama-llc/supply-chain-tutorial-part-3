package com.company.tutorial3.application.parts.editor;

import java.util.ArrayList;
import java.util.List;

import com.amalgamasimulation.desktop.ui.editor.Page;
import com.amalgamasimulation.desktop.ui.editor.PropertyPart;
import com.company.tutorial3.application.pages.ArcPage;
import com.company.tutorial3.application.pages.NodePage;
import com.company.tutorial3.application.pages.PointPage;
import com.company.tutorial3.application.pages.ScenarioPage;
import com.company.tutorial3.application.pages.StorePage;
import com.company.tutorial3.application.pages.TruckPage;
import com.company.tutorial3.application.pages.WarehousePage;

public class PropertiesPart extends PropertyPart {

	@Override
	protected List<Class<? extends Page<?>>> getPages() {
		List<Class<? extends Page<?>>> list = new ArrayList<>();
		list.add(ArcPage.class);
		list.add(NodePage.class);
		list.add(PointPage.class);
		list.add(ScenarioPage.class);
		list.add(StorePage.class);
		list.add(TruckPage.class);
		list.add(WarehousePage.class);

		return list;
	}
}
