package com.mygdx.game.transitions;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.mygdx.game.entities.tweenEntities.TweenSpriteAccessor;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.main.Game;
import com.mygdx.game.states.GameState;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class RightToLeftTransition extends GameState{
	FrameBuffer bufferPrevious;
	FrameBuffer bufferNext;

	Sprite previousSprite;
	Sprite nextSprite;

	GameState previousState;
	GameState nextState;

	TweenCallback tweenCallback;

	private final TweenManager tweenManager = new TweenManager();

	public RightToLeftTransition(GameStateManager m, GameState from, GameState to) {
		super(m);

		bufferPrevious = new FrameBuffer(Pixmap.Format.RGBA8888, (int) Game.VWIDTH, (int) Game.VHEIGHT, false);
		bufferNext = new FrameBuffer(Pixmap.Format.RGBA8888, (int) Game.VWIDTH, (int) Game.VHEIGHT, false);

		// TWEEN SETTINGS
		Tween.setCombinedAttributesLimit(4);
		Tween.registerAccessor(Sprite.class, new TweenSpriteAccessor());

		this.previousState = from;
		this.nextState = to;

		// to sprite render
		bufferNext.begin();
		nextState.render();
		bufferNext.end();

		nextSprite = new Sprite(bufferNext.getColorBufferTexture());
		nextSprite.setPosition(Game.VWIDTH, 0);
		nextSprite.flip(false, false);

		// from sprite render
		bufferPrevious.begin();
		previousState.render();
		bufferPrevious.end();
		previousSprite = new Sprite(bufferPrevious.getColorBufferTexture());
		previousSprite.setPosition(0, 0);
		previousSprite.flip(false, false);

		tweenCallback = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				getGameStateManager().setState(getNextState());
			}
		};

		Tween.to(nextSprite, TweenSpriteAccessor.POS_XY, 1f)
						.target(0, 0)
						.delay(0.5f)
						.ease(TweenEquations.easeOutBack)
						.setCallback(tweenCallback)
						.setCallbackTriggers(TweenCallback.COMPLETE)
						.start(tweenManager);
	}

	@Override
	public void update(float dt) {
		tweenManager.update(dt);

		previousState.update(dt);

		bufferPrevious.begin();
		previousState.render();
		bufferPrevious.end();

	}

	@Override
	public void render() {

		s.begin();
		previousSprite.draw(s);
		nextSprite.draw(s);
		s.end();

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
