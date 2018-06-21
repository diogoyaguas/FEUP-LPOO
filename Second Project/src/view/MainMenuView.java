package com.cluedo.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cluedo.game.Cluedo;


public class MainMenuView extends GameView {

    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    private Sprite title;
    private Sprite play;
    private Sprite exit;
    private Sprite background;
    private SpriteBatch batch;

    private float size;

    /**
     * class constructor
     * @param next_screen
     */
    public MainMenuView(ScreenAdapter next_screen) {

        super(next_screen);

        loadAssets();

        loadElements();
    }

    /**
     * loads the diferent elements required to display the main menu GameView
     */
    private void loadElements() {

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * loads the assets used in the main menu (imageButtons, sprites and music)
     */
    private void loadAssets() {

        atlas = new TextureAtlas("assets.txt");

        background = atlas.createSprite("background");

        title = atlas.createSprite("cluedo");
        title.scale((float) 1);
        size = (title.getHeight() + 100);

        play = atlas.createSprite("play");
        play.scale((float) 0.5);

        exit = atlas.createSprite("exit");
        exit.scale((float) 0.5);

        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("cluedoMusic.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    @Override
    public ScreenAdapter renderAndUpdate(float delta) {
        this.render(delta);
        return this;
    }

    @Override
    public void show() {

        Table mainTable = new Table();

        mainTable.setFillParent(true);

        mainTable.top();

        Drawable playDrawable = new SpriteDrawable(play);
        ImageButton playButton = new ImageButton(playDrawable);

        Drawable exitDrawable = new SpriteDrawable(exit);
        ImageButton exitButton = new ImageButton(exitDrawable);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Cluedo.getInstance().setCurrentState(new ModeGameView(Cluedo.getInstance().getGameView()));
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        mainTable.add(playButton).padTop(size + 150);
        mainTable.row();
        mainTable.add(exitButton).padTop(100);

        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        background.draw(batch);

        title.setPosition((Gdx.graphics.getWidth() - title.getWidth()) / 2, Gdx.graphics.getHeight() - size);
        title.draw(batch);

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
        stage.dispose();
    }
}
