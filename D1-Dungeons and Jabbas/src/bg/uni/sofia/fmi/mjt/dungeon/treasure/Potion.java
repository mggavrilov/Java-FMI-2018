package bg.uni.sofia.fmi.mjt.dungeon.treasure;

public abstract class Potion implements Treasure {

	protected int potionPoints;

	protected Potion(int potionPoints) {
		this.potionPoints = potionPoints;
	}

	public int heal() {
		return potionPoints;
	}

}
