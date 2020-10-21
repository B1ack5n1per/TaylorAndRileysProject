package client;

public enum Directions {
	LEFT, RIGHT, UP, DOWN, NONE;
	
	public static String getString(Directions dir) {
		switch(dir) {
			case LEFT:
				return "left";
			case RIGHT: 
				return "right";
			case UP:
				return "up";
			case DOWN:
				return "down";
			default:
		}
		return null;
	}
	
	public static Directions getDirection(String dir) {
		switch(dir) {
		case "up":
			return UP;
		case "down":
			return DOWN;
		case "right":
			return RIGHT;
		case "left":
			return LEFT;
		}
		return null;
	}
}
