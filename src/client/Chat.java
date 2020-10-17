package client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Chat extends VBox {
	
	public VBox messages = new VBox();
	
	public Chat(ChatController controller, String username) {
		super();
		
		TextField message = new TextField();
		message.setMinWidth(250);
		message.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				controller.sendMessage(username, message.getText());
				message.setText("");
			}
		});
		
		Button send = new Button("Send");
		send.setOnAction(e -> {
			controller.sendMessage(username, message.getText());
			message.setText("");
		});
		
		HBox chatControls = new HBox();
		chatControls.getChildren().addAll(message, send);
		
		messages.setMinWidth(276);
		messages.setMaxWidth(276);
		
		ScrollPane msgContainer = new ScrollPane(messages);
		msgContainer.setMinHeight(Main.height - 32);
		msgContainer.setHbarPolicy(ScrollBarPolicy.NEVER);
		msgContainer.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		this.getChildren().addAll(msgContainer, chatControls);
		this.setAlignment(Pos.BOTTOM_LEFT);
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), new Insets(0))));
		this.setPadding(new Insets(8, 0, 0, 8));
		this.setMinWidth(300);
		this.setMaxWidth(300);
	}
}
