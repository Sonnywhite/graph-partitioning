package structs;

import main.GraphType;

public class AbstractGraph {
	
	protected int edgesCount = 0;
	protected int verticesCount = 0;
	private GraphType graphType = GraphType.SIMPLE;
	
	public int getEdgesCount() {
		return getGraphType().equals(GraphType.SIMPLE)?edgesCount/2:edgesCount;
	}

	public int getVerticesCount() {
		return verticesCount;
	}
	
	public GraphType getGraphType() {
		return graphType;
	}


	public void setGraphType(GraphType graphType) {
		this.graphType = graphType;
	}

}
