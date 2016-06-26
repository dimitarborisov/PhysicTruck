package com.mygdx.game.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

public class MyUserData {
	
	private String name;
	private Body body;
	private Sprite sprite;
	
	public MyUserData(String name, Body body, String texture){
		this.name = name;
		this.body = body;
		Texture te = new Texture(Gdx.files.internal(texture), true);
		te.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		sprite = new Sprite(te);
	}
	
	public void render(SpriteBatch sb){

		sb.begin();
			sprite.setSize(70 , 70);
			sprite.setOrigin(sprite.getHeight() / 2, sprite.getWidth() / 2);
			
			sprite.setPosition(body.getPosition().x * Box2DVariables.PPM - sprite.getHeight() / 2,
							   body.getPosition().y * Box2DVariables.PPM - sprite.getHeight() / 2);
			
			sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		
			sprite.draw(sb);
		sb.end();
		
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public String toString() {
		return name;
	}
	
}
