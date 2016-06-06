package main;

import interfaces.Graph;
import interfaces.Heuristic;
import structs.HashMapGraph;

public class Partitioner {

	public void partGraph(Heuristic heuristic, Graph graph, int k) {
		
		heuristic.applyHeuristic(graph, k);
		
		// TODO optimize partitioning
		
		return;
	}

}
