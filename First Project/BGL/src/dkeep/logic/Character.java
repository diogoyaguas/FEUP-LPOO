package dkeep.logic;

import java.io.Serializable;

/**
 * Character.java -  Class that represents the characters of the game
 */
public abstract class Character implements Serializable {

    private int pos_x;
    private int pos_y;
    private char piece;

    /**
     * Class constructor
     *
     * @param x
     * @param y
     * @param c
     */
    Character(int x, int y, char c) {

        this.pos_x = x;
        this.pos_y = y;
        this.piece = c;
    }

    /**
     * Set the position of the Character to x and y
     *
     * @param x
     * @param y
     */
    public void setpos(int x, int y) {
        this.pos_x = x;
        this.pos_y = y;
    }

    /**
     * Set the char of the Character to c
     *
     * @param c
     */
    public void model(char c) {
        piece = c;
    }

    /**
     * Gets the position x of the Character
     *
     * @return int pos_x
     */
    public int getx() {
        return pos_x;
    }

    /**
     * Gets the position y of the Character
     *
     * @return int pos_y
     */
    public int gety() {
        return pos_y;
    }

    /**
     * Gets the char of the Character
     *
     * @return char piece
     */
    public char getc() {
        return piece;
    }

}