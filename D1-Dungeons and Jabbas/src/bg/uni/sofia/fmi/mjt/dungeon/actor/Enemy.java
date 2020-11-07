package bg.uni.sofia.fmi.mjt.dungeon.actor;

import bg.uni.sofia.fmi.mjt.dungeon.treasure.Spell;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.Weapon;

public class Enemy extends Character {
	public Enemy(String name, int health, int mana, Weapon weapon, Spell spell) {
		super(name, health, mana);
		this.weapon = weapon;
		this.spell = spell;
	}
}
