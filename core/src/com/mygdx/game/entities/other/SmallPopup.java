package com.mygdx.game.entities.other;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.tweenEntities.TweenSpriteAccessor;
import com.mygdx.game.main.Game;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class SmallPopup {
	
	private SimpleImageButton yesButton;
	private SimpleImageButton noButton;
	private Sprite background;
	private boolean toggled;
	
	public SmallPopup(){
		Texture texture = Game.cm.getTexture("levelCompletedBackground2");
		background = new Sprite(texture);
		background.setSize(220, 120);
		background.setPosition((Game.VWIDTH / 2) - background.getWidth() / 2, Game.VHEIGHT);
		
		texture = Game.cm.getTexture("yesButton");
		yesButton = new SimpleImageButton(texture, background.getX() + 5 , background.getY() + 10, 100, 100, false, true);
		
		texture = Game.cm.getTexture("noButton");
		noButton = new SimpleImageButton(texture, background.getX() + background.getWidth() -100 -5, background.getY() + 10, 100, 100, false, true);
	
		toggled = false;
	}
	
	
	public void render(SpriteBatch sb){
		sb.begin();
		background.draw(sb);
		sb.end();
		
		yesButton.render(sb);
		noButton.render(sb);
		
	}

	
	public void update(float dt, float x, float y){
		yesButton.setX(background.getX() + 5 );
		yesButton.setY(background.getY() + 10);
		
		noButton.setX(background.getX() + background.getWidth() -100 -5 );
		noButton.setY(background.getY() + 10);
		
		yesButton.update(x, y);
		noButton.update(x, y);
	}
	
	public boolean yesIsClicked(){
		return yesButton.isClicked();
	}
	
	public boolean noIsClicked(){
		return noButton.isClicked();
	}
	
	public Sprite getBackgroundSprite(){
		return background;
	}
	
	public boolean isToggled(){
		return toggled;
	}

	public void toogle(TweenManager tweenManager){
		if(!toggled){
			Tween.to(background, TweenSpriteAccessor.POS_XY, 0.5f)
						.target((Game.VWIDTH / 2) - background.getWidth() / 2, Game.VHEIGHT - background.getHeight())
						.ease(TweenEquations.easeInOutQuad)
						.start(tweenManager);
			
			toggled = !toggled;
		}else{
			Tween.to(background, TweenSpriteAccessor.POS_XY, 0.5f)
						.target((Game.VWIDTH / 2) - background.getWidth() / 2, Game.VHEIGHT)
						.ease(TweenEquations.easeInOutQuad)
						.start(tweenManager);
				
			toggled = !toggled;
		}
	}
	
	public void reload(){
		toggled = false;
		background.setPosition((Game.VWIDTH / 2) - background.getWidth() / 2, Game.VHEIGHT);
		
		yesButton.reloadButton();
		noButton.reloadButton();
	}
}
	
