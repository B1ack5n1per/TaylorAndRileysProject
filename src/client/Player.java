package client;

import org.json.simple.JSONObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player {
	public int x, y, id;
	public TankColor color;
	private Image img;
	
	public Player(int id, int x, int y, TankColor color) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.color = color;
		
		switch(this.color) {
			case BLACK: 
				img = new Image("Assets/Tiles/Mountain.png");
				break;
			case BLUE: 
				img = new Image("Assets/Tiles/Mountain.png");
				break;
			case RED: 
				img = new Image("Assets/Tiles/Mountain.png");
				break;
			default:
		}
	}
	
	public Player(JSONObject obj) {
		this.id = (int) obj.get("id");
		this.x = (int) obj.get("x");
		this.y = (int) obj.get("y");
		this.color = TankColor.getColor((String) obj.get("color"));
		
		switch(this.color) {
			case BLACK: 
				img = new Image("Assets/Tiles/Mountain.png");
				break;
			case BLUE: 
				img = new Image("Assets/Tiles/Mountain.png");
				break;
			case RED: 
				img = new Image("Assets/Tiles/Mountain.png");
				break;
			default:
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("x", x);
		obj.put("y", y);
		obj.put("color", TankColor.getString(color));
		return obj.toJSONString();
	}
	
	public void draw(GraphicsContext gc) {
		gc.drawImage(img, x * 32, y * 32);
	}
}
