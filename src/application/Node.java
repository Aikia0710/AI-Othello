package application;

import java.util.ArrayList;

/**
 * this class is used to create the node tree of the MCTS algorithm
 * 
 * @author Zibo WAng
 * @version 10.09.2019
 *
 */
public class Node {

	private int w;
	private int n;
	private ArrayList<Node> nodes;
	private PieceType[][] board;
	private Position position;
	private PieceType pieceType;
	private Node parent = null;

	public Node(PieceType[][] board, Position position, PieceType pieceType) {
		// TODO Auto-generated constructor stub
		this.board = board;
		this.pieceType = pieceType;
		this.position = position;
		w = 0;
		n = 0;
		nodes = new ArrayList<>();
	}

	public boolean isLeaf() {
		return nodes.isEmpty();

	}

	public double calculateUct(int total) {
		if (n == 0) {
			return Math.random() * 10 + 10;
		}

		return w * 1.0 / n + 0.5 * Math.sqrt(Math.log(total) / n);

	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	public PieceType[][] getBoard() {
		return board;
	}

	public void setBoard(PieceType[][] board) {
		this.board = board;
	}

	public PieceType getPieceType() {
		return pieceType;
	}

	public void setPieceType(PieceType pieceType) {
		this.pieceType = pieceType;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

}
