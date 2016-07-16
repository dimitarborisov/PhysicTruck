package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Box2DSprite {
	
	public abstract void update(float dt);
	public abstract void render(SpriteBatch sb);

}
