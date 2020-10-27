package client;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Projectile {
	private double x1, y1, x2, y2, speed = 5, xdir, ydir;;
	private Image img;
	public Directions dir;
	
	public Projectile(int x1, int y1, int x2, int y2) {
		this.x1 = x1 * Main.tileSize;
		this.y1 = y1 * Main.tileSize;
		this.x2 = x2 * Main.tileSize;
		this.y2 = y2 * Main.tileSize;
		if (x2 - x1 > 0) dir = Directions.RIGHT;
		if (x2 - x1 < 0) dir = Directions.LEFT;
		if (y2 - y1 > 0) dir = Directions.DOWN;
		if (y2 - y1 < 0) dir = Directions.UP;
		
		if (x2 - x1 != 0) this.xdir = ((double) (x2 - x1)) / Math.abs(x2 - x1);
		else this.xdir = 0;
		if (y2 - y1 != 0) this.ydir = ((double) (y2 - y1)) / Math.abs(y2 - y1);
		else this.ydir = 0;
		
		this.img = new Image(FileSettings.assets + "/projectile" + Directions.getString(dir) + ".png");
	}
	
	public void draw(GraphicsContext gc) {
		boolean moved = false;
		if (x1 * xdir <= x2 && xdir != 0) {
			x1 += xdir * speed;
			moved = true;
		}
		if (y1 * ydir <= y2 && ydir != 0) {
			y1 += ydir * speed;
			moved = true;
		}
		if (moved) gc.drawImage(img, x1, y1);
		Tile location = Main.map.getTile(x1, y1);
		if (location != null) {
			for (Player player: Main.players) {
				if (player.x == location.x && player.y == location.y && player.isAlive) {
					player.kill();
				}
			}
		}
	}
}
