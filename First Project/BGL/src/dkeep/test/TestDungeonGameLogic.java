package dkeep.test;

import dkeep.logic.*;
import org.junit.Test;

import static dkeep.logic.Game.State.*;
import static org.junit.Assert.*;


public class TestDungeonGameLogic {

    private char[][] map = {
            {'X', 'X', 'X', 'X', 'X'},
            {'X', 'H', ' ', 'G', 'X'},
            {'I', ' ', 'X', 'I', 'X'},
            {'I', 'k', 'X', ' ', 'X'},
            {'X', 'X', 'X', 'X', 'X'}
    };

    private char[][] empty = {
            {'X', 'X', 'X', 'X', 'X'},
            {' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', 'X'},
            {'X', 'X', 'X', 'X', 'X'}};

    /*
     *  TASK 1
     */

    @Test // 1
    public void testMoveHeroIntoToFreeCell() {
        Board board = new Board(map, 0);
        Game game = new Game();
        Game.setBoard(board);
        game.getCharsObjects(board);
        assertEquals(1, game.hero.getx());
        assertEquals(1, game.hero.gety());
        game.move_hero('w');
        game.move_hero('s');
        assertEquals(1, game.hero.getx());
        assertEquals(2, game.hero.gety());

    }

    @Test // 2
    public void testMoveHeroIntoWall() {
        Board board = new Board(map, 0);
        Game game = new Game();
        Game.setBoard(board);
        game.getCharsObjects(board);
        assertEquals(1, game.hero.getx());
        assertEquals(1, game.hero.gety());
        game.move_hero('a');
        assertEquals(1, game.hero.getx());
        assertEquals(1, game.hero.gety());
    }

    @Test // 3
    public void testMoveHeroIntoGuard() {
        Board board = new Board(map, 0);
        Game game = new Game();
        Game.setBoard(board);
        game.level = 1;
        game.getCharsObjects(board);
        assertEquals(1, game.hero.getx());
        assertEquals(1, game.hero.gety());
        game.nextLevel();
        assertEquals(GAME, game.getState());
        game.move_hero('d');
        game.caughtByGuards();
        assertEquals(DEFEAT, game.getState());
    }

    @Test // 4
    public void testMoveHeroIntoCloseDoor() {
        Board board = new Board(map, 0);
        Game game = new Game();
        Game.setBoard(board);
        game.getCharsObjects(board);
        assertEquals(1, game.hero.getx());
        assertEquals(1, game.hero.gety());
        game.move_hero('s');
        game.move_hero('a');
        game.nextLevel();
        assertEquals(GAME, game.getState());
    }

    @Test // 5
    public void testMoveHeroIntoLeverCell() {
        Board board = new Board(map, 0);
        Game game = new Game();
        Game.setBoard(board);
        game.getCharsObjects(board);
        assertEquals(1, game.hero.getx());
        assertEquals(1, game.hero.gety());
        assertFalse(game.keylevers.get(0).getActive());
        game.move_hero('s');
        game.move_hero('s');
        assertEquals(3, game.keylevers.get(0).gety(), game.hero.getx());
        assertEquals(1, game.keylevers.get(0).getx(), game.hero.gety());
        game.lever('s');
        assertTrue(game.keylevers.get(0).getActive());
    }

    @Test // 6
    public void testMoveHeroIntoOpenDoor() {
        Board board = new Board(map, 0);
        Game game = new Game();
        Game.setBoard(board);
        game.level = 1;
        game.getCharsObjects(board);
        assertEquals(1, game.hero.getx());
        assertEquals(1, game.hero.gety());
        game.move_hero('s');
        game.move_hero('s');
        assertFalse(game.keylevers.get(0).getActive());
        assertEquals(3, game.keylevers.get(0).gety(), game.hero.getx());
        assertEquals(1, game.keylevers.get(0).getx(), game.hero.gety());
        game.lever('s');
        assertTrue(game.keylevers.get(0).getActive());
        assertEquals(1, game.doors.get(1).getCode());
        game.move_hero('a');
        game.nextLevel();
        assertEquals(WIN, game.getState());
    }

    @Test // 7
    public void testCreateEmptyMap() {

        Board board = new Board(empty, 1);
        Game game = new Game();
        Game.setBoard(board);
        Door door = new Door(0, 1, 1);
        Keylever key = new Keylever(3, 1, 1, true);
        Hero hero = new Hero(1, 4);
        Guard guard = new Guard(3, 3, null, "Rookie");
        assertEquals("X X X X X\n" + "        X\n" + "X       X\n" + "X       X\n" + "X       X\n" + "X X X X X\n", game.display());
        game.doors.add(door);
        game.keylevers.add(key);
        game.guards.add(guard);
        game.hero = hero;
        assertEquals("X X X X X\n" + "I     k X\n" + "X       X\n" + "X     G X\n" + "X H     X\n" + "X X X X X\n", game.display());

    }

    @Test // 8
    public void testMoveGuard() {
        Board board = new Board(map, 0);
        Game game = new Game();
        Game.setBoard(board);
        game.level = 1;
        game.getCharsObjects(board);
        Door door = new Door(0, 1, 1);
        Keylever key = new Keylever(3, 1, 1, true);
        Hero hero = new Hero(1, 4);
        Guard guard = new Guard(3, 3, null, "Rookie");
        char[] path = {'w', 'a', 's', 'd'};
        guard.setPath(path);
        game.doors.add(door);
        game.keylevers.add(key);
        game.guards.add(guard);
        game.hero = hero;
        assertEquals(3, guard.getx());
        assertEquals(3, guard.gety());
        guard.move();
        assertEquals(3, guard.getx());
        assertEquals(2, guard.gety());
        guard.move();
        assertEquals(2, guard.getx());
        assertEquals(2, guard.gety());
        guard.move();
        assertEquals(2, guard.getx());
        assertEquals(3, guard.gety());
        guard.move();
        assertEquals(3, guard.getx());
        assertEquals(3, guard.gety());
    }

    @Test(timeout = 1000) // 9
    public void testSuspiciousGuard() {
        Board board = new Board(map, 0);
        Game game = new Game();
        Game.setBoard(board);
        game.level = 1;
        game.getCharsObjects(board);
        Door door = new Door(0, 1, 1);
        Keylever key = new Keylever(3, 1, 1, true);
        Hero hero = new Hero(1, 4);
        Guard guard = new Guard(3, 3, null, "Rookie");
        char[] path = {'w', 'a', 's', 'd'};
        guard.setPath(path);
        guard.setPersonality("Suspicious");
        game.doors.add(door);
        game.keylevers.add(key);
        game.guards.add(guard);
        game.hero = hero;

        while (!guard.getReverse()) {

            guard.move();

        }

    }

    @Test(timeout = 1000) // 10
    public void testDrunkenGuard() {
        Board board = new Board(map, 0);
        Game game = new Game();
        Game.setBoard(board);
        game.level = 1;
        game.setMove();
        game.getCharsObjects(board);
        Door door = new Door(0, 1, 1);
        Keylever key = new Keylever(3, 1, 1, true);
        Hero hero = new Hero(1, 4);
        Guard guard = new Guard(3, 3, null, "Rookie");
        char[] path = {'w', 'a', 's', 'd'};
        guard.setPath(path);
        guard.setPersonality("Drunken");
        game.doors.add(door);
        game.keylevers.add(key);
        game.guards.add(guard);
        game.hero = hero;
        boolean sleep = false;
        while (!sleep) {

            guard.move();
            if (guard.getSleep() > 0) {
                sleep = true;
            }

        }

    }
}