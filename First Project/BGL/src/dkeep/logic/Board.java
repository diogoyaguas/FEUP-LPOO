package dkeep.logic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Board.java - Class that represents the board of the game
 */
public class Board implements Serializable {

    private char[][] array;
    private ArrayList<int[]> ending = new ArrayList<>();
    private int lever_key;
    private char[] path = {'a', 's', 's', 's', 's', 'a', 'a', 'a', 'a', 'a', 'a', 's', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'w', 'w', 'w', 'w', 'w'};

    /**
     * Constructor of the Class
     *
     * @param board
     * @param key
     */
    public Board(char[][] board, int key) {

        array = board;
        lever_key = key;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {


                if ((array[i][j] == 'I') && ((i == 0) || (i == (board.length - 1))) || ((j == 0) || (j == (board[0].length - 1)))) {
                    int[] pos = {j, i};
                    ending.add(pos);

                }

            }
        }
    }

    /**
     * Gets the array of chars
     *
     * @return array of chars that represents the board
     */
    public char[][] getBoard() {

        return array;
    }

    /**
     * Gets the Hero on the Board
     *
     * @return the Hero
     */
    public Hero getHero() {

        Hero hero = null;

        for (int i = 0; i < getBoard().length; i++) {
            for (int j = 0; j < getBoard()[i].length; j++) {

                if (array[i][j] == 'H' || array[i][j] == 'A') {

                    hero = new Hero(j, i);

                }
            }
        }

        return hero;
    }

    /**
     * Gets all the Guards on the Board
     *
     * @return an array list of Guard
     */
    public ArrayList<Guard> getGuard() {

        Guard guard;
        ArrayList<Guard> guards = new ArrayList<>();

        for (int i = 0; i < getBoard().length; i++) {
            for (int j = 0; j < getBoard()[i].length; j++) {

                if (array[i][j] == 'G') {

                    guard = new Guard(j, i, path, "Rookie");
                    guards.add(guard);
                }
            }
        }
        return guards;
    }

    /**
     * Gets all the Doors on the Board
     *
     * @return an array list of Door
     */
    public ArrayList<Door> getDoors() {

        Door door;
        ArrayList<Door> doors = new ArrayList<>();
        for (int i = 0; i < getBoard().length; i++) {
            for (int j = 0; j < getBoard()[i].length; j++) {
                if (array[i][j] == 'I') {
                    if (i == 0 || j == 0) door = new Door(j, i, 0);
                    else door = new Door(j, i, 1);
                    doors.add(door);
                }
            }
        }
        return doors;
    }

    /**
     * Gets all the Keylevers on the Board
     *
     * @return an array list of Keylevers
     */
    public ArrayList<Keylever> getKeylevers() {

        Keylever keylever;
        ArrayList<Keylever> keylevers = new ArrayList<>();

        for (int i = 0; i < getBoard().length; i++) {
            for (int j = 0; j < getBoard()[i].length; j++) {
                if (array[i][j] == 'k') {
                    if (lever_key == 0) keylever = new Keylever(j, i, 0, false);
                    else keylever = new Keylever(j, i, 0, true);
                    keylevers.add(keylever);
                }
            }
        }
        return keylevers;
    }

    /**
     * Gets and array with the coordinates of the ending
     *
     * @return an array list of the coordinates of the end
     */
    public ArrayList<int[]> getEnd() {
        return ending;
    }

    /**
     * Gets all the Ogres on the Board
     *
     * @return an array list of Ogre
     */
    public ArrayList<Ogre> getOgres() {

        Ogre ogre;
        ArrayList<Ogre> ogres = new ArrayList<>();

        for (int i = 0; i < getBoard().length; i++) {
            for (int j = 0; j < getBoard()[i].length; j++) {

                if (array[i][j] == 'O') {

                    ogre = new Ogre(j, i);
                    ogres.add(ogre);

                }
            }
        }
        return ogres;

    }

    /**
     * Gets if the Keylever on the board is a key or a lever
     *
     * @return int lever_key
     */
    public int getLever_key() {

        return lever_key;
    }

    /**
     * Set a char on the board with the coordinates i and j
     *
     * @param i
     * @param j
     * @param c
     */
    public void model(int i, int j, char c) {

        this.array[j][i] = c;
    }

}
