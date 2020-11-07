package bg.uni.sofia.fmi.mjt.dungeon.actor;

import bg.uni.sofia.fmi.mjt.dungeon.treasure.Spell;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.Weapon;

public class Hero extends Character {
	private Position position;

	private int maxHealth;
	private int maxMana;

	public Hero(String name, int health, int mana, Position position) {
		super(name, health, mana);
		this.position = position;

		maxHealth = health;
		maxMana = mana;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(int x, int y) {
		position.setX(x);
		position.setY(y);
	}

	public void takeHealing(int healingPoints) {
		if (health > 0) {
			if (health + healingPoints >= maxHealth) {
				health = maxHealth;
			} else {
				health += healingPoints;
			}
		}
	}

	public void takeMana(int manaPoints) {
		if (mana + manaPoints >= maxMana) {
			mana = maxMana;
		} else {
			mana += manaPoints;
		}
	}

	public void equip(Weapon weapon) {
		if (this.weapon == null) {
			this.weapon = weapon;
		} else {
			if (this.weapon.getDamage() < weapon.getDamage()) {
				this.weapon = weapon;
			}
		}
	}

	public void learn(Spell spell) {
		if (this.spell == null) {
			this.spell = spell;
		} else {
			if (this.spell.getDamage() < spell.getDamage()) {
				this.spell = spell;
			}
		}
	}
}
