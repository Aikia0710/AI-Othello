package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javax.swing.Action;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * this controller class is crossponding to the Viewer.fxml, which implements
 * the Initializable interface all the control units in Scene Builder are
 * created as object in this class
 * 
 * @author Zibo Wang
 * @version 10.09.2019
 *
 */
public class Controller implements Initializable {

	@FXML
	AnchorPane mainPane;
	@FXML
	Label blackInf;
	@FXML
	Label whiteInf;
	@FXML
	Label gameInf;
	@FXML
	Circle circlePiece;
	@FXML
	RadioMenuItem itemAbMc;
	@FXML
	RadioMenuItem itemHumanAb;
	@FXML
	RadioMenuItem itemHumanMc;
	@FXML
	RadioMenuItem itemHumanMcts;
	@FXML
	RadioMenuItem itemAbMcts;

	private Game game = new Game();

	PieceIcon[][] boardIcon;

	/**
	 * the function is used to activate the game by clicking the game start button
	 * @param actionEvent
	 */
	public void itemStartClick(ActionEvent actionEvent) {
		game.start();
	}

	/**
	 * the method is used to show the bar chart when click on the chart button
	 * it uses the fxml loader to load the fxml file
	 * @param actionEvent
	 */
	public void itemChartClick(ActionEvent actionEvent) {
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader();
		String viewerFxml = "ChartViewer.fxml";
		Parent page = null;
		ChartController chartController = null;
		try {
			page = fxmlLoader.load(this.getClass().getResource(viewerFxml).openStream());
			chartController = (ChartController) fxmlLoader.getController();
			chartController.setDatas(game.getPlayers());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(page);
		stage.setScene(scene);
		stage.setTitle("Chart Review");
		stage.setResizable(false);
		stage.show();
	}

	/**
	 * the battle mode of the Alpha-beta and the random player is selected
	 * @param actionEvent
	 */
	public void itemAbMCClick(ActionEvent actionEvent) {
		game.setAbMc();
		itemAbMc.setSelected(true);
		itemHumanAb.setSelected(false);
		itemHumanMc.setSelected(false);
		itemHumanMcts.setSelected(false);
		itemAbMcts.setSelected(false);
	}

	/**
	 * the batlle mode of the human player and the alpha-beta player is selected.
	 * 
	 * @param actionEvent
	 */
	public void itemHumanAbClick(ActionEvent actionEvent) {
		game.setHumanAb();
		itemAbMc.setSelected(false);
		itemHumanAb.setSelected(true);
		itemHumanMc.setSelected(false);
		itemHumanMcts.setSelected(false);
		itemAbMcts.setSelected(false);
	}

	/**
	 * the batlle mode of the human player and the random player is selected
	 * 
	 * @param actionEvent
	 */
	public void itemHumanMCClick(ActionEvent actionEvent) {
		game.setHumanMc();
		itemAbMc.setSelected(false);
		itemHumanAb.setSelected(false);
		itemHumanMc.setSelected(true);
		itemHumanMcts.setSelected(false);
		itemAbMcts.setSelected(false);
	}
	
	/**
	 * the game mode of the human player and the AI MCTS player is selcted
	 * @param actionEvent
	 */
	public void itemHumanMctsClick(ActionEvent actionEvent) {
		game.setHumanMcts();
		itemAbMc.setSelected(false);
		itemHumanAb.setSelected(false);
		itemHumanMc.setSelected(false);
		itemHumanMcts.setSelected(true);
		itemAbMcts.setSelected(false);
	}
	/**
	 * the game mode of the AI alpha-beta player and the AI MCTS player is selected
	 * @param actionEvent
	 */
	public void itemAbMctsClick(ActionEvent actionEvent) {
		game.setAbMcts();
		itemAbMc.setSelected(false);
		itemHumanAb.setSelected(false);
		itemHumanMc.setSelected(false);
		itemHumanMcts.setSelected(false);
		itemAbMcts.setSelected(true);
	}

	/**
	 * the mouse event for players click cells on the game board to put pieces
	 * @param mouseEvent
	 */
	private void pieceClick(MouseEvent mouseEvent) {
		PieceIcon pieceIcon = (PieceIcon) mouseEvent.getSource();
		int r = pieceIcon.getR();
		int c = pieceIcon.getC();
		game.humanPlay(r, c);

	}

	/**
	 * to display the piece in the game board cell
	 * bond the centre of each piece at the centre of each cell
	 * @param object
	 */
	private void displayPiece(Object object) {
		PieceType[][] board = (PieceType[][]) object;

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				PieceIcon pieceIcon = boardIcon[i][j];
				pieceIcon.getChildren().clear();
				Color color;
				if (board[i][j] == null) {
					continue;
				}
				if (board[i][j].equals(PieceType.BLACK)) {
					color = Color.BLACK;
				} else {
					color = Color.WHITE;
				}
				Circle circle = new Circle(20, color);
				circle.setCenterX(pieceIcon.getWidth() / 2);
				circle.setCenterY(pieceIcon.getHeight() / 2);
				pieceIcon.getChildren().add(circle);
			}
		}
	}
	/**
	 * while using the initializable interface
	 * the initialize functin must be overidden
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initBordIcon();
		game.setDisplayPiece(this::displayPiece);
		game.setBlackInf(blackInf);
		game.setWhiteInf(whiteInf);
		game.setGameInf(gameInf);
		game.setCirclePiece(circlePiece);

	}

	/**
	 * this function is used to create the game board into the border pane in the fxml GUI
	 * the size of each square grid is set as 50, and set a mouse click event on each of the grid  
	 */
	private void initBordIcon() {
		boardIcon = new PieceIcon[8][8];
		int size = 50;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				PieceIcon pieceIcon = new PieceIcon(i, j);
				pieceIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						// TODO Auto-generated method stub
						pieceClick(event);

					}
				});
				pieceIcon.setPrefSize(size, size);
				pieceIcon.setLayoutX(j * size);
				pieceIcon.setLayoutY(i * size);
				mainPane.getChildren().add(pieceIcon);
				boardIcon[i][j] = pieceIcon;
			}
		}
		mainPane.setPrefSize(size * 8, size * 8);
	}
}
