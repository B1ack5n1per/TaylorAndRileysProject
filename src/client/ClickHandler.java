package client;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ClickHandler implements EventHandler<MouseEvent> {

	public State state;
	private Map map;
	
	public ClickHandler(Map map) {
		this.map = map;
		this.state = State.STANDBY;
	}
	public ClickHandler(Map map, State state) {
		this.state = state;
	}
	
	@Override
	public void handle(MouseEvent e) {
		if (state == State.STANDBY) {
			System.out.println(map.getTile(e.getSceneX(), e.getSceneY()).dirs);
		}
	}

}
