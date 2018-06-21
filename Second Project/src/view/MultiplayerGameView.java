package com.cluedo.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cluedo.game.Cluedo;
import com.cluedo.game.controller.GameController;
import com.cluedo.game.model.Card.Card;
import com.cluedo.game.model.Card.Character;
import com.cluedo.game.model.Player;
import com.cluedo.game.model.Tile.Entrance;
import com.cluedo.game.model.Tile.Room;

import java.util.ArrayList;

import static com.cluedo.game.controller.GameController.getInstance;
import static com.cluedo.game.model.Card.Division.Conservatory;
import static com.cluedo.game.model.Card.Division.Kitchen;
import static com.cluedo.game.model.Card.Division.Library;
import static com.cluedo.game.model.Card.Division.Lounge;
import static com.cluedo.game.model.Card.Division.Study;
import static java.lang.Math.abs;

public class MultiplayerGameView extends GameView {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Stage stage;
    private TextureAtlas atlas;
    private InputMultiplexer multiplexer;

    private ArrayList<Sprite> characterSprites = new ArrayList<>();
    private ArrayList<ImageButton> playerButtonsCards;
    private Sprite board;

    private Label actualText;
    private ImageButton rollDiceButton;
    private ImageButton roomExitRoomButton;
    private ImageButton secretExitRoomButton;

    private ImageButton moveMakeAccusationButton;
    private ImageButton entranceMakeAccusationButton;
    private ImageButton roomMakeAccusationButton;
    private ImageButton secretMakeAccusationButton;

    private ImageButton enterMakeSuggestionButton;
    private ImageButton roomMakeSuggestionButton;
    private ImageButton secretMakeSuggestionButton;

    private ImageButton secretPassButton;
    private ImageButton enterRoomButton;

    private ImageButton moveEndTurnButton;
    private ImageButton entranceEndTurnButton;
    private ImageButton roomEndTurnButton;
    private ImageButton secretEndTurnButton;

    private Table entranceTable;
    private Table moveTable;
    private Table enterRoomTable;
    private Table dicesTable;
    private Table roomTable;
    private Table roomSecretTable;
    private Table textTable;
    private Table backCards;

    private Actor ballRoomOne;
    private Actor ballRoomTwo;
    private Actor ballRoomThree;
    private Actor ballRoomFour;
    private Actor billiardExitOne;
    private Actor billiardExitTwo;
    private Actor libraryOne;
    private Actor libraryTwo;
    private Actor hallOne;
    private Actor hallTwo;
    private Actor dinningExitOne;
    private Actor hallThree;
    private Actor dinningExitTwo;

    private boolean rollDices = true;
    private boolean movePlayer = false;
    private boolean startTurn = true;
    private boolean atHallway = true;
    private boolean atRoom = false;
    private boolean exitRoom = false;
    private boolean secretPass = false;

    /**
     * class constructor
     * @param next_screen
     */
    MultiplayerGameView(ScreenAdapter next_screen) {

        super(next_screen);

        getInstance().stateMachine();

        loadElements();
        loadAssets();
        loadPlayers();
        loadBackCards();
        loadPlayerCards();
        addExitButtons();
    }

    /**
     * loads the imageButtons for all the cards
     */
    private void loadPlayerCards() {

        ArrayList<Card> playerCards = getInstance().getPlayerByCharacter(getInstance().getCurrentPlayer()).getCards();
        playerButtonsCards = new ArrayList<>();

        for (Card c : playerCards) {

            switch (c.toString()) {

                case "Miss Scarlet":

                    ImageButton missScarletCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Character_Miss_Scarlet")));
                    setupActualCardsListener(missScarletCardButton);
                    playerButtonsCards.add(missScarletCardButton);
                    break;

                case "Professor Plum":

                    ImageButton professorPlumCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Character_Professor_Plum")));
                    setupActualCardsListener(professorPlumCardButton);
                    playerButtonsCards.add(professorPlumCardButton);
                    break;

                case "Mrs Peacock":

                    ImageButton mrsPeacockCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Character_Mrs_Peacock")));
                    setupActualCardsListener(mrsPeacockCardButton);
                    playerButtonsCards.add(mrsPeacockCardButton);
                    break;

                case "Mr Green":

                    ImageButton mrGreenCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Character_The_Reverend_Green")));
                    setupActualCardsListener(mrGreenCardButton);
                    playerButtonsCards.add(mrGreenCardButton);
                    break;

                case "Colonel Mustard":

                    ImageButton colonelMustardCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Character_Colonel_Mustard")));
                    setupActualCardsListener(colonelMustardCardButton);
                    playerButtonsCards.add(colonelMustardCardButton);
                    break;

                case "Mrs White":

                    ImageButton mrsWhiteCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Character_Mrs_White")));
                    setupActualCardsListener(mrsWhiteCardButton);
                    playerButtonsCards.add(mrsWhiteCardButton);
                    break;

                case "Kitchen":

                    ImageButton kitchenCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Location_Kitchen")));
                    setupActualCardsListener(kitchenCardButton);
                    playerButtonsCards.add(kitchenCardButton);
                    break;

                case "Ballroom":

                    ImageButton ballroomCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Location_Ball_room")));
                    setupActualCardsListener(ballroomCardButton);
                    playerButtonsCards.add(ballroomCardButton);
                    break;

                case "Conservatory":

                    ImageButton conservatoryCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Location_Conservatory")));
                    setupActualCardsListener(conservatoryCardButton);
                    playerButtonsCards.add(conservatoryCardButton);
                    break;

                case "Dinning Room":

                    ImageButton dinningRoomCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Location_Dining_Room")));
                    setupActualCardsListener(dinningRoomCardButton);
                    playerButtonsCards.add(dinningRoomCardButton);
                    break;

                case "Billiard Room":

                    ImageButton billiardRoomCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Location_Billard_Room")));
                    setupActualCardsListener(billiardRoomCardButton);
                    playerButtonsCards.add(billiardRoomCardButton);
                    break;

                case "Library":

                    ImageButton libraryCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Location_Library")));
                    setupActualCardsListener(libraryCardButton);
                    playerButtonsCards.add(libraryCardButton);
                    break;

                case "Lounge":

                    ImageButton loungeCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Location_Lounge")));
                    setupActualCardsListener(loungeCardButton);
                    playerButtonsCards.add(loungeCardButton);
                    break;

                case "Hall":

                    ImageButton hallCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Location_Study")));
                    setupActualCardsListener(hallCardButton);
                    playerButtonsCards.add(hallCardButton);
                    break;

                case "Study":

                    ImageButton studyCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Location_Hall")));
                    setupActualCardsListener(studyCardButton);
                    playerButtonsCards.add(studyCardButton);
                    break;

                case "Candlestick":

                    ImageButton candlestickCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Weapon_Candlestick")));
                    setupActualCardsListener(candlestickCardButton);
                    playerButtonsCards.add(candlestickCardButton);
                    break;

                case "Dagger":

                    ImageButton daggerCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Weapon_Dagger")));
                    setupActualCardsListener(daggerCardButton);
                    playerButtonsCards.add(daggerCardButton);
                    break;

                case "Lead Pipe":

                    ImageButton leadPipeCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Weapon_Lead_Pipe")));
                    setupActualCardsListener(leadPipeCardButton);
                    playerButtonsCards.add(leadPipeCardButton);
                    break;

                case "Revolver":

                    ImageButton revolverCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Weapon_Revolver")));
                    setupActualCardsListener(revolverCardButton);
                    playerButtonsCards.add(revolverCardButton);
                    break;

                case "Rope":

                    ImageButton ropeCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Weapon_Rope")));
                    setupActualCardsListener(ropeCardButton);
                    playerButtonsCards.add(ropeCardButton);
                    break;

                case "Wrench":

                    ImageButton wrenchCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Weapon_Spanner")));
                    setupActualCardsListener(wrenchCardButton);
                    playerButtonsCards.add(wrenchCardButton);
                    break;
            }
        }

        if (!playerButtonsCards.isEmpty()) {

            distributeCards(playerButtonsCards);
        }
    }

    /**
     * distributes the cards according to number of players
     * @param playerButtonsCards
     */
    private void distributeCards(ArrayList<ImageButton> playerButtonsCards) {

        switch (getInstance().getJoinedPlayers().size()) {

            case 3:

                if (!playerButtonsCards.isEmpty()) {

                    float size = (((camera.viewportWidth - board.getWidth()) / 3) - playerButtonsCards.get(0).getWidth()) / 2;

                    playerButtonsCards.get(0).setPosition(size, actualText.getHeight());
                    playerButtonsCards.get(1).setPosition(size, actualText.getHeight() + playerButtonsCards.get(1).getHeight() + 25);

                    playerButtonsCards.get(2).setPosition(3 * size + playerButtonsCards.get(2).getWidth() , actualText.getHeight());
                    playerButtonsCards.get(3).setPosition(3 * size + playerButtonsCards.get(2).getWidth(), actualText.getHeight() + playerButtonsCards.get(3).getHeight() + 25);

                    playerButtonsCards.get(4).setPosition(5 * size + 2 *playerButtonsCards.get(2).getWidth(), actualText.getHeight());
                    playerButtonsCards.get(5).setPosition(5 * size + 2 *playerButtonsCards.get(2).getWidth(), actualText.getHeight() + playerButtonsCards.get(5).getHeight() + 25);
                }
                break;

            case 4:

                if (!playerButtonsCards.isEmpty()) {

                    float size = (((camera.viewportWidth - board.getWidth()) / 2) - playerButtonsCards.get(0).getWidth()) / 2;

                    playerButtonsCards.get(0).setPosition(size, actualText.getHeight());
                    playerButtonsCards.get(1).setPosition(size, actualText.getHeight() + playerButtonsCards.get(1).getHeight() + 25);

                    playerButtonsCards.get(2).setPosition(3 * size + playerButtonsCards.get(2).getWidth() , actualText.getHeight());
                    playerButtonsCards.get(3).setPosition(3 * size + playerButtonsCards.get(2).getWidth(), actualText.getHeight() + playerButtonsCards.get(3).getHeight() + 25);

                }
                break;

            case 5:
            case 6:

                if (!playerButtonsCards.isEmpty()) {

                    float size = (((camera.viewportWidth - board.getWidth()) / 3) - playerButtonsCards.get(0).getWidth()) / 2;

                    playerButtonsCards.get(0).setPosition(size, actualText.getHeight());
                    playerButtonsCards.get(1).setPosition(3 * size + playerButtonsCards.get(0).getWidth() , actualText.getHeight());
                    playerButtonsCards.get(2).setPosition(5 * size + 2 *playerButtonsCards.get(0).getWidth(), actualText.getHeight());

                }
                break;
        }

        for(ImageButton c : playerButtonsCards){

            c.setVisible(false);
            stage.addActor(c);
        }

    }

    /**
     * sets the cards' imageButtons listeners
     * @param cardButton
     */
    private void setupActualCardsListener(ImageButton cardButton) {

        cardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                backCards.setVisible(true);
                for(ImageButton c : playerButtonsCards){

                    c.setVisible(false);
                }
            }
        });
    }

    /**
     * loads the imageButtons of the cards that are turned over
     */
    private void loadBackCards() {

        Sprite backCard = atlas.createSprite("backCard");

        ImageButton backCard1 = new ImageButton(new SpriteDrawable(backCard));
        setupBackCardsListener(backCard1);

        ImageButton backCard2 = new ImageButton(new SpriteDrawable(backCard));
        setupBackCardsListener(backCard2);

        ImageButton backCard3 = new ImageButton(new SpriteDrawable(backCard));
        setupBackCardsListener(backCard3);

        ImageButton backCard4 = new ImageButton(new SpriteDrawable(backCard));
        setupBackCardsListener(backCard4);

        ImageButton backCard5 = new ImageButton(new SpriteDrawable(backCard));
        setupBackCardsListener(backCard5);

        ImageButton backCard6 = new ImageButton(new SpriteDrawable(backCard));
        setupBackCardsListener(backCard6);

        switch (getInstance().getJoinedPlayers().size()) {

            case 3:

                backCards = new Table();
                backCards.setFillParent(true);
                backCards.bottom();
                backCards.padRight(board.getWidth());
                backCards.padBottom(actualText.getPrefHeight());
                backCards.add(backCard1).expandX().spaceBottom(25);
                backCards.add(backCard2).expandX().spaceBottom(25);
                backCards.add(backCard3).expandX().spaceBottom(25);
                backCards.row();
                backCards.add(backCard4).expandX();
                backCards.add(backCard5).expandX();
                backCards.add(backCard6).expandX();

                break;

            case 4:

                backCards = new Table();
                backCards.setFillParent(true);
                backCards.bottom();
                backCards.padRight(board.getWidth());
                backCards.padBottom(actualText.getPrefHeight());
                backCards.add(backCard1).expandX().spaceBottom(25);
                backCards.add(backCard2).expandX().spaceBottom(25);
                backCards.row();
                backCards.add(backCard3).expandX();
                backCards.add(backCard4).expandX();

                break;

            case 5:
            case 6:

                backCards = new Table();
                backCards.setFillParent(true);
                backCards.bottom();
                backCards.padRight(board.getWidth());
                backCards.padBottom(actualText.getPrefHeight());
                backCards.add(backCard1).expandX().spaceBottom(25);
                backCards.add(backCard2).expandX().spaceBottom(25);
                backCards.add(backCard3).expandX();

        }

        backCards.setVisible(true);
        stage.addActor(backCards);
    }

    /**
     * sets the turned over cards' imageButtons listener
     * @param cardButton
     */
    private void setupBackCardsListener(ImageButton cardButton) {

        cardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                backCards.setVisible(false);
                loadPlayerCards();
                for(ImageButton c : playerButtonsCards){

                    c.setVisible(true);
                }
            }
        });
    }

    /**
     * loads the sprites that represent the players on the board
     */
    private void loadPlayers() {

        ArrayList<Character> players = getInstance().getJoinedPlayers();

        for (Character c : players) {

            switch (c.toString()) {

                case "Miss Scarlet":

                    Sprite missScarlet = atlas.createSprite("Token_Miss_Scarlet");
                    missScarlet.setSize(missScarlet.getWidth() * 1.3f, missScarlet.getHeight() * 1.3f);
                    missScarlet.setPosition(camera.viewportWidth - 17 * (board.getWidth() / 24), 0);
                    characterSprites.add(missScarlet);
                    break;

                case "Professor Plum":

                    Sprite professorPlum = atlas.createSprite("Token_Professor_Plum");
                    professorPlum.setSize(professorPlum.getWidth() * 1.3f, professorPlum.getHeight() * 1.3f);
                    professorPlum.setPosition(camera.viewportWidth - 1 * (board.getWidth() / 24), camera.viewportHeight - 20 * (board.getHeight() / 25));
                    characterSprites.add(professorPlum);
                    break;

                case "Mrs Peacock":

                    Sprite mrsPeacock = atlas.createSprite("Token_Mrs_Peacock");
                    mrsPeacock.setSize(mrsPeacock.getWidth() * 1.3f, mrsPeacock.getHeight() * 1.3f);
                    mrsPeacock.setPosition(camera.viewportWidth - 1 * (board.getWidth() / 24), camera.viewportHeight - 7 * (board.getHeight() / 25));
                    characterSprites.add(mrsPeacock);
                    break;

                case "Mr Green":

                    Sprite mrGreen = atlas.createSprite("Token_The_Reverend_Green");
                    mrGreen.setSize(mrGreen.getWidth() * 1.3f, mrGreen.getHeight() * 1.3f);
                    mrGreen.setPosition(camera.viewportWidth - 10 * (board.getWidth() / 24), camera.viewportHeight - 1 * (board.getHeight() / 25));
                    characterSprites.add(mrGreen);
                    break;

                case "Colonel Mustard":

                    Sprite colonelMustard = atlas.createSprite("Token_Colonel_Mustard");
                    colonelMustard.setSize(colonelMustard.getWidth() * 1.3f, colonelMustard.getHeight() * 1.3f);
                    colonelMustard.setPosition(camera.viewportWidth - 24 * (board.getWidth() / 24), camera.viewportHeight - 18 * (board.getHeight() / 25));
                    characterSprites.add(colonelMustard);
                    break;

                case "Mrs White":

                    Sprite mrsWhite = atlas.createSprite("Token_Mrs_White");
                    mrsWhite.setSize(mrsWhite.getWidth() * 1.3f, mrsWhite.getHeight() * 1.3f);
                    mrsWhite.setPosition(camera.viewportWidth - 15 * (board.getWidth() / 24), camera.viewportHeight - 1 * (board.getHeight() / 25));
                    characterSprites.add(mrsWhite);
                    break;

            }
        }
    }

    /**
     * loads elementes required to display this GameView
     */
    private void loadElements() {

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        FitViewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        multiplexer = new InputMultiplexer();

        stage = new Stage(viewport, batch);

        initMultiplexer();

        Gdx.input.setInputProcessor(multiplexer);
    }

    /**
     * initialises multiplexer
     */
    private void initMultiplexer() {

        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new GestureDetector(new GestureDetector.GestureAdapter() {

            @Override
            public boolean fling(float velocityX, float velocityY, int button) {

                return movePlayer(velocityX, velocityY);
            }
        }));
    }

    /**
     * loads the assets of this GameView
     */
    private void loadAssets() {

        atlas = new TextureAtlas("assets.txt");

        board = atlas.createSprite("board");
        board.setPosition(camera.viewportWidth - board.getWidth(), 0);

        addButtons();

        addLabel();

        addTables();

        stage.addActor(dicesTable);
        stage.addActor(moveTable);
        stage.addActor(entranceTable);
        stage.addActor(enterRoomTable);
        stage.addActor(roomTable);
        stage.addActor(roomSecretTable);
        stage.addActor(textTable);

    }

    /**
     * loads the tables
     */
    private void addTables() {

        dicesTable = new Table();
        dicesTable.setFillParent(true);
        dicesTable.top();
        dicesTable.padRight(board.getWidth());
        dicesTable.add(rollDiceButton);

        moveTable = new Table();
        moveTable.setFillParent(true);
        moveTable.top();
        moveTable.padRight(board.getWidth());
        moveTable.add(moveMakeAccusationButton);
        moveTable.row();
        moveTable.add(moveEndTurnButton);

        entranceTable = new Table();
        entranceTable.setFillParent(true);
        entranceTable.padRight(board.getWidth());
        entranceTable.top();
        entranceTable.add(entranceMakeAccusationButton);
        entranceTable.row();
        entranceTable.add(entranceEndTurnButton);
        entranceTable.row();
        entranceTable.add(enterRoomButton);

        enterRoomTable = new Table();
        enterRoomTable.setFillParent(true);
        enterRoomTable.padRight(board.getWidth());
        enterRoomTable.top();
        enterRoomTable.add(enterMakeSuggestionButton);

        roomTable = new Table();
        roomTable.setFillParent(true);
        roomTable.padRight(board.getWidth());
        roomTable.top();
        roomTable.add(roomMakeSuggestionButton);
        roomTable.row();
        roomTable.add(roomMakeAccusationButton);
        roomTable.row();
        roomTable.add(roomExitRoomButton);
        roomTable.row();
        roomTable.add(roomEndTurnButton);

        roomSecretTable = new Table();
        roomSecretTable.setFillParent(true);
        roomSecretTable.padRight(board.getWidth());
        roomSecretTable.top();
        roomSecretTable.add(secretMakeSuggestionButton);
        roomSecretTable.row();
        roomSecretTable.add(secretMakeAccusationButton);
        roomSecretTable.row();
        roomSecretTable.add(secretExitRoomButton);
        roomSecretTable.row();
        roomSecretTable.add(secretPassButton);
        roomSecretTable.row();
        roomSecretTable.add(secretEndTurnButton);

        textTable = new Table();
        textTable.setFillParent(true);
        textTable.padRight(board.getWidth());
        textTable.bottom();
        textTable.add(actualText);

    }

    /**
     * adds the label that shows the status of the game
     */
    private void addLabel() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SHLOP.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        BitmapFont font12 = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font12;
        labelStyle.fontColor = new Color(1f, 1f, 1f, 1);

        actualText = new Label("", labelStyle);
        actualText.setAlignment(Align.center);
    }

    /**
     * loads the imageButtons
     */
    private void addButtons() {

        Sprite rollDice = atlas.createSprite("rollDices");
        rollDiceButton = new ImageButton(new SpriteDrawable(rollDice));

        Sprite exitRoom = atlas.createSprite("exitRoom");
        roomExitRoomButton = new ImageButton(new SpriteDrawable(exitRoom));
        secretExitRoomButton = new ImageButton(new SpriteDrawable(exitRoom));

        Sprite makeAccusation = atlas.createSprite("makeAccusation");
        moveMakeAccusationButton = new ImageButton(new SpriteDrawable(makeAccusation));
        entranceMakeAccusationButton = new ImageButton(new SpriteDrawable(makeAccusation));
        roomMakeAccusationButton = new ImageButton(new SpriteDrawable(makeAccusation));
        secretMakeAccusationButton = new ImageButton(new SpriteDrawable(makeAccusation));
        makeAccusation(moveMakeAccusationButton);
        makeAccusation(entranceMakeAccusationButton);
        makeAccusation(roomMakeAccusationButton);
        makeAccusation(secretMakeAccusationButton);

        Sprite makeSuggestion = atlas.createSprite("makeSuggestion");
        enterMakeSuggestionButton = new ImageButton(new SpriteDrawable(makeSuggestion));
        roomMakeSuggestionButton = new ImageButton(new SpriteDrawable(makeSuggestion));
        secretMakeSuggestionButton = new ImageButton(new SpriteDrawable(makeSuggestion));
        makeSuggestion(enterMakeSuggestionButton);
        makeSuggestion(roomMakeSuggestionButton);
        makeSuggestion(secretMakeSuggestionButton);

        Sprite secretPass = atlas.createSprite("secretPass");
        secretPassButton = new ImageButton(new SpriteDrawable(secretPass));

        Sprite enterRoom = atlas.createSprite("enterRoom");
        enterRoomButton = new ImageButton(new SpriteDrawable(enterRoom));

        Sprite endTurn = atlas.createSprite("endTurn");
        moveEndTurnButton = new ImageButton(new SpriteDrawable(endTurn));
        entranceEndTurnButton = new ImageButton(new SpriteDrawable(endTurn));
        roomEndTurnButton = new ImageButton(new SpriteDrawable(endTurn));
        secretEndTurnButton = new ImageButton(new SpriteDrawable(endTurn));
        endTurnListener(moveEndTurnButton);
        endTurnListener(entranceEndTurnButton);
        endTurnListener(roomEndTurnButton);
        endTurnListener(secretEndTurnButton);

        setupListeners();

    }

    /**
     * loads the exit buttons
     */
    private void addExitButtons() {

        ballRoomOne = new Actor();
        ballRoomOne.setPosition(camera.viewportWidth - 17 * (board.getWidth() / 24), camera.viewportHeight - 6 * (board.getHeight() / 25));
        ballRoomOne.setSize(60, 60);
        exitListener(ballRoomOne, 0);

        ballRoomTwo = new Actor();
        ballRoomTwo.setPosition(camera.viewportWidth - 8 * (board.getWidth() / 24), camera.viewportHeight - 6 * (board.getHeight() / 25));
        ballRoomTwo.setSize(60, 60);
        exitListener(ballRoomTwo, 1);

        ballRoomThree = new Actor();
        ballRoomThree.setPosition(camera.viewportWidth - 15 * (board.getWidth() / 24), camera.viewportHeight - 9 * (board.getHeight() / 25));
        ballRoomThree.setSize(60, 60);
        exitListener(ballRoomThree, 2);

        ballRoomFour = new Actor();
        ballRoomFour.setPosition(camera.viewportWidth - 10 * (board.getWidth() / 24), camera.viewportHeight - 9 * (board.getHeight() / 25));
        ballRoomFour.setSize(60, 60);
        exitListener(ballRoomFour, 3);

        billiardExitOne = new Actor();
        billiardExitOne.setPosition(camera.viewportWidth - 7 * (board.getWidth() / 24), camera.viewportHeight - 10 * (board.getHeight() / 25));
        billiardExitOne.setSize(60, 60);
        exitListener(billiardExitOne, 0);

        billiardExitTwo = new Actor();
        billiardExitTwo.setPosition(camera.viewportWidth - 2 * (board.getWidth() / 24), camera.viewportHeight - 14 * (board.getHeight() / 25));
        billiardExitTwo.setSize(60, 60);
        exitListener(billiardExitTwo, 1);

        libraryOne = new Actor();
        libraryOne.setPosition(camera.viewportWidth - 4 * (board.getWidth() / 24), camera.viewportHeight - 14 * (board.getHeight() / 25));
        libraryOne.setSize(60, 60);
        exitListener(libraryOne, 0);

        libraryTwo = new Actor();
        libraryTwo.setPosition(camera.viewportWidth - 8 * (board.getWidth() / 24), camera.viewportHeight - 17 * (board.getHeight() / 25));
        libraryTwo.setSize(60, 60);
        exitListener(libraryTwo, 1);

        hallOne = new Actor();
        hallOne.setPosition(camera.viewportWidth - 13 * (board.getWidth() / 24), camera.viewportHeight - 18 * (board.getHeight() / 25));
        hallOne.setSize(60, 60);
        exitListener(hallOne, 0);

        hallTwo = new Actor();
        hallTwo.setPosition(camera.viewportWidth - 12 * (board.getWidth() / 24), camera.viewportHeight - 18 * (board.getHeight() / 25));
        hallTwo.setSize(60, 60);
        exitListener(hallTwo, 1);

        hallThree = new Actor();
        hallThree.setPosition(camera.viewportWidth - 9 * (board.getWidth() / 24), camera.viewportHeight - 21 * (board.getHeight() / 25));
        hallThree.setSize(60, 60);
        exitListener(hallThree, 2);

        dinningExitOne = new Actor();
        dinningExitOne.setPosition(camera.viewportWidth - 16 * (board.getWidth() / 24), camera.viewportHeight - 13 * (board.getHeight() / 25));
        dinningExitOne.setSize(60, 60);
        exitListener(dinningExitOne, 0);

        dinningExitTwo = new Actor();
        dinningExitTwo.setPosition(camera.viewportWidth - 18 * (board.getWidth() / 24), camera.viewportHeight - 17 * (board.getHeight() / 25));
        dinningExitTwo.setSize(60, 60);
        exitListener(dinningExitTwo, 1);

        disableExitButton();

        stage.addActor(ballRoomOne);
        stage.addActor(ballRoomTwo);
        stage.addActor(ballRoomThree);
        stage.addActor(ballRoomFour);
        stage.addActor(billiardExitOne);
        stage.addActor(billiardExitTwo);
        stage.addActor(libraryOne);
        stage.addActor(libraryTwo);
        stage.addActor(hallOne);
        stage.addActor(hallTwo);
        stage.addActor(hallThree);
        stage.addActor(dinningExitOne);
        stage.addActor(dinningExitTwo);

    }

    /**
     * sets the exit buttons invisible
     */
    private void disableExitButton() {

        ballRoomOne.setVisible(false);
        ballRoomTwo.setVisible(false);
        ballRoomThree.setVisible(false);
        ballRoomFour.setVisible(false);
        billiardExitOne.setVisible(false);
        billiardExitTwo.setVisible(false);
        libraryOne.setVisible(false);
        libraryTwo.setVisible(false);
        hallOne.setVisible(false);
        hallTwo.setVisible(false);
        hallThree.setVisible(false);
        dinningExitOne.setVisible(false);
        dinningExitTwo.setVisible(false);

    }

    /**
     * sets the exit buttons listeners
     * @param actor
     * @param number
     */
    private void exitListener(final Actor actor, final int number) {

        actor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ArrayList<Character> players = getInstance().getJoinedPlayers();
                Character character = GameController.getInstance().getCurrentPlayer();
                Player player = GameController.getInstance().getPlayerByCharacter(character);

                for (int i = 0; i < players.size(); i++) {

                    if (players.get(i).equals(character)) {

                        characterSprites.get(i).setPosition(actor.getX(), actor.getY());
                        if (!getInstance().getBoard().lookForExit(player).isEmpty())
                            player.setPosition(getInstance().getBoard().lookForExit(player).get(number));
                        disableExitButton();
                        movePlayer = true;
                        exitRoom = false;
                    }
                }
            }
        });
    }

    /**
     * sets the listeners of the available actions imagesButtons
     */
    private void setupListeners() {

        rollDiceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                rollDices = false;
                startTurn = false;
                getInstance().stateMachine();
                actualText.setText("You rolled " + getInstance().getRemainingSteps(getInstance().getCurrentPlayer()));
                verifyIfInRoom();
            }
        });

        roomExitRoomButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                exitRoom();
            }
        });

        secretExitRoomButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                exitRoom();
            }
        });

        secretPassButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                secretPass();
            }
        });

        enterRoomButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                enterRoom();
            }
        });

    }

    /**
     * moves the player through the secret passage
     */
    private void secretPass() {

        ArrayList<Character> players = getInstance().getJoinedPlayers();
        Character character = GameController.getInstance().getCurrentPlayer();
        Player player = GameController.getInstance().getPlayerByCharacter(character);

        switch (player.getPosition().toString()) {

            case "Kitchen":

                for (int i = 0; i < players.size(); i++) {

                    if (players.get(i).equals(character)) {

                        player.setPosition(getInstance().getBoard().getRoom(Study));
                        characterSprites.get(i).setPosition(camera.viewportWidth - 4 * (board.getWidth() / 24), camera.viewportHeight - 22 * (board.getHeight() / 25));
                    }
                }
                break;

            case "Study":

                for (int i = 0; i < players.size(); i++) {

                    if (players.get(i).equals(character)) {

                        player.setPosition(getInstance().getBoard().getRoom(Kitchen));
                        characterSprites.get(i).setPosition(camera.viewportWidth - 22 * (board.getWidth() / 24), camera.viewportHeight - 6 * (board.getHeight() / 25));
                    }
                }
                break;

            case "Conservatory":

                for (int i = 0; i < players.size(); i++) {

                    if (players.get(i).equals(character)) {

                        player.setPosition(getInstance().getBoard().getRoom(Lounge));
                        characterSprites.get(i).setPosition(camera.viewportWidth - 21 * (board.getWidth() / 24), camera.viewportHeight - 23 * (board.getHeight() / 25));
                    }
                }
                break;

            case "Lounge":

                for (int i = 0; i < players.size(); i++) {

                    if (players.get(i).equals(character)) {

                        player.setPosition(getInstance().getBoard().getRoom(Conservatory));
                        characterSprites.get(i).setPosition(camera.viewportWidth - 4 * (board.getWidth() / 24), camera.viewportHeight - 5 * (board.getHeight() / 25));

                    }
                }
                break;
        }
    }

    /**
     * updates the player status as he enters the room
     */
    private void enterRoom() {

        ArrayList<Character> players = getInstance().getJoinedPlayers();
        Character character = getInstance().getCurrentPlayer();
        Player player = getInstance().getPlayerByCharacter(character);


        if (getInstance().getBoard().lookForEntrance(player) != null) {

            atHallway = false;
            atRoom = true;
            movePlayer = false;

            Room room = getInstance().getBoard().lookForEntrance(player).toRoom();

            player.setPosition(room);

            switch (room.toString()) {

                case "Kitchen":

                    for (int i = 0; i < players.size(); i++) {

                        if (players.get(i).equals(character)) {

                            characterSprites.get(i).setPosition(camera.viewportWidth - 22 * (board.getWidth() / 24), camera.viewportHeight - 6 * (board.getHeight() / 25));
                            secretPass = true;
                        }
                    }
                    break;

                case "Ballroom":

                    for (int i = 0; i < players.size(); i++) {

                        if (players.get(i).equals(character)) {

                            characterSprites.get(i).setPosition(camera.viewportWidth - 13 * (board.getWidth() / 24), camera.viewportHeight - 6 * (board.getHeight() / 25));
                            secretPass = false;
                        }
                    }
                    break;

                case "Conservatory":

                    for (int i = 0; i < players.size(); i++) {

                        if (players.get(i).equals(character)) {

                            characterSprites.get(i).setPosition(camera.viewportWidth - 4 * (board.getWidth() / 24), camera.viewportHeight - 5 * (board.getHeight() / 25));
                            secretPass = true;
                        }
                    }
                    break;
                case "Dinning Room":

                    for (int i = 0; i < players.size(); i++) {

                        if (players.get(i).equals(character)) {

                            characterSprites.get(i).setPosition(camera.viewportWidth - 21 * (board.getWidth() / 24), camera.viewportHeight - 14 * (board.getHeight() / 25));
                            secretPass = false;
                        }
                    }
                    break;
                case "Billiard Room":

                    for (int i = 0; i < players.size(); i++) {

                        if (players.get(i).equals(character)) {

                            characterSprites.get(i).setPosition(camera.viewportWidth - 4 * (board.getWidth() / 24), camera.viewportHeight - 12 * (board.getHeight() / 25));
                            secretPass = false;
                        }
                    }
                    break;
                case "Library":

                    for (int i = 0; i < players.size(); i++) {

                        if (players.get(i).equals(character)) {

                            characterSprites.get(i).setPosition(camera.viewportWidth - 4 * (board.getWidth() / 24), camera.viewportHeight - 18 * (board.getHeight() / 25));
                            secretPass = false;
                        }
                    }
                    break;
                case "Lounge":

                    for (int i = 0; i < players.size(); i++) {

                        if (players.get(i).equals(character)) {

                            characterSprites.get(i).setPosition(camera.viewportWidth - 21 * (board.getWidth() / 24), camera.viewportHeight - 23 * (board.getHeight() / 25));
                            secretPass = true;
                        }
                    }
                    break;
                case "Hall":

                    for (int i = 0; i < players.size(); i++) {

                        if (players.get(i).equals(character)) {

                            characterSprites.get(i).setPosition(camera.viewportWidth - 12 * (board.getWidth() / 24), camera.viewportHeight - 23 * (board.getHeight() / 25));
                            secretPass = false;
                        }
                    }
                    break;
                case "Study":

                    for (int i = 0; i < players.size(); i++) {

                        if (players.get(i).equals(character)) {

                            characterSprites.get(i).setPosition(camera.viewportWidth - 4 * (board.getWidth() / 24), camera.viewportHeight - 22 * (board.getHeight() / 25));
                            secretPass = true;
                        }
                    }
                    break;
            }

        }
    }

    /**
     * updates the player status as he leaves the room
     */
    private void exitRoom() {

        ArrayList<Character> players = getInstance().getJoinedPlayers();
        Character character = GameController.getInstance().getCurrentPlayer();
        Player player = GameController.getInstance().getPlayerByCharacter(character);

        switch (player.getPosition().toString()) {

            case "Kitchen":

                for (int i = 0; i < players.size(); i++) {

                    if (players.get(i).equals(character)) {

                        characterSprites.get(i).setPosition(camera.viewportWidth - 20 * (board.getWidth() / 24), camera.viewportHeight - 8 * (board.getHeight() / 25));
                        player.setPosition(getInstance().getBoard().lookForExit(player).get(0));
                        movePlayer = true;
                        exitRoom = false;
                    }
                }

                break;

            case "Ballroom":

                actualText.setText("Choose an exit");
                ballRoomOne.setVisible(true);
                ballRoomTwo.setVisible(true);
                ballRoomThree.setVisible(true);
                ballRoomFour.setVisible(true);
                break;

            case "Conservatory":

                for (int i = 0; i < players.size(); i++) {

                    if (players.get(i).equals(character)) {

                        characterSprites.get(i).setPosition(camera.viewportWidth - 6 * (board.getWidth() / 24), camera.viewportHeight - 6 * (board.getHeight() / 25));
                        player.setPosition(getInstance().getBoard().lookForExit(player).get(0));
                        movePlayer = true;
                        exitRoom = false;
                    }
                }
                break;

            case "Dinning Room":

                actualText.setText("Choose an exit");
                dinningExitOne.setVisible(true);
                dinningExitTwo.setVisible(true);
                break;

            case "Billiard Room":

                actualText.setText("Choose an exit");
                exitRoom = false;
                billiardExitOne.setVisible(true);
                billiardExitTwo.setVisible(true);
                break;

            case "Library":

                actualText.setText("Choose an exit");
                libraryOne.setVisible(true);
                libraryTwo.setVisible(true);
                break;

            case "Lounge":

                for (int i = 0; i < players.size(); i++) {

                    if (players.get(i).equals(character)) {

                        characterSprites.get(i).setPosition(camera.viewportWidth - 18 * (board.getWidth() / 24), camera.viewportHeight - 19 * (board.getHeight() / 25));
                        player.setPosition(getInstance().getBoard().lookForExit(player).get(0));
                        movePlayer = true;
                        exitRoom = false;
                    }
                }
                break;

            case "Hall":

                actualText.setText("Choose an exit");
                hallOne.setVisible(true);
                hallTwo.setVisible(true);
                hallThree.setVisible(true);
                break;

            case "Study":

                for (int i = 0; i < players.size(); i++) {

                    if (players.get(i).equals(character)) {

                        characterSprites.get(i).setPosition(camera.viewportWidth - 7 * (board.getWidth() / 24), camera.viewportHeight - 21 * (board.getHeight() / 25));
                        player.setPosition(getInstance().getBoard().lookForExit(player).get(0));
                        movePlayer = true;
                        exitRoom = false;
                    }
                }
                break;
        }
    }

    /**
     * sets the end of turn imageButton listener
     * @param button
     */
    private void endTurnListener(ImageButton button) {

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                endTurn();
            }
        });
    }

    /**
     * sets the make accusation imageButton listener
     * @param button
     */
    private void makeAccusation(ImageButton button) {

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                makeAccusation();
            }
        });
    }

    /**
     * sets the make suggestion imageButton listener
     * @param button
     */
    private void makeSuggestion(ImageButton button) {

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                makeSuggestion();
            }
        });
    }

    /**
     * processes the end of the current player's turn
     */
    private void endTurn() {

        getInstance().currentPlayerEndTurn();
        getInstance().stateMachine();
        backCards.setVisible(true);
        for (int i = 0; i < getInstance().getJoinedPlayers().size(); i++) {

            if (getInstance().getJoinedPlayers().get(i) == getInstance().getCurrentPlayer()) {

                for(ImageButton c : playerButtonsCards){

                    c.setVisible(false);
                }
            }
        }
        exitRoom = false;
        startTurn = true;
        rollDices = true;
        movePlayer = false;
    }

    /**
     * checks if a player is in a room and updates his movement permissiions accordingly
     */
    private void verifyIfInRoom() {

        Character character = getInstance().getCurrentPlayer();
        Player player = getInstance().getPlayerByCharacter(character);

        if (getInstance().getBoard().lookForExit(player) != null) {

            exitRoom = true;

            secretPass = getInstance().getBoard().lookForSecPas(player) != null;

        } else movePlayer = true;

    }

    /**
     * changes to the accusation view to perform an accusation
     */
    private void makeAccusation() {

        this.pause();
        getInstance().setAccuse(true);
        Cluedo.getInstance().insertScreens(this);
        Cluedo.getInstance().setCurrentState(new AccusationView(Cluedo.getInstance().getGameView()));
        backCards.setVisible(true);
        for (int i = 0; i < getInstance().getJoinedPlayers().size(); i++) {

            if (getInstance().getJoinedPlayers().get(i) == getInstance().getCurrentPlayer()) {

                for(ImageButton c : playerButtonsCards){

                    c.setVisible(false);
                }
            }
        }
    }

    /**
     * changes to the accusation view to perform a suggestion
     */
    private void makeSuggestion() {

        this.pause();
        getInstance().setAccuse(false);
        Cluedo.getInstance().insertScreens(this);
        Cluedo.getInstance().setCurrentState(new AccusationView(Cluedo.getInstance().getGameView()));
        backCards.setVisible(true);
        for (int i = 0; i < getInstance().getJoinedPlayers().size(); i++) {

            if (getInstance().getJoinedPlayers().get(i) == getInstance().getCurrentPlayer()) {

                for(ImageButton c : playerButtonsCards){

                    c.setVisible(false);
                }
            }
        }
        atRoom = false;
        atHallway = true;
    }

    /**
     * this function interprets the movement on the screen and translates it into a move in game
     * @param velocityX
     * @param velocityY
     * @return true if the humam player can move and false if he can't
     */
    private boolean movePlayer(float velocityX, float velocityY) {

        ArrayList<Character> players = getInstance().getJoinedPlayers();
        Character character = GameController.getInstance().getCurrentPlayer();
        Player player = GameController.getInstance().getPlayerByCharacter(character);
        int remainingSteps = GameController.getInstance().getRemainingSteps(character);

        if (remainingSteps > 0 && movePlayer) {

            if (abs(velocityX) > abs(velocityY)) {
                if (velocityX > 0) {

                    if (GameController.getInstance().getBoard().lookRight(player) != null) {

                        GameController.getInstance().movePlayer(character, GameController.getInstance().getBoard().lookRight(player));

                        for (int i = 0; i < players.size(); i++) {

                            if (players.get(i).equals(character)) {

                                characterSprites.get(i).setPosition(characterSprites.get(i).getX() + (board.getWidth() / 24), characterSprites.get(i).getY());
                                remainingSteps--;
                                actualText.setText("You have " + remainingSteps + " steps left.");
                                getInstance().setRemainingSteps(character, remainingSteps);


                                if (remainingSteps == 0) {
                                    endTurn();
                                }

                            }
                        }

                        return true;
                    }
                }

                if (velocityX < 0) {

                    if (GameController.getInstance().getBoard().lookLeft(player) != null) {

                        GameController.getInstance().movePlayer(character, GameController.getInstance().getBoard().lookLeft(player));

                        for (int i = 0; i < players.size(); i++) {

                            if (players.get(i).equals(character)) {

                                characterSprites.get(i).setPosition(characterSprites.get(i).getX() - (board.getWidth() / 24), characterSprites.get(i).getY());
                                remainingSteps--;
                                actualText.setText("You have " + remainingSteps + " steps left.");
                                getInstance().setRemainingSteps(character, remainingSteps);

                                if (GameController.getInstance().getBoard().lookRight(player) instanceof Entrance) {

                                    moveTable.setVisible(false);
                                    entranceTable.setVisible(true);

                                }

                                if (remainingSteps == 0) {
                                    endTurn();
                                }
                            }
                        }

                        return true;
                    }
                }
            }

            if (abs(velocityY) > abs(velocityX)) {
                if (velocityY < 0) {

                    if (GameController.getInstance().getBoard().lookFront(player) != null) {

                        GameController.getInstance().movePlayer(character, GameController.getInstance().getBoard().lookFront(player));

                        for (int i = 0; i < players.size(); i++) {

                            if (players.get(i).equals(character)) {

                                characterSprites.get(i).setPosition(characterSprites.get(i).getX(), characterSprites.get(i).getY() + (board.getHeight() / 25));
                                remainingSteps--;
                                actualText.setText("You have " + remainingSteps + " steps left.");
                                getInstance().setRemainingSteps(character, remainingSteps);

                                if (GameController.getInstance().getBoard().lookRight(player) instanceof Entrance) {

                                    moveTable.setVisible(false);
                                    entranceTable.setVisible(true);

                                }

                                if (remainingSteps == 0) {
                                    endTurn();
                                }
                            }
                        }

                        return true;
                    }
                }

                if (velocityY > 0) {

                    if (GameController.getInstance().getBoard().lookBack(player) != null) {

                        GameController.getInstance().movePlayer(character, GameController.getInstance().getBoard().lookBack(player));

                        for (int i = 0; i < players.size(); i++) {

                            if (players.get(i).equals(character)) {

                                characterSprites.get(i).setPosition(characterSprites.get(i).getX(), characterSprites.get(i).getY() - (board.getHeight() / 25));
                                remainingSteps--;
                                actualText.setText("You have " + remainingSteps + " steps left.");
                                getInstance().setRemainingSteps(character, remainingSteps);

                                if (GameController.getInstance().getBoard().lookRight(player) instanceof Entrance) {

                                    moveTable.setVisible(false);
                                    entranceTable.setVisible(true);

                                }

                                if (remainingSteps == 0) {
                                    endTurn();
                                }

                            }
                        }

                        return true;
                    }
                }

            }
        }

        return false;
    }

    /**
     * checks the player position and makes visible the appropriate assets
     */
    private void verifyPosition() {

        Character character = getInstance().getCurrentPlayer();
        Player player = getInstance().getPlayerByCharacter(character);

        if (getInstance().getBoard().lookForEntrance(player) != null && atHallway && !rollDices) {

            moveTable.setVisible(false);
            entranceTable.setVisible(true);

        }
    }

    /**
     * makes the tables invisible
     */
    private void disableTables() {

        dicesTable.setVisible(false);
        moveTable.setVisible(false);
        entranceTable.setVisible(false);
        enterRoomTable.setVisible(false);
        roomTable.setVisible(false);
        roomSecretTable.setVisible(false);
    }

    /**
     * returns to the current screen
     */
    private void returnScreen() {

        if (Gdx.input.getInputProcessor() != multiplexer) {
            this.resume();
            if (getInstance().getAccuse()) {

                characterSprites.remove(getInstance().getPlayerRemoved());
            }
            endTurn();
            Gdx.input.setInputProcessor(multiplexer);
        }
    }

    @Override
    public ScreenAdapter renderAndUpdate(float delta) {
        this.render(delta);
        return this;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        returnScreen();

        disableTables();

        batch.begin();
        board.draw(batch);

        for (Sprite s : characterSprites) {

            s.draw(batch);
        }

        verifyPosition();

        if (startTurn) actualText.setText(getInstance().getCurrentPlayer().toString() + "'s move");

        if (rollDices) dicesTable.setVisible(true);

        if (movePlayer) {
            moveTable.setVisible(true);
        }

        if (atRoom) {

            actualText.setText(getInstance().getCurrentPlayer().toString() + "'s suggestion");
            entranceTable.setVisible(false);
            enterRoomTable.setVisible(true);
        }

        if (exitRoom) {

            if (secretPass) {
                roomSecretTable.setVisible(true);
                secretPass = true;
            } else roomTable.setVisible(true);
        }

        batch.end();

        stage.act();
        stage.draw();

    }

    @Override
    public void dispose() {
        stage.dispose();
        atlas.dispose();
    }

}
