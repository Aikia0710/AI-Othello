package application;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ReversiBoard extends Pane {

	// B means black piece, W means white piece
	public char piece = ' ';
	public int playerStep; // it is used to count the piece steps
	private Alert gameWinner = new Alert(AlertType.INFORMATION);
	private MyPiece circle = null;
	public static boolean[][] filled = new boolean[8][8];

	public ReversiBoard() {

	}

	public ReversiBoard(int x, int y) {
		setStyle("-fx-border-color:black");
		this.setPrefSize(65, 65);
		this.setOnMouseClicked(e -> {
			handleMouseClick(x, y);
		});
	}

	public void handleMouseClick(int x, int y) {
		if (isValidPosition(x, y) == false) {
			System.out.println("this is not a valid position");
		} else {
			setPiece(Main.whoseTurn, x, y, true);
		}
		//setPiece(Main.whoseTurn, x, y, true);
	}

	public void setPiece(char c, int x, int y, boolean flag) {
		piece = c;
		MyPiece circle = null;
		int row = x + 1;
		int column = y + 1;
		Main.step++;
		Main.RevBoard[x][y].playerStep = Main.step;
		if (piece == ' ') {
			circle = new MyPiece(25, Color.BLACK);
			// output the accurate piece positions in the text area
			if (flag == true)
				Main.textArea.insertText(0,
						"step: " + Main.step + " black piece: column" + column + ", row" + row + '\n' + '\n');
		} else if (piece == 'W') {
			circle = new MyPiece(25, Color.WHITE);
			if (flag == true)
				Main.textArea.insertText(0,
						"step: " + Main.step + " white piece: column" + column + ", row" + row + '\n' + '\n');
		}
		// bind the piece at the centre of each block
		circle.centerXProperty().bind(Main.RevBoard[x][y].widthProperty().divide(2));
		circle.centerYProperty().bind(Main.RevBoard[x][y].heightProperty().divide(2));
		Main.RevBoard[x][y].getChildren().add(circle);
		Main.RevBoard[x][y].piece = c;
		Main.arrayCircle[x][y] = circle;
	}

	// the method is used to judge the winner of one battle
	public boolean judge(char whoseTurn, int x, int y) {
		return false;

	}

	public boolean isValidPosition(int x, int y) {
		if (Main.arrayCircle[x][y].getFill() == Color.TRANSPARENT)
			return true;
		if (Main.arrayCircle[x][y].getFill() == Color.BLACK || Main.arrayCircle[x][y].getFill() == Color.WHITE) {
			return false;
		}
		// the player with the black piece
		if (piece == 'B') {
			for (int i = 0; i < 8; i++) {
				//to judge the piece in the vertical direction
				if (Main.arrayCircle[i][y].getFill() == Color.BLACK) {
					if ((i - x) >= 2) {
						int count = 0;
						for (int k = x; k < i; k++) {
							if (Main.arrayCircle[i][y].getFill() == Color.WHITE) {
								count++;
							}
						}
						if (count == (i - x - 1)) {//if the piece between two black pieces are in white colour
							return true;
						}
					}
					if ((x - i) >= 2) {
						int count = 0;
						for (int k = x; k > i; k--) {
							if (Main.arrayCircle[i][y].getFill() == Color.WHITE) {
								count++;
							}
						}
						if (count == (x - i - 1))
							return true;
					}
				}

		}
//			// 水平方向判断
//			for (int j = 0; j < 4; j++) {
//				if (button[x][j].getBackground() == Color.BLACK) {
//					if ((j - y) >= 2) {
//						int count = 0;
//						for (int k = y; k < j; k++) {
//							if (button[x][k].getBackground() == Color.WHITE)
//								count++;
//						}
//						if (count == (j - y - 1))
//							return true;
//					}
//					if ((y - j) >= 2) {
//						int count = 0;
//						for (int k = y; k > j; k--) {
//							if (button[x][k].getBackground() == Color.white)
//								count++;
//						}
//						if (count == (y - j - 1))
//							return true;
//					}
//				}
//			}
	}
		return false;
	}

	public boolean highlightValidPositionDot() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (isValidPosition(i, j))
					return true;
			}
		}
		return false;
	}

	public void printWinner() {

		if (Main.whoseTurn == 'B') {
			Main.whoseTurn = ' ';
			Main.textArea.insertText(0, "Black piece wins, press 'Start' button to start a new game!" + '\n' + '\n');
			gameWinner.setContentText("Balck piece wins");
			gameWinner.setHeaderText(null);
			gameWinner.showAndWait();
		} else if (Main.whoseTurn == 'W') {
			Main.whoseTurn = ' ';
			Main.textArea.insertText(0, "White piece wins, press 'Start' button to start a new game!" + '\n' + '\n');
			gameWinner.setContentText("White piece wins！！！");
			gameWinner.setHeaderText(null);
			gameWinner.showAndWait();
		}
	}

}
