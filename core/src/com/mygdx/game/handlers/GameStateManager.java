package com.mygdx.game.handlers;

import java.util.Stack;

import com.mygdx.game.main.Game;
import com.mygdx.game.states.GameState;
import com.mygdx.game.states.LevelSelect;
import com.mygdx.game.states.Play;
import com.mygdx.game.states.SplashScreen;

public class GameStateManager {
	private Game game;
	
	public static final int PLAY = 0;
	public static final int LEVELSELECT = 1;
	public static final int SPLASHSCREEN = 2;
	
	private Stack<GameState> gameStates;
	
	public GameStateManager(Game game){
		this.game = game;
		gameStates = new Stack<GameState>();
		gameStates.push(getState(PLAY));
		
	}
	
	public Game getGame(){ return game; }
	
	public void update(float dt){
		gameStates.peek().update(dt);
	}
	
	public void render(){
		gameStates.peek().render();
	}
	
	private GameState getState(int state){
		if(state == PLAY){
			return new Play(this);
		}
		
		if(state == LEVELSELECT){
			return new LevelSelect(this);
		}
		
		if(state == SPLASHSCREEN){
			return new SplashScreen(this);
		}
		
		return null;
	}

	public void setState(int state){
		GameState g = gameStates.pop();
		g.dispose();
		gameStates.push(getState(state));
	}
}
