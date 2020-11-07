package bg.uni.sofia.fmi.mjt.dungeon;

import bg.uni.sofia.fmi.mjt.dungeon.actor.Enemy;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Hero;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Position;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.Treasure;

public class GameEngine {
	private char[][] map;
	private Hero hero;
	private Enemy[] enemies;
	private Treasure[] treasures;

	private int maxWidthIndex;
	private int maxHeightIndex;

	private int treasureNumber;
	private int enemyNumber;

	private boolean gameOver;

	public GameEngine(char[][] map, Hero hero, Enemy[] enemies, Treasure[] treasures) {
		this.map = map;
		this.hero = hero;
		this.enemies = enemies;
		this.treasures = treasures;

		maxWidthIndex = map[0].length - 1;
		maxHeightIndex = map.length - 1;

		treasureNumber = 0;
		enemyNumber = 0;

		gameOver = false;
	}

	public char[][] getMap() {
		return map;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public String makeMove(int command) {
		int nextX;
		int nextY;

		Position currentPosition = hero.getPosition();

		switch (command) {
		case 0:
			nextX = currentPosition.getX();
			nextY = currentPosition.getY() - 1;
			break;
		case 1:
			nextX = currentPosition.getX() - 1;
			nextY = currentPosition.getY();
			break;
		case 2:
			nextX = currentPosition.getX();
			nextY = currentPosition.getY() + 1;
			break;
		case 3:
			nextX = currentPosition.getX() + 1;
			nextY = currentPosition.getY();
			break;
		default:
			return new String("Unknown command entered.");
		}

		// validate new position
		if (nextX > maxHeightIndex || nextY > maxWidthIndex || nextX < 0 || nextY < 0 || map[nextX][nextY] == '#') {
			return new String("Wrong move. There is an obstacle and you cannot bypass it.");
		}

		String returnString = "";

		switch (map[nextX][nextY]) {
		// intentional fall-through
		case '.':
		case 'S':
			returnString = "You moved successfully to the next position.";
			break;
		case 'T':
			returnString = treasures[treasureNumber++].collect(hero);
			break;
		case 'G':
			gameOver = true;
			returnString = "You have successfully passed through the dungeon. Congrats!";
			break;
		case 'E':
			returnString = battle(hero, enemies[enemyNumber++]);
			break;
		}

		map[currentPosition.getX()][currentPosition.getY()] = '.';
		map[nextX][nextY] = 'H';
		hero.setPosition(nextX, nextY);

		return returnString;
	}

	private String battle(Hero hero, Enemy enemy) {
		while (hero.isAlive() && enemy.isAlive()) {
			enemy.takeDamage(hero.attack());

			if (enemy.isAlive()) {
				hero.takeDamage(enemy.attack());
			}
		}

		if (hero.isAlive()) {
			return new String("Enemy died.");
		} else {
			gameOver = true;
			return new String("Hero is dead! Game over!");
		}
	}
}
