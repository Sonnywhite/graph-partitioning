package structs;

public class AdjacencyListEdgeElement {

	private int verticeID = -1;
	private AdjacencyListEdgeElement next = null;

	public AdjacencyListEdgeElement(int verticeID) {
		this.setVerticeID(verticeID);
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
		return verticeID;
	}

	/**
	 * @param verticeID
	 *            the verticeID to set
	 */
	public void setVerticeID(int verticeID) {
		this.verticeID = verticeID;
	}

}
