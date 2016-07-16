package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Box2DLoad implements Box2DSprite{
	public abstract Body getLoad();
	
	public abstract Sprite getLoadSprite();
	
	public abstract void move(float x, float y, float angle);
}
