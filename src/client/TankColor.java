package client;

public enum TankColor {
	BLACK, BLUE, RED;
	
	public static String getString(TankColor color) {
		switch(color) {
			case BLACK:
				return "black";
			case BLUE:
				return "blue";
			case RED:
				return "red";
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
	}
	return null;
}
}
