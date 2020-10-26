package client;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Window {
	public static void display(String msg, double fontSize) {
		
		Label text = new Label(msg);
		text.setFont(new Font("Arial", fontSize));
		text.setWrapText(true);
		
		StackPane container = new StackPane();
		container.getChildren().add(text);
		container.setPadding(new Insets(16));
		
		Stage window = new Stage();
		window.setScene(new Scene(container, 400, 300));
		window.setTitle(msg);
		window.show();
	}
}
