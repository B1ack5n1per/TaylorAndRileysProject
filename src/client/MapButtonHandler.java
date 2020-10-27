package client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MapButtonHandler implements EventHandler<ActionEvent> {

	private int mapId;
	
	public MapButtonHandler(int mapId) {
		this.mapId = mapId;
	}
	
	@Override
	public void handle(ActionEvent e) {
		MapSelection.mapId = mapId;
		MapSelection.window.close();
	}

}
