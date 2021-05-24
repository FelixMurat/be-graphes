package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;

public class AStarTest extends ShortestPathAlgoTest{
	public ShortestPathSolution ShortestPathAlgo(ShortestPathData data) {
		AStarAlgorithm algo=new AStarAlgorithm(data);
		return algo.run();
		
	}
	
}