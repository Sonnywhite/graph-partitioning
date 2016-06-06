package main;

import java.util.Comparator;

import interfaces.Vertice;

public class VerticeNameComparator implements Comparator<Vertice> {

	@Override
	public int compare(Vertice o1, Vertice o2) {
		// TODO Auto-generated method stub
		return Integer.compare(o1.getVerticeID(), o2.getVerticeID());
	}

}
