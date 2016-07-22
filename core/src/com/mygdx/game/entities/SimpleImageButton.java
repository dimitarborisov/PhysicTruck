package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SimpleImageButton {

	private Sprite imageSprite;
	boolean clicked;

	public SimpleImageButton(Texture texture, float x, float y, float width, float height, boolean fX, boolean fY) {

		clicked = false;
		
		texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		imageSprite = new Sprite(texture);
		imageSprite.setPosition(x, y);
		imageSprite.setSize(width, height);
		
		imageSprite.flip(fX, fY);
	}

	public void update(float input_x, float input_y) {
		clicked = checkIfClicked(input_x, input_y);
	}
	
	public void render(SpriteBatch sb) {
		sb.begin();
		imageSprite.draw(sb);
		sb.end();
	}

	private Boolean checkIfClicked(float ix, float iy) {
		//System.out.println(buttonSprite.getX()+ " " + buttonSprite.getWidth());
		if((ix > imageSprite.getX() && ix < imageSprite.getX() + imageSprite.getWidth())
			&& (iy > imageSprite.getY() && iy < imageSprite.getY() + imageSprite.getHeight())){
			return true;
		}
		return false;
		
	}
	
	public void setX(float x){
		imageSprite.setX(x);
	}
	
	public void setY(float y){
		imageSprite.setX(y);
	}
	
	public boolean isClicked(){
		boolean tclicked = clicked;
		clicked = false;
		return tclicked;
	}

}
