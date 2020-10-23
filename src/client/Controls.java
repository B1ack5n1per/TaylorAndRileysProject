package client;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class Controls extends HBox {

	private Map map;
	
	public Controls(Map map) {
		super();
		
		this.map = map;
		Button up = new Button();
		up.setMinSize(Main.tileSize, Main.tileSize);
		up.setMaxSize(Main.tileSize, Main.tileSize);
		up.setOnAction(e -> {
			click(Directions.UP);
		});
		
		Button down = new Button();
		down.setMinSize(Main.tileSize, Main.tileSize);
		down.setMaxSize(Main.tileSize, Main.tileSize);
		down.setOnAction(e -> {
			click(Directions.DOWN);
		});
		
		Button left = new Button();
		left.setMinSize(Main.tileSize, Main.tileSize);
		left.setMaxSize(Main.tileSize, Main.tileSize);
		left.setOnAction(e -> {
			click(Directions.LEFT);
		});
		
		Button right = new Button();
		right.setMinSize(Main.tileSize, Main.tileSize);
		right.setMaxSize(Main.tileSize, Main.tileSize);
		right.setOnAction(e -> {
			click(Directions.RIGHT);
		});
		
		GridPane arrows = new GridPane();
		arrows.add(up, 1, 0);
		arrows.add(down, 1, 2);
		arrows.add(left, 0, 1);
		arrows.add(right, 2, 1);
		arrows.setPadding(new Insets(16, 0, 0, 32));
		
		this.getChildren().addAll(arrows);
	}
	
	private void click(Directions dir) {
		Tile playerTile = map.getTile(Main.player.x, Main.player.y);
		
		if (playerTile.canMove(dir)) {
			switch (dir) {
				case UP:
					if (Main.turns.add(new ActionData(Actions.MOVE, Main.player.x, Main.player.y - 1))) {
						Main.player.move(0, -1);
						Main.player.changeDir(dir);
					}
					break;
				case DOWN:
					if (Main.turns.add(new ActionData(Actions.MOVE, Main.player.x, Main.player.y + 1))) {
						Main.player.move(0, 1);
						Main.player.changeDir(dir);
					}
					break;
				case LEFT:
					if (Main.turns.add(new ActionData(Actions.MOVE, Main.player.x - 1, Main.player.y))) {
						Main.player.move(-1, 0);
						Main.player.changeDir(dir);
					}
					break;
				case RIGHT:
					if (Main.turns.add(new ActionData(Actions.MOVE, Main.player.x + 1, Main.player.y))) {
						Main.player.move(1, 0);
						Main.player.changeDir(dir);
					}
				default:
					break;
			}
		}
	}
}
