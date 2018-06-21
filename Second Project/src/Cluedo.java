package com.cluedo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cluedo.game.controller.GameController;
import com.cluedo.game.view.GameView;
import com.cluedo.game.view.MainMenuView;
import com.cluedo.game.view.ModeGameView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class Cluedo extends Game {

	public static final float DEFAULT_SCALE = 2.4107144f;
	private ScreenAdapter curr_state;
    private Stack<ScreenAdapter> screens;
	private long prev_time ;
	private GameView temp;

	private static Cluedo singleton;

	private Cluedo() {}

	public static Cluedo getInstance( ) {
		if(singleton == null)
		    singleton = new Cluedo();
		return singleton;
	}

	@Override
	public void create () {
		prev_time =0;
		screens = new Stack<>();
        temp = new ModeGameView(null);
		this.curr_state = new MainMenuView(temp);
		temp.setNextScreen( this.curr_state  );
		this.setScreen ( curr_state );
	}

	@Override
	public void dispose () {
		super.dispose();
	}

	public void render(){
		long curr_time= System.currentTimeMillis();
		long delta=curr_time -prev_time;
		prev_time=curr_time;
		ScreenAdapter temp= ((GameView)curr_state).renderAndUpdate((float)delta/1000);
		if (temp!=curr_state){
			this.curr_state=temp;
			this.setScreen(curr_state);
		}
	}

	public void setCurrentState(ScreenAdapter screen) {

		this.curr_state = screen;
	}

	public GameView getGameView() {

		return this.temp;
	}

	public void insertScreens(ScreenAdapter screen){

		this.screens.push(screen);
	}

	public ScreenAdapter getLastScreen(){

		if(!this.screens.empty()) {

			return this.screens.pop();
		}

		return this.curr_state;
	}
}
