package gui;

import example.Student;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.AnchorPane;

public class CustomBarChart extends AnchorPane {

	private CategoryAxis xAxis;
	private NumberAxis yAxis;
	private BarChart<String, Number> chart;
	private Series<String, Number> series;
	private ObservableList<Student> list;

	public CustomBarChart() {

		/* chart init */
		xAxis = new CategoryAxis();
		yAxis = new NumberAxis();
		chart = new BarChart<>(xAxis, yAxis);
		chart.setTitle("Distribution of marks");
		xAxis.setLabel("Mark");
		yAxis.setLabel("Count");
		chart.setAnimated(false);

		getChildren().add(chart);
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
		chart.getData().add(series);
	}
}
