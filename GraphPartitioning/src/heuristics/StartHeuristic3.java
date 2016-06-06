package heuristics;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import interfaces.Graph;
import interfaces.Heuristic;
import interfaces.Vertice;
import io.ConsoleLogger;
import main.Main;
import main.Utils;
import main.VertriceDegreeComparator;
import structs.HashMapDegreeGraph;
import structs.HashMapGraph;

/*
 * 3rd Startheuristic
 * - based on recent result of our first two heuristics and new ideas
 * 
 * 1. sort vertices by degree
 * 2. build up k partitions but try to fill them with neighbours
 * 3. spread rest of vertices equally
 * 
 */

public class StartHeuristic3 extends ConsoleLogger implements Heuristic {

	// TODO PLACEHOLDER
	private int PARTITION_SIZE;

	private int[] partitionSizes;

	public StartHeuristic3() {
		super(StartHeuristic3.class.getSimpleName());
	}

	@Override
	public void applyHeuristic(Graph graph, int k) {

		PARTITION_SIZE = (int) Math.ceil(graph.getVerticesCount() / k);
		partitionSizes = new int[k];

		List<Vertice> vertices = graph.getAllVertices();
		Collections.sort(vertices, new VertriceDegreeComparator());

		long startFirst = System.currentTimeMillis();
		int partition = 0;
		for (Vertice vertice : vertices) { // O(|V|)
			if (partition == k)
				partition = 0;

			if (vertice.getPartitionAssignment() != Main.NO_PARTITION_ASSIGNMENT)
				continue;

			vertice.setPartitionAssignment(partition);
			partitionSizes[partition]++;

			// try to add neighbours to the same partition
			for (Vertice neighbour : vertice.getNeighbours()) { // O(PARTITION_SIZE)
				if (neighbour.getPartitionAssignment() != Main.NO_PARTITION_ASSIGNMENT)
					continue;
				if (partitionSizes[partition] == PARTITION_SIZE)
					break;

				neighbour.setPartitionAssignment(partition);
				partitionSizes[partition]++;
			}

			partition++;
		}
		long endFirst = System.currentTimeMillis();

		long startSecond = System.currentTimeMillis();
		for (Vertice vertice : vertices) { // O(|V|)

			if (vertice.getPartitionAssignment() != Main.NO_PARTITION_ASSIGNMENT)
				continue;

			if (vertice.getNeighbours().size() == 0) {
				partition = Utils.indexOfMin(partitionSizes);
				vertice.setPartitionAssignment(partition);
				partitionSizes[partition]++;
			} else {
				// determine best partition between neighbours
				determinePartitionFromNeighbours(vertice, k);
			}
		}
		long endSecond = System.currentTimeMillis();

		long startThird = System.currentTimeMillis();
		partition = 0;
		for (Vertice vertice : vertices) { // O(|V|)
			if (partition == k)
				partition = 0;

			if (vertice.getPartitionAssignment() != Main.NO_PARTITION_ASSIGNMENT)
				continue;

			// TODO
			partition = Utils.indexOfMin(partitionSizes);
			vertice.setPartitionAssignment(partition);
			partitionSizes[partition]++;

			partition++;
		}
		long endThird = System.currentTimeMillis();

		logger.log("heuristic times: " + Utils.inSeconds(startFirst, endFirst) + "s, "
				+ Utils.inSeconds(startSecond, endSecond) + "s, " + Utils.inSeconds(startThird, endThird) + "s");

	}

	private void determinePartitionFromNeighbours(Vertice vertice, int k) {
		int[] partitionConn = new int[k];
		Arrays.fill(partitionConn, 0);
		for (Vertice neighbour : vertice.getNeighbours()) {
			partitionConn[neighbour.getPartitionAssignment()]++;
		}
		int bestPartition = Utils.indexOfMax(partitionConn);
		if (bestPartition >= 0) {
			vertice.setPartitionAssignment(bestPartition);
			partitionSizes[bestPartition]++;
		}
	}

	@Override
	public Graph getGraphInstance() {
		return new HashMapGraph();
	}

}
