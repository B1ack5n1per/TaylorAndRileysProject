package client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class Controls extends HBox {

	private Map map;
	private double arrowWidth = 40;
	private Font font = new Font("Arial", 48);
	public Button ready = new Button("Ready");
	
	public Controls(Map map) {
		super();
		
		this.map = map;
		Button up = new Button();
		up.setMinSize(arrowWidth, arrowWidth);
		up.setMaxSize(arrowWidth, arrowWidth);
		up.setGraphic(new ImageView(new Image(FileSettings.assets + "/arrow.png")));
		up.getGraphic().setRotate(-90);
		up.setOnAction(e -> {
			dirClick(Directions.UP);
		});
		
		Button down = new Button();
		down.setMinSize(arrowWidth, arrowWidth);
		down.setMaxSize(arrowWidth, arrowWidth);
		down.setGraphic(new ImageView(new Image(FileSettings.assets + "/arrow.png")));
		down.getGraphic().setRotate(90);
		down.setOnAction(e -> {
			dirClick(Directions.DOWN);
		});
		
		Button left = new Button();
		left.setMinSize(arrowWidth, arrowWidth);
		left.setMaxSize(arrowWidth, arrowWidth);
		left.setGraphic(new ImageView(new Image(FileSettings.assets + "/arrow.png")));
		left.getGraphic().setRotate(180);
		left.setOnAction(e -> {
			dirClick(Directions.LEFT);
		});
		
		Button right = new Button();
		right.setMinSize(arrowWidth, arrowWidth);
		right.setMaxSize(arrowWidth, Main.tileSize);
		right.setGraphic(new ImageView(new Image(FileSettings.assets + "/arrow.png")));
		right.setOnAction(e -> {
			dirClick(Directions.RIGHT);
		});
		
		GridPane arrows = new GridPane();
		arrows.add(up, 1, 0);
		arrows.add(down, 1, 2);
		arrows.add(left, 0, 1);
		arrows.add(right, 2, 1);
		arrows.setPadding(new Insets(4, 0, 0, 32));
		
		Button fire = new Button("Fire");
		fire.setFont(font);
		fire.setOnAction(e -> {
			if (Main.state == GameState.PLAY) {
				int x1 = Main.player.x, y1 = Main.player.y;
				int x2 = x1, y2 = y1;
				Directions dir = Main.player.dir;
				while(map.getTile(x2, y2).canMove(dir)) {
					switch (dir) {
						case UP:
							y2--;
							break;
						case DOWN:
							y2++;
							break;
						case LEFT:
							x2--;
							break;
						case RIGHT:
							x2++;
						default:
							break;
					}
				}
				Main.turns.add(new ActionData(Actions.SHOOT, dir, x1, y1, x2, y2));
			}
		});
		
		Button wait = new Button("Wait");
		wait.setFont(font);
		wait.setOnAction(e -> {
			Main.turns.add(new ActionData(Actions.WAIT, Directions.NONE));
		});

		ready.setFont(font);
		ready.setOnAction(e -> {
			ready.setDisable(true);
			ready.setText("Waiting");
			Main.ready = true;
		});
		
		this.getChildren().addAll(arrows, fire, wait, ready);
		this.setSpacing(16);
		this.setAlignment(Pos.CENTER_LEFT);
	}
	
	private void dirClick(Directions dir) {
		if (Main.state == GameState.PLAY) {
			Tile playerTile = map.getTile(Main.player.x, Main.player.y);
			if (playerTile.canMove(dir)) {
				switch (dir) {
					case UP:
						Main.player.move(0, -1);
						Main.player.changeDir(dir);
						break;
					case DOWN:
						Main.player.move(0, 1);
						Main.player.changeDir(dir);
						break;
					case LEFT:
						Main.player.move(-1, 0);
						Main.player.changeDir(dir);
						break;
					case RIGHT:
						Main.player.move(1, 0);
						Main.player.changeDir(dir);
					default:
						break;
				}
			}
		}
	}
}
