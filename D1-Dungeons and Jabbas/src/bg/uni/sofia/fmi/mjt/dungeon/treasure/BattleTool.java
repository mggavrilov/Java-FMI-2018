package bg.uni.sofia.fmi.mjt.dungeon.treasure;

public abstract class BattleTool implements Treasure {

	protected String name;
	protected int damage;

	protected BattleTool(String name, int damage) {
		this.name = name;
		this.damage = damage;
	}

	public String getName() {
		return name;
	}

	public int getDamage() {
		return damage;
	}
}
