package v2;

public class Mobi {
	private Announcer announcer;
	private int x;
	private int y;
	private Integer dir;

	public Mobi(int x, int y) {
		this.announcer = new Announcer();
		this.x = x;
		this.y = y;
	}

	void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
		this.announcer.announce(new MobiMoveEvent(this));
	}

	void register(Object o) {
		this.announcer.register(o, MobiMoveEvent.class);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void configureToMoveToLeft() {
		this.dir = -1;
	}

	public void configureToMoveToRight() {
		this.dir = 1;
	}

	public void moveBy(int gap) {
		this.moveTo(this.x + (dir * gap), this.y);
	}

}
