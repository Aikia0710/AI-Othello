package application;

import java.util.Random;

/**
 * this class is used to define the simple functions for player
 * 
 * @author Zibo Wang
 * @version 10.09.2019
 *
 */
public class Player {
	private PieceType pieceType;// black or white piece
	private PlayerType playerType;// the player type
	private Game game;// the game class
	private AI ai;// the AI algorithms
	private int wins;// the winning times

	public Player(PieceType pieceType, Game game) {
		// TODO Auto-generated constructor stub
		this.pieceType = pieceType;
		this.game = game;
		ai = new AI(game, pieceType);
	}

	/**
	 * player step
	 * 
	 * @return
	 */
	public boolean play() {
		if (playerType == PlayerType.Human) {
			return false;
		}
		Position position = ai.getPosition();// AI piece display turn
		play(position.getR(), position.getC());
		return true;

	}

	public void play(int r, int c) {
		game.setPiece(game.getBoard(), r, c, pieceType);
	}

	public PieceType getPieceType() {
		return pieceType;
	}

	public void setPieceType(PieceType pieceType) {
		this.pieceType = pieceType;
	}

	public PlayerType getPlayerType() {
		return playerType;
	}

	public void setPlayerType(PlayerType playerType) {
		this.playerType = playerType;
		if (playerType == PlayerType.AImcts) {
			ai.setIsmcts(true);
		}
		if (playerType == PlayerType.AImc) {
			ai.setIsmc(true);
		} else {
			if (playerType == PlayerType.AIab) {
				ai.setIsmc(false);
			}
		}
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String inf() {
		return String.format("%s %s", pieceType, playerType);
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}
}
