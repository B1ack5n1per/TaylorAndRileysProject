package client;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LocalMenu {
	public static final int BUTTONSIZE = 200;
	public static int mapId = 0;
	public static Stage window = new Stage();
	
	public static int display() {
		Font font = new Font("Arial", 40);
		
		Label title = new Label("Choose Your Map!");
		title.setFont(font);
	
		Button b1 = new Button();
		ImageView map1 = new ImageView(new Image("Assets/Map1.png"));
		map1.setFitWidth(BUTTONSIZE);
		map1.setFitHeight(BUTTONSIZE);
		b1.setGraphic(map1);
		b1.setOnAction(new MapButtonHandler(0));
	
		Button b2 = new Button();
		ImageView map2 = new ImageView(new Image("Assets/Map2.png"));
		map2.setFitWidth(BUTTONSIZE);
		map2.setFitHeight(BUTTONSIZE);
		b2.setGraphic(map2);
		b2.setOnAction(new MapButtonHandler(1));
	
		Button b3 = new Button();
		ImageView map3 = new ImageView(new Image("Assets/Map3.png"));
		map3.setFitWidth(BUTTONSIZE);
		map3.setFitHeight(BUTTONSIZE);
		b3.setGraphic(map3);
		b3.setOnAction(new MapButtonHandler(2));
	
		Button b4 = new Button();
		ImageView map4 = new ImageView(new Image("Assets/Map4.png"));
		map4.setFitWidth(BUTTONSIZE);
		map4.setFitHeight(BUTTONSIZE);
		b4.setGraphic(map4);
		b4.setOnAction(new MapButtonHandler(3));
	
		Button b5 = new Button();
		ImageView map5 = new ImageView(new Image("Assets/BasicTestMap.png"));
		map5.setFitWidth(BUTTONSIZE);
		map5.setFitHeight(BUTTONSIZE);
		b5.setGraphic(map5);
		b5.setOnAction(new MapButtonHandler(4));
	
		Button b6 = new Button();
		ImageView map6 = new ImageView(new Image("Assets/BasicTestMap.png"));
		map6.setFitWidth(BUTTONSIZE);
		map6.setFitHeight(BUTTONSIZE);
		b6.setGraphic(map6);
		b6.setOnAction(new MapButtonHandler(5));
	
		Button b7 = new Button();
		ImageView map7 = new ImageView(new Image("Assets/BasicTestMap.png"));
		map7.setFitWidth(BUTTONSIZE);
		map7.setFitHeight(BUTTONSIZE);
		b7.setGraphic(map7);
		b7.setOnAction(new MapButtonHandler(6));
	
		Button b8 = new Button();
		ImageView map8 = new ImageView(new Image("Assets/BasicTestMap.png"));
		map8.setFitWidth(BUTTONSIZE);
		map8.setFitHeight(BUTTONSIZE);
		b8.setGraphic(map8);
		b8.setOnAction(new MapButtonHandler(7));
	
		Button ran = new Button("Random!");
		ran.setFont(font);
		ran.setOnAction(new MapButtonHandler((int) Math.floor(Math.random() * 7)));
		
		GridPane maps = new GridPane();
		maps.add(b1, 0, 0);
		maps.add(b2, 1, 0);
		maps.add(b3, 2, 0);
		maps.add(b4, 0, 1);
		maps.add(ran, 1, 1);
		maps.add(b5, 2, 1);
		maps.add(b6, 0, 2);
		maps.add(b7, 1, 2);
		maps.add(b8, 2, 2);
	
		VBox container = new VBox();
		container.getChildren().addAll(title, maps);
		container.setAlignment(Pos.CENTER);
	
		window.setScene(new Scene(container, 650, 675));
		window.setResizable(false);
		window.setTitle("Map Selection");
		window.showAndWait();
		
		return mapId;
	}

}
