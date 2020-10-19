package client;

import java.util.LinkedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.scene.canvas.GraphicsContext;



public class Map {
	public LinkedList<LinkedList<Tile>> tiles = new LinkedList<LinkedList<Tile>>();
	public Map(JSONObject obj, int mapId) {
		JSONArray rows = (JSONArray) ((JSONArray) obj.get("maps")).get(mapId);
		for (int i = 0; i < rows.size(); i++) {
			JSONArray col = (JSONArray) rows.get(i);
			tiles.add(new LinkedList<Tile>());
			for (int j = 0; j < col.size(); j++) {
				tiles.get(i).add(new Tile(j, i, getType((String) ((JSONObject) col.get(j)).get("type")), parseDirection((JSONArray) ((JSONObject) col.get(j)).get("dirs"))));
			}
		}
	}
	
	private LinkedList<Directions> parseDirection(JSONArray dirs) {
		LinkedList<Directions> res = new LinkedList<Directions>();
		for (int i = 0; i < dirs.size(); i++) {
			switch((String) dirs.get(i)) {
				case "up": 
					res.add(Directions.UP);
					break;
				case "down":
					res.add(Directions.DOWN);
					break;
				case "right":
					res.add(Directions.RIGHT);
				case "left":
					res.add(Directions.LEFT);
					break;
			}
		}
		return res;
	}
	
	private TileType getType(String name) {
		switch(name) {
		case "MOUNTAIN":
			return TileType.MOUNTAIN;
		case "GRASS":
			return TileType.GRASS;
		case "ROAD":
			return TileType.ROAD;
		case "BUILDINGSMALL":
			return TileType.BUILDINGSMALL;
		case "BUILDINGLARGE":
			return TileType.BUILDINGLARGE;
		case "FOREST":
			return TileType.FOREST;
		case "FARM":
			return TileType.FARM;
		case "FACTORY":
			return TileType.FACTORY;
		}
		return null;
	}
	
	public void draw(GraphicsContext gc) {
		for (LinkedList<Tile> list : tiles) for (Tile tile: list) tile.draw(gc);
	}
}
