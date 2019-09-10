package application;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.Pane;

/**
 * this class extends the pane, which help to create the game board
 * 
 * @author Zibo Wang
 * @version 10.09.2019
 *
 */
public class PieceIcon extends Pane {
	private int r;// row of the game board
	private int c;// column of the game board

	// set the board colour of the game board
	public PieceIcon(int r, int c) {
		// TODO Auto-generated constructor stub
		setStyle("-fx-border-color: black; -fx-background-color:#E3CF57;");
		this.r = r;
		this.c = c;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

}
