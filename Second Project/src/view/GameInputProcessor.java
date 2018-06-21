package com.cluedo.game.view;

import com.badlogic.gdx.input.GestureDetector;

public class GameInputProcessor extends GestureDetector.GestureAdapter {

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        return false;
    }
}
