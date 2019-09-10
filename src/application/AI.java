package application;

import java.util.ArrayList;
import java.util.Random;
/**
 * this class contains two of the implemented AI algorithms
 * one is the Minimax with Alphabeta pruning algorithm
 * another one is the MOnte Carlo Tree Search algorithm 
 * @author Zibo Wang
 * @version 10.09.2019
 *
 */
public class AI {
	private Game game;
	private PieceType pieceType;
	private boolean ismc=false;//set the default value of the random player as false 
	private boolean ismcts=false;// set the default value of the MCTS as false
	private int level=5;//the search depth level of the alpha-beta AI algorithm
	private Position bestPosition;
	
	//create a Node object as the root node of the MCTS
	private Node root = null;
	
	public AI(Game game,PieceType pieceType) {
		// TODO Auto-generated constructor stub
		this.game=game;
		this.pieceType=pieceType;
	}
	
	/**
	 * this function is used to recall the position class to get the coordinations of the best position
	 * @return
	 */
	public Position getPosition() {
		bestPosition=null;
		if (ismcts) {
			mctsc();
		}else {
			alphaBeta(game.getBoard(), level, -Integer.MAX_VALUE, Integer.MAX_VALUE);
		}
		return bestPosition;
	}
	
	private void mctsc() {
		root=new Node(copyBoard(game.getBoard()),null,null);
		for (int i = 0; i < 100; i++) {
			Node node0 = select(root);
			
			Node node=expand(node0);
			boolean win=false;
			if (node!=null) {
				win=sim(node);
				backpropagation(node,win);
			}else {
				win=iswin(node0.getBoard());
				backpropagation(node0,win);
			}
		}
		ArrayList<Node> nodes=root.getNodes();
		Node best=nodes.get(0);
		for (Node node : nodes) {
			if (node.calculateUct(root.getN())>best.calculateUct(root.getN())) {
				best=node;
			}
		}
		bestPosition=best.getPosition();
	}
	/**
	 * this function is used to present the propagation process of the MCTS
	 * @param node
	 * @param win
	 */
	private void backpropagation(Node node,boolean win) {
		int w=0;
		if (win) {
			w=1;
		}
		Node tem=node;
		while (tem!=null) {
			tem.setW(tem.getW()+w);
			tem.setN(tem.getN()+1);
			
			tem=tem.getParent();
		}
		
	}
	/**
	 * the function is used to judge if the game wins or not with the boolean
	 * @param board
	 * @return
	 */
	private boolean iswin(PieceType[][]board) {
		int me = 0;
		int other = 0;
		for (int i = 0; i < Game.BOARDSIZE; i++) {
			for (int j = 0; j < Game.BOARDSIZE; j++) {
				PieceType tem=board[i][j];
				if (tem!=null) {
					if (tem==pieceType) {
						me++;
					} else {
						other++;

					}
				}
			}
		}
		return me>other;
		
	}
	/**
	 * the function is used to present the simulation process
	 * @param node
	 * @return
	 */
	private boolean sim(Node node) {
		PieceType[][] board=copyBoard(node.getBoard());
		PieceType pieceType=node.getPieceType();
		Random random=new Random();
		while (!isover(board)) {
			pieceType=opPieceType(pieceType, board);
			ArrayList<Position> positions=game.canMoves(board, pieceType);
			Position position=positions.get(random.nextInt(positions.size()));
			int r=position.getR();
			int c=position.getC();
			game.setPiece(board, r, c, pieceType);
		}
		return iswin(board);
	}
	/**
	 * to judge if the game over or not
	 * @param board
	 * @return
	 */
	private boolean isover(PieceType[][]board) {
		return game.hasLocations(board, PieceType.BLACK)==0&&game.hasLocations(board, PieceType.WHITE)==0;
	}
	/**
	 * the function is used to present the expansion process
	 * @param leaf
	 * @return
	 */
	private Node expand(Node leaf) {
		if (isover(leaf.getBoard())) {
			return null;
		}
		if (leaf.getN()==0) {
			return leaf;
		}
		PieceType pieceType=opPieceType(leaf.getPieceType(), copyBoard(leaf.getBoard()));
		ArrayList<Position> positions=game.canMoves(leaf.getBoard(), pieceType);
		ArrayList<Node> nodes=leaf.getNodes();
		for (Position position : positions) {
			PieceType[][] board=copyBoard(leaf.getBoard());
			int r=position.getR();
			int c=position.getC();
			game.setPiece(board, r, c, pieceType);
			Node node=new Node(board, position, pieceType);
			node.setParent(leaf);
			nodes.add(node);
		}
		Random random=new Random();
		return nodes.get(random.nextInt(nodes.size()));
	}
	/**
	 * the function is to judge the opponents piece colou in each step
	 * @param pieceType
	 * @param board
	 * @return
	 */
	private PieceType opPieceType(PieceType pieceType,PieceType[][]board) {
		if (pieceType==null) {
			return this.pieceType;
		}
		if (pieceType==PieceType.BLACK) {
			if (game.hasLocations(board, PieceType.WHITE)==0) {
				return pieceType;
			}
			return PieceType.WHITE;
		}else {
			if (game.hasLocations(board, PieceType.BLACK)==0) {
				return pieceType;
			}
			return PieceType.BLACK;
		}
	}
	/**
	 * this function is used to present the node selection process
	 * @param root
	 * @return
	 */
	private Node select(Node root) {
		if (root.isLeaf()) {
			//System.out.println();
			return root;
		}
		Node max=root.getNodes().get(0);
		for (Node node:root.getNodes()) {
			//System.out.println(root.getN());
			if (node.calculateUct(root.getN())>max.calculateUct(root.getN())) {
				max=node;
			}
		}
		//System.out.println(root.getNodes().indexOf(max));
		return select(max);
		
	}
	
	/**
	 * the AI with alpha-beta search algorithm
	 * @param board
	 * @param depth
	 * @param alpha
	 * @param beta
	 * @return
	 */
	private int alphaBeta(PieceType [][]board,int depth,int alpha,int beta) {
		if (depth==0) {
			if (level%2==1) {
				return -game.getScore(board, pieceType);
			}
			return game.getScore(board, pieceType);
		}
		ArrayList<Position> positions=game.canMoves(board, pieceType);
		if (positions.isEmpty()) {
			
			int dif=Math.abs(depth-level);
			if (dif%2==1) {
				return -game.getScore(board, pieceType);
			}
			return game.getScore(board, pieceType);
			
		}
		while (!positions.isEmpty()) {
			Position position=selectPosition(positions);
			PieceType[][]copy=copyBoard(board);
			putPiece(copy, depth, position);
			int val=-alphaBeta(copy, depth-1, -beta, -alpha);
			if (val>=beta) {
				return beta;
			}
			if (val>alpha) {
				alpha=val;
				if (depth==level) {
					bestPosition=position;
				}
			}
			
		}
		return alpha;
	}
	/**
	 * this function is used to help AI player to define the displayed piece type
	 * @param board
	 * @param depth
	 * @param position
	 */
	private void putPiece(PieceType[][]board,int depth,Position position) {
		PieceType pieceType;
		if (Math.abs(level-depth)%2==0) {
			pieceType = this.pieceType;
		}else {
			if (this.pieceType == PieceType.WHITE) {
				pieceType = PieceType.BLACK;
			} else {
				pieceType = PieceType.WHITE;
			}
		}
		int r = position.getR();//the integer row value
		int c = position.getC();//the integer column value
		game.setPiece(board, r, c, pieceType);
	}
	/**
	 * the function is used to copy the board 
	 * @param board
	 * @return
	 */
	private PieceType[][] copyBoard(PieceType[][]board) {
		
		PieceType[][]copy=new PieceType[Game.BOARDSIZE][Game.BOARDSIZE];
		for (int i = 0; i < Game.BOARDSIZE; i++) {
			for (int j = 0; j < Game.BOARDSIZE; j++) {
				copy[i][j]=board[i][j];
			}
		}
		return copy;
		
	}
	/**
	 * here is the Monte-Carlo method section, which use the monte carlo function in the Random method
	 * @param positions
	 * @return
	 */
	private Position selectPosition(ArrayList<Position> positions) {
		if (ismc) {//���ѡ��λ��
			Random random=new Random();
			return positions.remove(random.nextInt(positions.size()));
		}else {//��˳��ѡ��λ��
			return positions.remove(0);
		}
	}

	public boolean isIsmc() {
		return ismc;
	}

	public void setIsmc(boolean ismc) {
		
		ismcts=false;
		this.ismc = ismc;
	}

	public boolean isIsmcts() {
		return ismcts;
	}

	public void setIsmcts(boolean ismcts) {
		
		this.ismcts = ismcts;
	}
	
	
	
	

}
