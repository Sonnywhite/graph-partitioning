package structs;

import java.util.ArrayList;
import java.util.List;

import interfaces.Graph;
import interfaces.Vertice;

public class AdjacencyListGraph extends AbstractGraph implements Graph {

	private AdjacencyListVerticeElement rootVertice = null;
	private AdjacencyListVerticeElement tailVertice = null;

	@Override
	public boolean addVertice(int verticeID) {

		// TODO test if vertice already added

		AdjacencyListVerticeElement newVertice = new AdjacencyListVerticeElement(verticeID);

		if (rootVertice == null) {
			rootVertice = newVertice;
			tailVertice = rootVertice;
		} else {
			tailVertice.setNext(newVertice);
			newVertice.setPre(tailVertice);
			tailVertice = newVertice;
		}

		verticesCount++;
		return true;
	}

	@Override
	public boolean addEdge(int verticeID1, int verticeID2) {

		// traverse vertices with a short trick: since we run from line 1 to n
		// through the file, with 1 to n being the vertices we can speed the
		// search up a lot with starting at the tail of our list and traverse to
		// the front

		AdjacencyListVerticeElement currVertice = tailVertice;

		// == null means we have traversed whole list
		while (currVertice != null) { 
			if (currVertice.getVerticeID()==verticeID1) {
				if (currVertice.addEdge(verticeID2)) {
					edgesCount++;
					return true;
				} else {
					System.out.println("ERROR: edge already added");
					return false;
				}
			}
			currVertice = currVertice.getPre();
		}

		return false;
	}

	@Override
	public List<Vertice> getAllVertices() {
		List<Vertice> allVertices = new ArrayList<>();
		AdjacencyListVerticeElement currElem = rootVertice;
		while(currElem!=null) {
			allVertices.add(currElem);
			currElem = currElem.getNext();
		}
		return allVertices;
	}

}