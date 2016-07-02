package com.mygdx.game.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.handlers.ContentManager;
import com.mygdx.game.handlers.GameStateManager;

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
	
	public static ContentManager cm;
	
	
	@Override
	public void create() {
		//load textures
		cm = new ContentManager();
		cm.loadTexture("sprites/body.png", "Truck");
		cm.loadTexture("sprites/path4209.png", "Wheel");
		cm.loadTexture("sprites/box.png", "Box");
		cm.loadTexture("sprites/track0,1.png", "track0");
		
		cm.loadTexture("sprites/layer1.png", "layer1");
		cm.loadTexture("sprites/layer2.png", "layer2");
		cm.loadTexture("sprites/skybox.png", "skybox");
		
		cm.loadTexture("sprites/image10.png", "image10.png");
		cm.loadTexture("sprites/image4146.png", "image4146.png");
		cm.loadTexture("sprites/image4157.png", "image4157.png");
		cm.loadTexture("sprites/image4168.png", "image4168.png");
		
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

}
