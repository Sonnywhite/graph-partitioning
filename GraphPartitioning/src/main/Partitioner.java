package main;

import interfaces.Heuristic;
import structs.HashMapGraph;

public class Partitioner {

	public void partGraph(Heuristic heuristic, HashMapGraph graph, int k) {
		
		heuristic.applyHeuristic(graph, k);
		
		// TODO optimize partitioning
		
		return;
	}

}
