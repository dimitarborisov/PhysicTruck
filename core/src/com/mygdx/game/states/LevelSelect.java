package com.mygdx.game.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.entities.LevelSelectButton;
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
	private int ty, tx;

	ShapeRenderer shapeRenderer;

	private final TweenManager tweenManager = new TweenManager();
	
	Sprite backgroundSprite0;
	Sprite backgroundSprite1;
	Sprite backgroundSprite2;
	Sprite backgroundSprite3;

	BitmapFont font;

	OrthographicCamera levelCam;

	public LevelSelect(GameStateManager m) {
		super(m);
		
		shapeRenderer = new ShapeRenderer();

		levelCam = new OrthographicCamera();
		levelCam.setToOrtho(true, Game.VWIDTH, Game.VHEIGHT);

		ty = -1;
		tx = -1;

		// add buttons
		sib = new ArrayList<LevelSelectButton>();
	
		setupBackground();
		setupButtons();
		setupTweenBackground();

		Gdx.input.setInputProcessor(new InputProcessor() {

			@Override
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				if(keycode == Keys.ESCAPE){
					getStateManager().setTransition(GameStateManager.RIGHTLEFT, LevelSelect.this, GameStateManager.SPLASHSCREEN, true, true);
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
				// Vector3 input = new Vector3(screenX, screenY, 0);
				// cam.unproject(input);
				tx = screenX;
				ty = screenY;

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
		});
		
	}

	private void setupTweenBackground() {
		// TWEEN SETTINGS
		Tween.setCombinedAttributesLimit(4);
		Tween.registerAccessor(Sprite.class, new TweenSpriteAccessor());

		Tween.to(backgroundSprite1, TweenSpriteAccessor.POS_XY, 1.5f)
				.delay(0.5f)
				.target(0, 0)
				.ease(TweenEquations.easeOutBack)
				.start(tweenManager);

		
		Tween.to(backgroundSprite2, TweenSpriteAccessor.ALPHA, 1.5f)
				.target(1).delay(2f)
				.ease(TweenEquations.easeInOutQuad)
				.start(tweenManager);

		Tween.to(backgroundSprite3, TweenSpriteAccessor.POS_XY, 1.5f)
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
				//Play.STAGESELECTED = iButton.getStageSelected();
				//m.setState(GameStateManager.PLAY);
				System.out.println(iButton);
			}
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
				Tween.to(sib.get(i).getSprite(), TweenSpriteAccessor.POS_XY, 1f)
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
				Tween.to(sib.get(i).getSprite(), TweenSpriteAccessor.POS_XY, 1f)
						.target(sX, sY)
						.delay(delay)
						.ease(TweenEquations.easeInOutQuad)
						.start(tweenManager);
			}
			
			sX += deltaX;
			j++;
		}
		
		
	}
	
	private void setupButtons() {
		// row 1
		//X1 = 215
		//Y1 = 21
		
		//deltaX = 116
		//dealtaY = 142
		
		sib.add(new LevelSelectButton("1", Game.VWIDTH + 215, 21, 106, 106, false, true, true));
		sib.add(new LevelSelectButton("2", Game.VWIDTH + 331, 21, 106, 106, false, true, true));
		sib.add(new LevelSelectButton("3", Game.VWIDTH + 447, 21, 106, 106, false, true, true));
		sib.add(new LevelSelectButton("4", Game.VWIDTH + 563, 21, 106, 106, false, true, true));
		sib.add(new LevelSelectButton("5", Game.VWIDTH + 679, 21, 106, 106, false, true, true));
		

		// row2
		sib.add(new LevelSelectButton("6", Game.VWIDTH + 215, 163, 106, 106, false, true, true));
		sib.add(new LevelSelectButton("7", Game.VWIDTH + 331, 163, 106, 106, false, true, true));
		sib.add(new LevelSelectButton("8", Game.VWIDTH + 447, 163, 106, 106, false, true, true));
		sib.add(new LevelSelectButton("9", Game.VWIDTH + 563, 163, 106, 106, false, true, true));
		sib.add(new LevelSelectButton("10", Game.VWIDTH + 679, 163, 106, 106, false, true, true));

		// row3
		sib.add(new LevelSelectButton("11", Game.VWIDTH + 215, 305, 106, 106, false, true, true));
		sib.add(new LevelSelectButton("12", Game.VWIDTH + 331, 305, 106, 106, false, true, true));
		sib.add(new LevelSelectButton("13", Game.VWIDTH + 447, 305, 106, 106, false, true, true));
		sib.add(new LevelSelectButton("14", Game.VWIDTH + 563, 305, 106, 106, false, true, true));
		sib.add(new LevelSelectButton("15", Game.VWIDTH + 679, 305, 106, 106, false, true, true));

		// row4
		sib.add(new LevelSelectButton("16", Game.VWIDTH + 215, 447, 106, 106, false, true, true));
		sib.add(new LevelSelectButton("17", Game.VWIDTH + 331, 447, 106, 106, false, true, true));
		sib.add(new LevelSelectButton("18", Game.VWIDTH + 447, 447, 106, 106, false, true, true));
		sib.add(new LevelSelectButton("19", Game.VWIDTH + 563, 447, 106, 106, false, true, true));
		sib.add(new LevelSelectButton("20", Game.VWIDTH + 679, 447, 106, 106, false, true, true));
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
