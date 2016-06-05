package io;

import java.io.FileWriter;
import java.util.Collections;
import java.util.List;

import interfaces.Graph;
import interfaces.Vertice;
import main.VerticeNameComparator;

/*
 * The partition/cluster assignment files have n uncommented lines. In line
 * i the assignment value for vertex i is given as an integer between 0 and
 * p-1, where p is the number of partitions.
 */

public class PartitionWriter extends ConsoleLogger {

	public PartitionWriter() {
		super(PartitionWriter.class.getSimpleName());
	}

	public boolean write(Graph graph, String outputfilepath) {

		List<Vertice> vertices = graph.getAllVertices();
		Collections.sort(vertices, new VerticeNameComparator());

		try (FileWriter writer = new FileWriter(outputfilepath)) {
			for (Vertice vertice : vertices) {
				writer.write(vertice.getPartitionAssignment() + "\n");
			}
			writer.flush();

			logger.log("writing successfully complete to file: " + outputfilepath);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
