package client;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

public class TurnBox extends VBox {
	
	private LinkedList<TurnIndicator> turns = new LinkedList<TurnIndicator>();
	private Button ready = new Button("Ready");
	
	@SuppressWarnings("unchecked")
public TurnBox() {
		super();
		for (int i = 0; i < Main.moves; i++) {
			turns.add(new TurnIndicator());
			this.getChildren().add(turns.get(i));
		}
		this.getChildren().add(ready);
		this.setAlignment(Pos.BASELINE_CENTER);
		this.setSpacing(8);
		
		ready.setOnAction(e -> {
			ready.setDisable(true);
			ready.setText("Waiting...");
			String res;
			
			JSONObject data = new JSONObject();
			data.put("player", Main.player.toJSON());
			data.put("moves", toJSONArray());
			try {
				res = Main.client.send(HttpRequest.newBuilder()
						.uri(new URI(HttpSettings.uri + "/move"))
						.POST(HttpRequest.BodyPublisher.fromString(data.toJSONString()))
						.header("Content-Type", "application/json")
						.build(),
						HttpResponse.BodyHandler.asString()).body();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			ready.setDisable(false);
			ready.setText("Ready");
		});
	}
	
	@SuppressWarnings("unchecked")
	private JSONArray toJSONArray() {
		JSONArray arr = new JSONArray();
		for(TurnIndicator turn: turns) arr.add(Actions.getString(turn.action));
		return arr;
	}
}
