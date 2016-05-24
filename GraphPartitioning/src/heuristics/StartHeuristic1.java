package heuristics;

import java.util.Collections;
import java.util.List;

import interfaces.Graph;
import interfaces.Heuristic;
import interfaces.Vertice;
import main.VertriceDegreeComparator;

/*
 * 1. choose k vertices (with a high degree)
 * 2. for each vertice add one of the free vertices (random)
 */

public class StartHeuristic1 implements Heuristic {

	@Override
	public void applyHeuristic(Graph graph, int k) {

		// get vertices and sort them by degree
		List<Vertice> vertices = graph.getAllVertices();
		Collections.sort(vertices, new VertriceDegreeComparator());

		// build up K vertice sets (partitions)
		for (int i = 0; i < k; i++) 
			vertices.get(i).setPartitionAssignment(i);

		// fill up vertice sets equally
		int tmp = 0;
		for (int i = k; i < vertices.size(); i++) {
			if (tmp == k)
				tmp = 0;
			vertices.get(i).setPartitionAssignment(tmp);
			tmp++;
		}

		return;
	}

}
