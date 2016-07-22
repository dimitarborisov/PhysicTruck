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

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class LevelSelectButton {
		
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
	
	boolean scrolled;
	boolean scrolling;
	
	float expansionX;
	float expansionY;
	
	Texture textureStar;
	Sprite spriteStar;
	
	int stars;
	
	public LevelSelectButton(String name, int sSelected, float x, float y, float width, float height, boolean iX, boolean iY, boolean renderText){
		this( name, sSelected, x, y, width, height, iX, iY, 3, renderText);
	}
	
	public LevelSelectButton(String name, int sSelected, float x, float y, float width, float height, boolean iX, boolean iY, int stars, boolean renderText){
		this.stars = stars;
		
		texture1 = Game.cm.getTexture("buttonStage");
		texture1.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		scrolled = false;
		scrolling = false;
		
		textureExpansion = Game.cm.getTexture("levelSelectButtonExpansion");
		texture1.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		textureStar = Game.cm.getTexture("star");
		textureStar.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		this.name = name;	
		stageSelect = sSelected;
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
		
		spriteStar = new Sprite(textureStar);
		spriteStar.setPosition(expansionX + 12, y + expansionSprite.getHeight() -  26);
		spriteStar.setSize(24, 24);
		spriteStar.flip(iX, iY);
		
		//Text setup
		layout = new GlyphLayout(); //dont do this every frame!
		layout.setText(font , this.name);
		fontWidth = layout.width;
		fontHeight = layout.height;

	}
	

	public void update(float input_x, float input_y) {
		clicked = checkIfClicked(input_x, input_y);
		
	}

	public void render(SpriteBatch sb) {
		//update expansion
		if(!scrolling){
			expansionX = buttonSprite.getX();
			if(scrolled){
				expansionY = buttonSprite.getY() + buttonSprite.getHeight() -  expansionSprite.getHeight() + 26;
			}else{
				expansionY = buttonSprite.getY() + buttonSprite.getHeight() -  expansionSprite.getHeight();
			}
			expansionSprite.setPosition(expansionX, expansionY);
		}
		
		spriteStar.setX(expansionSprite.getX() + 12);
		spriteStar.setY(expansionSprite.getY() + expansionSprite.getHeight() -  26);
		
		
		sb.begin();
		
		expansionSprite.draw(sb); //draw expansion
		
		//draw stars
		for(int i = 0; i < stars; i++){
			spriteStar.draw(sb);
			spriteStar.setX(spriteStar.getX() + 28);
		}
		
		
		buttonSprite.draw(sb); // draw the button
		
		
		if(renderText){
			font.draw(sb, layout
			, (buttonSprite.getX() + buttonSprite.getWidth() / 2) - fontWidth / 2
			, (buttonSprite.getY() + buttonSprite.getHeight() / 2) - fontHeight / 2);
		}
		
		sb.end();
	}

	private Boolean checkIfClicked(float ix, float iy) {
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
		scrolling = true;
		
		Tween.to(expansionSprite, TweenSpriteAccessor.POS_XY, 0.5f)
					.targetRelative(0, 26)
					.ease(TweenEquations.easeOutBack)
					.setCallbackTriggers(TweenCallback.COMPLETE)
					.setCallback(new TweenCallback(){

						@Override
						public void onEvent(int arg0, BaseTween<?> arg1) {
							setScrolled(true);
							setScrolling(false);
						}
						
					})
					.start(manager);
										
	}
	
	private void setScrolling(boolean scrolling){
		this.scrolling = scrolling;
	}

	private boolean getScrolling(){
		return scrolling;
	}

	private boolean getScrolled(){
		return this.scrolled;
	}
	
	private void setScrolled(boolean scrolled){
		this.scrolled = scrolled;
	}

}