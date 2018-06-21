package dkeep.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static dkeep.logic.Game.State.*;

/**
 * Game.java -  Class that represents the game
 */
public class Game implements Serializable {

    public static Board board;
    public Hero hero;
    public State state;
    public ArrayList<Door> doors = new ArrayList<>();
    public ArrayList<Keylever> keylevers = new ArrayList<>();
    public ArrayList<Guard> guards = new ArrayList<>();
    public ArrayList<Ogre> ogres = new ArrayList<>();
    public int level;
    private boolean move = false;
    private boolean unlocked = false;

    /**
     * Class constructor
     */
    public Game() {

        this.state = GAME;
        this.level = 1;
    }

    /**
     * Sets the game board
     *
     * @param board
     */
    public static void setBoard(Board board) {
        Game.board = board;
    }

    /**
     * Sets the guard personality
     *
     * @param guard
     */
    public void setGuard(String guard) {

        for (Guard g : guards) {
            g.setPersonality(guard);
        }
    }

    /**
     * Sets the number of ogres
     *
     * @param ogres
     */
    public void setOgres(int ogres) {
        this.ogres.clear();
        createOgres(ogres);
    }

    /**
     * Gets the game state
     *
     * @return state
     */
    public State getState() {
        return state;
    }

    /**
     * Gets all the objects on the game board
     *
     * @param board
     */
    public void getCharsObjects(Board board) {

        doors = board.getDoors();
        hero = board.getHero();
        if (level == 1) guards = board.getGuard();
        keylevers = board.getKeylevers();
        if (level == 2) {
            ogres = board.getOgres();
            guards.clear();
        }

    }

    /**
     * Display the board
     *
     * @return string with the board
     */
    public String display() {
        StringBuilder map = new StringBuilder();
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                map.append(displayOgres(i, j));
                if (!displayOgres(i, j).isEmpty()) continue;
                map.append(displayClubs(i, j));
                if (!displayClubs(i, j).isEmpty()) continue;
                map.append(displayGuards(i, j));
                if (!displayGuards(i, j).isEmpty()) continue;
                map.append(displayHero(i, j));
                if (!displayHero(i, j).isEmpty()) continue;
                map.append(displayKeylevers(i, j));
                if (!displayKeylevers(i, j).isEmpty()) continue;
                map.append(displayDoors(i, j));
                if (!displayDoors(i, j).isEmpty()) continue;
                map.append(displayWalls(i, j));
            }
            map.append('\n');
        }
        System.out.print(map);
        return map.toString();
    }

    private String displayOgres(int i, int j) {

        StringBuilder map = new StringBuilder();

        for (Ogre o : ogres) {
            if (j == o.getx() && i == o.gety()) {
                map.append(o.getc());
                map.append(" ");
            }
        }
        return map.toString();
    }

    private String displayClubs(int i, int j) {

        StringBuilder map = new StringBuilder();

        for (Ogre o : ogres) {
            if (j == o.getClubX() && i == o.getClubY()) {
                if (o.getStun() || ((o.getClubX() == 0 || ogres.get(0).getClubX() == 9) || (o.getClubY() == 0 || ogres.get(0).getClubY() == 9)))
                    continue;
                map.append(o.getClubChar());
                map.append(" ");
            }
        }
        return map.toString();
    }

    private String displayGuards(int i, int j) {

        StringBuilder map = new StringBuilder();

        for (Guard g : guards) {
            if (j == g.getx() && i == g.gety()) {
                map.append(g.getc());
                map.append(" ");
            }
        }
        return map.toString();
    }

    private String displayHero(int i, int j) {

        String map = "";

        if (hero != null) {
            if (j == hero.getx() && i == hero.gety()) {
                map += hero.getc();
                map += " ";
            }
        }
        return map;
    }

    private String displayKeylevers(int i, int j) {

        StringBuilder map = new StringBuilder();

        for (Keylever k : keylevers) {
            if (j == k.getx() && i == k.gety()) {
                map.append('k');
                map.append(" ");
            }
        }
        return map.toString();
    }

    private String displayDoors(int i, int j) {

        StringBuilder map = new StringBuilder();

        for (Door s : doors) {

            if (j == s.getx() && i == s.gety()) {
                map.append(s.getChar());
                map.append(' ');
            }
        }
        return map.toString();
    }

    private String displayWalls(int i, int j) {
        String map = "";

        if (board.getBoard()[i][j] != 'X') {
            map += " ";
            map += " ";
        } else {
            if (j == board.getBoard()[i].length - 1)
                map += 'X';
            else {
                map += 'X';
                map += " ";
            }
        }
        return map;
    }

    /**
     * Move the hero and the enemies
     *
     * @param key
     */
    public void move_hero(char key) {

        int x = hero.getx();
        int y = hero.gety();
        switch (key) {
            case 'w':
                heroCondition(x, y - 1);
                break;
            case 's':
                heroCondition(x, y + 1);
                break;
            case 'd':
                heroCondition(x + 1, y);
                break;
            case 'a':
                heroCondition(x - 1, y);
                break;
        }
        updateOgres();
        if (move) {
            moveGuard();
            moveOgres();
        }
    }

    private void heroCondition(int x, int y) {

        if (!((board.getBoard()[y][x] == 'I' && !isDoorOpen(x, y)) || board.getBoard()[y][x] == 'X' || (board.getBoard()[y][x] == 'k' && board.getLever_key() == 0)) && !(isEnemyBlocking(x, y))) {
            hero.setpos(x, y);
        }
    }

    private boolean isEnemyBlocking(int x, int y) {
        if (level == 1) {
            for (Guard g : guards) {
                if (x == g.getx() && y == g.gety()) return true;
            }
        } else {
            for (Ogre o : ogres) {
                if (x == o.getx() && y == o.gety()) return true;
            }
        }
        return false;
    }

    private boolean isDoorOpen(int x, int y) {
        for (Door d : doors) {
            if (d.getx() == x && d.gety() == y) return d.getOpen();
        }
        return false;
    }

    private void moveGuard() {
        if (guards.isEmpty())
            return;

        for (Guard guard : guards) {
            guard.move();
        }
    }

    private void moveOgres() {

        for (int o = 0; o < ogres.size(); o++) {
            int i = 0;
            if (ogres.get(o).getStun()) {
                ogres.get(o).incStunCounter();
                continue;
            }
            while (i < 2) {
                changePos(i, o);
                changeChar(i, o);
                i++;
            }
        }
    }

    private void changePos(int i, int o) {

        int x = ogres.get(o).getx();
        int y = ogres.get(o).gety();
        int rand = ThreadLocalRandom.current().nextInt(0, 4);
        switch (rand) {
            case 0:
                ogreCondition(o, i, x, y - 1);
                break;
            case 1:
                ogreCondition(o, i, x, y + 1);
                break;
            case 2:
                ogreCondition(o, i, x + 1, y);
                break;
            case 3:
                ogreCondition(o, i, x - 1, y);
                break;
        }
    }

    private void ogreCondition(int o, int i, int x, int y) {

        char[][] map = board.getBoard();
        char c = hero.getc();
        if (map[y][x] != 'X' && map[y][x] != 'I' && map[y][x] != 'S' && map[y][x] != c)
            if (i == 0) ogres.get(o).setpos(x, y);
            else ogres.get(o).setClubPos(x, y);
    }

    private void changeChar(int i, int o) {
        if (i == 0) {
            changeOgreChar(o);

        } else {
            changeClubChar(o);
        }
    }

    private void changeOgreChar(int o) {

        for (Keylever keylever : keylevers) {
            int x = ogres.get(o).getx(), y = ogres.get(o).gety();
            if (x == keylever.getx() && y == keylever.gety()) {
                Ogre ogre = ogres.get(o);
                ogre.model('$');
                ogres.set(o, ogre);
            } else {
                Ogre ogre = ogres.get(o);
                ogre.model('O');
                ogres.set(o, ogre);
            }
        }
    }

    private void changeClubChar(int o) {

        for (Keylever keylever : keylevers) {
            int x = ogres.get(o).getClubX(), y = ogres.get(o).getClubY();
            if (x == keylever.getx() && y == keylever.gety()) {
                Ogre ogre = ogres.get(o);
                ogre.setClub('$');
                ogres.set(o, ogre);
            } else {
                Ogre ogre = ogres.get(o);
                ogre.setClub('*');
                ogres.set(o, ogre);
            }
        }
    }

    private void updateOgres() {

        for (Ogre o : ogres) {
            if (hero.getArmed()) {

                if (hero.getx() == o.getx()) {
                    if (hero.gety() == o.gety() - 1 || hero.gety() == o.gety() + 1)
                        o.setStun();
                }

                if (hero.gety() == o.gety()) {
                    if (hero.getx() == o.getx() - 1 || hero.getx() == o.getx() + 1)
                        o.setStun();
                }
            }

        }
    }

    /**
     * Checks if the position of the hero is in collision with the guard
     */
    public void caughtByGuards() {

        for (Guard g : guards) {
            if ((g.getc() == 'G') && (g.getx() == hero.getx()) && ((g.gety() == (hero.gety() - 1)) || (g.gety() == (hero.gety() + 1)))) {

                this.state = DEFEAT;
            } else if ((g.getc() == 'G') && ((g.gety() == hero.gety()) && ((g.getx() == (hero.getx() - 1)) || (g.getx() == (hero.getx() + 1))))) {

                this.state = DEFEAT;
            } else if ((g.getc() == 'G') && ((g.getx() == hero.getx()) && (g.gety() == hero.gety()))) {

                this.state = DEFEAT;
            }
        }
    }

    /**
     * Checks if the position of the hero is in collision with the ogre
     */
    public void caughtByOgres() {

        for (Ogre o : ogres) {

            int ox = o.getx(), oy = o.gety();

            int cx = o.getClubX(), cy = o.getClubY();

            if (!hero.getArmed()) {

                caughtCondition(ox, oy);
            }

            if (!o.getStun()) {
                caughtCondition(cx, cy);
            }
        }
    }

    private void caughtCondition(int ox, int oy) {

        int hx = hero.getx(), hy = hero.gety();
        if (((ox == hx) && ((oy == (hy - 1)) || (oy == (hy + 1)))) || ((oy == hy) && ((ox == (hx - 1)) || (ox == (hx + 1))))
                || ((ox == hx) && (oy == hy))) this.state = DEFEAT;
    }

    /**
     * Checks if the hero is activating the lever
     *
     * @param key
     */
    public void lever(char key) {

        for (int i = 0; i < keylevers.size(); i++) {
            int kx = keylevers.get(i).getx();
            int ky = keylevers.get(i).gety();
            if (doorCondition(key, kx, ky)) openDoor(i, key);
        }
    }

    /**
     * Checks if the hero gets the key
     */
    public void key() {

        for (int i = 0; i < keylevers.size(); i++) {

            if (keylevers.get(i).getx() == hero.getx() && keylevers.get(i).gety() == hero.gety() && keylevers.get(i).get_isKey()) {

                keylevers.remove(i);
                hero.model('K');
                hero.setHaveKey();

            }
        }
    }

    /**
     * Open the doors of the board
     *
     * @param i
     * @param key
     */
    public void openDoor(int i, char key) {

        switch (board.getLever_key()) {

            case 0:
                keylevers.get(i).setActive();
                openSimpleDoor(i);
                break;

            case 1:
                openComplexDoor(key);
                break;
        }
    }

    private void openSimpleDoor(int i) {

        for (int j = 0; j < doors.size(); j++) {

            if (keylevers.get(i).getCode() == doors.get(j).getCode()) {

                door(j);

            }
        }
    }

    private void openComplexDoor(char key) {

        if (hero.getKey()) for (int j = 0; j < doors.size(); j++) {
            int x = doors.get(j).getx();
            int y = doors.get(j).gety();
            if (doorCondition(key, x, y)) {
                if (unlocked) door(j);
                else unlocked = true;
            }
        }
    }

    private boolean doorCondition(char key, int x, int y) {

        switch (key) {
            case 'a':
                if (x == hero.getx() - 1 && y == hero.gety()) return true;
                break;
            case 'w':
                if (x == hero.getx() && y == hero.gety() - 1) return true;
                break;
            case 'd':
                if (x == hero.getx() + 1 && y == hero.gety()) return true;
                break;
            case 's':
                if (x == hero.getx() && y == hero.gety() + 1) return true;
                break;
        }
        return false;
    }

    /**
     * Checks if the game reunite all the conditions to go to next level
     */
    public void nextLevel() {

        for (int i = 0; i < board.getEnd().size(); i++) {

            if (hero.getx() == board.getEnd().get(i)[0] && hero.gety() == board.getEnd().get(i)[1]) {


                this.state = WIN;
            }
        }

    }

    private void door(int j) {

        unlocked = false;

        Door door = doors.get(j);
        door.open();
        doors.set(j, door);
    }

    private void createOgres(int i) {

        int o;
        if (board == null) return;
        int maxOgres = (board.getBoard().length - 2) * (board.getBoard()[0].length - 2) / 10;
        if (maxOgres == 0) maxOgres++;
        if (maxOgres > 5) maxOgres = 5;
        if (i == 0) o = ThreadLocalRandom.current().nextInt(1, 1 + maxOgres);
        else o = i;

        while (o > 0) {

            int x = ThreadLocalRandom.current().nextInt(1, board.getBoard().length);
            int y = ThreadLocalRandom.current().nextInt(1, board.getBoard()[x].length);

            if (board.getBoard()[x][y] == ' ' || board.getBoard()[x][y] == 'O') {
                board.model(x, y, 'O');
                Ogre ogre = new Ogre(x, y);
                ogres.add(ogre);
                o--;
            }
        }
    }

    /**
     * Update all the chars on the board
     */
    public void updateMap() {

        board.model(hero.gety(), hero.getx(), 'H');
        for (Door d : doors) {
            board.model(d.gety(), d.getx(), d.getChar());
        }
        for (Keylever k : keylevers) {
            board.model(k.gety(), k.getx(), 'k');
        }
        for (Ogre o : ogres) {
            board.model(o.gety(), o.getx(), 'O');
        }
        for (Guard g : guards) {
            board.model(g.gety(), g.getx(), 'G');
        }
    }

    /**
     * Sets if the enemies can move
     */
    public void setMove() {

        move = !move;
    }

    /**
     * Gets if the hero can unlock the door
     *
     * @return boolean unlocked
     */
    public boolean getUnlocked() {
        return unlocked;
    }

    /**
     * States of the game
     */
    public enum State {
        GAME, WIN, DEFEAT, QUIT
    }
}
