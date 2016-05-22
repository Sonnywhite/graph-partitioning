package main;

import java.io.FileInputStream;
import java.io.InputStream;

import heuristics.StartHeuristic1;
import heuristics.StartHeuristic2;
import io.GraphFormatReader;
import io.PartitionWriter;
import structs.AdjacencyListGraph;

public class Main {

	public static int K = 13;

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();
		System.out.println("INFO: Starting Graph Partitioning...");
		
		// try read graph
		try {
			
			// args evaluation
			String testgraph1 = "/tinytest.graph";
			String testgraph2 = "/test.graph";
			//System.out.println("DEBUG: "+Main.class.getResource(testgraph2));
			//String filepath = Paths.get(Main.class.getResource(testgraph2).toURI()).toString();
			String filepath = testgraph2;
			InputStream inputStream = null;
			String outputfilepath = "output";
			if(args.length==0) {
				System.out.println("WARN: no input file specified, using test file at: "+filepath);
				inputStream = Main.class.getResourceAsStream(filepath);
				System.out.println("WARN: no K specified, using k="+K);
				System.out.println("WARN: no output file path specified, using: "+outputfilepath);
			}
			if(args.length==1) {
				filepath = args[0];
				System.out.println("INFO: try to read file at "+filepath);
				inputStream=new FileInputStream(filepath);
				System.out.println("WARN: no K specified, using k="+K);
				System.out.println("WARN: no output file path specified, using: "+outputfilepath);
			}
			if(args.length==2) {
				filepath = args[0];
				System.out.println("INFO: try to read file at "+filepath);
				inputStream=new FileInputStream(filepath);
				K = Integer.parseInt(args[1]);
				System.out.println("INFO: using k="+K);		
				System.out.println("WARN: no output file path specified, using: "+outputfilepath);
			}
			if(args.length==3) {
				filepath = args[0];
				System.out.println("INFO: try to read file at "+filepath);
				inputStream=new FileInputStream(filepath);
				K = Integer.parseInt(args[1]);
				System.out.println("INFO: using k="+K);
				outputfilepath = args[2];
				System.out.println("INFO: try to write result to "+outputfilepath);
			}
			
			
			AdjacencyListGraph graph = new AdjacencyListGraph();
			GraphFormatReader reader = new GraphFormatReader();
			graph = (AdjacencyListGraph) reader.read(inputStream, graph);
			
			// test if k is greater then vertice count
			if(K>graph.getVerticesCount())
			{
				System.err.println("ERROR: the choosen K ("+K+") is bigger then the vertice count, in that case graph partitioning wont work");
				System.out.println("INFO: finished after "+(double)(System.currentTimeMillis()-startTime)/1000+"s.");
				return;
			}
			
			// partitioning
			Partitioner partitioner = new Partitioner();
			partitioner.partGraph(new StartHeuristic2(), graph, K);
			
			// solving
			Solver solver = new Solver();
			solver.solve(graph, K);
			
			// write result
			PartitionWriter writer = new PartitionWriter();
			writer.write(graph,outputfilepath);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("INFO: finished after "+(double)(System.currentTimeMillis()-startTime)/1000+"s.");
		
	}

}
