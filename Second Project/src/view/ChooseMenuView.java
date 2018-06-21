package com.cluedo.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ChooseMenuView extends GameView {

    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final Stage stage;
    private final TextureAtlas atlas;
    private final Sprite Miss_Scarlet;
    private final Sprite Professor_Plum;
    private final Sprite Mrs_Peacock;
    private final Sprite Mr_Green;
    private final Sprite Colonel_Mustard;
    private final Sprite Mrs_White;

    /**
     * class constructor
     * @param next_screen
     */
    public ChooseMenuView(ScreenAdapter next_screen) {
        super(next_screen);

        atlas = new TextureAtlas("characters.txt");

        Miss_Scarlet = atlas.createSprite("Character_Miss_Scarlet");
        Professor_Plum = atlas.createSprite("Character_Professor_Plum");
        Mrs_Peacock = atlas.createSprite("Character_Mrs_Peacock");
        Mr_Green = atlas.createSprite("Character_Mr_Green");
        Colonel_Mustard = atlas.createSprite("Character_Colonel_Mustard");
        Mrs_White = atlas.createSprite("Character_Mrs_White");

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
    }

    @Override
    public ScreenAdapter renderAndUpdate(float delta) {
        this.render(delta);
        return this;
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();

        mainTable.setFillParent(true);

        mainTable.top();
        
        ImageButton MissScarletButton = new ImageButton(new SpriteDrawable(Miss_Scarlet));
        MissScarletButton.setPosition(100, 100);

        ImageButton ProfessorPlumButton = new ImageButton(new SpriteDrawable(Professor_Plum));
        ImageButton MrsPeacockButton = new ImageButton(new SpriteDrawable(Mrs_Peacock));
        ImageButton MrGreenButton = new ImageButton(new SpriteDrawable(Mr_Green));
        ImageButton ColonelMustardButton = new ImageButton(new SpriteDrawable(Colonel_Mustard));
        ImageButton MrsWhiteButton = new ImageButton(new SpriteDrawable(Mrs_White));

        ButtonGroup<ImageButton> characters = new ButtonGroup<>(MissScarletButton, ProfessorPlumButton, MrsPeacockButton, MrGreenButton, ColonelMustardButton, MrsWhiteButton);

        characters.setMaxCheckCount(1);
        characters.setMinCheckCount(1);

        characters.setUncheckLast(true);

        stage.addActor(MissScarletButton);
        stage.addActor(ProfessorPlumButton);
        stage.addActor(MrsPeacockButton);
        stage.addActor(MrGreenButton);
        stage.addActor(ColonelMustardButton);
        stage.addActor(MrsWhiteButton);
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        atlas.dispose();
    }
}
