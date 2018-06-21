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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cluedo.game.Cluedo;
import com.cluedo.game.controller.GameController;
import com.cluedo.game.model.Card.Character;

class MultiplayerView extends GameView {

    private TextureAtlas atlas;
    private Sprite background;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Stage stage;
    private Sprite insertName;
    private Sprite chooseCharacter;
    private Sprite insertNumberPlayers;
    private TextField name;
    private TextField numberPlayers;
    private ImageButton continueButton;
    private ImageButton parchmentButton;
    private ImageButton insertNumberPlayersButton;
    private Table mainTable;
    private Table inputTable;
    private Table nameInserted;
    private Table numberTable;
    private Table numberInserted;
    private Table continueTable;
    private int numberOfPlayers = 1;

    /**
     * class constructor
     * @param next_screen
     */
    MultiplayerView(ScreenAdapter next_screen) {

        super(next_screen);

        loadElements();

        loadAssets();
    }

    /**
     * loads the diferent elements required to display the game GameView
     */
    private void loadElements() {

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * loads the all the assets present in this GameView (i.e. ImageButtons, tables, textfields)
     */
    private void loadAssets() {

        atlas = new TextureAtlas("assets.txt");

        background = atlas.createSprite("background");

        loadTitles();

        loadTextFields();

        loadTables();

        loadCharacters();

        stage.addActor(mainTable);
        stage.addActor(inputTable);
        stage.addActor(nameInserted);
        stage.addActor(numberTable);
        stage.addActor(numberInserted);
        stage.addActor(continueTable);

    }

    /**
     * loads the textfields
     */
    private void loadTextFields() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SHLOP.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 150;
        BitmapFont font12 = generator.generateFont(parameter);
        generator.dispose();

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font12;
        textFieldStyle.fontColor = new Color(0, 0, 0, 1);

        name = new TextField("", textFieldStyle);
        name.setMaxLength(8);
        name.setAlignment(Align.center);

        TextField.TextFieldFilter.DigitsOnlyFilter filter = new TextField.TextFieldFilter.DigitsOnlyFilter();

        numberPlayers = new TextField("", textFieldStyle);
        numberPlayers.setMaxLength(1);
        numberPlayers.setAlignment(Align.center);
        numberPlayers.setTextFieldFilter(filter);
    }

    /**
     * loads the sprites with the titles
     */
    private void loadTitles() {

        insertName = atlas.createSprite("insertName");
        insertName.scale((float) 0.4);

        chooseCharacter = atlas.createSprite("chooseCharacter");
        chooseCharacter.scale((float) 0.4);

        insertNumberPlayers = atlas.createSprite("numberPlayers");
        insertNumberPlayersButton = new ImageButton(new SpriteDrawable(insertNumberPlayers));

        parchmentButton = new ImageButton(new SpriteDrawable(atlas.createSprite("pergamino")));

        Sprite continueImage = atlas.createSprite("continue");
        continueButton = new ImageButton(new SpriteDrawable(continueImage));
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameController.getInstance().setNumPlayers(Integer.parseInt(numberPlayers.getText()));
                numberPlayers.setVisible(false);
                continueButton.setVisible(false);
                numberInserted.setVisible(false);
                numberTable.setVisible(false);
                nameInserted.setVisible(true);
            }
        });
    }

    /**
     * loads the tables
     */
    private void loadTables() {

        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.bottom().padBottom(50);
        mainTable.setVisible(false);

        inputTable = new Table();
        inputTable.setFillParent(true);
        inputTable.top().padTop(insertNumberPlayers.getHeight() + 100);
        inputTable.add(parchmentButton);

        nameInserted = new Table();
        nameInserted.setFillParent(true);
        nameInserted.top().padTop(insertName.getHeight() + 150);
        nameInserted.padRight(20);
        nameInserted.add(name).prefWidth(500).expandX();
        nameInserted.setVisible(false);

        numberTable = new Table();
        numberTable.setFillParent(true);
        numberTable.top().padTop(100);
        numberTable.add(insertNumberPlayersButton);

        numberInserted = new Table();
        numberInserted.setFillParent(true);
        numberInserted.top().padTop(insertNumberPlayers.getHeight() + 150);
        numberInserted.padRight(20);
        numberInserted.add(numberPlayers).prefWidth(500).expandX();

        continueTable = new Table();
        continueTable.setFillParent(true);
        continueTable.top().padTop(insertNumberPlayers.getHeight() + parchmentButton.getHeight() + 200);
        continueTable.add(continueButton);
        continueTable.setVisible(false);
    }

    /**
     * loads the characters' imageButtons
     */
    private void loadCharacters() {

        Sprite missScarlet = atlas.createSprite("Profile_Miss_Scarlet");
        missScarlet.setSize(180, 300);
        ImageButton missScarletButton = new ImageButton(new SpriteDrawable(missScarlet));
        charactersListener(missScarletButton, Character.Miss_Scarlet);

        Sprite professorPlum = atlas.createSprite("Profile_Professor_Plum");
        professorPlum.setSize(180, 300);
        ImageButton professorPlumButton = new ImageButton(new SpriteDrawable(professorPlum));
        charactersListener(professorPlumButton, Character.Professor_Plum);

        Sprite mrsPeacock = atlas.createSprite("Profile_Mrs_Peacock");
        mrsPeacock.setSize(180, 300);
        ImageButton mrsPeacockButton = new ImageButton(new SpriteDrawable(mrsPeacock));
        charactersListener(mrsPeacockButton, Character.Mrs_Peacock);

        Sprite mrGreen = atlas.createSprite("Profile_The_Reverend_Green");
        mrGreen.setSize(180, 300);
        ImageButton mrGreenButton = new ImageButton(new SpriteDrawable(mrGreen));
        charactersListener(mrGreenButton, Character.Mr_Green);

        Sprite colonelMustard = atlas.createSprite("Profile_Colonel_Mustard");
        colonelMustard.setSize(180, 300);
        ImageButton colonelMustardButton = new ImageButton(new SpriteDrawable(colonelMustard));
        charactersListener(colonelMustardButton, Character.Colonel_Mustard);

        Sprite mrsWhite = atlas.createSprite("Profile_Mrs_White");
        mrsWhite.setSize(180, 300);
        ImageButton mrsWhiteButton = new ImageButton(new SpriteDrawable(mrsWhite));
        charactersListener(mrsWhiteButton, Character.Mrs_White);

        mainTable.add(missScarletButton).expandX();
        mainTable.add(professorPlumButton).expandX();
        mainTable.add(mrsPeacockButton).expandX();
        mainTable.add(mrGreenButton).expandX();
        mainTable.add(colonelMustardButton).expandX();
        mainTable.add(mrsWhiteButton).expandX();
    }

    /**
     * loads the characters' imageButtons listeners
     */
    private void charactersListener(final ImageButton button, final Character c){

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(numberOfPlayers == Integer.parseInt(numberPlayers.getText())){

                    GameController.getInstance().joinPlayer(c, name.getText());
                    Cluedo.getInstance().setCurrentState(new MultiplayerGameView(Cluedo.getInstance().getGameView()));
                } else{

                    numberOfPlayers++;
                    mainTable.removeActor(button);
                    mainTable.setVisible(false);
                    name.setText("");
                    GameController.getInstance().joinPlayer(c, name.getText());
                }

            }
        });
    }

    @Override
    public ScreenAdapter renderAndUpdate(float delta) {
        this.render(delta);
        return this;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);

        if(nameInserted.isVisible()) {
            insertName.setPosition(camera.viewportWidth / 2 - insertName.getWidth() / 2, camera.viewportHeight - insertName.getHeight() * 2);
            insertName.draw(batch);
        }

        if (!numberPlayers.getText().equals("") && Integer.parseInt(numberPlayers.getText()) >= 3 && Integer.parseInt(numberPlayers.getText()) <= 6) {

            continueTable.setVisible(true);

        } else continueTable.setVisible(false);

        if (!name.getText().equals("")) {

            chooseCharacter.setPosition(camera.viewportWidth / 2 - chooseCharacter.getWidth() / 2, camera.viewportHeight / 2 - chooseCharacter.getHeight());
            chooseCharacter.draw(batch);

            mainTable.setVisible(true);

        } else mainTable.setVisible(false);

        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void dispose() {
        stage.dispose();
        atlas.dispose();
    }

}



