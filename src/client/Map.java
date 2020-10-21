package client;

import java.util.LinkedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.scene.canvas.GraphicsContext;



public class Map {
	public LinkedList<LinkedList<Tile>> tiles = new LinkedList<LinkedList<Tile>>();
	public JSONArray spawns;
	public Map(JSONObject obj, int mapId) {
		JSONArray rows = (JSONArray) ((JSONArray) obj.get("maps")).get(mapId);
		for (int i = 0; i < rows.size(); i++) {
			JSONArray col = (JSONArray) rows.get(i);
			tiles.add(new LinkedList<Tile>());
			for (int j = 0; j < col.size(); j++) {
				tiles.get(i).add(new Tile(j, i, getType((String) ((JSONObject) col.get(j)).get("type")), parseDirection((JSONArray) ((JSONObject) col.get(j)).get("dirs"))));
			}
		}
		
		spawns = new JSONArray();//(JSONArray) obj.get("spawns");
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
		case "Mountain":
			return TileType.MOUNTAIN;
		case "Grass":
			return TileType.GRASS;
		case "Road":
			return TileType.ROAD;
		case "BuildingSmall":
			return TileType.BUILDINGSMALL;
		case "BuildingLarge":
			return TileType.BUILDINGLARGE;
		case "Forest":
			return TileType.FOREST;
		case "Farm":
			return TileType.FARM;
		case "Factory":
			return TileType.FACTORY;
		}
		System.out.println(name);
		return null;
	}
	
	public void draw(GraphicsContext gc) {
		for (LinkedList<Tile> list : tiles) for (Tile tile: list) tile.draw(gc);
	}
	
	public Tile getTile(double x, double y) {
		//for ()
		return null;
	}
}
