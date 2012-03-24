package c.city.desolate;


public class GameParam {
	private static GameParam gi;

	public static GameParam gi() {
		if (gi == null) {
			gi = new GameParam();
		}
		return gi;
	}

}
