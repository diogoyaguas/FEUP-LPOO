package com.cluedo.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
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
import com.cluedo.game.Cluedo;

public class ModeGameView extends GameView {

    private TextureAtlas atlas;
    private Sprite background;
    private Sprite title;
    private float size;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Stage stage;

    /**
     * class constructor
     * @param next_screen
     */
    public ModeGameView(ScreenAdapter next_screen) {
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
     * loads the all the assets present in this GameView (i.e. ImageButtons, tables)
     */
    private void loadAssets() {

        atlas = new TextureAtlas("assets.txt");
        background = atlas.createSprite("background");
        title = atlas.createSprite("cluedo");
        title.scale((float) 1);
        size = (title.getHeight() + 100);

        Sprite singleplayer = atlas.createSprite("singleplayer");
        Sprite multiplayer = atlas.createSprite("multiplayer");

        Table mainTable = new Table();

        mainTable.setFillParent(true);

        mainTable.top();

        ImageButton singleplayerButton = new ImageButton(new SpriteDrawable(singleplayer));

        singleplayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Cluedo.getInstance().setCurrentState(new SinglePlayerView(Cluedo.getInstance().getGameView()));
            }
        });

        ImageButton multiplayerButton = new ImageButton(new SpriteDrawable(multiplayer));

        multiplayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Cluedo.getInstance().setCurrentState(new MultiplayerView(Cluedo.getInstance().getGameView()));
            }
        });

        mainTable.add(singleplayerButton).padTop(size + 150);
        mainTable.row();
        mainTable.add(multiplayerButton).padTop(100);

        stage.addActor(mainTable);

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
    public void dispose() {
        stage.dispose();
        atlas.dispose();
    }

}
