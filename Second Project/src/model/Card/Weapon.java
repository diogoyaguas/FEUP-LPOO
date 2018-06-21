package com.cluedo.game.model.Card;

public enum Weapon implements Card {

    Candlestick, Dagger, Lead_Pipe, Revolver, Rope, Wrench;

    @Override
    public String toString() {
        return this.name().replaceAll("_"," ");
    }

    /**
     * Get the weapon whose ordinal is index.
     * @param pos
     * @return  the weapon at the given index
     */
    public Weapon getWeapon(int pos){
        switch(pos){
            case 0:
                return Candlestick;
            case 1:
                return Dagger;
            case 2:
                return Lead_Pipe;
            case 3:
                return Revolver;
            case 4:
                return Rope;
            case 5:
                return Wrench;
            default:
                return null;
        }
    }

    @Override
    public char toStringOnBoard() {
        char s = ' ';
        switch (this.ordinal()) {
            case 0:
                s = 'c';
                break;
            case 1:
                s = 'd';
                break;
            case 2:
                s = 'p';
                break;
            case 3:
                s = 'g';
                break;
            case 4:
                s = 'r';
                break;
            case 5:
                s = 's';
                break;
            default:
        }
        return s;
    }
}
