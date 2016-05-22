package io;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
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

public class PartitionWriter {

	public boolean write(Graph graph, String outputfilepath) {
		StringBuilder sb = new StringBuilder();

		List<Vertice> vertices = graph.getAllVertices();
		Collections.sort(vertices, new VerticeNameComparator());
		
		for(Vertice vertice : vertices) {
			sb.append(vertice.getPartitionAssignment()).append("\n");
		}
		
		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outputfilepath), "utf-8"))) {
			writer.write(sb.toString());
			
			System.out.println("INFO: writing successfully complete to file: "+outputfilepath);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
