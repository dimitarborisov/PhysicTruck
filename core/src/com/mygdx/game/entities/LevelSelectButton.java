package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.tweenEntities.TweenSpriteAccessor;
import com.mygdx.game.main.Game;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class LevelSelectButton {
	
	private static int id = 0;
	
	private Sprite buttonSprite;
	private boolean clicked;
	private String name;
	private int stageSelect;
	private boolean renderText;
	private BitmapFont font;
	private float fontWidth, fontHeight;
	GlyphLayout layout;
	Texture texture1;
	
	Texture textureExpansion;
	Sprite expansionSprite;
	
	float expansionX;
	float expansionY;
	
	public LevelSelectButton(String name, float x, float y, float width, float height, boolean iX, boolean iY, boolean renderText){
		texture1 = Game.cm.getTexture("buttonStage");
		texture1.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		textureExpansion = Game.cm.getTexture("levelSelectButtonExpansion");
		texture1.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		this.name = name;	
		stageSelect = id;
		this.renderText = renderText;
		
		
		buttonSprite = new Sprite(texture1);
		buttonSprite.setPosition(x, y);
		buttonSprite.setSize(width, height);
		buttonSprite.flip(iX, iY);
		
		
		expansionSprite = new Sprite(textureExpansion);
		
		expansionX = x;
		expansionY = y + buttonSprite.getHeight() -  expansionSprite.getHeight();
		
		expansionSprite.setPosition(expansionX, expansionY);
		expansionSprite.flip(iX, iY);
		
		if(iY){
			font = new BitmapFont(Gdx.files.internal("fonts/Burnstown_Dam.fnt"), true);
		}else{
			font = new BitmapFont(Gdx.files.internal("fonts/Burnstown_Dam.fnt"));
		}
		
		//Text setup
		layout = new GlyphLayout(); //dont do this every frame!
		layout.setText(font , this.name);
		fontWidth = layout.width;
		fontHeight = layout.height;
		
		id++;
	}
	

	public void update(int input_x, int input_y) {
		clicked = checkIfClicked(input_x, input_y);
		
		expansionSprite.setPosition(buttonSprite.getX(), expansionSprite.getY());
	}

	public void render(SpriteBatch sb) {
		sb.begin();
		
		expansionSprite.draw(sb); //draw expansion
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
	
	public void dropDown(TweenManager manager){
		Tween.to(expansionSprite, TweenSpriteAccessor.POS_XY, 0.5f)
					.targetRelative(0, 26)
					.ease(TweenEquations.easeOutBack)
					.start(manager);
	}
}