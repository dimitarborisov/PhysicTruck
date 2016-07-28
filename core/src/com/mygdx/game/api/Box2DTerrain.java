package com.mygdx.game.api;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Box2DTerrain implements Box2DSprite{
	public abstract Body getTerrain();
	public abstract Body getFinish();
	public abstract Sprite getSpriteTerrain();
	public abstract void moveTerrain(float x, float y, float a);
}
