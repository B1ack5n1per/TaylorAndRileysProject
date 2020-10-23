package client;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TurnIndicator extends Button {
	public ActionData action = new ActionData(Actions.NONE);
	public boolean set = false;
	
	public TurnIndicator() {
		super();
		this.setMinSize(Main.tileSize, Main.tileSize);
		this.setMaxSize(Main.tileSize, Main.tileSize);
		this.setDisable(true);
		this.setMouseTransparent(true);
		this.setFocusTraversable(false);
	}
	
	public void setAction(ActionData action) {
		this.action = action;
		this.set = true;
		this.setDisable(false);
		this.setGraphic(new ImageView(new Image(FileSettings.assets + "/Actions/" + Actions.getString(action.action) + ".png")));
	}
	
	
	public void clear() {
		this.action = new ActionData(Actions.NONE);
		this.set = false;
		this.setDisable(true);
		this.setGraphic(new ImageView());
	}
	
	
}
