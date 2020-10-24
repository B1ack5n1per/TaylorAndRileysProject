package client;

import java.util.LinkedList;

import org.json.simple.JSONArray;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class TurnBox extends VBox {
	
	private LinkedList<TurnIndicator> turns = new LinkedList<TurnIndicator>();
	
	public TurnBox() {
		super();
		for (int i = 0; i < Main.moves; i++) {
			turns.add(new TurnIndicator());
			this.getChildren().add(turns.get(i));
		}
		this.setMinWidth(Main.tileSize * 2);
		this.setAlignment(Pos.BASELINE_CENTER);
		this.setSpacing(8);
		
		
	}
	
	public boolean add(ActionData action) {
		for (TurnIndicator turn: turns) {
			if (turn.action.action == Actions.NONE) {
				turn.setAction(action);
				return true;
			}
		}
		return false;
	}
	
	public void clear() {
		for (TurnIndicator turn: turns) {
			turn.setAction(new ActionData(Actions.NONE));
		}
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray toJSONArray() {
		JSONArray arr = new JSONArray();
		for (TurnIndicator turn: turns) {
			if (turn.action.action == Actions.NONE) turn.setAction(new ActionData(Actions.WAIT));
			arr.add(turn.action.toJSON());
		}
		return arr;
	}
}
