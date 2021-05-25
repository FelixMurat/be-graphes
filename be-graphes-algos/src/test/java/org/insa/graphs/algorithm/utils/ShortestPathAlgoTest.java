package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.RoadInformation;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.BinaryGraphReader;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;

import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("unused")
public abstract class ShortestPathAlgoTest{
	
	protected static Graph graphCarre, graphInsa;
	
	@BeforeClass
	public static void initAll() throws IOException{
		String mapDirectory = "C:\\Users\\felix\\Desktop\\be-graphes\\Maps\\";
		String mapInsa = mapDirectory + "insa.mapgr";
		String mapCarre = mapDirectory + "carre.mapgr";
		GraphReader readerInsa = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapInsa))));
		graphInsa = readerInsa.read();
		GraphReader readerCarre = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapCarre))));
		graphCarre = readerCarre.read();
	}
	
	public abstract  ShortestPathSolution ShortestPathAlgo(ShortestPathData data);
	
	
	public void test (Graph graph, int FilterIndex) {
			int nbNodes =graph.size();
			for (int i=0;i<50;i++) {
				int OrigineInd=(int)Math.random()*(nbNodes-1);
				int DestinationInd=(int)Math.random()*(nbNodes-1);
				ShortestPathData data= new ShortestPathData(graph,graph.getNodes().get(OrigineInd), graph.getNodes().get(DestinationInd),ArcInspectorFactory.getAllFilters().get(FilterIndex));
				ShortestPathSolution solution=ShortestPathAlgo(data);
				BellmanFordAlgorithm AlgoBF =new BellmanFordAlgorithm(data);
				ShortestPathSolution BellFordSolution=AlgoBF.run();
				assertEquals(solution.getStatus(),BellFordSolution.getStatus());
				if (solution.getStatus()==Status.FEASIBLE) {
					assertTrue(solution.getPath().isValid());
					if (ArcInspectorFactory.getAllFilters().get(FilterIndex).getMode()==Mode.LENGTH) {
						assertTrue(solution.getPath().getLength()==BellFordSolution.getPath().getLength());
					}else {
						assertTrue(solution.getPath().getMinimumTravelTime()==BellFordSolution.getPath().getMinimumTravelTime());
					}
				}
			}
	 }
		
	@Test
	public void TestDistanceSansContraintes(){
		test(graphInsa,0);
		test(graphCarre,0);
	}
	
	@Test
	public void TestDistanceVoitures(){
		test(graphInsa,1);
		test(graphCarre,1);
	}
	
	@Test
	public void TestTempsSansContraintes(){
		test(graphInsa,2);
		test(graphCarre,2);
	}
	
	@Test
	public void TestTempsVoitures(){
		test(graphInsa,3);
		test(graphCarre,3);
	}
	
	@Test
	public void TestTempsPiétons(){
		test(graphInsa,4);
		test(graphCarre,4);
	}
		
		
}