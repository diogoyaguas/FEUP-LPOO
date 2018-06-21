package com.cluedo.game.model.Card;


/**
 * This enum class represents a character card in Cluedo
 *
 * There are six characters
 * Miss Scarlet, Colonel Mustard, Mrs White, The Reverend Green, Mrs Peacock, and Professor Plum.
 *
 * Each of them has a diferent starting position on board
 *
 */
public enum Character implements Card {

    Miss_Scarlet, Professor_Plum, Mrs_Peacock, Mr_Green, Colonel_Mustard, Mrs_White;

    @Override
    public String toString() {
        return this.name().replaceAll("_"," ");
    }

    /**
     * Get the character whose ordinal is index.
     * @param pos
     * @return  the character at the given index
     */
    public static Character getCharacter(int pos){
        switch(pos){
            case 0:
                return Miss_Scarlet;
            case 1:
                return Professor_Plum;
            case 2:
                return Mrs_Peacock;
            case 3:
                return Mr_Green;
            case 4:
                return Colonel_Mustard;
            case 5:
                return Mrs_White;
                default:
                   return null;
        }
    }

    /**
     * This method returns the next character in turn. When current character ends turn
     * @return  the next character in turn.
     */
    public Character nextCharacter() {
        switch (this) {
            case Colonel_Mustard:
                return Mrs_White;
            case Miss_Scarlet:
                return Colonel_Mustard;
            case Mrs_Peacock:
                return Professor_Plum;
            case Mrs_White:
                return Mr_Green;
            case Professor_Plum:
                return Miss_Scarlet;
            case Mr_Green:
                return Mrs_Peacock;
            default:
                return null;
        }
    }

    @Override
    public char toStringOnBoard() {
        char s = ' ';
        switch (this.ordinal()) {
            case 0:
                s = 'S';
                break;
            case 1:
                s = 'M';
                break;
            case 2:
                s = 'W';
                break;
            case 3:
                s = 'G';
                break;
            case 4:
                s = 'C';
                break;
            case 5:
                s = 'P';
                break;
            default:
        }
        return s;
    }
}
