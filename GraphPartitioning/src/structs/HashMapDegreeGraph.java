package structs;

import java.util.List;

import interfaces.Graph;
import interfaces.Vertice;

public class HashMapDegreeGraph extends AbstractGraph implements Graph {

	public HashMapDegreeGraph() {
		super(HashMapDegreeGraph.class.getSimpleName());
		
	}

	@Override
	public void addVertice(Integer verticeName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addEdge(Integer verticeName1, Integer verticeName2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Vertice> getAllVertices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getVerticesCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEdgesCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCutEdgesCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
