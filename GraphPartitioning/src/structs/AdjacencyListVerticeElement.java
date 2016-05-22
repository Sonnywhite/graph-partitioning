package structs;

import java.util.ArrayList;
import java.util.List;

import interfaces.Vertice;

public class AdjacencyListVerticeElement implements Vertice {

	private int verticeID = -1;
	private int verticeDegree = -1;
	private int partitionAssignment = -1;

	private AdjacencyListVerticeElement next = null;
	private AdjacencyListVerticeElement pre = null;

	private AdjacencyListEdgeElement rootEdge = null;
	private AdjacencyListEdgeElement tailEdge = null;

	public AdjacencyListVerticeElement(int verticeID) {
		this.setVerticeID(verticeID);
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

	/**
	 * @return the next
	 */
	public AdjacencyListVerticeElement getNext() {
		return next;
	}

	/**
	 * @param next
	 *            the next to set
	 */
	public void setNext(AdjacencyListVerticeElement next) {
		this.next = next;
	}

	/**
	 * @return the rootEdge
	 */
	public AdjacencyListEdgeElement getRootEdge() {
		return rootEdge;
	}

	/**
	 * @param rootEdge
	 *            the rootEdge to set
	 */
	public void setRootEdge(AdjacencyListEdgeElement rootEdge) {
		this.rootEdge = rootEdge;
	}

	/**
	 * @return the tailEdge
	 */
	public AdjacencyListEdgeElement getTailEdge() {
		return tailEdge;
	}

	/**
	 * @param tailEdge
	 *            the tailEdge to set
	 */
	public void setTailEdge(AdjacencyListEdgeElement tailEdge) {
		this.tailEdge = tailEdge;
	}

	public boolean addEdge(int verticeID) {

		// TODO test if edge is already added

		AdjacencyListEdgeElement newEdge = new AdjacencyListEdgeElement(verticeID);

		if (rootEdge == null) {
			rootEdge = newEdge;
			tailEdge = newEdge;
		} else {
			tailEdge.setNext(newEdge);
			tailEdge = newEdge;
		}

		setVerticeDegree(getVerticeDegree() + 1);
		return true;

	}

	/**
	 * @return the pre
	 */
	public AdjacencyListVerticeElement getPre() {
		return pre;
	}

	/**
	 * @param pre
	 *            the pre to set
	 */
	public void setPre(AdjacencyListVerticeElement pre) {
		this.pre = pre;
	}

	/**
	 * @return the verticeDegree
	 */
	public int getVerticeDegree() {
		return verticeDegree;
	}

	/**
	 * @param verticeDegree
	 *            the verticeDegree to set
	 */
	public void setVerticeDegree(int verticeDegree) {
		this.verticeDegree = verticeDegree;
	}

	/**
	 * @return the partitionAssignment
	 */
	@Override
	public int getPartitionAssignment() {
		return partitionAssignment;
	}

	/**
	 * @param partitionAssignment
	 *            the partitionAssignment to set
	 */
	public void setPartitionAssignment(int partitionAssignment) {
		this.partitionAssignment = partitionAssignment;
	}

	@Override
	public List<Integer> getNeighbourIDs() {

		List<Integer> neighbourIDs = new ArrayList<>();

		AdjacencyListEdgeElement currentEdge = rootEdge;
		while (currentEdge != null) {
			neighbourIDs.add(currentEdge.getVerticeID());
			currentEdge = currentEdge.getNext();
		}

		return neighbourIDs;
	}

}
