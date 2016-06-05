package main;

import java.util.Arrays;

import interfaces.Graph;
import interfaces.Vertice;
import io.ConsoleLogger;

public class Checker extends ConsoleLogger {

	public Checker() {
		super(Checker.class.getSimpleName());
	}

	public class CheckResult {
		private int cutWeight = -1;
		private int largestSubDomain = -1;
		private String usedHeuristic = "noone";

		public CheckResult(int cutWeight, int largestSubDomain, String usedHeuristic) {
			setCutWeight(cutWeight);
			setLargestSubDomain(largestSubDomain);
			setUsedHeuristic(usedHeuristic);
		}

		public int getCutWeight() {
			return cutWeight;
		}

		public void setCutWeight(int cutWeight) {
			this.cutWeight = cutWeight;
		}

		public int getLargestSubDomain() {
			return largestSubDomain;
		}

		public void setLargestSubDomain(int largestSubDomain) {
			this.largestSubDomain = largestSubDomain;
		}

		public String getUsedHeuristic() {
			return usedHeuristic;
		}

		public void setUsedHeuristic(String usedHeuristic) {
			this.usedHeuristic = usedHeuristic;
		}

		@Override
		public String toString() {
			// return getCutWeight()+" ("+getLargestSubDomain()+")
			// ["+getUsedHeuristic()+"]";
			return getCutWeight() + " [" + getUsedHeuristic() + "]";
		}

	}

	public CheckResult check(Graph graph, int k) {

		// since every vertice can only have one partition assignment, we dont
		// have to check if one vertice is assigned to more then one partition

		// but we have to check if every vertice has a partition assignment
		// while we are checking this, we can also count how many vertices one
		// partition has
		int[] kBuckets = new int[k];
		Arrays.fill(kBuckets, 0);
		for (Vertice vertice : graph.getAllVertices()) {
			if (vertice.getPartitionAssignment() >= 0)
				kBuckets[vertice.getPartitionAssignment()]++;
			else {
				logger.logError(
						"solution is not valid (vertice " + vertice.getVerticeID() + " has no partition assignment)");
				return null;
			}
		}

		logger.log("solution is valid!");
		CheckResult checkResult = new CheckResult(graph.getCutEdgesCount(), 666,
				Main.heuristic.getClass().getSimpleName());
		logger.log(checkResult.toString());
		logger.log("cut-weight (#cut-edges) (weight of largest subdomain) [heuristic used]");

		// logger.logOptional("subdomain vertice counts ("+k+" subdomains from 0
		// to "+(k-1)+") = "+Arrays.toString(kBuckets));

		return checkResult;
	}

}
