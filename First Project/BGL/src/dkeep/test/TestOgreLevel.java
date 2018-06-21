package dkeep.test;

import dkeep.logic.*;
import org.junit.Test;

import static dkeep.logic.Game.State.*;
import static org.junit.Assert.*;

public class TestOgreLevel {

    private char[][] map = {
            {'X', 'X', 'X', 'X', 'X'},
            {'I', ' ', ' ', 'k', 'X'},
            {'X', ' ', ' ', ' ', 'X'},
            {'X', 'H', ' ', ' ', 'X'},
            {'X', 'X', 'X', 'X', 'X'}};

    private char[][] empty = {
            {'X', 'X', 'X', 'X', 'X'},
            {' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', 'X'},
            {'X', 'X', 'X', 'X', 'X'}};

    /*
     *  TASK 2
     */

    @Test // 1
    public void testMoveHeroIntoOgre() {
        Board board = new Board(map, 1);
        Game game = new Game();
        Game.setBoard(board);
        game.level = 2;
        game.getCharsObjects(board);
        game.ogres.add(new Ogre(3, 3));
        assertEquals(1, game.hero.getx());
        assertEquals(3, game.hero.gety());
        game.move_hero('d');
        game.caughtByOgres();
        assertEquals(DEFEAT, game.getState());
    }

    @Test // 2
    public void testMoveArmedHeroIntoOgre() {
        Board board = new Board(map, 1);
        Game game = new Game();
        Game.setBoard(board);
        game.level = 2;
        game.getCharsObjects(board);
        game.setMove();
        game.ogres.add(new Ogre(3, 3));
        assertEquals(1, game.hero.getx());
        assertEquals(3, game.hero.gety());
        assertFalse(game.hero.getArmed());
        game.hero.setArmed();
        assertTrue(game.hero.getArmed());
        game.move_hero('d');
        assertTrue(game.ogres.get(0).getStun());
        game.move_hero('w');
        game.move_hero('w');
        assertFalse(game.ogres.get(0).getStun());

    }

    @Test // 3
    public void testMoveHeroIntoKeyCell() {
        Board board = new Board(map, 1);
        Game game = new Game();
        Game.setBoard(board);
        game.level = 2;
        game.getCharsObjects(board);
        game.ogres.add(new Ogre(3, 3));
        assertEquals(1, game.hero.getx());
        assertEquals(3, game.hero.gety());
        game.move_hero('w');
        game.move_hero('w');
        game.move_hero('d');
        game.move_hero('d');
        game.key();
        assertEquals('K', game.hero.getc());
        assertTrue(game.hero.getKey());
        assertEquals(true, game.keylevers.isEmpty());
    }

    @Test // 4
    public void testMoveHeroIntoClosedExitDoor() {
        Board board = new Board(map, 1);
        Game game = new Game();
        Game.setBoard(board);
        game.getCharsObjects(board);
        game.ogres.add(new Ogre(3, 3));
        assertEquals(1, game.hero.getx());
        assertEquals(3, game.hero.gety());
        game.move_hero('w');
        game.move_hero('w');
        game.move_hero('a');
        assertEquals(GAME, game.getState());
    }

    @Test // 5
    public void testHeroOpensDoor() {
        Board board = new Board(map, 1);
        Game game = new Game();
        Game.setBoard(board);
        game.level = 2;
        game.getCharsObjects(board);
        game.ogres.add(new Ogre(3, 3));
        assertEquals(1, game.hero.getx());
        assertEquals(3, game.hero.gety());
        game.move_hero('w');
        game.move_hero('w');
        game.move_hero('d');
        game.move_hero('d');
        game.key();
        assertEquals('K', game.hero.getc());
        assertTrue(game.hero.getKey());
        assertEquals(true, game.keylevers.isEmpty());
        game.move_hero('a');
        game.move_hero('a');
        game.move_hero('a');
        assertFalse(game.getUnlocked());
        game.openDoor(0, 'a');
        assertTrue(game.getUnlocked());
        game.openDoor(0, 'a');
        assertTrue(game.doors.get(0).getOpen());
    }

    @Test // 6
    public void testMoveHeroIntoOpenExitDoor() {
        Board board = new Board(map, 1);
        Game game = new Game();
        Game.setBoard(board);
        game.level = 2;
        game.getCharsObjects(board);
        game.ogres.add(new Ogre(3, 3));
        assertEquals(1, game.hero.getx());
        assertEquals(3, game.hero.gety());
        game.move_hero('w');
        game.move_hero('w');
        game.move_hero('d');
        game.move_hero('d');
        game.key();
        assertEquals('K', game.hero.getc());
        assertTrue(game.hero.getKey());
        assertEquals(true, game.keylevers.isEmpty());
        game.move_hero('a');
        game.move_hero('a');
        game.move_hero('a');
        assertFalse(game.getUnlocked());
        game.openDoor(0, 'a');
        assertTrue(game.getUnlocked());
        game.openDoor(0, 'a');
        assertTrue(game.doors.get(0).getOpen());
        game.move_hero('a');
        game.nextLevel();
        assertEquals(WIN, game.getState());
    }

    @Test // 7
    public void testCreateEmptyMap() {

        Board board = new Board(empty, 1);
        Game game = new Game();
        Game.setBoard(board);
        Door door = new Door(1, 0, 1);
        Keylever key = new Keylever(1, 3, 1, true);
        Hero hero = new Hero(4, 1);
        game.doors.add(door);
        game.keylevers.add(key);
        game.hero = hero;
        assertEquals(' ', Game.board.getBoard()[4][1]);
        assertEquals(' ', Game.board.getBoard()[1][0]);
        assertEquals(' ', Game.board.getBoard()[3][3]);
        game.updateMap();
        game.setOgres(1);
        game.updateMap();
        assertEquals('H', Game.board.getBoard()[4][1]);
        assertEquals('I', Game.board.getBoard()[1][0]);
        assertEquals('k', Game.board.getBoard()[1][3]);
        assertEquals('O', Game.board.getBoard()[game.ogres.get(0).getx()][game.ogres.get(0).gety()]);

    }

}