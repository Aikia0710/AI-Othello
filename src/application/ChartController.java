package application;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
/**
 * this class is used to give the data to the data property of
 * winning rate bar chart and present on the X and Y axis
 * 
 * @author Zibo Wang
 * @version 10.09.2019
 *
 */
public class ChartController {
	@FXML
	BarChart<String,Number> barChart;
	
	public void setDatas(Player[] players) {
		//create an object in series
		XYChart.Series<String, Number> series = new Series<>();
		for (Player player : players) {
			series.getData().add(new Data<String, Number>(player.inf(), player.getWins()));
			//adding the data to the data property
		}
		
		barChart.getData().add(series);
	}
}
