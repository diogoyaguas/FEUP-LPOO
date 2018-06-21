package com.cluedo.game.model;

import com.cluedo.game.model.Card.Card;
import com.cluedo.game.model.Card.Division;
import com.cluedo.game.model.Card.Weapon;
import com.cluedo.game.model.Card.Character;

import java.util.ArrayList;


public class Suggestion {

    public final Character character;

    public final Weapon weapon;

    public final Division division;

    /**
     * class constructor
     * the rules state that the suggestion is composed of a division, a weapon and character
     * @param character
     * @param weapon
     * @param location
     */
    public Suggestion(Character character, Weapon weapon, Division location) {
        super();
        this.character = character;
        this.division = location;
        this.weapon = weapon;
    }

    /**
     * puts the cards of a suggestion in an ArrayList
     * @return list
     */
    public ArrayList<Card> CardList() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(character);
        list.add(weapon);
        list.add(division);
        return list;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((character == null) ? 0 : character.hashCode());
        result = prime * result + ((division == null) ? 0 : division.hashCode());
        result = prime * result + ((weapon == null) ? 0 : weapon.hashCode());
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
        Suggestion other = (Suggestion) obj;
        return character == other.character && division == other.division && weapon == other.weapon;
    }

}
