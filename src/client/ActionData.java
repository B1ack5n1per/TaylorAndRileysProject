package client;

import org.json.simple.JSONObject;

public class ActionData {
	public Actions action;
	public Integer x1, y1, x2, y2;
	public Directions dir;
	
	public ActionData(Actions action, Directions dir, int ... coords) {
		this.action = action;
		this.dir = dir;
		if (coords.length > 1) this.x1 = coords[0];
		if (coords.length > 1) this.y1 = coords[1];
		if (coords.length > 3) this.x2 = coords[2];
		if (coords.length > 3) this.y2 = coords[3];
	}
	
	public ActionData(JSONObject obj) {
		this.action = Actions.getAction((String) obj.get("action"));
		try {
			this.dir = Directions.getDirection((String) obj.get("dir"));
			this.x1 = (Integer) ((int) ((long) obj.get("x1")));
			this.y1 = (Integer) ((int) ((long) obj.get("y1")));
			this.x2 = (Integer) ((int) ((long) obj.get("x2")));
			this.y2 = (Integer) ((int) ((long) obj.get("y2")));
		} catch (Exception e) {
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("action", Actions.getString(action));
		obj.put("dir", Directions.getString(dir));
		if (x1 != null) obj.put("x1", x1);
		if (y1 != null) obj.put("y1", y1);
		if (x2 != null) obj.put("x2", x2);
		if (y2 != null) obj.put("y2", y2);
		return obj;
	}
}
