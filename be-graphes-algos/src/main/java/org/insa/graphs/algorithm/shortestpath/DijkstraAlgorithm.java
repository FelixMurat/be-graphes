package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Path;
import java.util.ArrayList;
import java.util.Collections;

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
        
        BinaryHeap<Label> Tas=new BinaryHeap<Label>();
        
        for(Node o: data.getGraph().getNodes()) {
        	tabLabel[o.getId()]=new Label(o.getId());
        }
        
        tabLabel[data.getOrigin().getId()].setCost(0);
        Tas.insert(tabLabel[data.getOrigin().getId()]);
        
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        while ((tabLabel[data.getDestination().getId()].getMarque()==false)&&(!(Tas.isEmpty()))) {
	        Node n=data.getGraph().getNodes().get(Tas.deleteMin().getSommet());
	        tabLabel[n.getId()].setMarque(true);
	       // System.out.println(tabLabel[n.getId()].getCost());
	        notifyNodeMarked(n);
	        for (Arc a:n.getSuccessors()) {
	        	if (data.isAllowed(a)) {
		        	if(tabLabel[a.getDestination().getId()].getMarque()==false){
		        		if (tabLabel[a.getDestination().getId()].getCost()==Float.MAX_VALUE) {
		        			notifyNodeReached(a.getDestination());
		        		}
		        		if (tabLabel[a.getDestination().getId()].getCost()>tabLabel[n.getId()].getCost()+a.getLength()){
		        			tabLabel[a.getDestination().getId()].setCost(tabLabel[n.getId()].getCost()+a.getLength());
		        			tabLabel[a.getDestination().getId()].setPere(a);
		        			
		        			try {
		        				Tas.remove(tabLabel[a.getDestination().getId()]);
		        			} catch(Exception ElementNotFoundException ) {}
		        			Tas.insert(tabLabel[a.getDestination().getId()]);
		        		}
		        	}
	        	}
	        }
        }
        
     // Destination has no predecessor, the solution is infeasible...
        if (tabLabel[data.getDestination().getId()].getPere() == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {

            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = tabLabel[data.getDestination().getId()].getPere();
            while (arc != null) {
                arcs.add(arc);
                arc = tabLabel[arc.getOrigin().getId()].getPere();
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(data.getGraph(), arcs));
        }

        return solution;
    }

}
