package com.mygdx.game.handlers;

import java.util.Stack;

import com.mygdx.game.main.Game;
import com.mygdx.game.states.GameState;
import com.mygdx.game.states.LevelSelect;
import com.mygdx.game.states.Play;
import com.mygdx.game.states.SplashScreen;
import com.mygdx.game.transitions.RightToLeftTransition;

public class GameStateManager {
	private Game game;
	
	//general game states
	public static final int PLAY = 0;
	public static final int LEVELSELECT = 1;
	public static final int SPLASHSCREEN = 2;
	
	//game transitions
	public static final int BOTTOMUP = 0;
	public static final int RIGHTLEFT = 1;
	
	private Stack<GameState> gameStates;
	
	public GameStateManager(Game game){
		this.game = game;
		gameStates = new Stack<GameState>();
		gameStates.push(getState(SPLASHSCREEN));
		
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
	
	private GameState getTransition(int state, GameState from, GameState to, boolean updateFrom, boolean updateTo){
		
		if(state == RIGHTLEFT){
			return new RightToLeftTransition(this, from, to, updateFrom, updateTo);
		}
		
		return null;
	}
	
	public void setTransition(int transition ,GameState from, GameState to){
		setTransition(transition, from, to, false, false);
	}
	
	public void setTransition(int transition ,GameState from, int to){
		setTransition(transition, from, getState(to));
	}
	
	public void setTransition(int transition ,GameState from, int to, boolean updateFrom, boolean updateTo){
		setTransition(transition, from, getState(to), updateFrom, updateTo);
	}
	
	public void setTransition(int transition ,GameState from, GameState to, boolean updateFrom, boolean updateTo){
		GameState g = gameStates.pop();
		g.dispose();
		gameStates.push(getTransition(transition, from, to, updateFrom, updateTo));
	}
	
	public void setState(GameState state){
		GameState g = gameStates.pop();
		g.dispose();
		gameStates.push(state);
	}
	
	public void setState(int state){
		GameState g = gameStates.pop();
		g.dispose();
		gameStates.push(getState(state));
	}
}
