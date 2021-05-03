package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.shortestpath.Label;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;
import org.insa.graphs.algorithm.utils.BinaryHeap;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        Label[] tabLabel=new Label[data.getGraph().size()];
        
        BinaryHeap Tas=new BinaryHeap();
        
        for(Node o: data.getGraph().getNodes()) {
        	tabLabel[o.getId()]=new Label(o.getId());
        }
        
        tabLabel[data.getOrigin().getId()].setCost(0);
        Tas.insert(data.getOrigin());
        
        while (tabLabel[data.getDestination().getId()].getMarque()==false) {
	        Node n=Tas.deleteMin();
	        tabLabel[n.getId()].setMarque(true);
	        for (Arc a:n.getSuccessors()) {
	        	if(tabLabel[a.getDestination().getId()].getMarque()==false) {
	        		tabLabel[a.getDestination().getId()].setCost(java.lang.Math.min(tabLabel[a.getDestination().getId()].getCost(), (tabLabel[n.getId()].getCost()+a.getLength())));
	        	}
	        }
        	
        }
        return solution;
    }

}
