package com.cluedo.game.model.Tile;

import com.cluedo.game.model.Card.Division;

import java.util.ArrayList;

public class Room extends Position {

    private final Division division;

    private final Division secPasTo;

    private final ArrayList<Entrance> entrances;

    /**
     * class constructor
     * @param division division type
     * @param secPasTo division connected by secret passage
     */
    public Room(Division division, Division secPasTo) {

        this.division = division;
        this.secPasTo = secPasTo;
        entrances = new ArrayList<>();
    }

    /**
     * returns division
     * @return division
     */
    public Division getRoom() {
        return division;
    }

    /**
     * checks if division has secret passage
     * @return boolean true if there is a value diferent than null in secPasTo, false otherwise
     */
    public boolean hasSecPas() {
        return secPasTo != null;
    }

    /**
     * returns division that is connected by secret passage
     * @return secPasTo division which secret passage leads to
     */
    public Division getSecPasTo() {
        return secPasTo;
    }

    /**
     * returns all the entrances
     * @return entraces arrayList containnig all the entrances to this room
     */
    public ArrayList<Entrance> getEntrances() {
        return entrances;
    }

    /**
     * adds an entrance to the entrances
     * @param entrance
     */
    public void addEntrances(Entrance entrance) {
        if (!entrances.contains(entrance)) {
            entrances.add(entrance);
        }
    }

    @Override
    public String toString() {
        return division.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((division == null) ? 0 : division.hashCode());
        result = prime * result + ((secPasTo == null) ? 0 : secPasTo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Room other = (Room) obj;
        return division == other.division && secPasTo == other.secPasTo;
    }
}
