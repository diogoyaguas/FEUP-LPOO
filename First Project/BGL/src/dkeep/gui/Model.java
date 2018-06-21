package dkeep.gui;

import dkeep.logic.Board;
import dkeep.logic.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static dkeep.logic.Game.State.GAME;

public class Model {

    private static char[][] board1 = {
            {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
            {'X', 'H', ' ', ' ', 'I', ' ', 'X', ' ', 'G', 'X'},
            {'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X'},
            {'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X'},
            {'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X'},
            {'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X'},
            {'X', ' ', 'I', ' ', 'I', ' ', 'X', 'k', ' ', 'X'},
            {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}};
    private static char[][] board2 = {
            {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
            {'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'k', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', 'H', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}};
    private JFrame frame;
    private int width;
    private int height;
    private BufferedImage mainMenu;
    private BufferedImage wall;
    private BufferedImage ground;
    private BufferedImage door;
    private BufferedImage door2;
    private BufferedImage doorOpen;
    private BufferedImage guard;
    private BufferedImage guardSleep;
    private BufferedImage ogre;
    private BufferedImage ogreStun;
    private BufferedImage club;
    private BufferedImage hero;
    private BufferedImage heroArmed;
    private BufferedImage lever;
    private BufferedImage leverActive;
    private BufferedImage key;
    private BufferedImage key2;

    /*
     * Game Model
     */
    private Game game = null;
    private String guardType = "Rookie";
    private int enemyNum = 0;
    private Board customMap = new Board(board2, 1);
    private Board currentMap = new Board(board1, 0);

    Model(JFrame frame) throws IOException {

        this.frame = frame;
        this.width = frame.getWidth();
        this.height = frame.getHeight();
        loadImages();
    }

    private void loadImages() throws IOException {

        this.mainMenu = ImageIO.read(new File("BGL/images/menu.png"));
        this.wall = ImageIO.read(new File("BGL/images/wall.png"));
        this.ground = ImageIO.read(new File("BGL/images/ground.png"));
        this.hero = ImageIO.read(new File("BGL/images/hero.png"));
        this.heroArmed = ImageIO.read(new File("BGL/images/heroarmed.png"));
        this.door = ImageIO.read(new File("BGL/images/door2.png"));
        this.door2 = ImageIO.read(new File("BGL/images/door.png"));
        this.doorOpen = ImageIO.read(new File("BGL/images/opendoor.png"));
        this.lever = ImageIO.read(new File("BGL/images/leveroff.png"));
        this.leverActive = ImageIO.read(new File("BGL/images/leveron.png"));
        this.guard = ImageIO.read(new File("BGL/images/guard.png"));
        this.guardSleep = ImageIO.read(new File("BGL/images/guardsleep.png"));
        this.ogre = ImageIO.read(new File("BGL/images/enemy.png"));
        this.ogreStun = ImageIO.read(new File("BGL/images/enemystun.png"));
        this.club = ImageIO.read(new File("BGL/images/power.png"));
        this.key = ImageIO.read(new File("BGL/images/key2.png"));
        this.key2 = ImageIO.read((new File("BGL/images/key.png")));

    }

    public Font getFont() {

        return new Font("Monospaced", Font.PLAIN, 16);
    }

    public void newGame() {

        game = new Game();
        
        game.ogres.clear();
        Game.setBoard(currentMap);

        game.setMove();
        game.getCharsObjects(currentMap);
        game.setGuard(guardType);
    }

    public void loadGame() {

        Game.setBoard(customMap);
        game.state = GAME;
        game.getCharsObjects(customMap);
        game.hero.setArmed();
        if (game.ogres.isEmpty()) game.setOgres(enemyNum);
        if (customMap.getBoard() == board2) game.setOgres(enemyNum);

    }

    public void deleteGame() {
        game = null;
    }

    public void updateMove(char move) {
        game.move_hero(move);
    }

    public void updateGame(char key) {
        caught();
        keyLever(key);
        nextLevel();
    }

    private void caught() {
        game.caughtByGuards();
        game.caughtByOgres();
    }

    private void keyLever(char key) {
        if (Game.board.getLever_key() == 1) {
            game.key();
            game.openDoor(0, key);
        }
        game.lever(key);
    }

    private void nextLevel() {
        game.nextLevel();
    }

    public void levelUp() {
        game.level++;
    }

    public void loadPreviousGame() {

        this.game = null;
        try {
            FileInputStream fileIn = new FileInputStream("BGL/tmp/game.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.game = (Game) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
    }

    public void setGuardType(String guardType) {
        this.guardType = guardType;
    }

    public void setEnemyNum(int enemyNum) {
        this.enemyNum = enemyNum;
    }

    public void setCustomMap(char[][] customMap) {
        this.customMap = new Board(customMap, 1);
    }

    public void setFrame(int dimension) {
        setWidth(dimension * 70 + 300);
        setHeight(dimension * 70 + 47);
        frame.setSize(width, height);
    }

    public Game getGame() {
        return game;
    }

    public int getWidth() {
        return width;
    }

    private void setWidth(int width) {
        this.width = width;
    }

    public Game.State getGameState() {
        return game.state;
    }

    public int getHeight() {
        return height;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    public int getLevel() {
        return game.level;
    }

    public int getMapDimension() {
        return Game.board.getBoard().length;
    } 
    
    public int getMenuDimension(){
    	return board1.length;
    }

    public Board getCurrentMap() {
        return currentMap;
    }

    // Get Images Functions

    public BufferedImage getMainMenu() {
        return mainMenu;
    }

    public BufferedImage getWall() {
        return wall;
    }

    public BufferedImage getGround() {
        return ground;
    }

    public BufferedImage getDoor() {
        return door;
    }

    public BufferedImage getDoorOpen() {
        return doorOpen;
    }

    public BufferedImage getGuard() {
        return guard;
    }

    public BufferedImage getGuardSleep() {
        return guardSleep;
    }

    public BufferedImage getOgre() {
        return ogre;
    }

    public BufferedImage getOgreStun() {
        return ogreStun;
    }

    public BufferedImage getClub() {
        return club;
    }

    public BufferedImage getHero() {
        return hero;
    }

    public BufferedImage getHeroArmed() {
        return heroArmed;
    }

    public BufferedImage getLever() {
        return lever;
    }

    public BufferedImage getLeverActive() {
        return leverActive;
    }

    public BufferedImage getKey() {
        return key;
    }

    public BufferedImage getDoor2() {
        return door2;
    }

    public BufferedImage getKey2() {
        return key2;
    }
}
