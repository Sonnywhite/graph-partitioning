package heuristics;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.Graph;
import interfaces.Heuristic;
import interfaces.Vertice;
import io.ConsoleLogger;
import main.VertriceDegreeComparator;

/*
 * Second Heuristic
 * 
 * 1. choose k vertices (with a high degree)
 * 2. add these vertices one by one to a starting partition 
 * 3. for each vertice without a partition 
 * 	a. determine best connected partition
 *  b. add vertice
 *  c. update partition connections
 *  
 * the main problem will be the partition connectivity,
 * on the one hand to hold the information and on the other to update it
 * 
 * solution: whenever we assign a partition to a vertice,
 * we update the connectivity for its neighbours
 */

public class StartHeuristic2 extends ConsoleLogger implements Heuristic {

	public StartHeuristic2() {
		super(StartHeuristic2.class.getSimpleName());
	}

	/**
	 * holds the partition connectivity information for every vertice<br>
	 * <br>
	 * 0 - vertice is in this partition <br>
	 * -1 - no information regarding connectivity <br>
	 * -666 - empty column <br>
	 * 1+ - grade of connectivity
	 * 
	 */
	private Integer[][] partitionConnectivity;

	private Map<Integer, Vertice> idToVertice;

	@Override
	public void applyHeuristic(Graph graph, int k) {

		// verticeCount + 1, because now we can operate with vertice IDs
		partitionConnectivity = new Integer[graph.getVerticesCount() + 1][k];

		Arrays.fill(partitionConnectivity[0], -666);
		for (int i = 1; i <= graph.getVerticesCount(); i++) {
			Arrays.fill(partitionConnectivity[i], -1);
		}

		// DEBUG
		// printPartitionConnectivity();

		// get vertices and sort them by degree
		List<Vertice> vertices = graph.getAllVertices();
		idToVertice = new HashMap<>();
		for (Vertice vertice : vertices) // complexity: irrelevant
			idToVertice.put(vertice.getVerticeID(), vertice);
		Collections.sort(vertices, new VertriceDegreeComparator());

		int currVerticeCount = 0;
		
		// build up k partitions; complexity: O(k)
		for (int i = 0; i < k; i++) {

			// update partition connectivity for this vertice
			vertices.get(i).setPartitionAssignment(i);
			partitionConnectivity[vertices.get(i).getVerticeID()][i] = 0;

			// get neighbours & update partition connectivity
			for (int j : vertices.get(i).getNeighbourIDs())
				partitionConnectivity[j][i] = 1;
			
			currVerticeCount++;
			logger.logProgress(currVerticeCount, graph.getVerticesCount());
		}

		// DEBUG
		// printPartitionConnectivity();

		// fill partitions equally; 
		// complexity: O(|V|-k) * O(|V|) 
		int currentPartition = 0;
		for (int i = k; i < vertices.size(); i++) {
			if (currentPartition == k)
				currentPartition = 0;

			// determine best vertice to add
			// complexity: O(|V|)
			int maxConnectivity = -1;
			int maxConnVerticeID = -1;
			Vertice firstVerticeWithoutPartAssign = null;
			for (int j = 1; j <= graph.getVerticesCount(); j++) {

				// if a vertice has an assignment != -1 it is already assigned
				boolean isAlreadyAssigned = idToVertice.get(j).getPartitionAssignment() > -1;

				if (!isAlreadyAssigned && partitionConnectivity[j][currentPartition] > 0) {

					// vertice is not assigned + has a connectivity to this
					// partition
					maxConnectivity = partitionConnectivity[j][currentPartition];
					maxConnVerticeID = j;

				} else if (!isAlreadyAssigned && partitionConnectivity[j][currentPartition] == -1
						&& firstVerticeWithoutPartAssign == null) {

					// first looked at vertice that is not assigned + has no
					// connectivity to this partition
					firstVerticeWithoutPartAssign = idToVertice.get(j);

				}
			}

			// DEBUG
			// System.out.println("DEBUG: found vertice " + maxConnVerticeID + "
			// for partition " + tmp);

			if (maxConnectivity > 0 && maxConnVerticeID > 0) {

				// found the best vertice to add to this partition
				Vertice bestVerticeToAdd = idToVertice.get(maxConnVerticeID);
				addVerticeToPartitionAndUpdateConnectivity(bestVerticeToAdd, currentPartition);

			} else {

				// didnt found any vertice to add
				// -> add previously found first Vertice without an assignment
				addVerticeToPartitionAndUpdateConnectivity(firstVerticeWithoutPartAssign, currentPartition);
			}

			currentPartition++;
			
			currVerticeCount++;
			logger.logProgress(currVerticeCount,graph.getVerticesCount());
		}

		// DEBUG
		// printPartitionConnectivity();

		//logger.log(StartHeuristic2.class.getSimpleName() + " applied!");
		return;
	}

	// complexity: O(|V|)
	private void addVerticeToPartitionAndUpdateConnectivity(Vertice vertice, int currentPartition) {
		vertice.setPartitionAssignment(currentPartition);
		partitionConnectivity[vertice.getVerticeID()][currentPartition] = 0;

		// get neighbours & update partition connectivity
		for (int j : vertice.getNeighbourIDs()) {

			if (partitionConnectivity[j][currentPartition] > 0) {
				// has a grade to this partition already
				partitionConnectivity[j][currentPartition]++;
			} else if (partitionConnectivity[j][currentPartition] == -1) {
				// has no grade to this partition yet
				partitionConnectivity[j][currentPartition] = 1;
			}

			// if grade is 0 the neighbour is already in this partition
		}
	}

	@SuppressWarnings("unused")
	private void printPartitionConnectivity() {
		System.out.print("DEBUG: ");
		for (int i = 0; i < partitionConnectivity.length; i++)
			System.out.print(Arrays.toString(partitionConnectivity[i]));
		System.out.print("\n");
	}

}