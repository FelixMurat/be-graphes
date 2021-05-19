package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Node;


public class LabelStar extends Label {
	
	private float coûtDestination;
	public LabelStar(int sommet, boolean DistanceMode,Node noeud, Node destination) {
		super(sommet);
		if (DistanceMode==true) {
			this.coûtDestination=(float)noeud.getPoint().distanceTo(destination.getPoint());
		}else {
			this.coûtDestination=(float)noeud.getPoint().distanceTo(destination.getPoint())/10f;//on estime qu'un oiseau vol à 10m/s
		}
	}
	public float getCoûtTotal() {
		return (this.coût+this.coûtDestination);
	}
	public float getCoûtDest() {
		return this.coûtDestination;
	}
	public int compareTo (LabelStar other) {
		int resu;
        resu= Float.compare(getCoûtTotal(), other.getCoûtTotal());
        if (resu==0) {
        	return Float.compare(getCoûtDest(), other.getCoûtDest());
        }else {
        	return resu;
        }
    }
}