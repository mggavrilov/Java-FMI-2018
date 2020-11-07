package bg.uni.sofia.fmi.mjt.dungeon.treasure;

import bg.uni.sofia.fmi.mjt.dungeon.actor.Hero;

public class ManaPotion extends Potion {

	public ManaPotion(int potionPoints) {
		super(potionPoints);
	}

	@Override
	public String collect(Hero hero) {
		hero.takeMana(potionPoints);
		return new String("Mana potion found! " + potionPoints + " mana points added to your hero!");
	}

}
