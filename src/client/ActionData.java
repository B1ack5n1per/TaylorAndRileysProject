package client;

import org.json.simple.JSONObject;

public class ActionData {
	public Actions action;
	public Integer x1, y1, x2, y2;
	
	public ActionData(Actions action, int ... coords) {
		this.action = action;
		if (coords.length > 1) this.x1 = coords[0];
		if (coords.length > 1) this.y1 = coords[1];
		if (coords.length > 3) this.x2 = coords[2];
		if (coords.length > 3) this.y2 = coords[3];
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("action", Actions.getString(action));
		if (x1 != null) obj.put("x1", x1);
		if (y1 != null) obj.put("y1", y1);
		if (x2 != null) obj.put("x2", x2);
		if (y2 != null) obj.put("y2", y2);
		return obj;
	}
}
