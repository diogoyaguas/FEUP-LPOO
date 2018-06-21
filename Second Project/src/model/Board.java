package com.cluedo.game.model;

import com.cluedo.game.controller.GameController;
import com.cluedo.game.model.Card.Character;
import com.cluedo.game.model.Card.Division;
import com.cluedo.game.model.Tile.Entrance;
import com.cluedo.game.model.Tile.Position;
import com.cluedo.game.model.Tile.Room;
import com.cluedo.game.model.Tile.RoomTile;
import com.cluedo.game.model.Tile.Tile;

import java.util.ArrayList;
import java.util.Iterator;


public class Board {

    public static final int MIN_PLAYER = 3;

    public static final int MAX_PLAYER = 6;

    public static final int BOARD_WIDTH = 24;

    public static final int BOARD_HEIGHT = 25;

    public static final Room KITCHEN = new Room(Division.Kitchen, Division.Study);

    public static final Room BALL_ROOM = new Room(Division.Ballroom, null);

    public static final Room CONSERVATORY = new Room(Division.Conservatory, Division.Lounge);

    private static final Room BILLIARD_ROOM = new Room(Division.Billiard_Room, null);

    private static final Room LIBRARY = new Room(Division.Library, null);

    private static final Room STUDY = new Room(Division.Study, Division.Kitchen);

    public static final Room HALL = new Room(Division.Hall, null);

    public static final Room LOUNGE = new Room(Division.Lounge, Division.Conservatory);

    private static final Room DINNING_ROOM = new Room(Division.Dinning_Room, null);

    private ArrayList<Entrance> allEntrances = new ArrayList<>();

    private static final String MAP =
            "         #    $         \n" +
                    "       000    000       \n" +
                    " 1111 00   22   00 3333 \n" +
                    " 1111 00 222222 00 3333 \n" +
                    " 1111 00 222222 00 3333 \n" +
                    "  111 0b 222222 b0c     \n" +
                    "      00 222222 0000000%\n" +
                    "0000a000        0000000 \n" +
                    " 00000000b0000b000      \n" +
                    "     000000000000d 4444 \n" +
                    " 999    00     000 4444 \n" +
                    " 999999 00     000 4444 \n" +
                    " 999999 i0     000      \n" +
                    " 999999 00     00000e0d \n" +
                    " 999999 00     000      \n" +
                    "        00     00  555  \n" +
                    " 00000i000     0e 55555 \n" +
                    "@0000000000gg0000 5555  \n" +
                    " 00000h00      000      \n" +
                    "       00 7777 00000000^\n" +
                    " 88888 00 77777g0f00000 \n" +
                    " 88888 00 7777 00       \n" +
                    " 88888 00 7777 00 66666 \n" +
                    " 8888  00 7777 00  6666 \n" +
                    "       !        0   666 \n";


    private Position[][] board;

    private Tile[] startPositions;

    /**
     * class constructor
     * reads the map and creates objects accordingly
     */
    public Board() {

        startPositions = new Tile[Character.values().length];

        String map = MAP;

        board = new Position[BOARD_HEIGHT][BOARD_WIDTH];
        int index = 0; // index to track chars
        int x, y; // coordinates

        while (index < map.length()) {
            x = index % (BOARD_WIDTH + 1);
            y = index / (BOARD_WIDTH + 1);

            if (x == BOARD_WIDTH) {
                index++;
                continue;
            }

            switch (map.charAt(index)) {

                case '0':
                    board[y][x] = new Tile(x, y);
                    break;

                case ' ':
                    board[y][x] = null;
                    break;

                case '1':
                    board[y][x] = new RoomTile(KITCHEN, x, y);
                    break;
                case '2':
                    board[y][x] = new RoomTile(BALL_ROOM, x, y);
                    break;
                case '3':
                    board[y][x] = new RoomTile(CONSERVATORY, x, y);
                    break;
                case '4':
                    board[y][x] = new RoomTile(BILLIARD_ROOM, x, y);
                    break;
                case '5':
                    board[y][x] = new RoomTile(LIBRARY, x, y);
                    break;
                case '6':
                    board[y][x] = new RoomTile(STUDY, x, y);
                    break;
                case '7':
                    board[y][x] = new RoomTile(HALL, x, y);
                    break;
                case '8':
                    board[y][x] = new RoomTile(LOUNGE, x, y);
                    break;
                case '9':
                    board[y][x] = new RoomTile(DINNING_ROOM, x, y);
                    break;

                case '!':
                    Tile scarletStart = new Tile(x, y);
                    board[y][x] = scarletStart;
                    startPositions[Character.Miss_Scarlet.ordinal()] = scarletStart;
                    break;
                case '@':
                    Tile mustardStart = new Tile(x, y);
                    board[y][x] = mustardStart;
                    startPositions[Character.Colonel_Mustard.ordinal()] = mustardStart;
                    break;
                case '#':
                    Tile whiteStart = new Tile(x, y);
                    board[y][x] = whiteStart;
                    startPositions[Character.Mrs_White.ordinal()] = whiteStart;
                    break;
                case '$':
                    Tile greenStart = new Tile(x, y);
                    board[y][x] = greenStart;
                    startPositions[Character.Mr_Green.ordinal()] = greenStart;
                    break;
                case '%':
                    Tile peacockStart = new Tile(x, y);
                    board[y][x] = peacockStart;
                    startPositions[Character.Mrs_Peacock.ordinal()] = peacockStart;
                    break;
                case '^':
                    Tile plumStart = new Tile(x, y);
                    board[y][x] = plumStart;
                    startPositions[Character.Professor_Plum.ordinal()] = plumStart;
                    break;

                case 'a':
                    Entrance entrToKitchen = new Entrance(x, y, KITCHEN);
                    board[y][x] = entrToKitchen;
                    KITCHEN.addEntrances(entrToKitchen);
                    allEntrances.add(entrToKitchen);
                    break;
                case 'b':
                    Entrance entrToBall = new Entrance(x, y, BALL_ROOM);
                    board[y][x] = entrToBall;
                    BALL_ROOM.addEntrances(entrToBall);
                    allEntrances.add(entrToBall);
                    break;
                case 'c':
                    Entrance entrToCSVY = new Entrance(x, y, CONSERVATORY);
                    board[y][x] = entrToCSVY;
                    CONSERVATORY.addEntrances(entrToCSVY);
                    allEntrances.add(entrToCSVY);
                    break;
                case 'd':
                    Entrance entrToBLDR = new Entrance(x, y, BILLIARD_ROOM);
                    board[y][x] = entrToBLDR;
                    BILLIARD_ROOM.addEntrances(entrToBLDR);
                    allEntrances.add(entrToBLDR);
                    break;
                case 'e':
                    Entrance entrToLBRY = new Entrance(x, y, LIBRARY);
                    board[y][x] = entrToLBRY;
                    LIBRARY.addEntrances(entrToLBRY);
                    allEntrances.add(entrToLBRY);
                    break;
                case 'f':
                    Entrance entrToSTDY = new Entrance(x, y, STUDY);
                    board[y][x] = entrToSTDY;
                    STUDY.addEntrances(entrToSTDY);
                    allEntrances.add(entrToSTDY);
                    break;
                case 'g':
                    Entrance entrToHall = new Entrance(x, y, HALL);
                    board[y][x] = entrToHall;
                    HALL.addEntrances(entrToHall);
                    allEntrances.add(entrToHall);
                    break;
                case 'h':
                    Entrance entrToLounge = new Entrance(x, y, LOUNGE);
                    board[y][x] = entrToLounge;
                    LOUNGE.addEntrances(entrToLounge);
                    allEntrances.add(entrToLounge);
                    break;
                case 'i':
                    Entrance entrToDining = new Entrance(x, y, DINNING_ROOM);
                    board[y][x] = entrToDining;
                    DINNING_ROOM.addEntrances(entrToDining);
                    allEntrances.add(entrToDining);
                    break;

                default:
                    return;
            }
            index++;
        }
    }

    /**
     * returns the starting position of a ccharacter
     * @param character
     * @return a tile with the starting position of the choosen character
     */
    public Tile getStartPosition(Character character) {
        return startPositions[character.ordinal()];
    }

    /**
     * returns entrance where player is standing or null if it isn't on one
     * @param player
     * @return entrance where player is or nul if player is not at an entrance
     */
    public Entrance lookForEntrance(Player player) {

        Position playerPos = player.getPosition();
        if (playerPos instanceof Entrance) {
            Entrance entrance = (Entrance) playerPos;
            return entrance;
        } else {
            return null;
        }
    }

    /**
     * returns list of exits for a player in a room or null if the player is not in a room
     * @param player
     * @return ArrayList with the entrances (exits) of the room the player is in or null if the player is not in a room
     */
    public ArrayList<Entrance> lookForExit(Player player) {
        Position playerPos = player.getPosition();
        if (playerPos instanceof Room) {
            Room room = (Room) playerPos;
            return room.getEntrances();
        } else {
            return null;
        }
    }

    /**
     * returns room connect by secret passage to the room where the player is or null if the room has no secret passages
     * @param player
     * @return room connect by secret passage or null if there is no secret passage
     */
    public Room lookForSecPas(Player player) {
        Position playerPos = player.getPosition();
        if (playerPos instanceof Room) {
            Room room = (Room) playerPos;
            if (room.hasSecPas()) {
                return getRoom(room.getSecPasTo());
            }
        }
        return null;
    }

    public ArrayList<Position> getMovablePositions(Character character) {

        Player player = GameController.getInstance().getPlayerByCharacter(character);

        ArrayList<Position> movablePos = new ArrayList<>();

        // if there are tiles in four directions
        if (lookFront(player) != null) {
            movablePos.add(lookFront(player));
        }
        if (lookLeft(player) != null) {
            movablePos.add(lookLeft(player));
        }
        if (lookBack(player) != null) {
            movablePos.add(lookBack(player));
        }
        if (lookRight(player) != null) {
            movablePos.add(lookBack(player));
        }

        // if the player is standing at an entrance to a room
        if (lookForEntrance(player) != null) {
            movablePos.add(lookForEntrance(player));
        }

        // if the player is in a room, get the exits
        ArrayList<Entrance> entrances = lookForExit(player);
        if (entrances != null && !entrances.isEmpty()) {
            movablePos.addAll(entrances);
        }

        // if the player is in a room, and there is a secret passage
        if (lookForSecPas(player) != null) {
            movablePos.add(lookForSecPas(player));
        }

        return movablePos;
    }

    /**
     * checks if human player can move to the left
     * @param player
     * @return tile to the left of player if movement is possible or null if it isn't
     */
    public Tile lookLeft(Player player) {
        Position playerPos = player.getPosition();

        if (playerPos instanceof Room) {
            return null;
        }
        Tile playerTile = (Tile) playerPos;

        if (playerTile.x - 1 < 0) {
            return null;
        }

        if (board[playerTile.y][playerTile.x - 1] instanceof Room) {
            return null;
        }

        return (Tile) board[playerTile.y][playerTile.x - 1];
    }

    /**
     * checks if human player can move to the right
     * @param player
     * @return tile to the right of player if movement is possible or null if it isn't
     */
    public Tile lookRight(Player player) {
        Position playerPos = player.getPosition();

        if (playerPos instanceof Room) {
            return null;
        }
        Tile playerTile = (Tile) playerPos;

        if (playerTile.x + 1 > BOARD_WIDTH - 1) {
            return null;
        }

        if (board[playerTile.y][playerTile.x + 1] instanceof Room) {
            return null;
        }

        return (Tile) board[playerTile.y][playerTile.x + 1];
    }

    /**
     * checks if human player can move to the tile above his position
     * @param player
     * @return tile to the above player position if movement is possible or null if it isn't
     */
    public Tile lookBack(Player player) {
        Position playerPos = player.getPosition();

        if (playerPos instanceof Room) {
            return null;
        }
        Tile playerTile = (Tile) playerPos;

        if (playerTile.y + 1 > BOARD_HEIGHT - 1) {
            return null;
        }

        if (board[playerTile.y + 1][playerTile.x] instanceof Room) {
            return null;
        }

        return (Tile) board[playerTile.y + 1][playerTile.x];
    }

    /**
     * checks if human player can move to the tile bellow his position
     * @param player
     * @return tile to the bellow player position if movement is possible or null if it isn't
     */
    public Tile lookFront(Player player) {
        Position playerPos = player.getPosition();

        if (playerPos instanceof Room) {
            return null;
        }
        Tile playerTile = (Tile) playerPos;

        if (playerTile.y - 1 < 0) {
            return null;
        }

        if (board[playerTile.y - 1][playerTile.x] instanceof Room) {
            return null;
        }

        return (Tile) board[playerTile.y - 1][playerTile.x];
    }

    /**
     * moves player to choosen position
     * @param player
     * @param position
     */
    public void movePlayer(Player player, Position position) {
        player.setPosition(position);
    }

    /**
     * returns the room of the choosen division
     * @param div
     * @return room where the division is or null if the division doesn't exists
     */
    public Room getRoom(Division div) {
        switch (div) {
            case Ballroom:
                return BALL_ROOM;
            case Billiard_Room:
                return BILLIARD_ROOM;
            case Conservatory:
                return CONSERVATORY;
            case Dinning_Room:
                return DINNING_ROOM;
            case Hall:
                return HALL;
            case Kitchen:
                return KITCHEN;
            case Library:
                return LIBRARY;
            case Lounge:
                return LOUNGE;
            case Study:
                return STUDY;
            default:
                return null;
        }
    }

    /**
     * returns an ArrayList with all the entrances on the board
     * @return allEntrances
     */
    public ArrayList<Entrance> getAllEntrances() {
        return allEntrances;
    }

    /**
     * translates the string which holds the map information into an array of int arrays
     * @return parsedMap array of int arrays with the information retrieved from MAP
     */
    public int[][] parseMap(){

        String map = MAP;

        int[][] parsedMap = new int[BOARD_HEIGHT][BOARD_WIDTH];
        int index = 0; // index to track chars
        int x, y; // coordinates

        while (index < map.length()) {
            x = index % (BOARD_WIDTH + 1);
            y = index / (BOARD_WIDTH + 1);

            if (x == BOARD_WIDTH) {
                index++;
                continue;
            }

            switch (map.charAt(index)) {

                case '0':
                    parsedMap[y][x] = 1;
                    break;

                case '!':
                    parsedMap[y][x] = 3;
                    break;
                case '@':
                    parsedMap[y][x] = 3;
                    break;
                case '#':
                    parsedMap[y][x] = 3;

                    break;
                case '$':
                    parsedMap[y][x] = 3;
                    break;
                case '%':
                    parsedMap[y][x] = 3;
                    break;
                case '^':
                    parsedMap[y][x] = 3;
                    break;

                case 'a':
                    parsedMap[y][x] = 2;
                    break;
                case 'b':
                    parsedMap[y][x] = 2;
                    break;
                case 'c':
                    parsedMap[y][x] = 2;
                    break;
                case 'd':
                    parsedMap[y][x] = 2;
                    break;
                case 'e':
                    parsedMap[y][x] = 2;
                    break;
                case 'f':
                    parsedMap[y][x] = 2;
                    break;
                case 'g':
                    parsedMap[y][x] = 2;
                    break;
                case 'h':
                    parsedMap[y][x] = 2;
                    break;
                case 'i':
                    parsedMap[y][x] = 2;
                    break;

                default:
                    parsedMap[y][x] = 0;
                    break;
            }
            index++;
        }

        return parsedMap;
    }
}
