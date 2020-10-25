package client;

import org.json.simple.JSONObject;

import javafx.scene.image.Image;

public class PreformableAction extends ActionData {

	public Player player;
	
	public PreformableAction(Actions action, Directions dir, int[] coords, Player player) {
		super(action, dir, coords);
		this.player = player;
	}
	
	public PreformableAction(JSONObject obj, Player player) {
		super(obj);
		this.player = player;
	}
	
	public void perform() {
		switch (action) {
			case MOVE:
				player.x = x1;
				player.y = y1;
				player.lines.clear();
				player.lines.add(new int[]{ x1, y1 });
				player.changeDir(dir);
				break;
			case SHOOT:
				System.out.println(x1);
				System.out.println(y1);
				System.out.println(x2);
				System.out.println(y2);
				Main.projectiles.add(new Projectile(x1, y1, x2, y2, new Image(FileSettings.assets + "/Actions/shoot.ong")));
				break;
			case WAIT:
			case NONE:
		default:
			break;
		}
	}
	
	@Override
	public String toString() {
		return Actions.getString(action) + ", " + x1 + ", " + y1;
	}

}
