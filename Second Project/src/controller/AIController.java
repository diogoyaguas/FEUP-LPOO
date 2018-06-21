package com.cluedo.game.controller;

import com.badlogic.gdx.Game;
import com.cluedo.game.model.Card.Card;
import com.cluedo.game.model.Card.Character;
import com.cluedo.game.model.Card.Division;
import com.cluedo.game.model.Card.Weapon;
import com.cluedo.game.model.Player;
import com.cluedo.game.model.Suggestion;
import com.cluedo.game.model.Tile.Entrance;
import com.cluedo.game.model.Tile.Room;
import com.cluedo.game.model.Tile.Tile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class AIController {

    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<Integer> path = new ArrayList<>();
    private ArrayList<Room> visitedRooms = new ArrayList<>();
    private ArrayList<Weapon> unknownWeaponsCards;
    private ArrayList<Division> unknownDivisionsCards;
    private ArrayList<Character> unknownCharactersCards;

    private String name;
    public Character player;
    private Random rand = new Random();

    /**
     * class constructor
     */
    public AIController() {

        addName();

        name = chooseName();

        joinPlayer();

        unknownWeaponsCards = new ArrayList<>(GameController.getInstance().getWeapons());
        unknownDivisionsCards = new ArrayList<>(GameController.getInstance().getDivisions());
        unknownCharactersCards = new ArrayList<>(GameController.getInstance().getCharacters());

    }

    /**
     * add a player to game
     */
    private void joinPlayer() {

        ArrayList<Character> playableCharacters = GameController.getInstance().getPlayableCharacters();

        int playerNumber = 0;

        if (!playableCharacters.isEmpty())
            playerNumber = rand.nextInt(playableCharacters.size());

        GameController.getInstance().joinPlayer(playableCharacters.get(playerNumber), name);
        this.player = playableCharacters.get(playerNumber);
    }

    /**
     * choose ai name from pool
     * @return
     */
    private String chooseName() {

        int nameNumber = rand.nextInt(names.size());

        return names.get(nameNumber);
    }

    /**
     * add names to the pool
     */
    private void addName() {

        names.add("Mário");
        names.add("Maria");
        names.add("Joaquim");
        names.add("Beatriz");
        names.add("Daniel");
        names.add("João");
    }

    /**
     * looks for the closest exit from the actual player
     * @return closest entrance nearest to the player
     */
    private Entrance lookForClosestEntrance() {

        ArrayList<Entrance> entrances = GameController.getInstance().getBoard().getAllEntrances();

        removeVisitedEntrances(entrances);

        Player actual = GameController.getInstance().getPlayerByCharacter(player);
        Tile playerTile = (Tile) actual.getPosition();

        Entrance closest = entrances.get(0);
        int x = playerTile.x;
        int y = playerTile.y;
        double dest = sqrt((x - entrances.get(0).x) * (x - entrances.get(0).x) + (y - entrances.get(0).y) * (y - entrances.get(0).y));
        double temp;

        if(!entrances.isEmpty()) {

            for (int i = 1; i < entrances.size(); i++) {

                temp = sqrt((x - entrances.get(i).x) * (x - entrances.get(i).x) + (y - entrances.get(i).y) * (y - entrances.get(i).y));

                if (visitedRooms.isEmpty()) {

                    if (temp < dest) {
                        closest = entrances.get(i);
                        dest = temp;
                    }
                } else {
                    for (Room r : visitedRooms) {

                        if (r != entrances.get(i).toRoom()) {

                            if (temp < dest) {
                                closest = entrances.get(i);
                                dest = temp;
                            }
                        }
                    }
                }
            }
        }
        return closest;
    }

    /**
     * resets the number of visited entrances
     * @param entrances
     */
    private void removeVisitedEntrances(ArrayList<Entrance> entrances) {

        if(visitedRooms.size() == 5) visitedRooms.clear();

        Iterator<Room> iterRoom = visitedRooms.iterator();
        Iterator<Entrance> iterEntrance = entrances.iterator();

        while (iterEntrance.hasNext()) {

            Entrance entrance = iterEntrance.next();

            while (iterRoom.hasNext()) {

                Room room = iterRoom.next();

                if (room == entrance.toRoom()) {

                    iterEntrance.remove();
                }

            }

            iterRoom = visitedRooms.iterator();
        }
    }

    /**
     * calculates the path for the ai
     */
    private void calculatePath() {

        ArrayList<Integer> path = new ArrayList<>();
        Entrance entrance = lookForClosestEntrance();
        Player actual = GameController.getInstance().getPlayerByCharacter(player);
        Tile playerTile = (Tile) actual.getPosition();
        int x = playerTile.x;
        int y = playerTile.y;

        this.clearPath();

        int[][] map = GameController.getInstance().getBoard().parseMap();

        System.out.println(x);
        System.out.println(y);

        while (x != entrance.x || y != entrance.y) {

            if (abs(x - entrance.x) < abs(y - entrance.y)) {

                if (y > entrance.y) {
                    if (map[y - 1][x] == 1 || map[y - 1][x] == 2) {
                        y--;
                        movePath(1, map, path, x, y);
                    } else {
                        if (x < entrance.x) {
                            if (map[y][x + 1] == 1 || map[y][x + 1] == 2) {
                                x++;
                                movePath(2, map, path, x, y);
                            } else {
                                while (!(map[y - 1][x] == 1 || map[y - 1][x] == 2) && (map[y][x - 1] == 1 || map[y][x - 1] == 2)) {
                                    x--;
                                    movePath(4, map, path, x, y);
                                }
                            }
                        } else {
                            if (map[y][x - 1] == 1 || map[y][x - 1] == 2) {
                                x--;
                                movePath(4, map, path, x, y);
                            } else {
                                while (!(map[y - 1][x] == 1 || map[y - 1][x] == 2) && (map[y][x + 1] == 1 || map[y][x + 1] == 2)) {
                                    x++;
                                    movePath(2, map, path, x, y);
                                }
                            }
                        }
                    }
                } else {
                    if (map[y + 1][x] == 1 || map[y + 1][x] == 2) {
                        y++;
                        movePath(3, map, path, x, y);
                    } else {
                        if (x < entrance.x) {
                            if (map[y][x + 1] == 1 || map[y][x + 1] == 2) {
                                x++;
                                movePath(2, map, path, x, y);

                            } else {
                                while (!(map[y + 1][x] == 1 || map[y + 1][x] == 2) && (map[y][x - 1] == 1 || map[y][x - 1] == 2)) {
                                    x--;
                                    movePath(4, map, path, x, y);

                                }
                            }
                        } else {
                            if (map[y][x - 1] == 1 || map[y][x - 1] == 2) {
                                x--;
                                movePath(4, map, path, x, y);
                            } else {
                                while (!(map[y + 1][x] == 1 || map[y + 1][x] == 2) && (map[y][x + 1] == 1 || map[y][x + 1] == 2)) {
                                    x++;
                                    movePath(2, map, path, x, y);
                                }
                            }
                        }
                    }
                }


            } else {
                if (x > entrance.x) {
                    if (map[y][x - 1] == 1 || map[y][x - 1] == 2) {
                        x--;
                        movePath(4, map, path, x, y);
                    } else {
                        if (y > entrance.y) {
                            if (map[y - 1][x] == 1 || map[y - 1][x] == 2) {
                                y--;
                                movePath(1, map, path, x, y);
                            } else {
                                while (!(map[y][x + 1] == 1 || map[y][x + 1] == 2) && (map[y + 1][x] == 1 || map[y + 1][x] == 2)) {
                                    y++;
                                    movePath(3, map, path, x, y);
                                }
                            }
                        } else {
                            if (map[y + 1][x] == 1 || map[y + 1][x] == 2) {
                                y++;
                                movePath(3, map, path, x, y);

                            } else {
                                while (!(map[y][x - 1] == 1 || map[y][x - 1] == 2) && (map[y - 1][x] == 1 || map[y - 1][x] == 2)) {
                                    y--;
                                    movePath(1, map, path, x, y);
                                }
                            }
                        }
                    }
                } else {
                    if (map[y][x + 1] == 1 || map[y][x + 1] == 2) {
                        x++;
                        movePath(2, map, path, x, y);
                    } else {
                        if (y > entrance.y) {
                            if (map[y - 1][x] == 1 || map[y - 1][x] == 2) {
                                y--;
                                movePath(1, map, path, x, y);
                            } else {
                                while (!(map[y][x + 1] == 1 || map[y][x + 1] == 2) && (map[y + 1][x] == 1 || map[y + 1][x] == 2)) {
                                    y++;
                                    movePath(3, map, path, x, y);
                                }
                            }
                        } else {
                            if (map[y + 1][x] == 1 || map[y + 1][x] == 2) {
                                y++;
                                movePath(3, map, path, x, y);

                            } else {
                                while (!(map[y][x + 1] == 1 || map[y][x + 1] == 2) && (map[y - 1][x] == 1 || map[y - 1][x] == 2)) {
                                    y--;
                                    movePath(1, map, path, x, y);
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println(x);
        System.out.println(y);

        this.path = path;
    }

    /**
     * inserts the move on the path
     * @param direction
     * @param map
     * @param path
     * @param x
     * @param y
     */
    private void movePath(int direction, int[][] map, ArrayList<Integer> path, int x, int y) {

        switch (direction) {
            case 1:
                map[y + 1][x] = 3;
                break;
            case 2:
                map[y + 1][x - 1] = 3;
                break;
            case 3:
                map[y - 1][x] = 3;
                break;
            case 4:
                map[y][x + 1] = 3;
                break;

        }
        path.add(direction);

    }

    /**
     * returns the path
     * @return path
     */
    public ArrayList<Integer> getPath() {

        if (this.path.isEmpty()) {

            calculatePath();
        }

        return path;
    }

    /**
     * sets an suggestion
     */
    public void setSuggestion() {

        if(!setAccusation()) {

            setAccusedCharacter();

            setAccusedDivision();

            setAccusedWeapon();

        }

    }

    /**
     * sets an accusation
     */
    private boolean setAccusation() {

        if(GameController.getInstance().getKnownCards().size() == 18){

            GameController.getInstance().setAccusedDivison(unknownDivisionsCards.get(0));
            GameController.getInstance().setAccusedWeapon(unknownWeaponsCards.get(0));
            GameController.getInstance().setAccusedCharacter(unknownCharactersCards.get(0));


        } return false;
    }

    /**
     * sets the weapon for the accusation/suggestion
     */
    private void setAccusedWeapon() {

        Iterator<Weapon> weaponIterator = unknownWeaponsCards.iterator();

        while (weaponIterator.hasNext()) {

            Weapon w = weaponIterator.next();

            if (GameController.getInstance().getKnownCards().contains(w)) {

                weaponIterator.remove();
            }
        }

        if (!unknownWeaponsCards.isEmpty()) {
            int weaponNumber = rand.nextInt(unknownWeaponsCards.size());
            GameController.getInstance().setAccusedWeapon(unknownWeaponsCards.get(weaponNumber));
        }
    }

    /**
     * sets the division for the accusation/suggestion
     */
    private void setAccusedDivision() {

        Iterator<Division> divisionIterator = unknownDivisionsCards.iterator();

        while (divisionIterator.hasNext()) {

            Division d = divisionIterator.next();

            if (GameController.getInstance().getKnownCards().contains(d)) {

                divisionIterator.remove();
            }
        }

        if (!unknownDivisionsCards.isEmpty()) {
            int divisionNumber = rand.nextInt(unknownDivisionsCards.size());
            GameController.getInstance().setAccusedDivison(unknownDivisionsCards.get(divisionNumber));
        }

    }

    /**
     * sets the character for the accusation/suggestion
     */
    private void setAccusedCharacter() {

        Iterator<Character> charactersIterator = unknownCharactersCards.iterator();

        while (charactersIterator.hasNext()) {

            Character c = charactersIterator.next();

            if (GameController.getInstance().getKnownCards().contains(c)) {

                charactersIterator.remove();
            }
        }

        if (!unknownCharactersCards.isEmpty()) {
            int characterNumber = rand.nextInt(unknownCharactersCards.size());
            GameController.getInstance().setAccusedCharacter(unknownCharactersCards.get(characterNumber));
        }
    }

    /**
     * returns the visited rooms
     * @return visitedRooms
     */
    public ArrayList<Room> getVisitedRooms() {

        return this.visitedRooms;
    }

    /**
     * resets the path ArrayList
     */
    public void clearPath() {

        path.clear();
    }

}
