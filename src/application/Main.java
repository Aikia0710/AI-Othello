package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;

/**
 * @author Zibo Wang 
 * the main class is the main entrance of the GUI
 *
 */
public class Main extends Application {
	
	public static ReversiBoard[][] RevBoard = new ReversiBoard[8][8];
	public static ReversiAI robot = new ReversiAI();
	public static boolean AIPlay = true;
	public static boolean AIPiece = false;
	public static char whoseTurn = ' ';
	public static Circle[][] arrayCircle = new Circle[8][8];
	public static int step = 0;
	public int WIDTH = 65;
	public static TextArea textArea = new TextArea();
	public static Pane mainPane = new Pane();

	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane grid = new GridPane();
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++)
					grid.add(RevBoard[i][j] = new ReversiBoard(i, j), i, j);
			grid.setLayoutX(65);
			grid.setLayoutY(50);

			/*
			 * inserting a background image of the GUI
			 */
			ImageView selectedImage = new ImageView();
			Image image1 = new Image(test.class.getResourceAsStream("/pictures/Wood_3888x2592.jpg"));
			selectedImage.setImage(image1);
			selectedImage.setFitHeight(650);
			selectedImage.setFitWidth(1060);

			/*
			 * drawing a list of buttons, and add them into the VBox, which will be located
			 * at the right side of the main pane
			 */
			BoardController controller = new BoardController(null, null, null, null);
			VBox vBox = new VBox(50);
			vBox.setPadding(new Insets(20, 20, 625, 625));
			vBox.setLayoutY(150);
			vBox.getChildren().addAll(controller.getBtSet(), controller.getBtStart(), controller.getBtSurrender(), controller.getBtExit());

			/*
			 * Drawing a textArea, defining the font size as 16, the max width and the max
			 * height, and drop it at the right hand side of the main pane,
			 */
			textArea.setWrapText(true);
			textArea.setFont(new Font(15));
			textArea.setMaxWidth(250);
			textArea.setMinHeight(500);
			textArea.setLayoutX(740);
			textArea.setLayoutY(75);
			textArea.setEditable(false);
			textArea.insertText(0, "Press the 'Start Game' button to start the game!" + '\n' + '\n');
			
			//recall the five function defined methods from the BoardController class
			controller.settingButton(null);
			controller.startButton(null);
			controller.surrenderButton(null);
			controller.exitButton(null);
			controller.boardReset(null);
			
			/*
			 * set a main pain which is used to contain the all GUI elements
			 */
			//mainPane.getChildren().addAll(selectedImage, grid, vBox, textArea);
			mainPane.getChildren().addAll(vBox, grid, textArea);

			Scene scene = new Scene(mainPane, 1050, 650);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			primaryStage.setTitle("Welcome to AI Reversi");
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	public static void gameStart() {
		textArea.insertText(0, "Game Start！" + '\n' + '\n');
	}
	
	public static void whiteWin() {
		textArea.insertText(0, "White piece wins！" + '\n' + '\n');
	}
	
	public static void blackWin() {
		textArea.insertText(0, "Black piece wins！" + '\n' + '\n');
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
