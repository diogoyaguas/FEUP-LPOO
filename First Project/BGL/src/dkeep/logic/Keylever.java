package dkeep.logic;

import java.io.Serializable;

/**
 * Keylever.java -  Class that represents the keylevers of the game
 */
public class Keylever extends Object implements Serializable {

    private boolean isKey;
    private boolean active = false;

    /**
     * Class constructor
     *
     * @param x
     * @param y
     * @param c
     * @param k
     */
    public Keylever(int x, int y, int c, boolean k) {

        super(x, y, c);
        this.model('k');
        isKey = k;
    }

    /**
     * Gets if is a key or a lever
     *
     * @return boolean isKey
     */
    public boolean get_isKey() {

        return isKey;
    }

    /**
     * Gets if the lever is activate or not
     *
     * @return boolean active
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Activate or deactivate lever
     */
    public void setActive() {
        active = !active;
    }

}
