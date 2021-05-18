package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;


public class Label implements Comparable<Label> {
	private boolean marque;
	private float coût;
	private Arc père;
	private int sommet;
	
	public Label(int sommet) {
		this.sommet=sommet;
		this.marque=false;
		this.coût=Float.MAX_VALUE;
		this.père=null;
	}
	
	public float getCost() {
		return this.coût;
	}
	
	public boolean getMarque() {
		return this.marque;
	}
	
	public int getSommet () {
		return this.sommet;
	}
	
	public void setCost(float cost) {
		this.coût=cost;
	}
	
	public void setMarque(boolean marque) {
		this.marque=marque;
	}
	
	public Arc getPere() {
		return this.père;
	}
	
	public void setPere(Arc père) {
		this.père=père;
	}
	
	public int compareTo(Label other) {
        return Double.compare(getCost(), other.getCost());
    }
}