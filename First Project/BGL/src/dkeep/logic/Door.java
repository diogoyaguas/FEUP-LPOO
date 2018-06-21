package dkeep.logic;

import java.io.Serializable;

/**
 * Door.java -  Class that represents the doors of the game
 */
public class Door extends Object implements Serializable {

    private boolean open = false;

    /**
     * Class constructor
     *
     * @param x
     * @param y
     * @param c
     */
    public Door(int x, int y, int c) {
        super(x, y, c);
        this.model('I');
    }

    /**
     * Function that open and close the door
     */
    public void open() {
        open = !open;
        if (open) this.model('S');
        else this.model('I');
    }

    /**
     * Gets if the door is open or closed
     *
     * @return boolean open
     */
    public boolean getOpen() {
        return open;
    }
}
