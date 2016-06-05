package io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import interfaces.Graph;
import main.GraphType;

/*
 * 
 * KOMMENTARE:
 * 
 * ...the graph files may contain comment lines that begin with the
 * character '%'. Such lines are ignored.
 * 
 * -----------------------------
 * 
 * PARAMETER:
 * 
 * 1. line = 2, 3 or 4 parameters:
 * 
 * The first two obligatory entries are the number of vertices and the
 * number of edges in the graph. (Note that in this case the number of edges
 * is only half of the sum of the vertex degrees.)
 * 
 * The third and fourth parameter in the first line are optional and control
 * input of weighted graphs.
 * 
 * (Important: if the graph contains self-loops and/or multiple edges, the
 * third parameter in the first line is set to 100
 * 
 * note that in this case the second parameter is the actual number of edge
 * entries in the file, = sum of the vertex degrees
 * 
 * deviation is necessary due to self-loops. They do not appear twice in the
 * file, as undirected (= bidirected) edges do.)
 * 
 * -----------------------------
 * 
 * KNOTEN:
 * 
 * In the unweighted case, the remaining n lines contain neighbor lists for
 * each vertex from 1 to n in order.
 * 
 * These lists are sets of integers separated by spaces and contain all the
 * neighbors of a given vertex. (The neighbors may be listed in any order.)
 * Note that vertices are numbered 1 to n, not from 0 to n-1.
 * 
 */

public class GraphFormatReader extends ConsoleLogger {

	public GraphFormatReader() {
		super(GraphFormatReader.class.getSimpleName());
	}

	public Graph read(InputStream inputStream, Graph graph) throws Exception {

		// try read file
		// Path path = Paths.get(filepath);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

			int verticesCountFromFile = 0;
			int edgesCountFromFile = 0;

			String line = null;
			String[] splittedLine = null;

			// read and parse first line (parameters)
			if ((line = reader.readLine()) != null) {

				// skip comment lines
				while (line != null && line.startsWith("%")) {

					line = reader.readLine();
				}

				// check first line again (in case we have skipped comment
				// lines)
				if (line == null) {
					logger.logError("first line was empty");
					return null;
				}

				splittedLine = line.trim().split(" +");

				if (splittedLine.length < 2) {
					logger.logError("parameter line has not enough values (" + splittedLine.length
							+ " given, min. 2 are required)");
					return null;
				}
				if (splittedLine.length > 4) {
					logger.logError("parameter line has too many values (" + splittedLine.length
							+ " given, max. 4 are specified)");
					return null;
				}

				verticesCountFromFile = Integer.parseInt(splittedLine[0]);
				edgesCountFromFile = Integer.parseInt(splittedLine[1]);

				if (splittedLine.length == 3 && splittedLine[2].equals("100"))
					graph.setGraphType(GraphType.WITH_SELFLOOPS);
				else if (splittedLine.length == 4) {
					if (splittedLine[2] == "100")
						graph.setGraphType(GraphType.WITH_SELFLOOPS);
					// fourth parameter is the weight... will be handled later
				}

			} else {
				logger.logError("first line was empty");
				return null;
			}

			// read vertices
			int currentVerticeName = -1;
			int lines = 1; // first line already read
			for (int i = 1; i <= verticesCountFromFile; i++) {
				line = reader.readLine();
				lines++;

				// skip comment line (dont raise anything but the lines count)
				if (line != null && line.startsWith("%")) {
					i--;
					continue;
				}

				if (line == null && i < verticesCountFromFile) {
					logger.logError("(at line: " + (i + 1) + ") corrupted file... there should be more lines");
					return null;
				} else if (line == null && i > verticesCountFromFile) {
					logger.logError("(at line: " + (i + 1) + ") corrupted file... there should be less lines");
					return null;
				}
				currentVerticeName = i;
				graph.addVertice(currentVerticeName);

				// dont try to split line when its empty
				if (line != null && !line.isEmpty()) {
					splittedLine = line.trim().split(" +");
					for (String neighbourVerticeNameString : splittedLine) {
						graph.addEdge(currentVerticeName, Integer.parseInt(neighbourVerticeNameString));
					}
				}
			}

			// reading evaluation
			if (graph.getVerticesCount() > 0) {
				String readingEval = "reading evaluation: " + graph.getVerticesCount()
						+ " vertices in graph (vertice count in file: " + verticesCountFromFile + "); "
						+ graph.getEdgesCount() + " edges in graph (edge count in file: " + edgesCountFromFile + "); "
						+ "GraphType: " + graph.getGraphType().toString() + "; lines read: " + lines;
				logger.log(readingEval);

			} else {
				logger.logError("no vertices been read");
			}
		}

		return graph;
	}

}
