package com.company.tutorial3.simulation;

import com.amalgamasimulation.utils.container.BiMap;
import com.company.tutorial3.simulation.model.Arc;
import com.company.tutorial3.simulation.model.Node;

public class Mapping {

		public BiMap<com.company.tutorial3.datamodel.Node, Node> nodesMap = new BiMap<>();
		public BiMap<com.company.tutorial3.datamodel.Arc, Arc> forwardArcsMap = new BiMap<>();

}

