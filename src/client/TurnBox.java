package client;

import java.util.LinkedList;

import org.json.simple.JSONArray;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class TurnBox extends VBox {
	
	private LinkedList<TurnIndicator> turns = new LinkedList<TurnIndicator>();
	private int highlighted = 0;
	
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
				turn.setDisable(false);
				return true;
			}
		}
		return false;
	}
	
	public void clear() {
		for (TurnIndicator turn: turns) {
			turn.setAction(new ActionData(Actions.NONE, Directions.NONE));
			turn.setDisable(true);
		}
	}
	
	public void highlightNext() {
		if (highlighted >= turns.size()) highlighted = 0;
		for (int i = 0; i < turns.size(); i++) {
			if (i != highlighted) turns.get(i).setDisable(true);
			else turns.get(i).setDisable(false);
		}
		highlighted++;
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray toJSONArray() {
		JSONArray arr = new JSONArray();
		for (TurnIndicator turn: turns) {
			if (turn.action.action == Actions.NONE) turn.setAction(new ActionData(Actions.WAIT, Directions.NONE));
			arr.add(turn.action.toJSON());
		}
		return arr;
	}
}
