package client;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class Message extends HBox {
	public String name, msg;
	public Message(String name, String msg) {
		super();
		this.name = name;
		this.msg = msg;
		
		setPadding(new Insets(4));
		
		Label label = new Label(name + ": " + msg);
		label.setFont(new Font("Arial", 18));
		label.setWrapText(true);
		getChildren().add(label);
	}
}
