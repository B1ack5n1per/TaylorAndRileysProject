package client;

import java.util.LinkedList;

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
		this.setAlignment(Pos.BASELINE_LEFT);
		this.setSpacing(8);
	}
}
