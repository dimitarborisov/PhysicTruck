package com.mygdx.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.main.Game;

public abstract class GameState {
	protected GameStateManager m;
	protected Game game;
	
	protected SpriteBatch s;
	
	protected OrthographicCamera cam;
	protected OrthographicCamera hud;
	
	protected GameState(GameStateManager m){
		this.m = m;
		game = m.getGame();
		s = game.getS();
		cam = game.getCam();
		hud = game.getHud();
	}
	
	public GameStateManager getStateManager(){ return m; }
	
	public abstract void update(float dt);
	public abstract void render();
	public abstract void dispose();
	
	public void reloadState(){
		
	}

}
