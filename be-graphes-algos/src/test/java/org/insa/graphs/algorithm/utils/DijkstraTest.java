package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;

public class DijkstraTest extends ShortestPathAlgoTest{
	public ShortestPathSolution ShortestPathAlgo(ShortestPathData data) {
		DijkstraAlgorithm algo=new DijkstraAlgorithm(data);
		return algo.run();
		
	}
	
}