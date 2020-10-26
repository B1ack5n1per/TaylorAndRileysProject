package client;

import org.json.simple.JSONObject;

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
		if (player.isAlive)
		switch (action) {
			case MOVE:
				player.x = x1;
				player.y = y1;
				player.lines.clear();
				player.lines.add(new int[] { x1, y1 });
				player.changeDir(dir);
				break;
			case SHOOT:
				Projectile proj = new Projectile(x1, y1, x2, y2);
				player.changeDir(proj.dir);
				Main.projectiles.add(proj);
				break;
			case WAIT:
			case NONE:
		default:
			break;
		}
	}
	
	@Override
	public String toString() {
		return "(" + Actions.getString(action) + ", " + x1 + ", " + y1 + ")";
	}

}
