package dkeep.logic;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Guard.java -  Class that represents the guard og the game
 */
public class Guard extends Character implements Serializable {

    private char[] path;
    private String personality;
    private int sleep = 0;
    private int order = 0;
    private boolean reverse = false;

    /**
     * Class constructor
     *
     * @param x
     * @param y
     * @param pth
     * @param per
     */
    public Guard(int x, int y, char[] pth, String per) {
        super(x, y, 'G');
        path = pth;
        personality = per;
    }

    private void setSleep() {

        sleep = ThreadLocalRandom.current().nextInt(0, 5);
        model('g');

    }

    private void setReverse() {

        reverse = !reverse;
        nextMove();
    }

    /**
     * Sets the personality of the guard
     *
     * @param c
     */
    public void setPersonality(String c) {
        personality = c;
    }

    private void nextMove() {

        if (reverse) {

            if (order == 0) {
                order = path.length - 1;
            } else
                order--;

        } else {

            if (order == path.length - 1) {
                order = 0;
            } else
                order++;
        }
    }

    private int getOrder() {
        return order;
    }

    /**
     * Gets the guard sleep
     *
     * @return
     */
    public int getSleep() {
        return sleep;
    }

    /**
     * Gets if the guard direction
     *
     * @return
     */
    public boolean getReverse() {

        return reverse;
    }

    private char[] getPath() {
        return path;
    }

    /**
     * Sets the path of the guard
     *
     * @param npath
     */
    public void setPath(char[] npath) {
        path = npath;
    }

    /**
     * Move the guard
     */
    public void move() {

        setRandom();

        char o = getPath()[getOrder()];

        if (sleep > 0) {
            sleep--;
            return;
        }

        model('G');

        if (reverse) moveReverse(o);
        else moveNormally(o);

        nextMove();
    }

    private void moveNormally(char o) {
        switch (o) {
            case 'w':
                this.setpos(getx(), gety() - 1);
                break;
            case 's':
                this.setpos(getx(), gety() + 1);
                break;
            case 'd':
                this.setpos(getx() + 1, gety());
                break;
            case 'a':
                this.setpos(getx() - 1, gety());
                break;
        }
    }

    private void moveReverse(char o) {
        switch (o) {
            case 'w':
                this.setpos(getx(), gety() + 1);
                break;
            case 's':
                this.setpos(getx(), gety() - 1);
                break;
            case 'd':
                this.setpos(getx() - 1, gety());
                break;
            case 'a':
                this.setpos(getx() + 1, gety());
                break;
        }
    }

    private void setRandom() {

        int s = ThreadLocalRandom.current().nextInt(0, 10);

        if (s == 0) {
            switch (personality) {

                case "Suspicious":
                    setReverse();
                    break;

                case "Drunken":
                    if (sleep == 0) {
                        setSleep();

                    }
                    break;
                case "Rookie":
                    break;
            }
        }
    }
}
