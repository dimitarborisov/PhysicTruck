package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.entities.other.SimpleImageButton;
import com.mygdx.game.entities.other.SmallPopup;
import com.mygdx.game.entities.tweenEntities.TweenSpriteAccessor;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.main.Game;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class Options extends GameState{

	private Sprite backgroundSprite;
	
	private SimpleImageButton returnButton;
	private SimpleImageButton resetButton;
	private SimpleImageButton exitButton;
	private SimpleImageButton changeScreenButton;
	
	private SimpleImageButton effectButton;
	private SimpleImageButton musicButton;
	
	private final TweenManager tweenManager = new TweenManager();
	
	OrthographicCamera optionsCam;
	
	InputProcessor inputProcessor;
	Runnable runnable, flushOptions;
	SmallPopup popUp;
	
	float tx, ty;
	
	public void reloadState(){
		tweenManager.killAll();
		
		backgroundSprite.setPosition(0, 0);
		returnButton.reloadButton();
		resetButton.reloadButton();
		effectButton.reloadButton();
		musicButton.reloadButton();
		exitButton.reloadButton();
		changeScreenButton.reloadButton();
		
		popUp.reload();
		
		setupTweenButtons();

		Gdx.input.setInputProcessor(inputProcessor);
	}
	
	public Options(GameStateManager m) {
		super(m);
		
		Tween.setCombinedAttributesLimit(4);
		Tween.registerAccessor(Sprite.class, new TweenSpriteAccessor());
		
		tx = -1;
		ty = -1;
		
		popUp = new SmallPopup();
		
		optionsCam = new OrthographicCamera();
		optionsCam.setToOrtho(true, Game.VWIDTH, Game.VHEIGHT);
		
		Texture texture = Game.cm.getTexture("image10.png");
		backgroundSprite = new Sprite(texture);
		
		backgroundSprite.setPosition(0, 0);
		backgroundSprite.setSize(Game.VWIDTH, Game.VHEIGHT);
		backgroundSprite.flip(false, true);
	
		
		returnButton = new SimpleImageButton(Game.cm.getTexture("downButton"),
												Game.VWIDTH - 150 - 30, 
												Game.VHEIGHT, 
												150, 150, false, true);
		
		resetButton = new SimpleImageButton(Game.cm.getTexture("resetButton"),
												30, 
												Game.VHEIGHT, 
												150, 150, false, true);
		
		changeScreenButton = new SimpleImageButton(Game.cm.getTexture("fullScreenButton"),
												30, 
												-150, 
												150, 150, false, true);
	
		exitButton = new SimpleImageButton(Game.cm.getTexture("exitButton"),
												Game.VWIDTH - 150 - 30, 
												-150, 
												150, 150, false, true);
		
		if(Game.cm.getPref("gameOptions").getBoolean("music", false)){
			texture = Game.cm.getTexture("musicOn");
		}else{
			texture = Game.cm.getTexture("musicOff");
		}
		
		musicButton = new SimpleImageButton(texture,
												Game.VWIDTH/2 - 255, 
												Game.VHEIGHT, 
												250, 250, false, true);
		
		
		if(Game.cm.getPref("gameOptions").getBoolean("effects", false)){
			texture = Game.cm.getTexture("soundOn");
		}else{
			texture = Game.cm.getTexture("soundOff");
		}
		
		
		effectButton = new SimpleImageButton(texture,
												Game.VWIDTH/2 + 5, 
												Game.VHEIGHT, 
												250, 250, false, true);
		
		
		inputProcessor = new InputProcessor() {

			@Override
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				if(keycode == Keys.ESCAPE){
					getStateManager().setTransition(GameStateManager.SLIDETOTOP, Options.this, GameStateManager.LEVELSELECT, true, false);
				}
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				Vector3 input = new Vector3(screenX, screenY, 0);
				optionsCam.unproject(input);
				tx = input.x;
				ty = input.y;
				
				return true;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				// TODO Auto-generated method stub
				return false;
			}
		};	
		runnable = new Runnable() {

			@Override
			public void run() {
				for(int i = 0; i <= 20; i++){
					Game.cm.getPref("stagesStars").putInteger(i + "", 0);
				}
				
				Game.cm.getPref("stagesStars").flush();
			}

		};
		
		flushOptions = new Runnable(){

			@Override
			public void run() {
				Game.cm.getPref("gameOptions").flush();
			}
			
		};
		
		Gdx.input.setInputProcessor(inputProcessor);
	
	
		setupTweenButtons();
	}
	
	private void setupTweenButtons(){
		Tween.to(returnButton.getSprite(), TweenSpriteAccessor.POS_XY, 0.5f)
				.target(Game.VWIDTH - 150 -30, Game.VHEIGHT - 150 - 20)
				.ease(TweenEquations.easeInOutQuad)
				.start(tweenManager);
	
		Tween.to(resetButton.getSprite(), TweenSpriteAccessor.POS_XY, 0.5f)
				.target(30, Game.VHEIGHT - 150 - 20)
				.ease(TweenEquations.easeInOutQuad)
				.start(tweenManager);
		
		Tween.to(changeScreenButton.getSprite(), TweenSpriteAccessor.POS_XY, 0.5f)
				.target(30, 30)
				.ease(TweenEquations.easeInOutQuad)
				.start(tweenManager);

		Tween.to(exitButton.getSprite(), TweenSpriteAccessor.POS_XY, 0.5f)
				.target(Game.VWIDTH - 150 -30, 30)
				.ease(TweenEquations.easeInOutQuad)
				.start(tweenManager);
		
		Tween.to(musicButton.getSprite(), TweenSpriteAccessor.POS_XY, 0.5f)
				.target(Game.VWIDTH/2 - 255, Game.VHEIGHT/2 - 125)
				.ease(TweenEquations.easeInOutQuad)
				.start(tweenManager); 
		
		Tween.to(effectButton.getSprite(), TweenSpriteAccessor.POS_XY, 0.5f)
				.target(Game.VWIDTH/2 + 5, Game.VHEIGHT/2 - 125)
				.ease(TweenEquations.easeInOutQuad)
				.start(tweenManager); 
		
	}

	@Override
	public void update(float dt) {
		tweenManager.update(dt);
		returnButton.update(tx, ty);
		resetButton.update(tx, ty);
		musicButton.update(tx, ty);
		effectButton.update(tx, ty);
		exitButton.update(tx, ty);
		changeScreenButton.update(tx, ty);
		
		popUp.update(dt, tx, ty);
		
		if(exitButton.isClicked()){
			Gdx.app.exit();
		}
		
		if(changeScreenButton.isClicked()){
			boolean screen = Game.cm.getPref("gameOptions").getBoolean("screen", false);
			if(!screen){
				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
				Game.cm.getPref("gameOptions").putBoolean("screen", true);
			}else{
				Gdx.graphics.setWindowedMode(Game.VWIDTH, Game.VHEIGHT);
				Game.cm.getPref("gameOptions").putBoolean("screen", false);
			}
			Game.cm.getPref("gameOptions").flush();
		}
		
		if(returnButton.isClicked()){
			getStateManager().setTransition(GameStateManager.SLIDETOTOP, Options.this, GameStateManager.LEVELSELECT, true, false);
		}
		
		if(resetButton.isClicked()){
			// start a new thread for use with HDD opreations
			popUp.toogle(tweenManager);
		}
		
		if(popUp.yesIsClicked()){
			new Thread(runnable).start();
			popUp.toogle(tweenManager);
		}
		
		if(popUp.noIsClicked()){
			popUp.toogle(tweenManager);
		}

		
		if(musicButton.isClicked()){
			if(!Game.cm.getPref("gameOptions").getBoolean("music", false)){
				musicButton.setSprite(Game.cm.getTexture("musicOn"));
				Game.cm.getPref("gameOptions").putBoolean("music", true);
			}else{
				musicButton.setSprite(Game.cm.getTexture("musicOff"));
				Game.cm.getPref("gameOptions").putBoolean("music", false);
			}
			
			new Thread(flushOptions).start();
		}
		
		if(effectButton.isClicked()){
			if(!Game.cm.getPref("gameOptions").getBoolean("effects", false)){
				effectButton.setSprite(Game.cm.getTexture("soundOn"));
				Game.cm.getPref("gameOptions").putBoolean("effects", true);
			}else{
				effectButton.setSprite(Game.cm.getTexture("soundOff"));
				Game.cm.getPref("gameOptions").putBoolean("effects", false);
			}
			
			new Thread(flushOptions).start();
		}
		
		tx = -1;
		ty = -1;
		
	}

	@Override
	public void render() {
		s.setProjectionMatrix(optionsCam.combined);
		
		s.begin();
		backgroundSprite.draw(s);
		s.end();
		
		
		returnButton.render(s);
		resetButton.render(s);
		musicButton.render(s);
		effectButton.render(s);
		exitButton.render(s);
		changeScreenButton.render(s);
		
		popUp.render(s);
	}

	@Override
	public void dispose() {
	}

}
