package application;

import javafx.application.Application;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MyPiece extends Circle {
	
	MyPiece(){		
		
	}
	
	//setting the radius, colour and shadow effect for the piece
	MyPiece(int radius, Color color){
		super.setRadius(radius);
		super.setStroke(color);
		super.setFill(Color.TRANSPARENT);
		DropShadow ds = new DropShadow();
		ds.setOffsetX(3.0);
		super.setEffect(ds);
	}

}
