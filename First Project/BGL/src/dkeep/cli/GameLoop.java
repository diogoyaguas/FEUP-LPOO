package dkeep.cli;

import dkeep.logic.Board;
import dkeep.logic.Game;

import java.util.Scanner;

import static dkeep.logic.Game.State.GAME;
import static dkeep.logic.Game.State.QUIT;

public class GameLoop {

    private static Scanner scan = new Scanner(System.in);

    private static char[][] board1 = {
            {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
            {'X', 'H', ' ', ' ', 'I', ' ', 'X', ' ', 'G', 'X'},
            {'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X'},
            {'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X'},
            {'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X'},
            {'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X'},
            {'X', ' ', 'I', ' ', 'I', ' ', 'X', 'k', ' ', 'X'},
            {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}};

    private static char[][] board2 = {
            {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
            {'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'k', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', 'H', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}};

    public static void main(String[] args) {

        Game game = new Game();

        while (game.getState() != QUIT) {
            switch (game.getState()) {

                case GAME:

                    game(game);
                    break;
                case WIN:
                    win(game);
                    break;

                case DEFEAT:
                    defeat(game);
                    break;
                case QUIT:
                    break;
            }
        }

    }

    private static void game(Game game) {

        init(game);

        while (game.getState() == GAME) {
            System.out.println("\nInsert a key (eg: W,A,S,D)");
            char key = scan.next().charAt(0);
            game.move_hero(key);
            switch (game.level) {
                case 1:
                    game.caughtByGuards();
                    game.lever(key);
                    break;
                case 2:
                    if (!game.keylevers.isEmpty()) game.key();
                    game.caughtByOgres();
                    game.openDoor(0, key);
                    break;

            }
            game.display();
            game.nextLevel();
        }
    }

    private static void init(Game game) {

        System.out.println("LEVEL " + game.level + "!\n");
        switch (game.level) {

            case 1:
                Game.board = new Board(board1, 0);
                break;
            case 2:
                Game.board = new Board(board2, 1);
                break;
        }

        game.getCharsObjects(Game.board);
        game.setMove();
        if (game.level == 2) {
            game.setMove();
            game.setOgres(0);
            game.hero.setArmed();
        }
        game.display();
    }

    private static void win(Game game) {

        if (game.level != 2) {
            game.level++;
            game.guards.clear();
            game.ogres.clear();
            game.state = GAME;
        } else
            game.state = QUIT;

    }

    private static void defeat(Game game) {

        if (game.level == 1) {
            System.out.println("\nYou got caught by the Guard...");
        } else if (game.level == 2) {
            System.out.println("\nYou got caught by the Ogre...");
        }
        game.state = QUIT;
    }

}