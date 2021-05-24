package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
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
    
    Label[] LabelInit(ShortestPathData data) {
    	Label[] tabLabel=new Label[data.getGraph().size()];
    	for(Node o: data.getGraph().getNodes()) {
    		tabLabel[o.getId()]=new Label(o.getId());
    	}
    	tabLabel[data.getOrigin().getId()].setCost(0);
    	return tabLabel;
    }
    
    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        
        
        Label[] tabLabel = LabelInit(data);
        BinaryHeap<Label> Tas=new BinaryHeap<Label>();
        
        Tas.insert(tabLabel[data.getOrigin().getId()]);
        
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        while ((tabLabel[data.getDestination().getId()].getMarque()==false)&&(!(Tas.isEmpty()))) {
	        Node n=data.getGraph().getNodes().get(Tas.deleteMin().getSommet());
	        tabLabel[n.getId()].setMarque(true);
	        //System.out.println(tabLabel[n.getId()].getTotalCost());
	        notifyNodeMarked(n);
	        //int x=0;
	       // System.out.println(Tas.isValid());
	        for (Arc a:n.getSuccessors()) {
	        	//x+=1;
	        	if (data.isAllowed(a)) {
		        	if(tabLabel[a.getDestination().getId()].getMarque()==false){
		        		if (tabLabel[a.getDestination().getId()].getCost()==Float.MAX_VALUE) {
		        			notifyNodeReached(a.getDestination());
		        		}
		        		if(data.getMode()==Mode.LENGTH) {
			        		if (tabLabel[a.getDestination().getId()].getCost()>tabLabel[n.getId()].getCost()+a.getLength()){
			        			tabLabel[a.getDestination().getId()].setCost(tabLabel[n.getId()].getCost()+a.getLength());
			        			tabLabel[a.getDestination().getId()].setPere(a);
			        			
			        			try {
			        				Tas.remove(tabLabel[a.getDestination().getId()]);
			        			} catch(Exception ElementNotFoundException ) {}
			        			Tas.insert(tabLabel[a.getDestination().getId()]);
			        		}
		        		}else {
		        			if (tabLabel[a.getDestination().getId()].getCost()>tabLabel[n.getId()].getCost()+a.getMinimumTravelTime()){
			        			tabLabel[a.getDestination().getId()].setCost(tabLabel[n.getId()].getCost()+(float)a.getMinimumTravelTime());
			        			tabLabel[a.getDestination().getId()].setPere(a);
			        			
			        			try {
			        				Tas.remove(tabLabel[a.getDestination().getId()]);
			        			} catch(Exception ElementNotFoundException ) {}
			        			Tas.insert(tabLabel[a.getDestination().getId()]);
			        			notifyNodeMarked(n);
		        			}
		        	    }
	        	  }
	        }
         }
	 //    if (x==n.getNumberOfSuccessors()) {
      //      System.out.println("ok");
      //  }else {
      //  	System.out.println("pas ok");
      //  }
        
     // Destination has no predecessor, the solution is infeasible...
        }
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

