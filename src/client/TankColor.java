package client;

public enum TankColor {
	BLACK, BLUE, RED, WHITE, GREEN, YELLOW;
	
	public static String getString(TankColor color) {
		switch(color) {
			case BLACK:
				return "black";
			case BLUE:
				return "blue";
			case RED:
				return "red";
			case GREEN:
				return "green";
			case WHITE:
				return "white";
			case YELLOW:
				return "yellow";
		}
		return null;
	}
	public static TankColor getColor(String color) {
		switch(color) {
			case "black":
				return BLACK;
			case "blue":
				return BLUE;
			case "red":
				return RED;
			case "white":
				return WHITE;
			case "green":
				return GREEN;
			case "yellow":
				return YELLOW;
		}
		return null;
	}
}
