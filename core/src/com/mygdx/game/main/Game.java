package com.mygdx.game.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.handlers.ContentManager;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.handlers.StageManager;

public class Game implements ApplicationListener{
	
	//Game name
	public static final String TITLE = "TruckGame";
	
	//Virtual game size
	public static final int VWIDTH = 1000;
	public static final int VHEIGHT = 600;
	
	public static final float STEP = 1/60f;
	private float accumulator;
	
	//Game size scale factor
	public static final int VSCALE = 1; 
	
	private SpriteBatch s;
	private OrthographicCamera cam;
	private OrthographicCamera hud;
	private GameStateManager m;

	
	
	public static StageManager stageManager;
	public static ContentManager cm;
	
	
	@Override
	public void create() {
		//load textures
		cm = new ContentManager();
		cm.loadTexture("sprites/body.png", "Truck");
		cm.loadTexture("sprites/path4209.png", "Wheel");
		cm.loadTexture("sprites/box.png", "Box");
		cm.loadTexture("sprites/track0,0.png", "track0");
		
		cm.loadTexture("sprites/layer1.png", "layer1");
		cm.loadTexture("sprites/layer2.png", "layer2");
		cm.loadTexture("sprites/skybox.png", "skybox");
		
		cm.loadTexture("sprites/image10.png", "image10.png");
		cm.loadTexture("sprites/image4146.png", "image4146.png");
		cm.loadTexture("sprites/image4157.png", "image4157.png");
		
		cm.loadTexture("sprites/finish.png", "finish");
		
		cm.loadTexture("sprites/path4172.png", "FarmTruck");
		
		//distance bar
		cm.loadTexture("sprites/distanceBar.png", "distanceBar");
		cm.loadTexture("sprites/distanceCursor.png", "distanceCursor");
		
		//Stage2
		cm.loadTexture("sprites/rect4136.png", "Stage02");
		
		//Stage3
		cm.loadTexture("sprites/stage03.png", "Stage03");
		
		//stage select
		cm.loadTexture("sprites/levelselectbuttonSmallSolo.png", "buttonStage");
		
		//stage select background
		cm.loadTexture("sprites/levelselect0.png", "levelselect0");
		cm.loadTexture("sprites/levelselect1.png", "levelselect1");
		cm.loadTexture("sprites/levelselect2.png", "levelselect2");
		cm.loadTexture("sprites/levelselect3.png", "levelselect3");
		
		
		
		//press enter "button"
		cm.loadTexture("sprites/press_enter.png", "press_enter");
		cm.loadTexture("sprites/levelselectbuttonSmallExtention.png", "levelSelectButtonExpansion");
		cm.loadTexture("sprites/star.png", "star");
		
		
		//level completed menu
		cm.loadTexture("sprites/rect4162.png", "levelCompletedBackground");
		cm.loadTexture("sprites/rect4162-7.png", "levelCompletedBackground2");
		cm.loadTexture("sprites/replay.png", "levelCompletedReplay");
		cm.loadTexture("sprites/continue.png", "levelCompletedContinue");
		cm.loadTexture("sprites/levelCompleted.png", "levelCompleted");
		cm.loadTexture("sprites/levelCompleted2.png", "levelCompleted2");
		cm.loadTexture("sprites/flowRoot4194-2.png", "levelCompleted3");
		cm.loadTexture("sprites/levelfailed.png", "levelfailed");
		
		//COMMING SOON
		cm.loadTexture("sprites/comingSoon.png", "comingSoon");
		
		//OTHER BUTTONS
		cm.loadTexture("sprites/backButton.png", "backButton");
		cm.loadTexture("sprites/optionsButton.png", "optionsButton");
		cm.loadTexture("sprites/downButton.png", "downButton");
		cm.loadTexture("sprites/downButton100.png", "downButton100");
		cm.loadTexture("sprites/upButton.png", "upButton");
		cm.loadTexture("sprites/boxes.png", "hudBoxes");
		
		
		//OTHER INIT
		s = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, VWIDTH, VHEIGHT);
		
		hud = new OrthographicCamera();
		hud.setToOrtho(false, VWIDTH, VHEIGHT);
		
		m = new GameStateManager(this);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		accumulator += Gdx.graphics.getDeltaTime();
		while(accumulator >= STEP){
			accumulator -= STEP;
			m.update(STEP);
			m.render();
		}
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public SpriteBatch getS() {
		return s;
	}
	public void setS(SpriteBatch s) {
		this.s = s;
	}
	public OrthographicCamera getCam() {
		return cam;
	}
	public void setCam(OrthographicCamera cam) {
		this.cam = cam;
	}
	public OrthographicCamera getHud() {
		return hud;
	}
	public void setHud(OrthographicCamera hud) {
		this.hud = hud;
	}
	
	public GameStateManager getGameState(){
		return m;
	}

}
