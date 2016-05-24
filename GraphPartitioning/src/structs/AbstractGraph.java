package structs;

import io.ConsoleLogger;
import main.GraphType;

public class AbstractGraph {
	
	protected ConsoleLogger logger;
	private GraphType graphType = GraphType.SIMPLE;
	
	public GraphType getGraphType() {
		return graphType;
	}

	public void setGraphType(GraphType graphType) {
		this.graphType = graphType;
	}

}
