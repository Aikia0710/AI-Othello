package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class test extends Application {
	
	//public int WIDTH = 50;
	//public Rectangle block1 = new Rectangle(50,50);
	//public Rectangle block2 = new Rectangle(50,50);

    public void start(Stage primaryStage) throws Exception {
        GridPane pane=new GridPane();
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                Rectangle rectangle=new Rectangle(80,80);
                rectangle.setFill(Color.LIGHTGREEN);
            	
            	//block1.setFill(Color.LIGHTGREEN);
            	//block2.setFill(Color.DARKGREEN);
            	
                if ((i+j)%2==0){
                    pane.add(rectangle,i,j);
                }
            }
        }
        Scene scene=new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
	public static void main(String[] args) {
		launch(args);
	}
}