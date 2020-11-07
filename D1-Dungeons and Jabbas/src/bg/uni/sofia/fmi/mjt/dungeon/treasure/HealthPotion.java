package bg.uni.sofia.fmi.mjt.dungeon.treasure;

import bg.uni.sofia.fmi.mjt.dungeon.actor.Hero;

public class HealthPotion extends Potion {

	public HealthPotion(int potionPoints) {
		super(potionPoints);
	}

	@Override
	public String collect(Hero hero) {
		hero.takeHealing(potionPoints);
		return new String("Health potion found! " + potionPoints + " health points added to your hero!");
	}

}
