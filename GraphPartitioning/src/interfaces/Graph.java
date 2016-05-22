package interfaces;

import java.util.List;

import main.GraphType;

public interface Graph {
	
	public boolean addVertice(int verticeName);
	public boolean addEdge(int verticeName1, int verticeName2);
	public List<Vertice> getAllVertices();
	public GraphType getGraphType();
	public void setGraphType(GraphType graphType);
	public int getVerticesCount();
	public int getEdgesCount();

}
