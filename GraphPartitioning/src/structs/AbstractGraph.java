package structs;

import io.ConsoleLogger;
import main.GraphType;

public class AbstractGraph extends ConsoleLogger {
	
	public AbstractGraph(String className) {
		super(className);
	}

	private GraphType graphType = GraphType.SIMPLE;
	
	public GraphType getGraphType() {
		return graphType;
	}

	public void setGraphType(GraphType graphType) {
		this.graphType = graphType;
	}

}
