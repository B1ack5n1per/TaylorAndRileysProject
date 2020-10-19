package client;

import java.util.LinkedList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tile {
	private LinkedList<Directions> dirs = new LinkedList<Directions>();
	private TileType type;
	private int x, y;
	
	public Tile(int x, int y, TileType type, LinkedList<Directions> dirs) {
		this.dirs = dirs;
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public Tile(int x, int y, TileType type, Directions ... dirs) {
		for (Directions dir: dirs) this.dirs.add(dir);
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public boolean canMove(Directions dir) {
		return dirs.contains(dir);
	}
	
	public void draw(GraphicsContext gc) {
		gc.drawImage(getImage(), x * 32, y * 32, 32, 32);
	}
	
	private Image getImage() {
		switch(type) {
			case MOUNTAIN:
				return new Image("Assets/Tiles/mountain.png");
			case GRASS:
				return new Image("Assets/Tiles/grass.png");
			case ROAD:
				return new Image("Assets/Tiles/road.png");
			case BUILDINGSMALL:
				return new Image("Assets/Tiles/small_building.png");
			case BUILDINGLARGE:
				return new Image("Assets/Tiles/large_building.png");
			case FOREST:
				return new Image("Assets/Tiles/forest.png");
			case FARM:
				return new Image("Assets/Tiles/farm.png");
			case FACTORY:
				return new Image("Assets/Tiles/factory.png");
		}
		return null;
 	}
}
