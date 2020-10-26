package client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;

import org.json.simple.JSONObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

public class Player {
	public int x, y, id, xi, yi;
	public LinkedList<int[]> lines = new LinkedList<int[]>();
	public TankColor color;
	private Image img;
	public Directions dir;
	public boolean isAlive = true;
	
	public Player(int id, int x, int y, TankColor color, Directions dir) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.xi = x;
		this.yi = y;
		this.color = color;
		this.dir = dir;
		
		img = new Image("Assets/Tanks" + TankColor.getString(color) + Directions.getString(dir));
	}
	
	public Player(JSONObject obj, boolean useInit) {
		this.id = (int) ((long) obj.get("id"));
		this.isAlive = (boolean) obj.get("alive");
		if (useInit) {
			this.x = (int) ((long) obj.get("xi"));
			this.y = (int) ((long) obj.get("yi"));
			this.xi = x;
			this.yi = y;
		} else {
			this.x = (int) ((long) obj.get("x"));
			this.y = (int) ((long) obj.get("y"));
			this.xi = x;
			this.yi = y;
		}
		lines.add(new int[] { x, y });
		this.color = TankColor.getColor((String) obj.get("color"));
		this.dir = Directions.getDirection((String) obj.get("dir"));
		
		if (isAlive) img = new Image(FileSettings.assets + "/Tanks/Tank" + TankColor.getString(color) + Directions.getString(dir) + ".png");
		else img = new Image(FileSettings.assets + "/Tanks/dead.png");
	}
	
	public void kill() {
		this.isAlive = false;
		this.img = new Image(FileSettings.assets + "/Tanks/dead.png");
		try{
			Main.client.send(HttpRequest.newBuilder()
					.uri(new URI(HttpSettings.uri + "/kill"))
					.POST(HttpRequest.BodyPublisher.fromString(this.toJSON().toJSONString()))
					.header("Content-Type", "application/json")
					.build(),
					HttpResponse.BodyHandler.asString());
		} catch(IOException | InterruptedException | URISyntaxException e) {
			e.printStackTrace();
		}
				
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
		if (Main.turns.add(new ActionData(Actions.MOVE, dir, this.x + x, this.y + y))) {
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
		obj.put("xi", xi);
		obj.put("yi", yi);
		obj.put("color", TankColor.getString(color));
		obj.put("dir", Directions.getString(dir));
		obj.put("alive", isAlive);
		return obj;
	}
	
	public void draw(GraphicsContext gc) {
		gc.drawImage(img, x * Main.tileSize, y * Main.tileSize);
	}
}
