package client;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Projectile {
	public double x1, y1, x2, y2;
	public double speed = 5;
	public Image img;
	public boolean completed;
	
	public Projectile(int x1, int y1, int x2, int y2, Image img) {
		this.x1 = x1 * Main.tileSize;
		this.y1 = y1 * Main.tileSize;
		this.x2 = x2 * Main.tileSize;
		this.y2 = y2 * Main.tileSize;
		this.img = img;
	}
	
	public void draw(GraphicsContext gc) {
		update();
		gc.drawImage(img, x1, y1);
	}
	
	public void update() {
		if (!completed) {
			double dx = ((double) (x2 - x1)) / Math.abs(x2 - x1);
			double dy = ((double) (y2 - y1)) / Math.abs(y2 - y1);
			x1 += dx * speed;
			y1 += dy * speed;
		}
		if (x1 == x2 & y1 == y2) completed = true;
	}
}
