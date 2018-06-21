package com.cluedo.game.model;

import com.cluedo.game.model.Tile.Position;
import com.cluedo.game.model.Card.Card;
import com.cluedo.game.model.Card.Character;

import java.util.ArrayList;

public class Player {

    private final Character token;

    private String name;

    private Position position;

    private ArrayList<Card> cards;

    private int remainingSteps;

    private boolean isPlaying;

    /**
     * class constructor
     * @param c_token
     * @param pos
     * @param isPlaying
     */
    public Player(Character c_token, Position pos, boolean isPlaying) {
        this.token = c_token;
        this.position = pos;
        this.isPlaying = isPlaying;
        cards = new ArrayList<>();
    }

    /**
     * returns player's character card
     * @return token character card
     */
    public Character getToken() {
        return token;
    }

    /**
     * returns player's name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * returns player's position
     * @return position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * sets player name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets palyer position
     * @param position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * returns player's cards
     * @return cards ArrayList with the player's cards
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * gives the player a card
     * @param card
     */
    public void drawACard(Card card) {
        cards.add(card);
    }

    /**
     * checks if a player has a certain card
     * @param card
     * @return true if player has the card or false if doesn't
     */
    public boolean hasCard(Card card) {
        return cards.contains(card);
    }

    /**
     * returns the remaining number of movements
     * @return remainingSteps number of movements left
     */
    public int getRemainingSteps() {
        return remainingSteps;
    }

    /**
     * sets the number of remaining steps
     * @param remaingingSteps
     */
    public void setRemainingSteps(int remaingingSteps) {
        if (remaingingSteps < 0) {
            return;
        }
        remainingSteps = remaingingSteps;
    }

    /**
     * checks if player is playing
     * @return isPlaying true if player is in the current game, false if it isn't
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * sets the player to play or to not belong to the game
     * @param isPlaying
     */
    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((token == null) ? 0 : token.hashCode());
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
        Player other = (Player) obj;
        return token == other.token;
    }
}
