package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.states.GameState;

public class SimpleImageButton {

	private Sprite buttonSprite;
	private boolean clicked;
	
	public SimpleImageButton(Texture texture, float x, float y, float width, float height) {
		texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		buttonSprite = new Sprite(texture); // your image
		buttonSprite.setPosition(x, y);
		buttonSprite.setSize(width, height);
		
		buttonSprite.flip(false, true);
	}

	public void update(int input_x, int input_y) {
		clicked = checkIfClicked(input_x, input_y);
	}

	public void render(SpriteBatch sp) {
		sp.begin();
		buttonSprite.draw(sp); // draw the button
		sp.end();
	}

	private Boolean checkIfClicked(int ix, int iy) {
		
		if(buttonSprite.getBoundingRectangle().contains(ix, iy)){
			return true;
		}
		return false;
		
	}
	
	public boolean isClicked(){
		boolean tclicked = clicked;
		clicked = false;
		return tclicked;
	}
}