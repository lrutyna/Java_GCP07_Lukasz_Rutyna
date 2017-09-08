package controller;

import java.net.URL;
import java.util.ResourceBundle;

import example.Student;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;

public class CBCcontroller implements Initializable{

	@FXML
	private BarChart<String, Number> customBarChart;
	
	@FXML
	private CategoryAxis catAxis;
	@FXML
	private NumberAxis numbAxis;
	
	private Series<String, Number> series;
	private ObservableList<Student> list;

	@FXML
	private void initialize()
	{
		
	}
	
	public BarChart<String, Number> getBarChart(){
		return customBarChart;
	}
	
	public ObservableList<Student> getList() {
		return list;
	}

	public void setList(ObservableList<Student> list) {
		this.list = list;
	}

	public Series<String, Number> getSeries() {
		return series;
	}

	public void setSeries(Series<String, Number> series) {
		this.series = series;
		customBarChart.getData().add(series);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	

}
