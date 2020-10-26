package client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
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
	public static LinkedList<KeyCode> keys = new LinkedList<KeyCode>();
	public static GameState state = GameState.PLAY;
	public static LinkedList<Player> players = new LinkedList<Player>();
	public static LinkedList<LinkedList<PreformableAction>> turnData = new LinkedList<LinkedList<PreformableAction>>();
	public static LinkedList<Projectile> projectiles = new LinkedList<Projectile>();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage window) throws ParseException, IOException {
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
		JSONObject startData = new JSONObject();
		startData.put("map", map.id);
		startData.put("spawns", map.spawns);
		try {
			player = new Player((JSONObject) new JSONParser().parse(client.send(HttpRequest.newBuilder()
					.uri(new URI(HttpSettings.uri + "/join"))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublisher.fromString(startData.toJSONString()))
					.build(),
					HttpResponse.BodyHandler.asString()).body()), false);
		} catch(Exception e) {
			Window.display("Sorry, server unresponsive please restart the application", 18);
			return;
		}
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
			private boolean editing = false, updating = false, animating = false;
			private long animateTarget;
			
			private void getAnimationTarget(Long curTime) {
				long minTime = 0;
				for (int i = 0; i < turnData.size(); i++) {
					long testTime = Actions.getTime(turnData.get(i).get(0).action);
					if (testTime > minTime) minTime = testTime;
				}
				animateTarget = curTime + minTime;
			}
			
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
					try {
						timeIn = time;
						updating = false;
						
						// Http Request Setup
						String res = "";
						JSONObject data = new JSONObject();
						data.put("player", Main.player.toJSON());
						data.put("turns", turns.toJSONArray());
						data.put("map", map.id);
						
						// Http Request
						res = Main.client.send(HttpRequest.newBuilder()
							.uri(new URI(HttpSettings.uri + "/ready"))
							.POST(HttpRequest.BodyPublisher.fromString(data.toJSONString()))
							.header("Content-Type", "application/json")
							.build(),
							HttpResponse.BodyHandler.asString()).body();
						
						// Handle Data
						JSONObject resData = (JSONObject) new JSONParser().parse(res);
						JSONArray resPlayers = (JSONArray) resData.get("players");
						turnData.clear();
						players.clear();
						projectiles.clear();
						for (int i = 0; i < resPlayers.size(); i++) {
							JSONObject obj = (JSONObject) resPlayers.get(i);
							Player newPlayer = new Player(obj, true);
							players.add(newPlayer);
							if (newPlayer.isAlive) {
								if ((int)((long) obj.get("id")) == player.id) player = newPlayer;
								LinkedList<PreformableAction> actions = new LinkedList<PreformableAction>();
								JSONArray actionData = (JSONArray) obj.get("turns");
								for (int j = 0; j < actionData.size(); j++) {
									actions.add(new PreformableAction((JSONObject) actionData.get(j), newPlayer));
								}
								turnData.add(actions);
							}
							
						}
						
						state = GameState.ANIMATE;
						
						// HUD Reset
						control.ready.setDisable(false);
						control.ready.setText("Ready");
						player.clearLines();
						control.ready.requestFocus();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
				
				// Test User Input
				if (state == GameState.PLAY) {
					if (keys.contains(KeyCode.W)) {
						keys.remove(KeyCode.W);
						Tile playerTile = map.getTile(player.x, player.y);
						if (player.dir == Directions.UP && playerTile.canMove(Directions.UP)) player.move(0, -1);
						else player.changeDir(Directions.UP);
					}
					if (keys.contains(KeyCode.A)) {
						keys.remove(KeyCode.A);
						Tile playerTile = map.getTile(player.x, player.y);
						if (player.dir == Directions.LEFT && playerTile.canMove(Directions.LEFT)) player.move(-1, 0);
						else player.changeDir(Directions.LEFT);
					}
					if (keys.contains(KeyCode.S)) {
						keys.remove(KeyCode.S);
						Tile playerTile = map.getTile(player.x, player.y);
						if (player.dir == Directions.DOWN && playerTile.canMove(Directions.DOWN)) player.move(0, 1);
						else player.changeDir(Directions.DOWN);
					}
					if (keys.contains(KeyCode.D)) {
						keys.remove(KeyCode.D);
						Tile playerTile = map.getTile(player.x, player.y);
						if (player.dir == Directions.RIGHT && playerTile.canMove(Directions.RIGHT)) player.move(1, 0);
						else player.changeDir(Directions.RIGHT);
					}
				}
				
				// Update Canvas
				map.draw(gc);
				for (int i = 0; i < player.lines.size() - 1; i++) {
					double x1 = player.lines.get(i)[0] * tileSize + tileSize / 2;
					double y1 = player.lines.get(i)[1] * tileSize + tileSize / 2;
					double x2 = player.lines.get(i + 1)[0] * tileSize + tileSize / 2;
					double y2 = player.lines.get(i + 1)[1] * tileSize + tileSize / 2;
							
					gc.strokeLine(x1, y1, x2, y2);
				}
				
				if (state == GameState.ANIMATE || state == GameState.STANDBY) {
					if (!animating) {
						if (turnData.get(0).size() > 0) {
							getAnimationTarget(time);
							turns.highlightNext();
							for (LinkedList<PreformableAction> actionList: turnData) {
								actionList.get(0).perform();
								actionList.removeFirst();
							}
							
						} else {
							if (state == GameState.ANIMATE) state = GameState.PLAY;
							turns.clear();
						}
					}
					if (animateTarget > time) animating = true;
					else  {
						animating = false;
						player.xi = player.x;
						player.yi = player.y;
						projectiles.clear();
					}
					
				}
				
				if (!player.isAlive) {
					window.close();
					Window.display("Game Over", 56);
					this.stop();
				}
				
				
				for (Player play: players) play.draw(gc);
				for (Projectile proj: projectiles) proj.draw(gc);
				
			}
		};
		timer.start();

		HBox container = new HBox();
		container.getChildren().addAll(hud, chat);
		container.setAlignment(Pos.CENTER_RIGHT);
		container.setBackground(new Background(new BackgroundFill(Color.web("#333"), new CornerRadii(0), new Insets(0))));
	

		Scene scene = new Scene(container, width, height);
		scene.setOnMouseClicked(e -> {
			if (e.getSceneX() < width - 300) canvas.requestFocus();
		});
		
		scene.setOnKeyPressed(e -> {
			keys.add(e.getCode());
		});
		scene.setOnKeyReleased(e -> {
			try {
				keys.remove(e.getCode());
			} catch(NoSuchElementException err) {
				
			}
		});
		
		window.widthProperty().addListener((obs, old, nw) -> width = (double) nw);
		
		window.heightProperty().addListener((obs, old, nw) -> height = (double) nw);
		
		window.setScene(scene);
		window.setTitle("Game");
		window.setResizable(false);
		window.show();
		
		window.setOnCloseRequest(e -> {
			try {
				JSONObject leaveData = new JSONObject();
				leaveData.put("player", player.toJSON().toJSONString());
				leaveData.put("map", map.id);
				client.send(HttpRequest.newBuilder()
						.header("Content-Type", "application/json")
						.POST(HttpRequest.BodyPublisher.fromString(leaveData.toJSONString()))
						.uri(new URI(HttpSettings.uri + "/leave"))
						.build(),
						HttpResponse.BodyHandler.asString());
			} catch (Exception e1) {}
			chatController.stop();
		});
	}
}