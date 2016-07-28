package com.mygdx.game.entities.other;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.main.Game;

public class DistanceIndicator {

	private Sprite distanceBar;
	private Sprite distanceCursor;

	private float width, height;
	
	public DistanceIndicator(float x, float y, boolean flipX, boolean flipY){
		width = 250;
		height = 45;
		
		//LOAD DISTANCE BAR
		Texture texture = Game.cm.getTexture("distanceBar");
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		distanceBar = new Sprite(texture);
		distanceBar.setSize(width, height);
		distanceBar.flip(flipX, flipY);
		distanceBar.setPosition(x, y);
		
		
		//DISTANCE CURSOR
		texture = Game.cm.getTexture("distanceCursor");
		texture.setFilter(TextureFilter.MipMap, TextureFilter.MipMap);
		distanceCursor = new Sprite(texture);
		
		distanceCursor.setSize(20, height);
		distanceCursor.flip(flipX, flipY);
		distanceCursor.setPosition(x, y - distanceCursor.getWidth() / 2);
	
	
	}
	
	public void update(float dt, float p){
		if(p > 100){
			p = 100;
		}
		if(p < 0){
			p = 0;
		}
		
		distanceCursor.setY(distanceBar.getY());
		distanceCursor.setX(distanceBar.getX() + (float)((distanceBar.getWidth()*p) / 100) - distanceCursor.getWidth() / 2);
	}
	
	public void render(SpriteBatch sb){
		sb.begin();
		distanceBar.draw(sb);
		distanceCursor.draw(sb);
		sb.end();
	}
	
	public void setX(float x){
		distanceBar.setX(x);;
	}
	
	public void setY(float y){
		distanceBar.setY(y);
	}
}
