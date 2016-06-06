package main;

import java.util.Comparator;

import interfaces.Vertice;

public class VertriceDegreeComparator implements Comparator<Vertice> {

	@Override
	public int compare(Vertice o1, Vertice o2) {
		// reverse order
		return Integer.compare(o2.getVerticeDegree(), o1.getVerticeDegree());
	}

}
