package structs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.Graph;
import interfaces.Vertice;
import main.GraphType;

public class HashMapGraph extends AbstractGraph implements Graph {

	static int CUT_EDGES_COUNT = 0;
	
	private Map<Integer, Vertice> verticeMap = new HashMap<>();

	public HashMapGraph() {
		super(HashMapGraph.class.getSimpleName());
		CUT_EDGES_COUNT = 0;
	}
	
	/**
	 * this function is not just fast but also secures,<br>
	 * that the vertices we are working on are already added to the graph
	 * 
	 * @param verticeId
	 * @return the vertice from graph
	 */
	private Vertice getOrAddVertice(int verticeId) {
		if (!verticeMap.containsKey(verticeId))
			verticeMap.put(verticeId, new AdjacencyListVerticeElement(verticeId));
		return verticeMap.get(verticeId);
	}

	@Override
	public void addVertice(Integer verticeId) {
		getOrAddVertice(verticeId);
		return;
	}

	@Override
	public boolean addEdge(Integer verticeID1, Integer verticeID2) {
		getOrAddVertice(verticeID1).addEdge(getOrAddVertice(verticeID2));
		return true;
	}

	@Override
	public List<Vertice> getAllVertices() {
		return new ArrayList<Vertice>(verticeMap.values());
	}

	@Override
	public int getVerticesCount() {
		return verticeMap.size();
	}

	@Override
	public int getEdgesCount() {
		int edgesCount = 0;
		for(Vertice vertice : getAllVertices())
			edgesCount +=vertice.getVerticeDegree();
		
		return getGraphType().equals(GraphType.SIMPLE)?edgesCount/2:edgesCount;
	}

	@Override
	public int getCutEdgesCount() {
		return CUT_EDGES_COUNT;
	}

}