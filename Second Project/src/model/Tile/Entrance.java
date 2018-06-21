package com.cluedo.game.model.Tile;

public class  Entrance extends Tile {

    //room of the entrance
    private Room toRoom;

    /**
     * class constructor
     * @param x
     * @param y
     * @param toRoom room you can enter by this entrance
     */
    public Entrance(int x, int y, Room toRoom) {
        super(x, y);
        this.toRoom = toRoom;
    }

    /**
     * returns toRoom
     * @return room that this entrance leads to
     */
    public Room toRoom() {
        return toRoom;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((toRoom == null) ? 0 : toRoom.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Entrance other = (Entrance) obj;
        if (toRoom == null) {
            return other.toRoom == null;
        } else return toRoom.equals(other.toRoom);
    }

}
