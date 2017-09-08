package gui;

import example.Crawler;
import example.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import tasks.CrawlerTask;

public class CustomTabPane extends AnchorPane {

	private CustomTableView tableView;
	private CustomBarChart chart;
	private CustomGuiLoggerView logs;

	public CustomTabPane() {
		setPrefSize(600, 600);

		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		// tabs
		tableView = new CustomTableView();
		chart = new CustomBarChart();
		logs = new CustomGuiLoggerView();

		Tab tab1 = new Tab();
		tab1.setText("Students");
		tab1.setContent(tableView);
		tabPane.getTabs().add(tab1);

		Tab tab2 = new Tab();
		tab2.setText("Log");
		tab2.setContent(logs);
		tabPane.getTabs().add(tab2);

		Tab tab3 = new Tab();
		tab3.setText("Histogram");
		tab3.setContent(chart);
		tabPane.getTabs().add(tab3);

		setTopAnchor(tabPane, 0.0);
		setLeftAnchor(tabPane, 0.0);
		setRightAnchor(tabPane, 0.0);
		setBottomAnchor(tabPane, 0.0);

		getChildren().add(tabPane);

		

	}

	public CustomTableView getTableView() {
		return tableView;
	}

	public void setTableView(CustomTableView tableView) {
		this.tableView = tableView;
	}

	public CustomBarChart getChart() {
		return chart;
	}

	public void setChart(CustomBarChart chart) {
		this.chart = chart;
	}

	public CustomGuiLoggerView getLogs() {
		return logs;
	}

	public void setLogs(CustomGuiLoggerView logs) {
		this.logs = logs;
	}

}
