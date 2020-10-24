package client;

import java.util.LinkedList;

import org.json.simple.JSONObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player {
	public int x, y, id;
	public LinkedList<int[]> lines = new LinkedList<int[]>();
	public TankColor color;
	private Image img;
	public Directions dir;
	
	public Player(int id, int x, int y, TankColor color, Directions dir) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.color = color;
		this.dir = dir;
		
		img = new Image("Assets/Tanks" + TankColor.getString(color) + Directions.getString(dir));
	}
	
	public Player(JSONObject obj) {
		this.id = (int)((long) obj.get("id"));
		this.x = (int)((long) obj.get("x"));
		this.y = (int)((long) obj.get("y"));
		lines.add(new int[] {x, y});
		this.color = TankColor.getColor((String) obj.get("color"));
		this.dir = Directions.getDirection((String) obj.get("dir"));
		
		img = new Image(FileSettings.assets + "/Tanks/Tank" + TankColor.getString(color) + Directions.getString(dir) + ".png");
	}
	
	public void changeDir(Directions dir) {
		this.dir = dir;
		img = new Image(FileSettings.assets + "/Tanks/Tank" + TankColor.getString(color) + Directions.getString(dir) + ".png");
	}
	
	public void clearLines() {
		lines.clear();
		lines.add(new int[] {x, y});
	}
	
	public void move(int x, int y) {
		if (Main.turns.add(new ActionData(Actions.MOVE, this.x + x, this.y + y))) {
			this.x += x;
			this.y += y;
			lines.add(new int[] {this.x, this.y});
		}
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("x", x);
		obj.put("y", y);
		obj.put("color", TankColor.getString(color));
		obj.put("dir", Directions.getString(dir));
		return obj;
	}
	
	public void draw(GraphicsContext gc) {
		gc.drawImage(img, x * Main.tileSize, y * Main.tileSize);
	}
}
