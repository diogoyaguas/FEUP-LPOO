package com.cluedo.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cluedo.game.Cluedo;
import com.cluedo.game.controller.GameController;
import com.cluedo.game.model.Card.Character;
import com.cluedo.game.model.Card.Division;
import com.cluedo.game.model.Card.Weapon;

import static com.cluedo.game.controller.GameController.getInstance;
import static com.cluedo.game.model.Card.Character.Colonel_Mustard;
import static com.cluedo.game.model.Card.Character.Miss_Scarlet;
import static com.cluedo.game.model.Card.Character.Mr_Green;
import static com.cluedo.game.model.Card.Character.Mrs_Peacock;
import static com.cluedo.game.model.Card.Character.Mrs_White;
import static com.cluedo.game.model.Card.Character.Professor_Plum;
import static com.cluedo.game.model.Card.Division.Ballroom;
import static com.cluedo.game.model.Card.Division.Billiard_Room;
import static com.cluedo.game.model.Card.Division.Conservatory;
import static com.cluedo.game.model.Card.Division.Dinning_Room;
import static com.cluedo.game.model.Card.Division.Hall;
import static com.cluedo.game.model.Card.Division.Kitchen;
import static com.cluedo.game.model.Card.Division.Library;
import static com.cluedo.game.model.Card.Division.Lounge;
import static com.cluedo.game.model.Card.Division.Study;
import static com.cluedo.game.model.Card.Weapon.Candlestick;
import static com.cluedo.game.model.Card.Weapon.Dagger;
import static com.cluedo.game.model.Card.Weapon.Lead_Pipe;
import static com.cluedo.game.model.Card.Weapon.Revolver;
import static com.cluedo.game.model.Card.Weapon.Rope;
import static com.cluedo.game.model.Card.Weapon.Wrench;

class AccusationView extends GameView {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Stage stage;
    private TextureAtlas atlas;

    private Sprite who;
    private Sprite where;
    private Sprite what;

    private ImageButton missScarletCardButton;
    private ImageButton professorPlumCardButton;
    private ImageButton mrsPeacockCardButton;
    private ImageButton mrGreenCardButton;
    private ImageButton colonelMustardCardButton;
    private ImageButton mrsWhiteCardButton;
    private ImageButton kitchenCardButton;
    private ImageButton ballroomCardButton;
    private ImageButton conservatoryCardButton;
    private ImageButton dinningRoomCardButton;
    private ImageButton billiardRoomCardButton;
    private ImageButton libraryCardButton;
    private ImageButton loungeCardButton;
    private ImageButton hallCardButton;
    private ImageButton studyCardButton;
    private ImageButton candlestickCardButton;
    private ImageButton daggerCardButton;
    private ImageButton leadPipeCardButton;
    private ImageButton revolverCardButton;
    private ImageButton ropeCardButton;
    private ImageButton wrenchCardButton;
    private ImageButton exitButton;
    private ImageButton returnButton;

    private Table characterTable;
    private Table divisionTable;
    private Table weaponTable;
    private Table textTable;
    private Table exitTable;
    private Table returnTable;

    private boolean chosenCharacter = false;
    private boolean chosenDivision = false;
    private boolean firstTitle = true;

    private Label actualText;

    /**
     * class construtor
     * @param next_screen
     */
    AccusationView(ScreenAdapter next_screen) {

        super(next_screen);

        loadElements();

        loadAssets();

    }

    /**
     * loads the diferent elements required to display the accusation GameView
     */
    private void loadElements() {

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        FitViewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * loads the all the assets present in this GameView (i.e. ImageButtons)
     */
    private void loadAssets() {

        atlas = new TextureAtlas("assets.txt");

        loadQuestions();

        loadCharactersCards();

        loadDivisionsCards();

        loadWeaponCards();

        loadTables();

        addLabel();

        loadFinalButtons();

        stage.addActor(characterTable);
        stage.addActor(divisionTable);
        stage.addActor(weaponTable);
        stage.addActor(textTable);
        stage.addActor(exitTable);
        stage.addActor(returnTable);
    }


    /**
     * loads the buttons to exit this GameView
     */
    private void loadFinalButtons() {

        Sprite exit = atlas.createSprite("exit");
        exitButton = new ImageButton(new SpriteDrawable(exit));

        Sprite returnImage = atlas.createSprite("return");
        returnButton = new ImageButton(new SpriteDrawable(returnImage));

        exitTable = new Table();
        exitTable.setFillParent(true);
        exitTable.bottom();
        exitTable.padBottom(exit.getHeight());
        exitTable.add(exitButton);

        returnTable = new Table();
        returnTable.setFillParent(true);
        returnTable.bottom();
        returnTable.padBottom(exit.getHeight());
        returnTable.add(returnButton);

        exitTable.setVisible(false);
        returnButton.setVisible(false);

        setupFinalListeners();

    }

    /**
     * creates the listeners for the exit/return buttons
     */
    private void setupFinalListeners() {

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Gdx.app.exit();
            }
        });

        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Cluedo.getInstance().setCurrentState(Cluedo.getInstance().getLastScreen());
            }
        });
    }

    /**
     * loads the tables with each card type so that the player can choose a card from each type to make an accusation
     */
    private void loadTables() {

        characterTable = new Table();
        characterTable.setFillParent(true);
        characterTable.top();
        characterTable.padTop(who.getHeight());
        characterTable.add(missScarletCardButton).expandX();
        characterTable.add(professorPlumCardButton).expandX();
        characterTable.add(mrsPeacockCardButton).expandX();
        characterTable.add(mrGreenCardButton).expandX();
        characterTable.add(colonelMustardCardButton).expandX();
        characterTable.add(mrsWhiteCardButton).expandX();

        divisionTable = new Table();
        divisionTable.setFillParent(true);
        divisionTable.padTop(who.getHeight());
        divisionTable.add(kitchenCardButton).expandX();
        divisionTable.add(ballroomCardButton).expandX();
        divisionTable.add(conservatoryCardButton).expandX();
        divisionTable.add(dinningRoomCardButton).expandX();
        divisionTable.add(billiardRoomCardButton).expandX();
        divisionTable.add(libraryCardButton).expandX();
        divisionTable.add(loungeCardButton).expandX();
        divisionTable.add(hallCardButton).expandX();
        divisionTable.add(studyCardButton).expandX();

        weaponTable = new Table();
        weaponTable.setFillParent(true);
        weaponTable.bottom();
        weaponTable.add(candlestickCardButton).expandX();
        weaponTable.add(daggerCardButton).expandX();
        weaponTable.add(leadPipeCardButton).expandX();
        weaponTable.add(revolverCardButton).expandX();
        weaponTable.add(ropeCardButton).expandX();
        weaponTable.add(wrenchCardButton).expandX();

        divisionTable.setVisible(false);
        weaponTable.setVisible(false);
    }

    /**
     * loads the sprites with the questions to make the accusation
     */
    private void loadQuestions() {

        who = atlas.createSprite("who");
        where = atlas.createSprite("where");
        what = atlas.createSprite("what");

    }


    /**
     * creates the label that reports the state of the game
     */
    private void addLabel() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SHLOP.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 95;
        BitmapFont font12 = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font12;
        labelStyle.fontColor = new Color(1f, 1f, 1f, 1);

        actualText = new Label("", labelStyle);
        actualText.setAlignment(Align.center);

        textTable = new Table();
        textTable.setFillParent(true);
        textTable.add(actualText);
    }

    /**
     * loads the ImageButtons for the characters
     */
    private void loadCharactersCards() {

        missScarletCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Character_Miss_Scarlet")));

        professorPlumCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Character_Professor_Plum")));

        mrsPeacockCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Character_Mrs_Peacock")));

        mrGreenCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Character_The_Reverend_Green")));

        colonelMustardCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Character_Colonel_Mustard")));

        mrsWhiteCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Character_Mrs_White")));

        setupCharactersListeners();
    }

    /**
     * creates the listeners fo the characters ImageButtons
     */
    private void setupCharactersListeners() {

        missScarletCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                chooseCharacter(Miss_Scarlet);
            }
        });

        professorPlumCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                chooseCharacter(Professor_Plum);
            }
        });

        mrsPeacockCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                chooseCharacter(Mrs_Peacock);
            }
        });

        mrGreenCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                chooseCharacter(Mr_Green);
            }
        });

        colonelMustardCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                chooseCharacter(Colonel_Mustard);
            }
        });

        mrsWhiteCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                chooseCharacter(Mrs_White);
            }
        });

    }

    /**
     * sets the character as the accused one
     * @param character
     */
    private void chooseCharacter(Character character) {

        chosenCharacter = true;
        characterTable.setTouchable(Touchable.disabled);
        getInstance().setAccusedCharacter(character);
    }

    /**
     * loads the ImageButtons for the divisions
     */
    private void loadDivisionsCards() {

        kitchenCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Location_Kitchen")));
        
        ballroomCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Location_Ball_room")));

        conservatoryCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Location_Conservatory")));

        dinningRoomCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Location_Dining_Room")));

        billiardRoomCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Location_Billard_Room")));

        libraryCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Location_Library")));

        loungeCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Location_Lounge")));
        
        hallCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Location_Study")));

        studyCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Location_Hall")));

        setupDivisionListeners();
    }

    /**
     * creates the listeners for the divisions ImageButtons
     */
    private void setupDivisionListeners() {

        kitchenCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                chooseDivision(Kitchen);
            }
        });

        ballroomCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                chooseDivision(Ballroom);
            }
        });

        conservatoryCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                chooseDivision(Conservatory);
            }
        });

        dinningRoomCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                chooseDivision(Dinning_Room);
            }
        });

        billiardRoomCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                chooseDivision(Billiard_Room);
            }
        });

        libraryCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                chooseDivision(Library);
            }
        });

        loungeCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                chooseDivision(Lounge);
            }
        });

        hallCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                chooseDivision(Hall);
            }
        });

        studyCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                chooseDivision(Study);
            }
        });

    }

    /**
     * sets the division as the one where the murder occurred
     * @param division
     */
    private void chooseDivision(Division division) {

        chosenDivision = true;
        divisionTable.setTouchable(Touchable.disabled);
        getInstance().setAccusedDivison(division);
    }

    /**
     * loads the ImageButtons for the weapons
     */
    private void loadWeaponCards() {

        candlestickCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Weapon_Candlestick")));

        daggerCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Weapon_Dagger")));

        leadPipeCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Weapon_Lead_Pipe")));

        revolverCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Weapon_Revolver")));

        ropeCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Weapon_Rope")));

        wrenchCardButton = new ImageButton(new SpriteDrawable(atlas.createSprite("Weapon_Spanner")));

        setupWeaponsListeners();

    }

    /**
     * creates the listeners for the weapons ImageButtons
     */
    private void setupWeaponsListeners() {

        candlestickCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(getInstance().getAccuse()) {
                    makeAccusation(Candlestick);
                } else makeSuggestion(Candlestick);
            }
        });

        daggerCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(getInstance().getAccuse()) {
                makeAccusation(Dagger);
                } else makeSuggestion(Dagger);
            }
        });

        leadPipeCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(getInstance().getAccuse()) {
                makeAccusation(Lead_Pipe);
                } else makeSuggestion(Lead_Pipe);
            }
        });

        revolverCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(getInstance().getAccuse()) {
                makeAccusation(Revolver);
                } else makeSuggestion(Revolver);
            }
        });

        ropeCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(getInstance().getAccuse()) {
                makeAccusation(Rope);
                } else makeSuggestion(Rope);
            }
        });

        wrenchCardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(getInstance().getAccuse()) {
                makeAccusation(Wrench);
                } else makeSuggestion(Wrench);
            }
        });
    }

    /**
     * attempts to refute a suggestion made with the previously chosen character and division and the weapon
     * @param weapon
     */
    private void makeSuggestion(Weapon weapon) {

        getInstance().setAccusedWeapon(weapon);
        getInstance().setAccusation();

        firstTitle = false;
        chosenCharacter = false;
        chosenDivision = false;
        characterTable.setVisible(false);
        divisionTable.setVisible(false);
        weaponTable.setVisible(false);

        returnButton.setVisible(true);
        actualText.setText(getInstance().refuteSuggestion());

    }

    /**
     * makes an accusation with the previously chosen character and division and the weapon
     * rules state that if the accusation is incorrect the player loses and is removed from the game
     * @param weapon
     */
    private void makeAccusation(Weapon weapon) {

        getInstance().setAccusedWeapon(weapon);
        getInstance().setAccusation();

        firstTitle = false;
        chosenCharacter = false;
        chosenDivision = false;
        characterTable.setVisible(false);
        divisionTable.setVisible(false);
        weaponTable.setVisible(false);

        boolean isCorrect = getInstance().checkAccusation();
        if (isCorrect) {

            exitTable.setVisible(true);
            actualText.setText("Your accusation is correct.\nCongratulations!\n "
                            + getInstance().getCurrentPlayer().toString() + " ("
                            + getInstance().getPlayerByCharacter(getInstance().getCurrentPlayer()).getName()
                            + ") is the winner!");

        } else {
            if(getInstance().getWinner() == null)
            returnButton.setVisible(true);
            actualText.setText("Your accusation is WRONG, you are out!");
        }

        if(getInstance().getWinner() != null){

            exitTable.setVisible(true);
            actualText.setText("Congratulations!\n" + getInstance().getCurrentPlayer().toString() + " ("
                    + getInstance().getPlayerByCharacter(getInstance().getCurrentPlayer()).getName()
                    + ") is the final player!");
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

        batch.begin();

            if (firstTitle) {
                who.setPosition(camera.viewportWidth / 2 - who.getWidth() / 2, camera.viewportHeight - who.getHeight());
                who.draw(batch);
            }

            if (chosenCharacter) {
                where.setPosition(camera.viewportWidth / 2 - where.getWidth() / 2, camera.viewportHeight - who.getHeight() - characterTable.getPrefHeight());
                where.draw(batch);
            }

            if (chosenDivision) {
                what.setPosition(camera.viewportWidth / 2 - what.getWidth() / 2, camera.viewportHeight - who.getHeight() - characterTable.getPrefHeight() - where.getHeight() - 2 * divisionTable.getMinHeight() / 3);
                what.draw(batch);
            }

            if (chosenCharacter) divisionTable.setVisible(true);

            if (chosenDivision) weaponTable.setVisible(true);

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
