package main;

import interfaces.Heuristic;
import structs.AdjacencyListGraph;

public class Partitioner {

	public void partGraph(Heuristic heuristic, AdjacencyListGraph graph, int k) {
		
		heuristic.applyHeuristic(graph, k);
		
		// TODO optimize partitioning
		
		return;
	}

}
