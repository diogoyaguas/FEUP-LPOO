package dkeep.test;

import dkeep.logic.Board;
import dkeep.logic.Game;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestOgreRandomBehaviour {

    char[][] map = {
            {'X', 'X', 'X', 'X', 'X'},
            {'I', ' ', ' ', 'k', 'X'},
            {'X', ' ', ' ', 'O', 'X'},
            {'X', 'H', ' ', ' ', 'X'},
            {'X', 'X', 'X', 'X', 'X'}};

    /*
     *  TASK 3
     */

    @Test(timeout = 1000)
    public void testOgreRandomPositions() {
        Board board = new Board(map, 1);
        Game game = new Game();
        Game.setBoard(board);
        game.level = 2;
        game.getCharsObjects(board);
        game.setMove();

        boolean left = false;
        boolean right = false;
        boolean up = false;
        boolean down = false;
        boolean key = false;

        int old_x, old_y;
        int new_x, new_y;

        while (!left || !right || !up || !down || !key) {

            old_x = game.ogres.get(0).getx();
            old_y = game.ogres.get(0).gety();

            game.move_hero('s');

            new_x = game.ogres.get(0).getx();
            new_y = game.ogres.get(0).gety();

            if (isLeft(new_x, new_y, old_x, old_y))
                left = true;

            if (isRight(new_x, new_y, old_x, old_y))
                right = true;

            if (isUp(new_x, new_y, old_x, old_y))
                up = true;

            if (isDown(new_x, new_y, old_x, old_y))
                down = true;

            if (game.ogres.get(0).getc() == '$') {
                assertEquals(3, game.ogres.get(0).getx());
                assertEquals(1, game.ogres.get(0).gety());
                key = true;

            }

        }
    }

    @Test(timeout = 1000)
    public void testClubRandomPositions() {
        Board board = new Board(map, 1);
        Game game = new Game();
        Game.setBoard(board);
        game.level = 2;
        game.getCharsObjects(board);
        game.setMove();

        boolean left = false;
        boolean right = false;
        boolean up = false;
        boolean down = false;
        boolean key = false;

        int ogre_x, ogre_y;
        int club_x, club_y;

        while (!left || !right || !up || !down || !key) {

            game.move_hero('s');

            ogre_x = game.ogres.get(0).getx();
            ogre_y = game.ogres.get(0).gety();
            club_x = game.ogres.get(0).getClubX();
            club_y = game.ogres.get(0).getClubY();

            if (isLeft(ogre_x, club_x, ogre_y, club_y))
                left = true;

            if (isRight(ogre_x, club_x, ogre_y, club_y))
                right = true;

            if (isUp(ogre_x, club_x, ogre_y, club_y))
                up = true;

            if (isDown(ogre_x, club_x, ogre_y, club_y))
                down = true;

            if (game.ogres.get(0).getClubChar() == '$') {

                assertEquals(3, game.ogres.get(0).getClubX());
                assertEquals(1, game.ogres.get(0).getClubY());
                key = true;

            }


        }
    }

    private boolean isLeft(int new_x, int new_y, int old_x, int old_y) {

        if (new_x == old_x - 1 && new_y == old_y)
            return true;
        else
            return false;
    }

    private boolean isRight(int new_x, int new_y, int old_x, int old_y) {

        if (new_x == old_x + 1 && new_y == old_y)
            return true;
        else
            return false;
    }

    private boolean isUp(int new_x, int new_y, int old_x, int old_y) {

        if (new_x == old_x && new_y == old_y - 1)
            return true;
        else
            return false;

    }

    private boolean isDown(int new_x, int new_y, int old_x, int old_y) {

        if (new_x == old_x && new_y == old_y + 1)
            return true;
        else
            return false;

    }

}