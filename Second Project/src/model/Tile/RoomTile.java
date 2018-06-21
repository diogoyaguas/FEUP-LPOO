package com.cluedo.game.model.Tile;

public class RoomTile extends Room {

    private int x, y;

    /**
     * class constructor
     * @param room
     * @param x
     * @param y
     */
    public RoomTile(Room room, int x, int y) {
        super(room.getRoom(), room.getSecPasTo());
        this.x = x;
        this.y = y;
    }

    /**
     * returns x positions
     * @return x coordinate on x axis
     */
    public int getX() {
        return x;
    }

    /**
     * returns y position
     * @return u coordinate on y axis
     */
    public int getY() {
        return y;
    }

}
