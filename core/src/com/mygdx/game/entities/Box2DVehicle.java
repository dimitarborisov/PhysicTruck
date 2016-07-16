package com.mygdx.game.entities;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Box2DVehicle extends InputAdapter implements Box2DSprite{
	public abstract Body getBody();
	public abstract Body getLeftWheel();
	public abstract Body getRightWheel();
	
	public abstract Sprite getBodySprite();
	public abstract Sprite getLeftWheelSprite();
	public abstract Sprite getRightWheelSprite();
	
	public abstract void move(float x, float y, float angle);
}
