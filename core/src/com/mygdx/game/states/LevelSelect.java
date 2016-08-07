package com.mygdx.game.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.entities.other.LevelSelectButton;
import com.mygdx.game.entities.other.SimpleImageButton;
import com.mygdx.game.entities.tweenEntities.TweenSpriteAccessor;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.main.Game;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class LevelSelect extends GameState {

	private ArrayList<LevelSelectButton> sib;
	
	private SimpleImageButton optionsButton;
	private SimpleImageButton backButton;
	
	private float ty, tx;

	ShapeRenderer shapeRenderer;
	
	InputProcessor inputProcessor;

	private final TweenManager tweenManager = new TweenManager();
	
	Sprite backgroundSprite0;
	Sprite backgroundSprite1;
	Sprite backgroundSprite2;
	Sprite backgroundSprite3;

	OrthographicCamera levelCam;
	
	FitViewport viewport;

	public LevelSelect(GameStateManager m) {
		super(m);
		
		shapeRenderer = new ShapeRenderer();

		levelCam = new OrthographicCamera();
		levelCam.setToOrtho(true, Game.VWIDTH, Game.VHEIGHT);
		//viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), levelCam);
		//viewport.apply();
		
		ty = -1;
		tx = -1;

		// add buttons
		sib = new ArrayList<LevelSelectButton>();
		
		// TWEEN SETTINGS
		Tween.setCombinedAttributesLimit(4);
		Tween.registerAccessor(Sprite.class, new TweenSpriteAccessor());
	
		setupBackground();
		setupButtons();
		setupTweenBackground();
		
		
		inputProcessor = new InputProcessor() {

			@Override
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				if(keycode == Keys.ESCAPE){
					getStateManager().setTransition(GameStateManager.LEFTRIGHT, LevelSelect.this, GameStateManager.SPLASHSCREEN, true, true);
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
				levelCam.unproject(input);
				
				//tx = screenX;
				//ty = screenY;
				
				//Vector2 newPoints = new Vector2(screenX, screenY);
				//newPoints = viewport.unproject(newPoints);
				//System.out.println(viewport);
				//System.out.println(newPoints);
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
		

		Gdx.input.setInputProcessor(inputProcessor);
	}

	private void setupTweenBackground() {

		Tween.to(backgroundSprite1, TweenSpriteAccessor.POS_XY, 1.5f)
				.delay(0.5f)
				.target(0, 0)
				.ease(TweenEquations.easeOutBack)
				.start(tweenManager);

		
		Tween.to(backgroundSprite2, TweenSpriteAccessor.ALPHA, 1.5f)
				.target(1).delay(2f)
				.ease(TweenEquations.easeInOutQuad)
				.start(tweenManager);

		Tween.to(backgroundSprite3, TweenSpriteAccessor.POS_XY, 1.2f)
				.target(0, 0)
				.ease(TweenEquations.easeOutExpo)
				.setCallback(new TweenCallback(){

					@Override
					public void onEvent(int arg0, BaseTween<?> arg1) {
						setupTweenButtons();
					}
					
				})
				.setCallbackTriggers(TweenCallback.COMPLETE)
				.start(tweenManager);

		
	}

	
	private void setupBackground() {
		// layer 0
		Texture background = Game.cm.getTexture("levelselect0");
		background.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);

		backgroundSprite0 = new Sprite(background);
		backgroundSprite0.setSize(Game.VWIDTH, Game.VHEIGHT);
		backgroundSprite0.flip(false, true);
		backgroundSprite0.setPosition(0, 0);

		// layer 1
		background = Game.cm.getTexture("levelselect1");
		background.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);

		backgroundSprite1 = new Sprite(background);
		backgroundSprite1.setSize(Game.VWIDTH, Game.VHEIGHT);
		backgroundSprite1.flip(false, true);
		backgroundSprite1.setPosition(0, Game.VHEIGHT);

		// layer 2
		background = Game.cm.getTexture("levelselect2");
		background.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);

		backgroundSprite2 = new Sprite(background);
		backgroundSprite2.setSize(Game.VWIDTH, Game.VHEIGHT);
		backgroundSprite2.flip(false, true);
		backgroundSprite2.setPosition(0, 0);
		backgroundSprite2.setAlpha(0);

		// layer 3
		background = Game.cm.getTexture("levelselect3");
		background.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);

		backgroundSprite3 = new Sprite(background);
		backgroundSprite3.setSize(Game.VWIDTH, Game.VHEIGHT);
		backgroundSprite3.flip(false, true);
		backgroundSprite3.setPosition(0, Game.VWIDTH);

	}

	@Override
	public void update(float dt) {
		tweenManager.update(dt);

		// update buttons
		for (LevelSelectButton iButton : sib) {
			iButton.update(tx, ty);
			if (iButton.isClicked()) {
				Play.STAGESELECTED = iButton.getStageSelected();
				//m.setState(GameStateManager.PLAY);
				m.setTransition(GameStateManager.RIGHTLEFT, this, GameStateManager.PLAY, true, true);
				//System.out.println(iButton.getStageSelected());
			}
		}
		
		
		//check the options button
		optionsButton.update(tx, ty);
		
		if(optionsButton.isClicked()){
			getStateManager().setTransition(GameStateManager.SLIDEFROMTOP, LevelSelect.this, GameStateManager.OPTIONS, true, false);
		}
		
		
		//Check the back button
		backButton.update(tx, ty);
		
		if(backButton.isClicked()){
			getStateManager().setTransition(GameStateManager.LEFTRIGHT, LevelSelect.this, GameStateManager.SPLASHSCREEN, true, true);
		}
		
		// reset click position
		tx = -1;
		ty = -1;
	}

	@Override
	public void render() {
		// clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

		s.setProjectionMatrix(levelCam.combined);


		s.begin();
		backgroundSprite0.draw(s);
		backgroundSprite1.draw(s);
		backgroundSprite2.draw(s);
		backgroundSprite3.draw(s);
		s.end();

		for (LevelSelectButton iButton : sib) {
			iButton.render(s);
		}
		
		optionsButton.render(s);
		backButton.render(s);

	}

	private void setupTweenButtons(){
		float deltaX = 116;
		float deltaY = 142;
		
		float sX = 215;
		float sY = 21;
		
		float delay = 0.0f;
		
		int j = 0;
		for(int i = 0; i < sib.size(); i++){
			if(j >= 5){
				sY += deltaY;
				sX = 215;
				j = 0;
				delay += 0.1f;
			}
			
			if(i == sib.size() - 1){
				Tween.to(sib.get(i).getSprite(), TweenSpriteAccessor.POS_XY, 0.8f)
				.target(sX, sY)
				.delay(delay)
				.ease(TweenEquations.easeInOutQuad)
				.setCallbackTriggers(TweenCallback.COMPLETE)
				.setCallback(new TweenCallback(){

					@Override
					public void onEvent(int arg0, BaseTween<?> arg1) {
						for(LevelSelectButton lsb: sib){
							lsb.dropDown(tweenManager);
						}
						
					}
					
				})
				.start(tweenManager);

			}else{
				Tween.to(sib.get(i).getSprite(), TweenSpriteAccessor.POS_XY, 0.8f)
						.target(sX, sY)
						.delay(delay)
						.ease(TweenEquations.easeInOutQuad)
						.start(tweenManager);
			}
			
			sX += deltaX;
			j++;
		}
		
		Tween.to(optionsButton.getSprite(), TweenSpriteAccessor.POS_XY, 0.8f)
				.target(Game.VWIDTH - 150 -30, Game.VHEIGHT - 150 - 20)
				.delay(delay + 0.8f)
				.ease(TweenEquations.easeInOutQuad)
				.start(tweenManager);
		
		Tween.to(backButton.getSprite(), TweenSpriteAccessor.POS_XY, 0.8f)
				.target(+30, Game.VHEIGHT - 150 - 20)
				.delay(delay + 0.8f)
				.ease(TweenEquations.easeInOutQuad)
				.start(tweenManager);
		
	}
	
	private void setupButtons() {
		//SETUP BACK BUTTON
		backButton = new SimpleImageButton(Game.cm.getTexture("backButton"), 
												+ 30, 
												Game.VHEIGHT + 150, 
												150, 
												150, 
												false, false);
		
		//SETUP OPTIONS BUTTON
		optionsButton = new SimpleImageButton(Game.cm.getTexture("optionsButton"), 
												Game.VWIDTH - 150 - 30, 
												Game.VHEIGHT + 150, 
												150, 
												150, 
												false, false);
		
		// row 1
		//X1 = 215
		//Y1 = 21
		
		//deltaX = 116
		//dealtaY = 142
		
		
		sib.add(new LevelSelectButton("1", 0, Game.VWIDTH + 215, 21, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("1", 0), true));
		sib.add(new LevelSelectButton("2", 1, Game.VWIDTH + 331, 21, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("2", 0),true));
		sib.add(new LevelSelectButton("3", 2, Game.VWIDTH + 447, 21, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("3", 0),true));
		sib.add(new LevelSelectButton("4", 3, Game.VWIDTH + 563, 21, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("4", 0),true));
		sib.add(new LevelSelectButton("5", 4, Game.VWIDTH + 679, 21, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("5", 0),true));
		

		// row2
		sib.add(new LevelSelectButton("6", 5, Game.VWIDTH + 215, 163, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("6", 0),true));
		sib.add(new LevelSelectButton("7", 6, Game.VWIDTH + 331, 163, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("7", 0),true));
		sib.add(new LevelSelectButton("8", 7, Game.VWIDTH + 447, 163, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("8", 0),true));
		sib.add(new LevelSelectButton("9", 8, Game.VWIDTH + 563, 163, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("9", 0),true));
		sib.add(new LevelSelectButton("10", 9, Game.VWIDTH + 679, 163, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("10", 0),true));

		// row3
		sib.add(new LevelSelectButton("11", 10, Game.VWIDTH + 215, 305, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("11", 0),true));
		sib.add(new LevelSelectButton("12", 11, Game.VWIDTH + 331, 305, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("12", 0),true));
		sib.add(new LevelSelectButton("13", 12, Game.VWIDTH + 447, 305, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("13", 0),true));
		sib.add(new LevelSelectButton("14", 13, Game.VWIDTH + 563, 305, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("14", 0),true));
		sib.add(new LevelSelectButton("15", 14, Game.VWIDTH + 679, 305, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("15", 0),true));

		// row4
		sib.add(new LevelSelectButton("16", 15, Game.VWIDTH + 215, 447, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("16", 0),true));
		sib.add(new LevelSelectButton("17", 16, Game.VWIDTH + 331, 447, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("17", 0),true));
		sib.add(new LevelSelectButton("18", 17, Game.VWIDTH + 447, 447, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("18", 0),true));
		sib.add(new LevelSelectButton("19", 18, Game.VWIDTH + 563, 447, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("19", 0),true));
		sib.add(new LevelSelectButton("20", 19, Game.VWIDTH + 679, 447, 106, 106, false, true, Game.cm.getPref("stagesStars").getInteger("20", 0),true));
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reloadState() {
		//RESET TWEEN
		tweenManager.killAll();
		
		
		LevelSelectButton lsb;
		//BUTTONS
		for(int i = 0; i < sib.size(); i++){
			lsb = sib.get(i);
			lsb.reloadButton();
			lsb.setStars(Game.cm.getPref("stagesStars").getInteger(Integer.toString(i+1), 0));
		}
		backButton.reloadButton();
		optionsButton.reloadButton();
		
		
		//BACKGROUND
		backgroundSprite1.setPosition(0, Game.VHEIGHT);

		backgroundSprite2.setPosition(0, 0);
		backgroundSprite2.setAlpha(0);

		backgroundSprite3.setPosition(0, Game.VWIDTH);
		
		
		//TWEEN
		setupTweenBackground();
		
		
		//LOAD PREFERENCES
		//prefs = Gdx.app.getPreferences("stagesStars");
		
		
		//INPUT
		Gdx.input.setInputProcessor(inputProcessor);
	}
	
	

}
