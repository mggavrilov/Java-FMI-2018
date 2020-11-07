package bg.uni.sofia.fmi.mjt.dungeon.actor;

import bg.uni.sofia.fmi.mjt.dungeon.treasure.Spell;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.Weapon;

//class that contains common methods for Hero and Enemy
public abstract class Character implements Actor {
	protected String name;
	protected int health;
	protected int mana;

	protected Weapon weapon;
	protected Spell spell;

	protected Character(String name, int health, int mana) {
		this.name = name;
		this.health = health;
		this.mana = mana;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public int getMana() {
		return mana;
	}

	@Override
	public boolean isAlive() {
		return (health == 0) ? false : true;
	}

	@Override
	public void takeDamage(int damagePoints) {
		if (health - damagePoints <= 0) {
			health = 0;
		} else {
			health -= damagePoints;
		}
	}

	@Override
	public Weapon getWeapon() {
		return weapon;
	}

	@Override
	public Spell getSpell() {
		return spell;
	}

	@Override
	public int attack() {
		if (weapon != null && spell == null) {
			return weapon.getDamage();
		} else if (weapon == null && spell != null) {
			if (mana >= spell.getManaCost()) {
				mana -= spell.getManaCost();
				return spell.getDamage();
			} else {
				return 0;
			}
		} else if (weapon != null && spell != null) {
			if (weapon.getDamage() >= spell.getDamage()) {
				return weapon.getDamage();
			} else {
				if (mana >= spell.getManaCost()) {
					mana -= spell.getManaCost();
					return spell.getDamage();
				} else {
					return weapon.getDamage();
				}
			}
		} else {
			return 0;
		}
	}
}
