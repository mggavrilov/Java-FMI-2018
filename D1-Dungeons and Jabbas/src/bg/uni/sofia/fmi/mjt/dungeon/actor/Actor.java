package bg.uni.sofia.fmi.mjt.dungeon.actor;

import bg.uni.sofia.fmi.mjt.dungeon.treasure.Spell;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.Weapon;

public interface Actor {
	String getName();

	int getHealth();

	int getMana();

	boolean isAlive();

	void takeDamage(int damagePoints);

	Weapon getWeapon();

	Spell getSpell();

	int attack();
}
