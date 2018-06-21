package com.cluedo.game.view;

import com.badlogic.gdx.ScreenAdapter;

public abstract class GameView extends ScreenAdapter {

    protected boolean change;
    private ScreenAdapter nextScreen;

    /**
     * class constructor
     * @param next_screen
     */
    GameView(ScreenAdapter next_screen){
        this.nextScreen = next_screen;
    }

    /**
     *
     * @param delta
     * @return ScreenAdapter
     */
    public abstract ScreenAdapter renderAndUpdate(float delta);

    /**
     * sets the next screen adapter
     * @param next screen adapter
     */
    public void setNextScreen( ScreenAdapter next ){
        this.nextScreen = next;
    }

}