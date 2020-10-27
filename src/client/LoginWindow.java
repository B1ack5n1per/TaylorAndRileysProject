package client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginWindow {
	private static String username = null;
	private final static double BUTTONSIZE = 300;
	
	public static String display() {
		Stage window = new Stage();
		
		VBox menuContainer = new VBox();
		HBox menuList = new HBox();

		Label name = new Label("Username:");
		name.setFont(new Font("Arial", 24));
		
		TextField input = new TextField();
		input.setFont(new Font("Arial", 24));
		
		VBox userInput = new VBox();
		userInput.getChildren().addAll(name, input);
		userInput.setPadding(new Insets(16));
		
		Button online = new Button("    Online \n Multiplayer");

		Font font = new Font("Arial", 30);
		online.setFont(font);

		online.setContentDisplay(ContentDisplay.TOP);

		online.setMaxWidth(BUTTONSIZE);
		online.maxHeight(BUTTONSIZE);
		
		online.setOnAction(e -> {
			if (input.getText().length() > 0) {
				username = input.getText();
				window.close();
			}
		});
		menuList.getChildren().add(online);
		menuList.setAlignment(Pos.CENTER);

		menuContainer.getChildren().addAll(userInput, menuList);
		menuContainer.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(menuContainer, 600, 400);
		
		window.setScene(scene);
		window.setTitle("Tank Trouble");
		window.setResizable(false);
		window.showAndWait();
		return username;
	}
}
