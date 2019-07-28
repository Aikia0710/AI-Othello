package application;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * @author Zibo Wang this BoardController class mainly contains all implemented
 *         functions for the buttons
 */
public class BoardController {

	private Button btSet;
	private Button btStart;
	private Button btSurrender;
	private Button btExit;

	private Alert startConfirmWindow = new Alert(AlertType.CONFIRMATION,
			"The game has not ended yet, are you going to start a new game?");
	private Alert setConfirmWindow = new Alert(AlertType.INFORMATION);
	private Alert surrenderConfirmWindow = new Alert(AlertType.CONFIRMATION, "Are you going to surrender?");
	private Alert exitConfirmWindow = new Alert(AlertType.CONFIRMATION, "Are you going to exit the game?");
	private Alert winnerWindow = new Alert(AlertType.INFORMATION);

	Main method = new Main();

	public BoardController(Button btSet, Button btStart, Button btSurrender, Button btExit) {
		this.btSet = new Button("Setting");
		this.btStart = new Button("Start Game");
		this.btSurrender = new Button("Surrender");
		this.btExit = new Button("Finish Game");
	}

	public Button getBtSet() {
		return btSet;
	}

	public void setBtSet(Button btSet) {
		this.btSet = btSet;
	}

	public Button getBtStart() {
		return btStart;
	}

	public void setBtStart(Button btStart) {
		this.btStart = btStart;
	}

	public Button getBtSurrender() {
		return btSurrender;
	}

	public void setBtSurrender(Button btSurrender) {
		this.btSurrender = btSurrender;
	}

	public Button getBtExit() {
		return btExit;
	}

	public void setBtExit(Button btExit) {
		this.btExit = btExit;
	}

	/*
	 * this method defines the implemented function of the setting button
	 */
	public void settingButton(ActionEvent event) throws IOException {

		// game setting button function
		btSet.setOnAction(e -> {
			if (Main.whoseTurn == ' ') {
				Stage settingStage = new Stage();
				Pane settingPane = new Pane();

				// group1 is responsible to radio button 1 and 2
				ToggleGroup group1 = new ToggleGroup();
				// group2 is responsible to the option1 label
				ToggleGroup group2 = new ToggleGroup();
				ToggleGroup group3 = new ToggleGroup();

				Label option1 = new Label("Game Battle Mode");
				Label option2 = new Label("AI Battle Setting");
				Label option3 = new Label("Game Difficulty");
				option1.setStyle("-fx-font-size: 16px");
				option1.setLayoutX(30);
				option1.setLayoutY(15);
				option2.setStyle("-fx-font-size: 16px");
				option2.setLayoutX(30);
				option2.setLayoutY(100);
				option3.setStyle("-fx-font-size: 16px");
				option3.setLayoutX(30);
				option3.setLayoutY(190);

				// adding the radio buttons on the new stage, the following part is for the
				// "Game Battle Mode" setting
				// default selection is set for the AI Battle
				RadioButton radio1 = new RadioButton("AI Battle");
				RadioButton radio2 = new RadioButton("Players Battle");
				radio1.setLayoutX(50);
				radio1.setLayoutY(50);
				radio2.setLayoutX(200);
				radio2.setLayoutY(50);
				// adding the radio1 and radio2 buttons into the toggle group1
				radio1.setToggleGroup(group1);
				radio2.setToggleGroup(group1);
				radio1.setSelected(true);
				Line divLine1 = new Line(0, 80, 350, 80);
				divLine1.setStrokeWidth(2f);

				// adding the radio buttons on the new stage, the following part is for the "AI
				// Battle Mode" setting
				// default selection is set for the PC side takes the white chess piece
				RadioButton radio3 = new RadioButton("PC - White piece");
				RadioButton radio4 = new RadioButton("PC - Black piece");
				radio3.setLayoutX(50);
				radio3.setLayoutY(130);
				radio4.setLayoutX(200);
				radio4.setLayoutY(130);
				radio3.setToggleGroup(group2);
				radio4.setToggleGroup(group2);
				radio3.setSelected(true);
				Line divLine2 = new Line(0, 165, 350, 165);
				divLine2.setStrokeWidth(2f);

				// adding the radio buttons on the new stage, the following part is for the
				// "Game Difficulty" setting
				RadioButton radio5 = new RadioButton("Simple");
				RadioButton radio6 = new RadioButton("Regular");
				RadioButton radio7 = new RadioButton("Complex");
				radio5.setLayoutX(50);
				radio5.setLayoutY(220);
				radio6.setLayoutX(200);
				radio6.setLayoutY(220);
				radio7.setLayoutX(50);
				radio7.setLayoutY(250);
				radio5.setToggleGroup(group3);
				radio6.setSelected(true);
				radio6.setToggleGroup(group3);
				radio7.setToggleGroup(group3);
				Line divLine3 = new Line(0, 280, 350, 280);
				divLine3.setStrokeWidth(2f);

				// inserting an background image of the selection window
				ImageView bgImage = new ImageView();
				Image image1 = new Image(test.class
						.getResourceAsStream("/pictures/chessboard-perspective-background-vector_57911-3.jpg"));
				bgImage.setImage(image1);
				bgImage.setFitHeight(400);
				bgImage.setFitWidth(350);
				bgImage.setLayoutX(0);
				bgImage.setLayoutY(60);

				// adding two buttons at the bottom of the setting pane
				// close button is used to close the window, confirm button is used to confirm
				// all player's selection
				Button cancelBt = new Button("Cancel");
				Button confirmBt = new Button("Confirm");
				cancelBt.setLayoutX(90);
				cancelBt.setLayoutY(315);
				confirmBt.setLayoutX(190);
				confirmBt.setLayoutY(315);

				// defining function of the cancel button, which enable user to close the
				// setting board
				cancelBt.setOnAction(event2 -> {
					settingStage.close();
				});

				// defining function of the confirm button, clicking the button to change the
				// game parameters
				confirmBt.setOnAction(event3 -> {
					if (radio1.isSelected() == true) {
						Main.AIPlay = true;
						radio1.setSelected(true);
					} else if (radio2.isSelected() == true) {
						Main.AIPlay = false;
						radio2.setSelected(true);
					}
					if (radio3.isSelected() == true) {
						Main.AIPiece = true;
						radio3.setSelected(true);
					} else if (radio4.isSelected() == true) {
						Main.AIPiece = false;
						radio4.setSelected(true);
					}
					settingStage.close();
				});

				settingPane.getChildren().addAll(bgImage, option1, radio1, radio2, divLine1, option2, radio3, radio4,
						divLine2, option3, radio5, radio6, radio7, divLine3, cancelBt, confirmBt);
				Scene scene = new Scene(settingPane, 350, 400);
				settingStage.setScene(scene);
				settingStage.setTitle("Game Setting");
				settingStage.show();
				settingStage.setResizable(false);
			} else {
				setConfirmWindow.setHeaderText(null);
				setConfirmWindow.setContentText("Please finsih this game first, then doing the game setting change");
				setConfirmWindow.showAndWait();
			}

		});
	}

	/*
	 * this method defines the implemented function of the start button
	 */
	public void startButton(ActionEvent event) {
		btStart.setOnAction(e -> {
			if (Main.whoseTurn == ' ') {
				Main.textArea.setText(" ");
				startConfirmWindow.setTitle("Game Start!");
				startConfirmWindow.setHeaderText(null);
				Optional<ButtonType> result = startConfirmWindow.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK)
					boardReset(event);
			} else {
				boardReset(event);
			}	
		});
	}

	/*
	 * this method defines the implemented function of the surrender button
	 */
	public void surrenderButton(ActionEvent event) {
		btSurrender.setOnAction(e -> {
			surrenderConfirmWindow.setTitle("Surrender");
			surrenderConfirmWindow.setHeaderText(null);
			// if the player takes the black piece, surrender window shows the white piece
			// wins
			if (Main.whoseTurn == 'B') {
				Optional<ButtonType> computerWin = surrenderConfirmWindow.showAndWait();
				if (computerWin.isPresent() && computerWin.get() == ButtonType.OK) {
					Main.textArea.insertText(0, "White piece wins！" + '\n' + '\n');
					Main.whiteWin();
					winnerWindow.setContentText("White piece wins！！！");
					winnerWindow.setHeaderText(null);
					winnerWindow.showAndWait();
				}
			} else if (Main.whoseTurn == 'W') {
				Optional<ButtonType> computerWin = surrenderConfirmWindow.showAndWait();
				if (computerWin.isPresent() && computerWin.get() == ButtonType.OK) {
					Main.textArea.insertText(0, "Black piece wins！" + '\n' + '\n');
					Main.blackWin();
					winnerWindow.setContentText("Black piece wins！！！");
					winnerWindow.setHeaderText(null);
					winnerWindow.showAndWait();
				}
			}
		});
	}

	/*
	 * this method defines the implemented function of the exit button
	 */
	public void exitButton(ActionEvent event) {
		btExit.setOnAction(e -> {
			exitConfirmWindow.setTitle("Exit the Othello");
			exitConfirmWindow.setHeaderText(null);

			Optional<ButtonType> result = exitConfirmWindow.showAndWait();
			// press the confirm button to exit the whole system
			if (result.isPresent() && result.get() == ButtonType.OK) {
				System.exit(1);
			}
		});
	}

	/*
	 * this method defines the function to reset the whole Reversi board
	 */
	public void boardReset(ActionEvent event) {
		Main.gameStart();
		//Main.textArea.setText(" ");
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				Main.RevBoard[i][j].getChildren().remove(Main.arrayCircle[i][j]);
				Main.RevBoard[i][j].piece = ' ';
			}
		Main.whoseTurn = ' ';
		Main.step = 0;

		//if the game is in AI battle mode, computer will pre-place four (2 for each colour)
		//at the middle section of the Reversi board
		if (Main.AIPiece == true) {
			Main.RevBoard[3][3].setPiece(Main.whoseTurn, 3, 3, true);
			Main.RevBoard[4][3].setPiece(Main.whoseTurn, 4, 3, true);
			Main.RevBoard[3][4].setPiece(Main.whoseTurn, 3, 4, true);
			Main.RevBoard[4][3].setPiece(Main.whoseTurn, 4, 4, true);
			Main.whoseTurn = ' ';
		}
	}
}