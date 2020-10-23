package client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

public class Main extends Application {
	
	public static final HttpClient client = HttpClient.newHttpClient();
	private static String username = "";
	private static ChatController chatController;
	public static final double tileSize = 32;
	public static final int tilesX = 23, tilesY = 17;
	public static double height = tilesY * tileSize + 128, width = tilesX * tileSize + 300 + 2 * tileSize;
	public static Map map;
	public static Player player;
	public static int moves = 7;
	public static boolean ready = false;
	public static long timeIn = 0;
	public static TurnBox turns;

	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage window) throws ParseException, FileNotFoundException, IOException, InterruptedException, URISyntaxException {
		Object[] settings = LoginWindow.display();
		username = (String) settings[0];
		GameType gameType = (GameType) settings[1];
		
		
		switch(gameType) {
			case LOCAL:
				map = new Map((JSONObject) new JSONParser().parse(new FileReader(new File("src/Assets/MapData.json").getAbsolutePath())), LocalMenu.display());
				break;
			case ONLINE:
				break;
			default:
				return;	
		}
		
		// Canvas
		Canvas canvas = new Canvas(32 * 23, 32 * 17);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setLineWidth(4);
		gc.setStroke(Color.CYAN);
		
		// Object Setup
		LinkedList<Player> players = new LinkedList<Player>();
		player = new Player((JSONObject) new JSONParser().parse(client.send(HttpRequest.newBuilder()
				.uri(new URI(HttpSettings.uri + "/join"))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublisher.fromString(map.spawns.toJSONString()))
				.build(),
				HttpResponse.BodyHandler.asString()).body()));
		players.add(player);
		
		// HUD
		turns = new TurnBox();
		turns.setPadding(new Insets(16, 0, 0, 0));
		
		Controls control = new Controls(map);
		
		BorderPane canvasContainer = new BorderPane();
		canvasContainer.setCenter(canvas);
		canvasContainer.setRight(turns);
		canvasContainer.setMinSize(width - 300, height - 128);
		
		VBox hud = new VBox();
		hud.getChildren().addAll(canvasContainer, control);
		
		// Chat Control
		chatController = new ChatController();
		new Thread(chatController).start();

		Chat chat = new Chat(chatController, username);
		
		// Animation Timer
		AnimationTimer timer = new AnimationTimer() {
			private boolean editing = false;
			private boolean updating = false;
			
			@SuppressWarnings("unchecked")
			@Override
			public void handle(long time) {
				// Update Messages
				if (!editing) {
					editing = true;
					LinkedList<Message> msgList = chatController.getMessages();
					chat.messages.getChildren().clear();
					for (int i = 0; i < msgList.size(); i++) {
						if (i % 2 == 1) msgList.get(i).setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), new Insets(0))));
						else msgList.get(i).setBackground(new Background(new BackgroundFill(Color.web("EEE"), new CornerRadii(0), new Insets(0))));
						chat.messages.getChildren().add(msgList.get(i));
					}
					editing = false;
				}
				
				if (ready) {
					timeIn = time;
					ready = false;
					updating = true;
				}
				if (time - timeIn > 1 && updating) {
					timeIn = time;
					updating = false;
					String res;
					JSONObject data = new JSONObject();
					data.put("player", Main.player.toJSON());
					data.put("moves", turns.toJSONArray());
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
					turns.ready.setDisable(false);
					turns.ready.setText("Ready");
				}
				
				// Update Canvas
				map.draw(gc);
				for (int i = 0; i < player.lines.size() - 1; i++) {
					gc.strokeLine(player.lines.get(i)[0] * tileSize + tileSize / 2, player.lines.get(i)[1] * tileSize + tileSize / 2, player.lines.get(i + 1)[0] * tileSize + tileSize / 2, player.lines.get(i + 1)[1] * tileSize + tileSize / 2);
				}
				for (Player play: players) play.draw(gc);
			}
		};
		timer.start();

		HBox container = new HBox();
		container.getChildren().addAll(hud, chat);
		container.setAlignment(Pos.CENTER_RIGHT);
		container.setBackground(new Background(new BackgroundFill(Color.web("#333"), new CornerRadii(0), new Insets(0))));
		
	

		Scene scene = new Scene(container, width, height);
		
		scene.setOnMouseClicked(new ClickHandler(map));
		
		window.widthProperty().addListener((obs, old, nw) -> width = (double) nw);
		
		window.heightProperty().addListener((obs, old, nw) -> height = (double) nw);
		
		window.setScene(scene);
		window.setTitle("Game");
		window.setResizable(false);
		window.show();
		
		client.send(HttpRequest.newBuilder()
				.uri(new URI(HttpSettings.uri + "/confirm"))
				.POST(HttpRequest.BodyPublisher.fromString(player.toJSON().toJSONString()))
				.header("Content-Type", "application/json")
				.build(),
				HttpResponse.BodyHandler.asString());
		
		
		window.setOnCloseRequest(e -> {
			try {
				client.send(HttpRequest.newBuilder()
						.header("Content-Type", "application/json")
						.POST(HttpRequest.BodyPublisher.fromString(player.toJSON().toJSONString()))
						.uri(new URI(HttpSettings.uri + "/leave"))
						.build(),
						HttpResponse.BodyHandler.asString());
			} catch (Exception e1) {}
			chatController.stop();
		});
	}
}