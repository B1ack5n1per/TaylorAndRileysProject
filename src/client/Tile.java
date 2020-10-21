package client;

import java.util.LinkedList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tile {
	public LinkedList<Directions> dirs = new LinkedList<Directions>();
	public int x, y;
	private TileType type;
	private Image img;
	
	public Tile(int x, int y, TileType type, LinkedList<Directions> dirs) {
		this.dirs = dirs;
		this.type = type;
		this.x = x;
		this.y = y;
		img = getImage();
	}
	
	public boolean canMove(Directions dir) {
		return dirs.contains(dir);
	}
	
	public void draw(GraphicsContext gc) {
		gc.drawImage(img, x * 32, y * 32, 32, 32);
	}
	
	private Image getImage() {
		switch(type) {
			case MOUNTAIN:
				return new Image("Assets/Tiles/Mountain.png");
			case GRASS:
				return new Image("Assets/Tiles/Grass.png");
			case ROAD:
				return new Image("Assets/Tiles/Road.png");
			case BUILDINGSMALL:
				return new Image("Assets/Tiles/BuildingSmall.png");
			case BUILDINGLARGE:
				return new Image("Assets/Tiles/BuildingLarge.png");
			case FOREST:
				return new Image("Assets/Tiles/Forest.png");
			case FARM:
				return new Image("Assets/Tiles/Farm.png");
			case FACTORY:
				return new Image("Assets/Tiles/Factory.png");
		}
		return null;
 	}
	
}
