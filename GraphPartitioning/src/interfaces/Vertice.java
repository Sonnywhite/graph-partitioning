package interfaces;

import java.util.List;

public interface Vertice {
	
	public int getVerticeDegree();
	public int getVerticeID();
	public int getPartitionAssignment();
	public void setPartitionAssignment(int i);
	public List<Integer> getNeighbourIDs();
	public List<Vertice> getNeighbours();
	public boolean addEdge(Vertice vertice);

}
