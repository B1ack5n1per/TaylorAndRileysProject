package client;

import java.util.LinkedList;

import org.json.simple.JSONArray;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TurnBox extends VBox {
	
	private LinkedList<TurnIndicator> turns = new LinkedList<TurnIndicator>();
	public Button ready = new Button("Ready");
	
	public TurnBox() {
		super();
		for (int i = 0; i < Main.moves; i++) {
			turns.add(new TurnIndicator());
			this.getChildren().add(turns.get(i));
		}
		this.setMinWidth(Main.tileSize * 2);
		this.getChildren().add(ready);
		this.setAlignment(Pos.BASELINE_CENTER);
		this.setSpacing(8);
		
		ready.setOnAction(e -> {
			ready.setDisable(true);
			ready.setText("Waiting");
			Main.ready = true;
		});
	}
	
	public void add(ActionData action) {
		for (TurnIndicator turn: turns) if (turn.action == new ActionData(Actions.NONE)) turn.setAction(action);
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
