package com.mygdx.game.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.handlers.AudioHandler;
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
	public static AudioHandler ah;
	public static ContentManager cm;

	
	@Override
	public void create() {
		
		//load textures
		cm = new ContentManager();
		
		//Farm Truck
		cm.loadTexture("sprites/path4172.png", "FarmTruck");
		cm.loadModel("models/truck1,1.json", "farmTruck");
		
		cm.loadTexture("sprites/path4209.png", "Wheel");
		cm.loadTexture("sprites/box.png", "Box");
		
		//play parallax background
		cm.loadTexture("sprites/image10.png", "image10.png");
		cm.loadTexture("sprites/image4146.png", "image4146.png");
		cm.loadTexture("sprites/moutains.png", "image4157.png");
		
		//Finish Flag
		cm.loadTexture("sprites/finish.png", "finish");
		cm.loadModel("models/finish.json", "finish");
		
		//distance bar
		cm.loadTexture("sprites/distanceBar.png", "distanceBar");
		cm.loadTexture("sprites/distanceCursor.png", "distanceCursor");
			
		//SplashScreen track / stage 1
		cm.loadTexture("sprites/track0,0.png", "track0");
		cm.loadModel("models/track0,0.json", "Stage01");
		
		//Stage2
		cm.loadTexture("sprites/stage02.png", "Stage02");
		cm.loadModel("models/stage02new.json", "Stage02");
		
		//Stage3
		cm.loadTexture("sprites/stage03new.png", "Stage03");
		cm.loadModel("models/stage03new.json", "Stage03");
		
		//Stage4
		cm.loadTexture("sprites/stage04new.png", "Stage04");
		cm.loadModel("models/stage04new.json", "Stage04");
		
		//Stage5
		cm.loadTexture("sprites/stage05new.png", "Stage05");
		cm.loadModel("models/stage05new.json", "Stage05");
		
		//Stage6
		cm.loadTexture("sprites/stage06.png", "Stage06");
		cm.loadModel("models/stage06.json", "Stage06");
		
		//Stage7
		cm.loadTexture("sprites/stage07.png", "Stage07");
		cm.loadModel("models/stage07.json", "Stage07");
		
		//Stage8
		cm.loadTexture("sprites/stage08.png", "Stage08");
		cm.loadModel("models/stage08.json", "Stage08");
		
		//Stage9
		cm.loadTexture("sprites/stage09.png", "Stage09");
		cm.loadModel("models/stage09.json", "Stage09");
		
		//Stage10
		cm.loadTexture("sprites/stage10.png", "Stage10");
		cm.loadModel("models/stage10.json", "Stage10");
		
		//stage select
		cm.loadTexture("sprites/levelselectbuttonSmallSolo.png", "buttonStage");
		cm.loadTexture("sprites/levelselectbuttonSmallExtention.png", "levelSelectButtonExpansion");
		cm.loadTexture("sprites/star.png", "star");
		
		//stage select background
		cm.loadTexture("sprites/levelselect0.png", "levelselect0");
		cm.loadTexture("sprites/levelselect1.png", "levelselect1");
		cm.loadTexture("sprites/levelselect2.png", "levelselect2");
		cm.loadTexture("sprites/levelselect3.png", "levelselect3");
		
		//press enter label
		cm.loadTexture("sprites/press_enter.png", "press_enter");
			
		//level completed menu
		cm.loadTexture("sprites/rect4162-7.png", "levelCompletedBackground2");
		cm.loadTexture("sprites/replay.png", "levelCompletedReplay");
		cm.loadTexture("sprites/continue.png", "levelCompletedContinue");
		cm.loadTexture("sprites/flowRoot4194-2.png", "levelCompleted3");
		cm.loadTexture("sprites/levelfailed.png", "levelfailed");
		
		//OTHER BUTTONS
		cm.loadTexture("sprites/backButton.png", "backButton");
		cm.loadTexture("sprites/optionsButton.png", "optionsButton");
		cm.loadTexture("sprites/downButton.png", "downButton");
		cm.loadTexture("sprites/upButton.png", "upButton");
		cm.loadTexture("sprites/boxes.png", "hudBoxes");
		cm.loadTexture("sprites/resetButton.png", "resetButton");
		cm.loadTexture("sprites/ok.png", "yesButton");
		cm.loadTexture("sprites/no.png", "noButton");
		cm.loadTexture("sprites/musicOff.png", "musicOff");
		cm.loadTexture("sprites/musicOn.png", "musicOn");
		cm.loadTexture("sprites/soundOff.png", "soundOff");
		cm.loadTexture("sprites/soundOn.png", "soundOn");
		cm.loadTexture("sprites/exitButton.png", "exitButton");
		cm.loadTexture("sprites/changeScreenButton.png", "fullScreenButton");
		
		//STARS PREFERENCES
		cm.loadPreferences("stagesStars","stagesStars");
		cm.loadPreferences("gameOptions", "gameOptions");
		
		
		//Setup Screen
		boolean screen = Game.cm.getPref("gameOptions").getBoolean("screen", false);
		if(screen){
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		}else{
			Gdx.graphics.setWindowedMode(Game.VWIDTH, Game.VHEIGHT);
		}
		
		
		//OTHER INIT
		ah = new AudioHandler();
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
		
		//accumulator += Gdx.graphics.getRawDeltaTime();
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
