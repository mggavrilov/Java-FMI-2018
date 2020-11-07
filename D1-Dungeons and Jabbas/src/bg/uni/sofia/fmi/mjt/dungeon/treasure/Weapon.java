package bg.uni.sofia.fmi.mjt.dungeon.treasure;

import bg.uni.sofia.fmi.mjt.dungeon.actor.Hero;

public class Weapon extends BattleTool {

	public Weapon(String name, int damage) {
		super(name, damage);
	}

	@Override
	public String collect(Hero hero) {
		hero.equip(this);
		return new String("Weapon found! Damage points: " + damage);
	}
}
