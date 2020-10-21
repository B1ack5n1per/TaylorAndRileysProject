package client;

import org.json.simple.JSONObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player {
	public int x, y, id;
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
		this.color = TankColor.getColor((String) obj.get("color"));
		this.dir = Directions.getDirection((String) obj.get("dir"));
		
		img = new Image("Assets/Tanks/Tank" + TankColor.getString(color) + Directions.getString(dir) + ".png");
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("x", x);
		obj.put("y", y);
		obj.put("color", TankColor.getString(color));
		return obj;
	}
	
	public void draw(GraphicsContext gc) {
		gc.drawImage(img, x * Main.tileSize, y * Main.tileSize);
	}
}
