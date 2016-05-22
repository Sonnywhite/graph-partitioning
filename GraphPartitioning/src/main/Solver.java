package main;

import java.util.Arrays;

import interfaces.Graph;
import interfaces.Vertice;

public class Solver {
	
	public boolean solve(Graph graph, int k) {
		
		// since every vertice can only have one partition assignment, we dont have to check if one vertice is assigned to more then one partition
		
		// but we have to check if every vertice has a partition assignment
		// while we are checking this, we can also count how many vertices one partition has
		int[] kBuckets = new int[k];
		Arrays.fill(kBuckets, 0);
		for(Vertice vertice : graph.getAllVertices()) {
			if(vertice.getPartitionAssignment()>=0)
				kBuckets[vertice.getPartitionAssignment()]++;
			else {
				System.out.println("ERROR: solution is not valid (vertice "+vertice.getVerticeID()+" has no partition assignment)");
				return false;
			}
		}
		
		System.out.println("INFO: partition vertice counts ("+k+" partitions from 0 to "+(k-1)+") = "+Arrays.toString(kBuckets));
		
		return true;
	}
	
}
