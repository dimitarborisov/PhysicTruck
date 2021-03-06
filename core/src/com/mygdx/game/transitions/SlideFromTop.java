package com.mygdx.game.transitions;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.mygdx.game.entities.tweenEntities.TweenSpriteAccessor;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.main.Game;
import com.mygdx.game.states.GameState;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class SlideFromTop extends GameState {
	FrameBuffer bufferPrevious;
	FrameBuffer bufferNext;

	Sprite previousSprite;
	Sprite nextSprite;

	GameState previousState;
	GameState nextState;

	SpriteBatch transitionBatch;
	OrthographicCamera transitionCam;

	TweenCallback tweenCallback;

	private final TweenManager tweenManager = new TweenManager();

	boolean updatePrevious;
	boolean updateNext;

	// NEW REUSE VERSION
	public SlideFromTop(GameStateManager m) {
		super(m);

		transitionBatch = new SpriteBatch();
		transitionCam = new OrthographicCamera();
		transitionCam.setToOrtho(true, Game.VWIDTH, Game.VHEIGHT);
		transitionCam.position.x = Game.VWIDTH / 2;
		transitionCam.position.y = Game.VHEIGHT / 2;
		transitionCam.update();

		bufferPrevious = new FrameBuffer(Pixmap.Format.RGBA8888, (int) Game.VWIDTH, (int) Game.VHEIGHT, false);
		bufferNext = new FrameBuffer(Pixmap.Format.RGBA8888, (int) Game.VWIDTH, (int) Game.VHEIGHT, false);

		previousSprite = new Sprite(bufferPrevious.getColorBufferTexture());
		previousSprite.setPosition(0, 0);

		nextSprite = new Sprite(bufferNext.getColorBufferTexture());
		nextSprite.setPosition(0, -nextSprite.getHeight());

		// TWEEN SETTINGS
		Tween.setCombinedAttributesLimit(4);
		Tween.registerAccessor(Sprite.class, new TweenSpriteAccessor());

		tweenCallback = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				getStateManager().setState(getNextState());
			}
		};
	}

	@Override
	public void reloadTransition(GameState from, GameState to, boolean updateFrom, boolean updateTo, boolean flipX,
			boolean flipY) {
		tweenManager.killAll();
		
		previousSprite.setPosition(0, 0);
		nextSprite.setPosition(0, -nextSprite.getHeight());

		previousState = from;
		nextState = to;

		// from sprite render
		bufferPrevious.begin();
		previousState.render();
		bufferPrevious.end();

		// to sprite render
		bufferNext.begin();
		nextState.render();
		bufferNext.end();

		updatePrevious = updateFrom;
		updateNext = updateTo;

		previousSprite.flip(flipX, flipY);
		nextSprite.flip(flipX, flipY);

		Tween.to(nextSprite, TweenSpriteAccessor.POS_XY, 0.6f)
				.target(0, 0)
				// .ease(TweenEquations.easeOutBack)
				.setCallback(tweenCallback)
				.setCallbackTriggers(TweenCallback.COMPLETE)
				.start(tweenManager);
	}

	// OLD
	// VERSION==================================================================================================
	public SlideFromTop(GameStateManager m, GameState from, GameState to) {
		this(m, from, to, false, false, false, false);
	}

	public SlideFromTop(GameStateManager m, GameState from, GameState to, boolean updateFrom, boolean updateTo) {
		this(m, from, to, updateFrom, updateTo, false, false);
	}

	public SlideFromTop(GameStateManager m, GameState from, GameState to, boolean updateFrom, boolean updateTo,
			boolean flipX, boolean flipY) {
		super(m);

		transitionBatch = new SpriteBatch();
		transitionCam = new OrthographicCamera();
		transitionCam.setToOrtho(true, Game.VWIDTH, Game.VHEIGHT);
		transitionCam.position.x = Game.VWIDTH / 2;
		transitionCam.position.y = Game.VHEIGHT / 2;
		transitionCam.update();

		updatePrevious = updateFrom;
		updateNext = updateTo;

		bufferPrevious = new FrameBuffer(Pixmap.Format.RGBA8888, (int) Game.VWIDTH, (int) Game.VHEIGHT, false);
		bufferNext = new FrameBuffer(Pixmap.Format.RGBA8888, (int) Game.VWIDTH, (int) Game.VHEIGHT, false);

		// TWEEN SETTINGS
		Tween.setCombinedAttributesLimit(4);
		Tween.registerAccessor(Sprite.class, new TweenSpriteAccessor());

		this.previousState = from;
		this.nextState = to;

		// from sprite render
		bufferPrevious.begin();
		previousState.render();
		bufferPrevious.end();

		previousSprite = new Sprite(bufferPrevious.getColorBufferTexture());
		previousSprite.setPosition(0, 0);
		previousSprite.flip(flipX, flipY);

		// to sprite render
		bufferNext.begin();
		nextState.render();
		bufferNext.end();

		nextSprite = new Sprite(bufferNext.getColorBufferTexture());
		nextSprite.setPosition(0, -nextSprite.getHeight());
		nextSprite.flip(flipX, flipY);

		tweenCallback = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				getGameStateManager().setState(getNextState());
			}
		};

		Tween.to(nextSprite, TweenSpriteAccessor.POS_XY, 1f).target(0, 0).delay(0.5f)
				// .ease(TweenEquations.easeOutBack)
				.setCallback(tweenCallback).setCallbackTriggers(TweenCallback.COMPLETE).start(tweenManager);
	}
	// OLD
	// VERSION==================================================================================================

	
	@Override
	public void update(float dt) {
		tweenManager.update(dt);

		if (updatePrevious) {
			previousState.update(dt);

			bufferPrevious.begin();
			previousState.render();
			bufferPrevious.end();
		}

		if (updateNext) {
			nextState.update(dt);

			bufferNext.begin();
			nextState.render();
			bufferNext.end();
		}

	}

	@Override
	public void render() {
		transitionBatch.disableBlending();
		transitionBatch.setProjectionMatrix(transitionCam.combined);

		transitionBatch.begin();
		previousSprite.draw(transitionBatch);
		nextSprite.draw(transitionBatch);
		transitionBatch.end();

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public GameState getNextState() {
		return nextState;
	}

	public GameStateManager getGameStateManager() {
		return super.m;
	}

}
