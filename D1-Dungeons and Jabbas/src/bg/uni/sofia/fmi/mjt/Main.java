package bg.uni.sofia.fmi.mjt;

import bg.uni.sofia.fmi.mjt.dungeon.GameEngine;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Enemy;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Hero;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Position;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.*;

import java.util.Scanner;

public class Main {

	public static void printMap(char[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j] + " ");
			}

			System.out.println();
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		char[][] map = { { 'S', '.', '#', '#', '.', '.', '.', '.', '.', 'T' },
				{ '#', 'T', '#', '#', '.', '.', '#', '#', '#', '.' },
				{ '#', '.', '#', '#', '#', 'E', '#', '#', '#', 'E' },
				{ '#', '.', 'E', '.', '.', '.', '#', '#', '#', '.' },
				{ '#', '#', '#', 'T', '#', '#', '#', '#', '#', 'G' } };

		Hero hero = new Hero("Pesho", 100, 100, new Position(0, 0));

		Weapon enemyWeapon = new Weapon("enemy weapon", 15);
		Spell enemySpell = new Spell("enemy spell", 30, 50);

		Enemy[] enemies = new Enemy[3];
		enemies[0] = new Enemy("e1", 20, 100, enemyWeapon, enemySpell);
		enemies[1] = new Enemy("e2", 20, 100, null, enemySpell);
		enemies[2] = new Enemy("e3", 20, 100, enemyWeapon, null);

		Treasure[] treasures = new Treasure[3];
		treasures[0] = new Weapon("hero weapon", 25);
		treasures[1] = new HealthPotion(25);
		treasures[2] = new Spell("hero spell", 30, 50);

		GameEngine game = new GameEngine(map, hero, enemies, treasures);

		System.out.println("Welcome to Dungeons and Jabbas!");
		System.out.println("0 = Left, 1 = Up, 2 = Right, 3 = Down");
		System.out.println("Press ENTER to start.");
		sc.nextLine();

		int playerMove;

		do {
			printMap(map);
			System.out.println("Move: ");
			playerMove = sc.nextInt();
			System.out.println(game.makeMove(playerMove));
		} while (!game.isGameOver());

		sc.close();
	}
}
