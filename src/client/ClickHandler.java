package client;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ClickHandler implements EventHandler<MouseEvent> {

	public State state;
	private Map map;
	
	public ClickHandler(Map map) {
		this.map = map;
		this.state = State.STANDBY;
	}
	public ClickHandler(Map map, State state) {
		this.state = state;
	}
	
	@Override
	public void handle(MouseEvent e) {
		if (state == State.STANDBY && e.getSceneX() < Main.tilesX * Main.tileSize && e.getSceneY() < Main.tilesY * Main.tileSize) {
			Tile tileClicked = map.getTile(e.getSceneX(), e.getSceneY());
			Directions dir = Directions.NONE;
			if (Main.player.x - tileClicked.x == 1 && Math.abs(Main.player.y - tileClicked.y) == 0) dir = Directions.LEFT;
			if (Main.player.x - tileClicked.x == -1 && Math.abs(Main.player.y - tileClicked.y) == 0) dir = Directions.RIGHT;
			if (Main.player.y - tileClicked.y == 1 && Math.abs(Main.player.x - tileClicked.x) == 0) dir = Directions.UP;
			if (Main.player.y - tileClicked.y == -1 && Math.abs(Main.player.x - tileClicked.x) == 0) dir = Directions.DOWN;
			if (dir != Directions.NONE) {
				Tile playerTile = map.getTile(Main.player.x, Main.player.y);
				if (playerTile.canMove(dir)) {
					Main.player.changeDir(dir);
					Main.player.x = tileClicked.x;
					Main.player.y = tileClicked.y;
					Main.turns.add(new ActionData(Actions.MOVE, tileClicked.x, tileClicked.y));
				}
			}
		}
	}
}
