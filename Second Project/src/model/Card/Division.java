package com.cluedo.game.model.Card;

/**
 * This enum class represents a Division card in Cluedo
 *
 * There are nine divisions
 * Kitchen, Ball room, Conservatory, Billard Room, Library, Study, Hall, Lounge, and Dining Room
 *
 */
public enum Division implements Card {

    Kitchen, Ballroom, Conservatory, Dinning_Room, Billiard_Room, Library, Lounge, Hall, Study;

    @Override
    public String toString() {
        return this.name().replaceAll("_", " ");
    }


    /**
     * Get the division whose ordinal is index.
     * @param pos
     * @return  the division at the given index
     */
    public Division getDivision(int pos){
        switch(pos){
            case 0:
                return Kitchen;
            case 1:
                return Ballroom;
            case 2:
                return Conservatory;
            case 3:
                return Dinning_Room;
            case 4:
                return Billiard_Room;
            case 5:
                return Library;
            case 6:
                return Lounge;
            case 7:
                return Hall;
            case 8:
                return Study;
            default:
                return null;
        }
    }

    @Override
    public char toStringOnBoard() {
        return ' ';
    }
}
