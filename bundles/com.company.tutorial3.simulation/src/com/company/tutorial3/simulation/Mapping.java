package com.company.tutorial3.simulation;

import java.util.function.Function;

import com.company.tutorial3.common.states.AppData;
import com.company.tutorial3.simulation.model.Arc;
import com.company.tutorial3.simulation.model.Node;
import com.amalgamasimulation.utils.container.BiMap;
import com.amalgamasimulation.utils.format.Formats;

public class Mapping {

		public BiMap<com.company.tutorial3.datamodel.Node, Node> nodesMap = new BiMap<>();
		public BiMap<com.company.tutorial3.datamodel.Arc, Arc> forwardArcsMap = new BiMap<>();
	
		public static final com.amalgamasimulation.utils.Mapping<Indicator.Type, String> INDICATOR_LABELS = com.amalgamasimulation.utils.Mapping.of(Indicator.Type.class, AppData.messages.INDICATOR_obj_UNKNOWN)
				.map(Indicator.Type.ARCS, AppData.messages.INDICATOR_ARCS)
				.map(Indicator.Type.NODES, AppData.messages.INDICATOR_NODES)
				.map(Indicator.Type.SERVICE_LEVEL, "Service level")
				.map(Indicator.Type.EXPENSES, "Expenses");
		
		public static final com.amalgamasimulation.utils.Mapping<Indicator.Type, Function<Double, String>> INDICATOR_FORMATTERS = com.amalgamasimulation.utils.Mapping.<Indicator.Type, Function<Double, String>>of(Indicator.Type.class, Formats.getDefaultFormats()::kmb)
				.map(Indicator.Type.ARCS, (Double f) -> String.valueOf(Math.round(f)))
				.map(Indicator.Type.NODES, (Double f) -> String.valueOf(Math.round(f)))
				.map(Indicator.Type.SERVICE_LEVEL, Formats.getDefaultFormats()::percent)
				.map(Indicator.Type.EXPENSES, Formats.getDefaultFormats()::dollarTwoDecimals);
}

