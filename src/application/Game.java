package application;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.Consumer;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * 
 * this class is used to contain these game logic functions
 * 
 * @author Zibo Wang
 * @version 10.09.2019
 */
public class Game {
	public static int BOARDSIZE = 8;
	private PieceType board[][];
	private Consumer<Object> displayPiece;// display piece event
	private Player[] players;
	private Player currentPlayer;
	private Label blackInf;// create as an labelshow the black side information
	private Label whiteInf;// create as an label in javaFx to show the white side information
	private Label gameInf;// create as an label in javaFX to show the game information
	private Circle circlePiece;// the piece sign at the moment

	public Game() {
		// TODO Auto-generated constructor stub
		initBoard();
		initPlayers();
	}

	/**
	 * AI player battle mode the alpha-beta player take white piece the MCTS take
	 * black piece
	 */
	public void setAbMcts() {
		players[0].setPlayerType(PlayerType.AIab);
		players[1].setPlayerType(PlayerType.AImcts);
		players[0].setWins(0);
		players[1].setWins(0);
	}

	/**
	 * AI player battle mode the alpha-beta player take white piece the random
	 * player take black piece
	 */
	public void setAbMc() {
		players[0].setPlayerType(PlayerType.AIab);
		players[1].setPlayerType(PlayerType.AImc);
		players[0].setWins(0);
		players[1].setWins(0);
	}

	/**
	 * Human player and AI player battle mode the alpha-beta player take white piece
	 * the human player take black piece
	 */
	public void setHumanAb() {
		players[0].setPlayerType(PlayerType.Human);
		players[1].setPlayerType(PlayerType.AIab);
		players[0].setWins(0);
		players[1].setWins(0);
	}

	/**
	 * Human player and random player battle mode the random player take white piece
	 * the human player take black piece
	 */
	public void setHumanMc() {
		players[0].setPlayerType(PlayerType.Human);
		players[1].setPlayerType(PlayerType.AImc);
		players[0].setWins(0);
		players[1].setWins(0);

	}

	/**
	 * Human player and MCTS player battle mode the MCTS player take white piece the
	 * human player take black piece
	 */
	public void setHumanMcts() {
		players[0].setPlayerType(PlayerType.Human);
		players[1].setPlayerType(PlayerType.AImcts);
		players[0].setWins(0);
		players[1].setWins(0);
	}

	/**
	 * the method is used to initiate the player types
	 */
	private void initPlayers() {
		players = new Player[] { new Player(PieceType.BLACK, this), new Player(PieceType.WHITE, this) };
		currentPlayer = players[0];
		setHumanAb();

	}

	/**
	 * set the piece and reverse the piece
	 * 
	 * @param board
	 * @param r
	 * @param c
	 * @param pieceType
	 */
	public void setPiece(PieceType[][] board, int r, int c, PieceType pieceType) {
		int[][] dirs = { { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 } };
		PieceType opType = (pieceType == PieceType.BLACK ? PieceType.WHITE : PieceType.BLACK);
		for (int i = 0; i < dirs.length; i++) {
			for (int j = 1; j < BOARDSIZE; j++) {
				int newr = dirs[i][0] * j + r;
				int newc = dirs[i][1] * j + c;
				if (validateRC(newr, newc)) {
					if (board[newr][newc] == opType) {
						continue;
					} else {
						if (board[newr][newc] == pieceType && j > 1) {
							for (int k = 1; k < j; k++) {// reverse the piece
								newr = dirs[i][0] * k + r;
								newc = dirs[i][1] * k + c;
								board[newr][newc] = pieceType;
							}
							break;
						} else {
							break;
						}
					}
				} else {
					break;
				}
			}
		}
		board[r][c] = pieceType;
	}

	/**
	 * the method is used to change the player turn
	 */
	private void nextPlayer() {
		if (currentPlayer == players[0]) {
			currentPlayer = players[1];
		} else {
			currentPlayer = players[0];
		}
		if (currentPlayer.getPieceType() == PieceType.BLACK) {
			circlePiece.setFill(Color.BLACK);
		} else {
			circlePiece.setFill(Color.WHITE);
		}
	}

	/**
	 * the method is used to start the game by using the multithreads
	 */
	public void start() {
		initBoard();
		currentPlayer = players[0];
		display();
		playThread();
		isover();
		if (currentPlayer.getPieceType() == PieceType.BLACK) {
			circlePiece.setFill(Color.BLACK);
		} else {
			circlePiece.setFill(Color.WHITE);
		}
		gameInf.setText("");
	}

	/**
	 * adding the multi-threads into the game
	 */
	private void playThread() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				play();
			}
		});
		thread.start();
	}

	/**
	 * the function using the run method to use the threads
	 */
	private void play() {
		if (!currentPlayer.play()) {
			return;
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				display();

			}
		});

		if (isover()) {
			return;
		}

		nextPlayer();
		if (hasLocations(board, currentPlayer.getPieceType()) == 0) {
			nextPlayer();
		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		play();
	}

	/**
	 * the function is used to initiate the board at the beginning of the game
	 */
	private void initBoard() {
		board = new PieceType[BOARDSIZE][BOARDSIZE];
		board[3][3] = PieceType.BLACK;
		board[4][4] = PieceType.BLACK;
		board[4][3] = PieceType.WHITE;
		board[3][4] = PieceType.WHITE;
	}

	private boolean validateRC(int r, int c) {
		return r >= 0 && r < BOARDSIZE && c >= 0 && c < BOARDSIZE;
	}

	/**
	 * the method is used to get the valid piece display location, which will enable
	 * human player to place the piece on the proper location
	 * 
	 * @param board
	 * @param pieceType
	 * @return
	 */
	public ArrayList<Position> canMoves(PieceType[][] board, PieceType pieceType) {
		ArrayList<Position> arrayList = new ArrayList<>();
		for (int i = 0; i < BOARDSIZE; i++) {
			for (int j = 0; j < BOARDSIZE; j++) {
				if (validateLocation(i, j, pieceType, board)) {
					arrayList.add(new Position(i, j));
				}
			}
		}

		return arrayList;

	}

	/**
	 * the function is used to show the coordiantion of the validate location
	 * 
	 * @param board
	 * @param pieceType
	 * @return
	 */
	public int hasLocations(PieceType[][] board, PieceType pieceType) {
		int count = 0;
		for (int i = 0; i < BOARDSIZE; i++) {
			for (int j = 0; j < BOARDSIZE; j++) {
				if (validateLocation(i, j, pieceType, board)) {
					count++;
					break;
				}
			}
		}
		return count;

	}

	/**
	 * the function is used to prove if the position(r,c) can display piece
	 * 
	 * @param r
	 * @param c
	 * @param pieceType
	 * @param board
	 * @return
	 */
	private boolean validateLocation(int r, int c, PieceType pieceType, PieceType[][] board) {
		if (board[r][c] != null) {
			return false;
		}
		int[][] dirs = { { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 } };
		PieceType opType = (pieceType == PieceType.BLACK ? PieceType.WHITE : PieceType.BLACK);
		for (int i = 0; i < dirs.length; i++) {
			for (int j = 1; j < BOARDSIZE; j++) {
				int newr = dirs[i][0] * j + r;
				int newc = dirs[i][1] * j + c;
				if (validateRC(newr, newc)) {
					if (board[newr][newc] == opType) {
						continue;
					} else {
						if (board[newr][newc] == pieceType && j > 1) {
							return true;
						} else {
							break;
						}
					}
				} else {
					break;
				}
			}
		}
		return false;

	}

	/**
	 * the method is used to get player's piece count number
	 * 
	 * @param board
	 * @param pieceType
	 * @return
	 */
	public int getScore(PieceType[][] board, PieceType pieceType) {
		int score = 0;
		for (int i = 0; i < BOARDSIZE; i++) {
			for (int j = 0; j < BOARDSIZE; j++) {
				if (board[i][j] == pieceType) {
					score++;
				}

			}
		}
		return score;
	}

	/**
	 * the function is used to get player's piece count number
	 * 
	 * @return
	 */
	private boolean isover() {

		int scores[] = new int[2];
		scores[0] = getScore(board, players[0].getPieceType());
		scores[1] = getScore(board, players[1].getPieceType());

		int canLocs[] = new int[2];
		canLocs[0] = hasLocations(board, players[0].getPieceType());
		canLocs[1] = hasLocations(board, players[1].getPieceType());

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				blackInf.setText(players[0].inf() + ":" + scores[0]);
				whiteInf.setText(players[1].inf() + ":" + scores[1]);

				if ((canLocs[0] == 0 && canLocs[1] == 0)) {
					String inf = "";

					if (scores[0] > scores[1]) {
						players[0].setWins(players[0].getWins() + 1);
						inf += players[0].inf() + " WIN!";
					} else if (scores[0] < scores[1]) {
						players[1].setWins(players[1].getWins() + 1);
						inf += players[1].inf() + " WIN!";
					} else {
						inf += " DRAW";
					}
					gameInf.setText(inf);
				}
			}
		});
		return canLocs[0] == 0 && canLocs[1] == 0;
	}

	/**
	 * human player to place the piece
	 * 
	 * @param r
	 * @param c
	 */
	public void humanPlay(int r, int c) {
		if (!validateLocation(r, c, currentPlayer.getPieceType(), board)) {
			return;
		}
		currentPlayer.play(r, c);
		display();
		if (isover()) {
			return;
		}

		nextPlayer();
		if (hasLocations(board, currentPlayer.getPieceType()) == 0) {
			nextPlayer();
		}

		playThread();
	}

	public void display() {

		if (displayPiece != null) {
			displayPiece.accept(board);
		}

	}

	public Consumer<Object> getDisplayPiece() {
		return displayPiece;
	}

	public void setDisplayPiece(Consumer<Object> displayPiece) {
		this.displayPiece = displayPiece;
	}

	public Label getBlackInf() {
		return blackInf;
	}

	public void setBlackInf(Label blackInf) {
		this.blackInf = blackInf;
	}

	public Label getWhiteInf() {
		return whiteInf;
	}

	public void setWhiteInf(Label whiteInf) {
		this.whiteInf = whiteInf;
	}

	public Label getGameInf() {
		return gameInf;
	}

	public void setGameInf(Label gameInf) {
		this.gameInf = gameInf;
	}

	public Circle getCirclePiece() {
		return circlePiece;
	}

	public void setCirclePiece(Circle circlePiece) {
		this.circlePiece = circlePiece;
	}

	public PieceType[][] getBoard() {
		return board;
	}

	public void setBoard(PieceType[][] board) {
		this.board = board;
	}

	public Player[] getPlayers() {
		return players;
	}
}
