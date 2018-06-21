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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cluedo.game.Cluedo;
import com.cluedo.game.controller.GameController;
import com.cluedo.game.model.Card.Character;

class SinglePlayerView extends GameView {

    private TextureAtlas atlas;
    private Sprite background;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Stage stage;
    private Sprite insertName;
    private Sprite chooseCharacter;
    private TextField name;
    private Table mainTable;
    private Table inputTable;
    private Table nameInserted;
    private ImageButton parchmentButton;

    /**
     * class constructor
     * @param next_screen
     */
    SinglePlayerView(ScreenAdapter next_screen) {

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
    }

    /**
     * loads the sprites with the titles
     */
    private void loadTitles() {

        insertName = atlas.createSprite("insertName");
        insertName.scale((float) 0.4);

        chooseCharacter = atlas.createSprite("chooseCharacter");
        chooseCharacter.scale((float) 0.4);

        parchmentButton = new ImageButton(new SpriteDrawable(atlas.createSprite("pergamino")));

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
        inputTable.top().padTop(insertName.getHeight() + 100);
        inputTable.add(parchmentButton);

        nameInserted = new Table();
        nameInserted.setFillParent(true);
        nameInserted.top().padTop(insertName.getHeight() + 150);
        nameInserted.padRight(20);
        nameInserted.add(name).prefWidth(500).expandX();

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
    private void charactersListener(ImageButton button, final Character c){

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameController.getInstance().setNumPlayers(3);
                GameController.getInstance().joinPlayer(c, name.getText());
                Cluedo.getInstance().setCurrentState(new SingleplayerGameView(Cluedo.getInstance().getGameView()));
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

        insertName.setPosition(camera.viewportWidth / 2 - insertName.getWidth() / 2, camera.viewportHeight - insertName.getHeight() * 2);
        insertName.draw(batch);

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
