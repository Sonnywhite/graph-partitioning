package structs;

import interfaces.Edge;
import interfaces.Vertice;

public class AdjacencyListEdgeElement implements Edge {

	private Vertice vertice = null;
	private AdjacencyListEdgeElement next = null;
	private boolean cutEdge = false;

	public AdjacencyListEdgeElement(Vertice vertice) {
		this.vertice=vertice;
	}

	/**
	 * @return the next
	 */
	public AdjacencyListEdgeElement getNext() {
		return next;
	}

	/**
	 * @param next
	 *            the next to set
	 */
	public void setNext(AdjacencyListEdgeElement next) {
		this.next = next;
	}

	/**
	 * @return the verticeID
	 */
	public int getVerticeID() {
		return vertice.getVerticeID();
	}

	public void setIsCutEdge(boolean val) {
		this.cutEdge=val;
	}
	
	@Override
	public boolean isCutEdge() {
		return cutEdge;
	}
	
	public Vertice getVertice() {
		return vertice;
	}

}
