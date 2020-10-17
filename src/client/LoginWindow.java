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
	private static GameType game = GameType.NONE;
	private final static double BUTTONSIZE = 300;
	
	public static Object[] display() {
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
		
		Button local = new Button("     Local \n Multiplayer");
		Button online = new Button("    Online \n Multiplayer");
		Button settings = new Button("Settings \n  Menu");

		Font font = new Font("Arial", 30);
		local.setFont(font);
		online.setFont(font);
		settings.setFont(font);

		local.setContentDisplay(ContentDisplay.TOP);
		online.setContentDisplay(ContentDisplay.TOP);
		settings.setContentDisplay(ContentDisplay.TOP);

		local.setMaxWidth(BUTTONSIZE);
		local.maxHeight(BUTTONSIZE);
		online.setMaxWidth(BUTTONSIZE);
		online.maxHeight(BUTTONSIZE);
		settings.setMinWidth(190);
		
		local.setOnAction(e -> {
			if (input.getText().length() > 0) {
				username = input.getText();
				game = GameType.LOCAL;
				window.close();
			}
		});
		online.setOnAction(e -> {
			if (input.getText().length() > 0) {
				username = input.getText();
				game = GameType.ONLINE;
				window.close();
			}
		});
		settings.setOnAction(e -> {
			
		});
		
		menuList.getChildren().addAll(local, online, settings);
		menuList.setAlignment(Pos.CENTER);

		menuContainer.getChildren().addAll(userInput, menuList);
		menuContainer.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(menuContainer, 600, 400);
		
		window.setScene(scene);
		window.setTitle("Tank Trouble");
		window.setResizable(false);
		window.showAndWait();
		return new Object[] {username, game};
	}
}
