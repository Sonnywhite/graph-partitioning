package interfaces;

import java.util.List;

import main.GraphType;

public interface Graph {
	
	public void addVertice(Integer verticeName);
	public boolean addEdge(Integer verticeName1, Integer verticeName2);
	public List<Vertice> getAllVertices();
	public GraphType getGraphType();
	public void setGraphType(GraphType graphType);
	public int getVerticesCount();
	public int getEdgesCount();
	public int getCutEdgesCount();

}
