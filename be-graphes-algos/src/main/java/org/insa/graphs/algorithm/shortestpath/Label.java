package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;


public class Label{
	private boolean marque;
	private double coût;
	private Arc père;
	private int sommet;
	
	public Label(int sommet) {
		this.sommet=sommet;
		this.marque=false;
		this.coût=Double.MAX_VALUE;
		this.père=null;
	}
	
	public double getCost() {
		return this.coût;
	}
}