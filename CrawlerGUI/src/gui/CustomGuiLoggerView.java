package gui;

import example.LogTableRow;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class CustomGuiLoggerView extends AnchorPane {

	private TableView<LogTableRow> table;

	public CustomGuiLoggerView() {

		table = new TableView<LogTableRow>();
		table.setEditable(true);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// usuniecie naglowka kolumny
		table.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> source, Number oldWidth, Number newWidth) {
				Pane header = (Pane) table.lookup("TableHeaderRow");
				if (header.isVisible()) {
					header.setMaxHeight(0);
					header.setMinHeight(0);
					header.setPrefHeight(0);
					header.setVisible(false);
				}
			}
		});

		TableColumn<LogTableRow, String> col = new TableColumn<>();
		col.setCellValueFactory(new PropertyValueFactory<LogTableRow, String>("log"));

		table.getColumns().addAll(col);

		setTopAnchor(table, 0.0);
		setLeftAnchor(table, 0.0);
		setRightAnchor(table, 0.0);
		setBottomAnchor(table, 0.0);

		getChildren().add(table);
	}

	public TableView<LogTableRow> getTable() {
		return table;
	}

	public void setTable(TableView<LogTableRow> table) {
		this.table = table;
	}

}
