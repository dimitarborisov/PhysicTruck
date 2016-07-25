package com.mygdx.game.handlers;

import java.util.HashMap;
import java.util.Stack;

import com.mygdx.game.main.Game;
import com.mygdx.game.states.GameState;
import com.mygdx.game.states.LevelSelect;
import com.mygdx.game.states.Options;
import com.mygdx.game.states.Play;
import com.mygdx.game.states.SplashScreen;
import com.mygdx.game.transitions.LeftToRightTransition;
import com.mygdx.game.transitions.RightToLeftTransition;
import com.mygdx.game.transitions.SlideFromTop;
import com.mygdx.game.transitions.SlideToTop;

public class GameStateManager {
	private Game game;
	
	private HashMap<Integer,GameState> gameStatesReady; 
	
	//general game states
	public static final int PLAY = 0;
	public static final int LEVELSELECT = 1;
	public static final int SPLASHSCREEN = 2;
	public static final int OPTIONS = 3;
	
	//game transitions
	public static final int SLIDETOTOP = 0;
	public static final int RIGHTLEFT = 1;
	public static final int SLIDEFROMTOP = 2;
	public static final int LEFTRIGHT = 3;
	
	private Stack<GameState> gameStates;
	
	public GameStateManager(Game game){
		this.game = game;
		
		//TESTING
		gameStatesReady = new HashMap<Integer,GameState>();
		gameStatesReady.put(SPLASHSCREEN, new SplashScreen(this));
		gameStatesReady.put(OPTIONS, new Options(this));
		gameStatesReady.put(LEVELSELECT, new LevelSelect(this));
				
		//STATE MANAGER
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
			gameStatesReady.get(LEVELSELECT).reloadState();
			return gameStatesReady.get(LEVELSELECT);
		}
		
		if(state == SPLASHSCREEN){
			gameStatesReady.get(SPLASHSCREEN).reloadState();
			return gameStatesReady.get(SPLASHSCREEN);
		}
		
		if(state == OPTIONS){
			gameStatesReady.get(OPTIONS).reloadState();
			return gameStatesReady.get(OPTIONS);
		}
		
		return null;
	}
	
	private GameState getTransition(int state, GameState from, GameState to, boolean updateFrom, boolean updateTo){
		
		if(state == RIGHTLEFT){
			return new RightToLeftTransition(this, from, to, updateFrom, updateTo);
		}
		if(state == SLIDETOTOP){
			return new SlideToTop(this, from, to, updateFrom, updateTo);
		}
		if(state == SLIDEFROMTOP){
			return new SlideFromTop(this, from, to, updateFrom, updateTo);
		}
		if(state == LEFTRIGHT){
			return new LeftToRightTransition(this, from, to, updateFrom, updateTo);
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
	
	
	
	//IF USING NEW INSTANCE EVERY TIME
	//public void setState(GameState state){
	//	GameState g = gameStates.pop();
	//	g.dispose();
	//	gameStates.push(state);
	//}
	
	//public void setState(int state){
	//	GameState g = gameStates.pop();
	//	g.dispose();
	//	gameStates.push(getState(state));
	//}
}
