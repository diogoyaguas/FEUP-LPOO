package dkeep.logic;

import java.io.Serializable;

/**
 * Object.java -  Class that represents the objects of the game
 */
public class Object implements Serializable {

    private int pos_x;
    private int pos_y;
    private int code;
    private char piece;

    /**
     * Class constructor
     *
     * @param x
     * @param y
     * @param c
     */
    Object(int x, int y, int c) {

        pos_x = x;
        pos_y = y;
        code = c;

    }

    /**
     * Gets the position x of the object
     *
     * @return int pos_x
     */
    public int getx() {
        return pos_x;
    }

    /**
     * Gets the position y of the game
     *
     * @return int pos_y
     */
    public int gety() {
        return pos_y;
    }

    /**
     * Gets code of the object
     *
     * @return int code
     */
    public int getCode() {
        return code;
    }

    /**
     * Gets the char of the object
     *
     * @return char piece
     */
    public char getChar() {
        return piece;
    }

    /**
     * Sets the piece
     *
     * @param p
     */
    public void model(char p) {
        piece = p;
    }
}	
