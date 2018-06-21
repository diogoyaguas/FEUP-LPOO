package dkeep.logic;

import java.io.Serializable;

/**
 * Hero.java -  Class that represents the hero of the game
 */
public class Hero extends Character implements Serializable {

    private boolean haveKey;
    private boolean armed;

    /**
     * Class Constructor
     *
     * @param x
     * @param y
     */
    public Hero(int x, int y) {

        super(x, y, 'H');
        this.armed = false;
        this.haveKey = false;
    }

    /**
     * Sets if the hero have a key or not
     */
    public void setHaveKey() {

        haveKey = !haveKey;
    }

    /**
     * Sets if the hero is armed or not
     */
    public void setArmed() {

        armed = !armed;
        this.model('A');
    }

    /**
     * Gets the if the hero have a key or not
     *
     * @return boolean haveKey
     */
    public boolean getKey() {

        return haveKey;
    }

    /**
     * Gets if the hero is armed or not
     *
     * @return boolean armed
     */
    public boolean getArmed() {

        return armed;
    }

}
