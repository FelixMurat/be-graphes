package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
    Label[] LabelInit(ShortestPathData data) {
    	boolean DistanceMode=data.getMode().equals(Mode.LENGTH);
    	LabelStar[] tabLabel=new LabelStar[data.getGraph().size()];
    	for(Node o: data.getGraph().getNodes()) {
    		tabLabel[o.getId()]=new LabelStar(o.getId(),DistanceMode,o,data.getDestination());
    	}
    	tabLabel[data.getOrigin().getId()].setCost(0);
    	return tabLabel;
    }

}
