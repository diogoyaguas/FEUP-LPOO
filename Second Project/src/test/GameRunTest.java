package com.cluedo.game.test;

import com.cluedo.game.controller.AIController;
import com.cluedo.game.controller.GameController;
import com.cluedo.game.model.Board;
import com.cluedo.game.model.Card.Character;
import com.cluedo.game.model.Card.Division;
import com.cluedo.game.model.Card.Weapon;
import com.cluedo.game.model.Suggestion;
import com.cluedo.game.model.Tile.Entrance;
import com.cluedo.game.model.Tile.Position;
import com.cluedo.game.model.Tile.Tile;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class GameRunTest {

    /**
     * Test that players are properly cycled.
     */
    @Test
    public void playerCycling() {

        GameController.getInstance().setNumPlayers(3);

        GameController.getInstance().joinPlayer(Character.Miss_Scarlet, "");
        GameController.getInstance().joinPlayer(Character.Colonel_Mustard, "");
        GameController.getInstance().joinPlayer(Character.Mrs_White, "");

        GameController.getInstance().decideWhoMoveFirst();
        GameController.getInstance().dealCard();

        assertEquals(GameController.getInstance().getCurrentPlayer(), Character.Miss_Scarlet);
        GameController.getInstance().currentPlayerEndTurn();

        assertEquals(GameController.getInstance().getCurrentPlayer(), Character.Colonel_Mustard);
        GameController.getInstance().currentPlayerEndTurn();

        assertEquals(GameController.getInstance().getCurrentPlayer(), Character.Mrs_White);

    }


    /**
     * Test that the player get correct options for movement if standing in a room
     */
    @Test
    public void moveOptionInRoom() {

        for (Character c : Character.values()) {
            GameController.getInstance().setNumPlayers(6);

            for (int i = 0; i < 6; i++) {
                GameController.getInstance().joinPlayer(Character.values()[i], "");
            }

            GameController.getInstance().createSolution();
            GameController.getInstance().dealCard();
            GameController.getInstance().decideWhoMoveFirst();

            GameController.getInstance().movePlayer(c, Board.LOUNGE);
            ArrayList<Position> positions = GameController.getInstance().getBoard().getMovablePositions(c);

            assertEquals(true, positions.contains(Board.CONSERVATORY));

            Entrance exit = new Entrance(6, 18, Board.LOUNGE);
            assertEquals(true, positions.contains(exit));
            {

            }
            assertEquals(2, positions.size());

            GameController.getInstance().movePlayer(c.nextCharacter(), exit);

            positions = GameController.getInstance().getBoard().getMovablePositions(c);

            assertEquals(true, positions.contains(Board.CONSERVATORY));

            assertEquals(2, positions.size());

            GameController.getInstance().movePlayer(c, Board.BALL_ROOM);
            positions = GameController.getInstance().getBoard().getMovablePositions(c);

            assertEquals(false, positions.contains(Board.HALL));

            Entrance exit_1 = new Entrance(7, 5, Board.BALL_ROOM);
            Entrance exit_2 = new Entrance(9, 8, Board.BALL_ROOM);
            Entrance exit_3 = new Entrance(14, 8, Board.BALL_ROOM);
            Entrance exit_4 = new Entrance(16, 5, Board.BALL_ROOM);

            assertEquals(true, positions.contains(exit_1));

            assertEquals(true, positions.contains(exit_2));

            assertEquals(true, positions.contains(exit_3));

            assertEquals(true, positions.contains(exit_4));

            assertEquals(4, positions.size());

            GameController.getInstance().movePlayer(c.nextCharacter(), exit_1);
            positions = GameController.getInstance().getBoard().getMovablePositions(c);

            assertEquals(true, positions.contains(exit_1));

            assertEquals(4, positions.size());

            GameController.getInstance().movePlayer(c.nextCharacter().nextCharacter(), exit_2);
            positions = GameController.getInstance().getBoard().getMovablePositions(c);

            assertEquals(true, positions.contains(exit_2));

            assertEquals(4, positions.size());
        }
    }


    /**
     * Test that the player get correct options for movement if standing on a normal tile,
     * not entrance.
     */
    @Test
    public void moveOptionOnTile() {


        for (Character c : Character.values()) {

            GameController.getInstance().setNumPlayers(6);

            for (int i = 0; i < 6; i++) {
                GameController.getInstance().joinPlayer(Character.values()[i], "");
            }

            GameController.getInstance().dealCard();
            GameController.getInstance().decideWhoMoveFirst();

            Tile testTile = new Tile(16, 13);
            Tile northTile = new Tile(16, 12);
            Tile southTile = new Tile(16, 14);
            Tile eastTile = new Tile(17, 13);
            Tile westTile = new Tile(15, 13);
            GameController.getInstance().movePlayer(c, testTile);
            List<Position> positions = GameController.getInstance().getBoard().getMovablePositions(c);

                assertEquals(4, positions.size());

                GameController.getInstance().movePlayer(c.nextCharacter(), northTile);
                positions = GameController.getInstance().getBoard().getMovablePositions(c);

                assertEquals(4, positions.size());

        }
    }

    /**
     * Test that the player get correct options for movement if standing on a entrance
     */
    @Test
    public void moveOptionOnEntrance() {

        for (Character c : Character.values()) {

            GameController.getInstance().setNumPlayers(3);

            for (int i = 0; i < 3; i++) {
                GameController.getInstance().joinPlayer(Character.values()[i], "");
            }

            GameController.getInstance().dealCard();
            GameController.getInstance().decideWhoMoveFirst();

            Entrance entrToKit = new Entrance(4, 7, Board.KITCHEN);
            GameController.getInstance().movePlayer(c, entrToKit);
            List<Position> positions = GameController.getInstance().getBoard().getMovablePositions(c);

            assertEquals(false, positions.contains(Board.KITCHEN));

            assertEquals(4, positions.size());

            GameController.getInstance().movePlayer(c.nextCharacter(), Board.KITCHEN);

            positions = GameController.getInstance().getBoard().getMovablePositions(c);

            assertEquals(false, positions.contains(Board.KITCHEN));

            assertEquals(4, positions.size());
        }
    }


    /**
     * Test that moves is processed properly
     */
    @Test
    public void validMove() {

        GameController.getInstance().setNumPlayers(3);
        for (int i = 0; i < 3; i++) {
            GameController.getInstance().joinPlayer(Character.values()[i], "");
        }

        GameController.getInstance().dealCard();
        GameController.getInstance().decideWhoMoveFirst();
        Character currentPlayer = GameController.getInstance().getCurrentPlayer();

        Tile testTile = new Tile(0, 7);

        GameController.getInstance().movePlayer(currentPlayer, testTile);
        List<Position> positions = GameController.getInstance().getBoard().getMovablePositions(currentPlayer);

        assertEquals(1, positions.size());
    }


    /**
     * Test that the Game won't prompt options to let player go out of board.
     */
    @Test
    public void boardBoundary() {

        GameController.getInstance().setNumPlayers(3);
        for (int i = 0; i < 3; i++) {
            GameController.getInstance().joinPlayer(Character.values()[i], "");
        }

        GameController.getInstance().dealCard();
        GameController.getInstance().decideWhoMoveFirst();
        Character currentPlayer = GameController.getInstance().getCurrentPlayer();

        Tile testTile = new Tile(0, 7);

        GameController.getInstance().movePlayer(currentPlayer, testTile);
        List<Position> positions = GameController.getInstance().getBoard().getMovablePositions(currentPlayer);

        assertEquals(1, positions.size());
    }


    /**
     * Test that the player won't ghost through walls into a room
     */
    @Test
    public void ghostThroughWall() {
        GameController.getInstance().setNumPlayers(3);
        for (int i = 0; i < 3; i++) {
            GameController.getInstance().joinPlayer(Character.values()[i], "");
        }

        GameController.getInstance().dealCard();
        GameController.getInstance().decideWhoMoveFirst();
        Character currentPlayer = GameController.getInstance().getCurrentPlayer();

        Tile testTile = new Tile(6, 3);

        GameController.getInstance().movePlayer(currentPlayer, testTile);
        List<Position> positions = GameController.getInstance().getBoard().getMovablePositions(currentPlayer);

        if (positions.contains(Board.KITCHEN))

            assertEquals(3, positions.size());
    }


    /**
     * Test that the player who made a correct accusation wins
     */
    @Test
    public void correctAccusationWins() {

        GameController.getInstance().setNumPlayers(3);
        for (int i = 0; i < 3; i++) {
            GameController.getInstance().joinPlayer(Character.values()[i], "");
        }

        GameController.getInstance().dealCard();
        GameController.getInstance().decideWhoMoveFirst();

        // skips several times, use different character for testing
        Random ran = new Random();
        int skips = ran.nextInt(20);
        for (int i = 0; i < skips; i++) {
            GameController.getInstance().currentPlayerEndTurn();
        }

        Character currentPlayer = GameController.getInstance().getCurrentPlayer();
        Suggestion solution = GameController.getInstance().getSolution();
        GameController.getInstance().setAccusation(solution);
        GameController.getInstance().checkAccusation();

        assertFalse(GameController.getInstance().isGameRunning());
        assertEquals(currentPlayer, GameController.getInstance().getWinner());
    }


    /**
     * Test that an incorrect accusation kicks the player out
     */
    @Test
    public void wrongAccuserOut() {

        GameController.getInstance().setNumPlayers(3);
        for (int i = 0; i < 3; i++) {
            GameController.getInstance().joinPlayer(Character.values()[i], "");
        }

        GameController.getInstance().dealCard();
        GameController.getInstance().decideWhoMoveFirst();

        Random ran = new Random();
        int skips = ran.nextInt(20);
        for (int i = 0; i < skips; i++) {
            GameController.getInstance().currentPlayerEndTurn();
        }

        Character currentPlayer = GameController.getInstance().getCurrentPlayer();
        Suggestion wrongAccusation = getWrongAccusation();

        GameController.getInstance().setAccusation(wrongAccusation);
        GameController.getInstance().checkAccusation();

        assertFalse(GameController.getInstance().getPlayerByCharacter(currentPlayer).isPlaying());
    }


    /**
     * The only one player left will automatically become winner
     */
    @Test
    public void lastSurvivorWin() {

        GameController.getInstance().setNumPlayers(3);
        for (int i = 0; i < 3; i++) {
            GameController.getInstance().joinPlayer(Character.values()[i], "");
        }

        GameController.getInstance().dealCard();
        GameController.getInstance().decideWhoMoveFirst();

        for (int i = 0; i < 3 - 1; i++) {
            Character currentPlayer = GameController.getInstance().getCurrentPlayer();
            Suggestion wrongAccusation = getWrongAccusation();
            assertFalse(GameController.getInstance().isGameRunning());


            GameController.getInstance().setAccusation(wrongAccusation);
            GameController.getInstance().checkAccusation();
            assertFalse(GameController.getInstance().getPlayerByCharacter(currentPlayer).isPlaying());

            GameController.getInstance().currentPlayerEndTurn();
        }

        Character currentPlayer = GameController.getInstance().getCurrentPlayer();
        assertFalse(GameController.getInstance().isGameRunning());
        assertEquals(GameController.getInstance().getWinner(), currentPlayer);
    }

    /**
     * Helper method to get a wrong accusation.
     */
    private Suggestion getWrongAccusation() {
        Random ran = new Random();

        Suggestion solution = GameController.getInstance().getSolution();

        Character c = Character.values()[ran.nextInt(Character.values().length)];
        Weapon w = Weapon.values()[ran.nextInt(Weapon.values().length)];
        Division l = Division.values()[ran.nextInt(Division.values().length)];
        Suggestion wrongAccusation = new Suggestion(c, w, l);

        // make sure this is a wrong accusation
        while (wrongAccusation.equals(solution)) {
            c = Character.values()[ran.nextInt(Character.values().length)];
            w = Weapon.values()[ran.nextInt(Weapon.values().length)];
            l = Division.values()[ran.nextInt(Division.values().length)];
            wrongAccusation = new Suggestion(c, w, l);
        }

        return wrongAccusation;
    }

}

