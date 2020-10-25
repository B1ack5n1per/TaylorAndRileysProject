package client;

public enum Actions {
	MOVE, WAIT, SHOOT, NONE;
	
	public static String getString(Actions action) {
		switch(action) {
			case MOVE:
				return "move";
			case WAIT:
				return "wait";
			case SHOOT:
				return "shoot";
			default:
				return "none";
		}
	}
	
	public static Actions getAction(String action) {
		switch(action) {
			case "move":
				return MOVE;
			case "wait":
				return WAIT;
			case "shoot":
				return SHOOT;
			default:
				return NONE;
		}
	}
	
	public static long getTime(Actions action) {
		switch(action) {
		case MOVE:
			return 1000000000L;
		case WAIT:
			return 1000000000L;
		case SHOOT:
			return 10000000000L;
		default:
			return 0;
		}
	}
	
	
}
