package com.mygdx.game.handlers;

import java.util.Stack;

import com.mygdx.game.main.Game;
import com.mygdx.game.states.GameState;
import com.mygdx.game.states.Play;

public class GameStateManager {
	private Game game;
	
	public static final int PLAY = 0;
	
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
		return null;
	}

	public void setState(int state){
		GameState g = gameStates.pop();
		g.dispose();
		gameStates.push(getState(state));
	}
}
