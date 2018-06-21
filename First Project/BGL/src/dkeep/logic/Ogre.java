package dkeep.logic;


import java.io.Serializable;

/**
 * Ogre.java -  Class that represents the ogres of the game
 */
public class Ogre extends Character implements Serializable {

    private int clubX;
    private int clubY;
    private char clubSymbol;
    private boolean stun = false;
    private int stunCounter = 0;

    /**
     * Class constructor
     *
     * @param x
     * @param y
     */
    public Ogre(int x, int y) {
        super(x, y, 'O');
        this.clubX = x - 1;
        this.clubY = y;
        this.clubSymbol = '*';
    }

    /**
     * Gets the position x of the ogre's club
     *
     * @return int clubX
     */
    public int getClubX() {
        return clubX;
    }

    /**
     * Gets the position y of the ogre's club
     *
     * @return int clubY
     */
    public int getClubY() {
        return clubY;
    }

    /**
     * Gets the char of the club
     *
     * @return char clubSymbol
     */
    public char getClubChar() {
        return clubSymbol;
    }

    /**
     * Sets the position of the ogre's club
     *
     * @param x int to be setted as club x
     * @param y int to be setted as club y
     */
    public void setClubPos(int x, int y) {
        this.clubX = x;
        this.clubY = y;
    }

    /**
     * Gets if the ogre is stuned or not
     *
     * @return boolean stun
     */
    public boolean getStun() {
        return stun;
    }

    /**
     * Stuns ogre
     */
    public void setStun() {
        stun = true;
        this.model('8');
    }

    /**
     * Sets the symbol of the ogre's club
     *
     * @param c
     */
    public void setClub(char c) {

        this.clubSymbol = c;
    }

    /**
     * Increments the number of rounds that the ogre has been stunned
     */
    public void incStunCounter() {
        stunCounter++;

        if (stunCounter == 3) {
            stun = false;
            stunCounter = 0;
            this.model('O');
        }
    }


}
