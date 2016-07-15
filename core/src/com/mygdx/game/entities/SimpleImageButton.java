package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.states.GameState;

public class SimpleImageButton {
	
	private static int id = 0;
	
	private Sprite buttonSprite;
	private boolean clicked;
	private String name;
	private int stageSelect;
	
	public SimpleImageButton(Texture texture, String name, float x, float y, float width, float height){
		texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		this.name = name;
		
		stageSelect = id;
		buttonSprite = new Sprite(texture);
		buttonSprite.setPosition(x, y);
		buttonSprite.setSize(width, height);
		
		//buttonSprite.flip(false, true);
		
		id++;
	}
	
	public SimpleImageButton(Texture texture, float x, float y, float width, float height) {
		this(texture, id + "", x, y, width, height);
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
		//System.out.println(buttonSprite.getX()+ " " + buttonSprite.getWidth());
		if((ix > buttonSprite.getX() && ix < buttonSprite.getX() + buttonSprite.getWidth())
			&& (iy > buttonSprite.getY() && iy < buttonSprite.getY() + buttonSprite.getHeight())){
			return true;
		}
		return false;
		
	}
	
	public boolean isClicked(){
		boolean tclicked = clicked;
		clicked = false;
		return tclicked;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public Sprite getSprite(){
		return buttonSprite;
	}
	
	public int getStageSelected(){
		return stageSelect;
	}
}