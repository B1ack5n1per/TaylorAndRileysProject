package client;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.net.URI;
import java.util.LinkedList;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

public class ChatController implements Runnable {
	private LinkedList<Message> messageQueue = new LinkedList<Message>(), messages = new LinkedList<Message>();
	private boolean running;
	private final String uri = "https://MenacingLemonchiffonInterpreter.b1ack5niper.repl.co";
	
	public ChatController() {
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		running = true;
		while(running) {
			try {
				// Delay
				Thread.sleep(100);
				
				// Send Messages
				while (messageQueue.size() > 0) {
					JSONObject data = new JSONObject();
					data.put("name", messageQueue.getFirst().name);
					data.put("msg", messageQueue.getFirst().msg);
					HttpClient.newHttpClient().send(HttpRequest.newBuilder()
							.header("Content-Type", "application/json")
							.POST(HttpRequest.BodyPublisher.fromString(data.toJSONString()))
							.uri(new URI(uri + "/message"))
							.build(),
							HttpResponse.BodyHandler.asString());
					messageQueue.removeFirst();
				}
				
				// Update Messages
				JSONArray data = ((JSONArray) new JSONParser().parse(requestChat()));
				messages.clear();
				for (int i = 0; i < data.size(); i++) {
					JSONObject obj = (JSONObject) data.get(i);
					messages.add(new Message((String) obj.get("name"), (String) obj.get("msg")));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public LinkedList<Message> getMessages() {
		LinkedList<Message> msgs = new LinkedList<Message>();
		for (Message msg: messages) msgs.add(msg);
		return msgs;
	}
	
	public void stop() {
		running = false;
	}
	
	public void sendMessage(String name, String msg) {
		messageQueue.add(new Message(name, msg));
	}
	
	private String requestChat() {
		try {
			return HttpClient.newHttpClient().send(HttpRequest.newBuilder()
					.header("Content-Type", "application/json")
					.GET()
					.uri(new URI(uri + "/messages"))
					.build(),
					HttpResponse.BodyHandler.asString()).body();
		} catch(Exception e) {
			return messages.toString();
		}
				
	}
	
}
