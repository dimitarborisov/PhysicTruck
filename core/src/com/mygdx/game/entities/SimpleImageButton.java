package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SimpleImageButton {
	
	private static int id = 0;
	
	private Sprite buttonSprite;
	private boolean clicked;
	private String name;
	private int stageSelect;
	private boolean renderText;
	private BitmapFont font;
	private float fontWidth, fontHeight;
	GlyphLayout layout;
	
	public SimpleImageButton(Texture texture, String name, float x, float y, float width, float height, boolean iX, boolean iY, boolean renderText){
		texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		this.name = name;
		
		stageSelect = id;
		buttonSprite = new Sprite(texture);
		buttonSprite.setPosition(x, y);
		buttonSprite.setSize(width, height);
		buttonSprite.flip(iX, iY);
		
		this.renderText = renderText;
		
		if(iY){
			font = new BitmapFont(Gdx.files.internal("fonts/Burnstown_Dam.fnt"), true);
		}else{
			font = new BitmapFont(Gdx.files.internal("fonts/Burnstown_Dam.fnt"));
		}
		
		layout = new GlyphLayout(); //dont do this every frame! Store it as member
		layout.setText(font , this.name);
		fontWidth = layout.width;// contains the width of the current set text
		fontHeight = layout.height; // contains the height of the current set text
		
		id++;
	}
	
	public SimpleImageButton(Texture texture, String name, float x, float y, float width, float height, boolean iX, boolean iY){
		this(texture, name, x, y, width, height, iX, iY, false);
	}
	
	public SimpleImageButton(Texture texture, String name, float x, float y, float width, float height){
		this(texture, name, x, y, width, height, false, false, false);
	}
	
	public SimpleImageButton(Texture texture, float x, float y, float width, float height) {
		this(texture, id + "", x, y, width, height, false, false, false);
	}

	public void update(int input_x, int input_y) {
		clicked = checkIfClicked(input_x, input_y);
	}

	public void render(SpriteBatch sb) {
		sb.begin();
		buttonSprite.draw(sb); // draw the button
		
		if(renderText){
			font.draw(sb, layout
			, (buttonSprite.getX() + buttonSprite.getWidth() / 2) - fontWidth / 2
			, (buttonSprite.getY() + buttonSprite.getHeight() / 2) - fontHeight / 2);
		}
		
		sb.end();
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
	
	public void setRenderText(boolean renderText){
		this.renderText = renderText;
	}
	
	public boolean getRenderText(){
		return this.renderText;
	}
}