package com.cluedo.game.controller;

import com.cluedo.game.model.Board;
import com.cluedo.game.model.Card.Card;
import com.cluedo.game.model.Card.Character;
import com.cluedo.game.model.Card.Division;
import com.cluedo.game.model.Card.Weapon;
import com.cluedo.game.model.Player;
import com.cluedo.game.model.Suggestion;
import com.cluedo.game.model.Tile.Position;
import com.cluedo.game.model.Tile.RoomTile;
import com.cluedo.game.model.Tile.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import static com.cluedo.game.controller.GameController.State.CURRENTPLAYER;
import static com.cluedo.game.controller.GameController.State.MOVEPLAYER;

public class GameController {

    private Suggestion solution;

    private Board board;

    private int numPlayers;

    private int numDices;

    private ArrayList<Player> players = new ArrayList<>();

    private ArrayList<Character> characters = new ArrayList<>();

    private ArrayList<Division> divisions = new ArrayList<>();

    private ArrayList<Weapon> weapons = new ArrayList<>();

    private ArrayList<Character> joinedPlayers = new ArrayList<>();

    private ArrayList<Card> remainingCards = new ArrayList<>();

    private static final Random RAN = new Random();

    private Map<Character, HashSet<Object>> knownCards = new HashMap<>();

    private Character currentPlayer;

    private Character winner;

    private int remainingSteps;

    private static GameController singleton;

    private State state = State.INITGAME;

    public enum State {INITGAME, CURRENTPLAYER, ROLLDICES, MOVEPLAYER, INROOM, INENTRANCE}

    private boolean accuse;

    private int playerRemoved;

    private Character accusedCharacter;

    private Division accusedDivision;

    private Weapon accusedWeapon;

    private Suggestion accusation;


    public static GameController getInstance() {

        if (singleton == null) {
            singleton = new GameController();

        }
        return singleton;
    }

    /**
     * class controller
     */
    private GameController() {

        board = new Board();
        players = new ArrayList<>(Character.values().length);
        characters = new ArrayList<>(Character.values().length);
        this.numDices = 2;
        winner = null;

        knownCards = new HashMap<>();
        for (int i = 0; i < Character.values().length; i++) {
            knownCards.put(Character.getCharacter(i), new HashSet<>());
        }

        addAll();

    }

    /**
     * stes the number of players if legal
     * @param numPlayers
     */
    public void setNumPlayers(int numPlayers) {

        if (numPlayers < Board.MIN_PLAYER || numPlayers > Board.MAX_PLAYER) {
            return;
        }

        this.numPlayers = numPlayers;
    }

    /**
     * adds all playable players and all cards to their respective ArrayList
     */
    private void addAll() {

        players.add(new Player(Character.Miss_Scarlet, board.getStartPosition(Character.Miss_Scarlet), false));
        players.add(new Player(Character.Professor_Plum, board.getStartPosition(Character.Professor_Plum), false));
        players.add(new Player(Character.Mrs_Peacock, board.getStartPosition(Character.Mrs_Peacock), false));
        players.add(new Player(Character.Mr_Green, board.getStartPosition(Character.Mr_Green), false));
        players.add(new Player(Character.Colonel_Mustard, board.getStartPosition(Character.Colonel_Mustard), false));
        players.add(new Player(Character.Mrs_White, board.getStartPosition(Character.Mrs_White), false));

        characters.add(Character.Miss_Scarlet);
        characters.add(Character.Professor_Plum);
        characters.add(Character.Mrs_Peacock);
        characters.add(Character.Mr_Green);
        characters.add(Character.Colonel_Mustard);
        characters.add(Character.Mrs_White);

        weapons.add(Weapon.Candlestick);
        weapons.add(Weapon.Dagger);
        weapons.add(Weapon.Lead_Pipe);
        weapons.add(Weapon.Revolver);
        weapons.add(Weapon.Rope);
        weapons.add(Weapon.Wrench);

        divisions.add(Division.Kitchen);
        divisions.add(Division.Ballroom);
        divisions.add(Division.Conservatory);
        divisions.add(Division.Dinning_Room);
        divisions.add(Division.Billiard_Room);
        divisions.add(Division.Library);
        divisions.add(Division.Lounge);
        divisions.add(Division.Hall);
        divisions.add(Division.Study);
    }

    /**
     * returns the characters ArrayList
     * @return characters
     */
    public ArrayList<Character> getCharacters() {
        return characters;
    }

    /**
     * returns the divisions Arraylist
     * @return divisions
     */
    public ArrayList<Division> getDivisions() {
        return divisions;
    }

    /**
     * returns the weapons ArrayList
     * @return weapons
     */
    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * creates the slution of one game and removes the three cards from the card pool which will go to the players' hands
     */
    public void createSolution() {
        remainingCards = new ArrayList<>();

        ArrayList<Character> characterCards = characters;
        Character solCharacter = characterCards.remove(RAN.nextInt(characterCards.size()));
        remainingCards.addAll(characterCards);

        ArrayList<Division> DivisionCards = divisions;
        Division solDivision = DivisionCards.remove(RAN.nextInt(DivisionCards.size()));
        remainingCards.addAll(DivisionCards);

        ArrayList<Weapon> weaponCards = weapons;
        Weapon solWeapon = weaponCards.remove(RAN.nextInt(weaponCards.size()));
        remainingCards.addAll(weaponCards);

        solution = new Suggestion(solCharacter, solWeapon, solDivision);
    }

    /**
     * add a player
     * @param playerChoice
     * @param name
     */
    public void joinPlayer(Character playerChoice, String name) {
        players.get(playerChoice.ordinal()).setPlaying(true);
        players.get(playerChoice.ordinal()).setName(name);
        joinedPlayers.add(playerChoice);
    }

    /**
     * remove a player
     * @param character
     */
    private void kickPlayerOut(Character character) {

        for(int i = 0; i < joinedPlayers.size(); i++){

            if(joinedPlayers.get(i).equals(character)){

                joinedPlayers.remove(i);
                playerRemoved = i;

            }
        }
        players.get(character.ordinal()).setPlaying(false);
        numPlayers--;
        if (numPlayers == 1) {
            for (Player p : players) {
                if (p.isPlaying()) {
                    setWinner(p.getToken());
                }
            }
        }
    }

    /**
     * deal random cards to players
     */
    public void dealCard() {
        if (remainingCards == null) {
            return;
        }

        while (remainingCards.size() >= numPlayers) {
            Collections.shuffle(remainingCards);
            for (Player p : players) {
                if (p.isPlaying()) {
                    if (!remainingCards.isEmpty()) {
                        p.drawACard(remainingCards.remove(RAN.nextInt(remainingCards.size())));
                    }
                }
            }
        }

        for (Player p : players) {
            knownCards.get(p.getToken()).addAll(p.getCards());
            knownCards.get(p.getToken()).addAll(remainingCards);
        }
    }

    /**
     * randomly decides the first player
     */
    public void decideWhoMoveFirst() {
        currentPlayer = Character.Miss_Scarlet;

        while (!getPlayerByCharacter(Objects.requireNonNull(currentPlayer)).isPlaying()) {
            currentPlayer = currentPlayer.nextCharacter();
        }
    }

    /**
     * ends the turn for the current player
     */
    public void currentPlayerEndTurn() {
        currentPlayer = currentPlayer.nextCharacter();
        this.state = CURRENTPLAYER;
        while (!getPlayerByCharacter(Objects.requireNonNull(currentPlayer)).isPlaying()) {
            currentPlayer = currentPlayer.nextCharacter();
        }
    }

    /**
     * moves player to the chosen position
     * @param character
     * @param position
     */
    public void movePlayer(Character character, Position position) {
        board.movePlayer(getPlayerByCharacter(character), position);
    }

    /**
     * attempts to refute the suggestion with a card in hand
     * @return string containing the card used to refute or the information that other player wasn't able to refute
     */
    public String refuteSuggestion() {

        Set<Object> knownCardsForCurrentPlayer = knownCards.get(currentPlayer);

        StringBuilder rejectMsg = new StringBuilder();
        ArrayList<Card> cardsInSuggestion = accusation.CardList();

        Collections.shuffle(cardsInSuggestion);

        outer:
        for (Player p : players) {

            if (p.getToken() != currentPlayer && !p.getCards().isEmpty()) {
                for (Card card : cardsInSuggestion) {
                    if (p.getCards().contains(card)) {
                        rejectMsg.append((char) 64).append(p.getToken().toString()).append(" rejects your\nsuggestion with card: ").append(card.toString()).append("\n");

                        knownCardsForCurrentPlayer.add(card);
                        continue outer;
                    }
                }
                rejectMsg.append((char) 64).append(p.getToken().toString()).append(" cannot reject your suggestion.\n");
            }
        }

        return rejectMsg.toString();
    }

    /**
     * compares the accusation with the solution
     * @return
     */
    public boolean checkAccusation() {
        if (solution.equals(accusation)) {

            setWinner(currentPlayer);
            return true;
        } else {

            kickPlayerOut(currentPlayer);
            return false;
        }
    }

    /**
     * returns the number of moves a player can do in his turn
     * @return total
     */
    private int rollDice() {

        int[] roll = new int[numDices];
        for (int i = 0; i < numDices; i++) {
            roll[i] = RAN.nextInt(6);
        }

        int total = 0;
        for (int aRoll : roll) {
            total += (aRoll + 1);
        }

        if(total == 1) total = 2;

        getPlayerByCharacter(currentPlayer).setRemainingSteps(total);

        return total;
    }

    /**
     * returns the winner
     * @return winner
     */
    public Character getWinner() {
        return winner;
    }

    /**
     * sets the winner
     * @param character
     */
    private void setWinner(Character character) {
        winner = character;
    }

    /**
     * returns the board
     * @return booard
     */
    public Board getBoard() {
        return board;
    }

    /**
     * returns the current player
     * @return currentPlayer
     */
    public Character getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * returns the player that is using a determined character
     * @param character
     * @return player
     */
    public Player getPlayerByCharacter(Character character) {
        return players.get(character.ordinal());
    }

    /**
     * returns the players who joined the game
     * @return joinedPlayers
     */
    public ArrayList<Character> getJoinedPlayers() {
        return joinedPlayers;
    }

    /**
     * returns the players who weren't chosen
     * @return playableCharacters
     */
    public ArrayList<Character> getPlayableCharacters() {
        ArrayList<Character> playableCharacters = new ArrayList<>();

        for (Player p : players) {
            if (!p.isPlaying()) {
                playableCharacters.add(p.getToken());
            }
        }

        return playableCharacters;
    }

    /**
     * returns the know cards to a player
     * @return Set with the known cards
     */
    public Set<Object> getKnownCards() {
        return knownCards.get(currentPlayer);
    }

    /**
     * returns the remaining number of moves of a player
     * @param character
     * @return number of steps remaining
     */
    public int getRemainingSteps(Character character) {
        return getPlayerByCharacter(character).getRemainingSteps();
    }

    /**
     * sets the remaining number of moves of a player
     * @param character
     * @param remainingSteps
     */
    public void setRemainingSteps(Character character, int remainingSteps) {
        getPlayerByCharacter(character).setRemainingSteps(remainingSteps);
    }

    /**
     * sets the character for the accusation/suggestion
     */
    public void setAccusedCharacter(Character character) {

        this.accusedCharacter = character;
    }

    /**
     * sets the division for the accusation/suggestion
     */
    public void setAccusedDivison(Division division) {

        this.accusedDivision = division;
    }

    /**
     * sets the weapon for the accusation/suggestion
     */
    public void setAccusedWeapon(Weapon weapon) {

        this.accusedWeapon = weapon;
    }

    /**
     * sets an accusation
     */
    public void setAccusation() {

        accusation = new Suggestion(accusedCharacter, accusedWeapon, accusedDivision);
    }

    /**
     * sets an accusation
     * @param suggestion
     */
    public void setAccusation(Suggestion suggestion){

        this.accusation = suggestion;
    }

    /**
     * controls the game states
     */
    public void stateMachine() {

        switch (this.state) {

            case INITGAME:
                decideWhoMoveFirst();
                createSolution();
                dealCard();
                this.state = CURRENTPLAYER;
            case CURRENTPLAYER:
                remainingSteps = this.getRemainingSteps(currentPlayer);
                if (remainingSteps == 0) {
                    this.state = State.ROLLDICES;
                } else this.state = MOVEPLAYER;
                break;
            case ROLLDICES:
                if (remainingSteps == 0) {

                    remainingSteps = rollDice();
                    setRemainingSteps(currentPlayer, remainingSteps);
                    this.state = MOVEPLAYER;
                }
                break;
            case MOVEPLAYER:
                break;
        }
    }

    /**
     * sets accuse
     * @param accuse if true player will make accusation else will make suggestion
     */
    public void setAccuse(boolean accuse) {

        this.accuse = accuse;
    }

    /**
     * returns accuse value
     * @return accuse
     */
    public boolean getAccuse(){

        return this.accuse;
    }

    /**
     * returns the eliminated player
     * @return playerRemoved
     */
    public int getPlayerRemoved(){

        return this.playerRemoved;
    }

    /**
     * returns the solution of the game
     * @return solution
     */
    public Suggestion getSolution() {
        return solution;
    }

    /**
     * returns if the game has a winner
     * @return true there is no winner and the game is running else false
     */
    public boolean isGameRunning() {
        return winner == null;
    }

}
