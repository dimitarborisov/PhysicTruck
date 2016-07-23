package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.tweenEntities.TweenSpriteAccessor;
import com.mygdx.game.main.Game;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class LevelCompletedMenu {
	private SimpleImageButton replayStage;
	private SimpleImageButton continueTo;
	
	private Sprite background;
	
	private Sprite starSprite1;
	private Sprite starSprite2;
	private Sprite starSprite3;
	
	private Sprite levelCompletedText;
	
	private OrthographicCamera levelCam;
	
	private final TweenManager tweenManager = new TweenManager();
	
	private int stars;
	
	private boolean isActive;
	private boolean isTriggered;

	
	public LevelCompletedMenu(){
		Tween.setCombinedAttributesLimit(4);
		Tween.registerAccessor(Sprite.class, new TweenSpriteAccessor());
		
		isActive = false;
		isTriggered = false;
		
		Texture texture = Game.cm.getTexture("levelCompletedBackground2");
		texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		background = new Sprite(texture);
		//345, 400
		background.setSize(345, Game.VHEIGHT);
		background.setOrigin(background.getWidth() / 2, background.getHeight() / 2);
		background.setPosition((Game.VWIDTH / 2) - (background.getWidth() / 2), (Game.VHEIGHT / 2) - (background.getHeight() / 2));
		background.setScale(0, 1);
		
		
		levelCam = new OrthographicCamera();
		levelCam.setToOrtho(true, Game.VWIDTH, Game.VHEIGHT);
		levelCam.update();
		
		replayStage = new SimpleImageButton(Game.cm.getTexture("levelCompletedReplay"),
											background.getX() + 20, 
											Game.VHEIGHT, 
											150, 150, 
											false, false);
		
		continueTo = new SimpleImageButton(Game.cm.getTexture("levelCompletedContinue"),
				background.getX() + background.getWidth() - 20 - 150, 
				Game.VHEIGHT, 
				150, 150, 
				false, false);
		
		
		texture = Game.cm.getTexture("star");
		texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		starSprite1 = new Sprite(texture);
		starSprite1.setSize(150, 150);
		starSprite1.setAlpha(0);
		starSprite1.flip(false, true);
		starSprite1.setPosition(background.getX() + 50 - starSprite1.getWidth() / 2, background.getY() + 145);
		
		
		starSprite2 = new Sprite(texture);
		starSprite2.setSize(150, 150);
		starSprite2.setAlpha(0);
		starSprite2.flip(false, true);
		starSprite2.setPosition(background.getX() + (background.getWidth() / 2) - starSprite2.getWidth() / 2, background.getY() + 95);
		
		starSprite3 = new Sprite(texture);
		starSprite3.setSize(150, 150);
		starSprite3.setAlpha(0);
		starSprite3.flip(false, true);
		starSprite3.setPosition(background.getX() - 50 + background.getWidth() - starSprite3.getWidth() / 2, background.getY() + 145);
	
		//levelCompleted.png
		texture = Game.cm.getTexture("levelCompleted3");
		texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		levelCompletedText = new Sprite(texture);
		levelCompletedText.setSize(335, 33);
		levelCompletedText.flip(false, true);
		levelCompletedText.setPosition(background.getX() + (background.getWidth() / 2) - levelCompletedText.getWidth() / 2, background.getY() - levelCompletedText.getHeight());
	}


	public void render(SpriteBatch sb){
		if(isActive){
			sb.setProjectionMatrix(levelCam.combined);
			
			sb.begin();
			background.draw(sb);

			starSprite1.draw(sb);
			starSprite2.draw(sb);
			starSprite3.draw(sb);

			
			levelCompletedText.draw(sb);
			sb.end();
			
			replayStage.render(sb);
			continueTo.render(sb);
		}
	}
	
	public void update(float dt, float x, float y){
		tweenManager.update(dt);
		if(isActive){
			replayStage.update(x, y);
			continueTo.update(x, y);
		}
	}
	
	public void setActive(boolean active){
		isActive = active;
	}
	
	public boolean isActive(){
		return isActive;
	}
	
	public boolean replayIsClicked(){
		return replayStage.isClicked();
	}
	
	public boolean continueIsClicked(){
		return continueTo.isClicked();
	}

	public void trigger(int stars){
		
		if(!isTriggered){
			this.stars = stars;
			
			Tween.to(background, TweenSpriteAccessor.SCALE, 0.4f)
		    		.target(1f, 1f)
		    		.ease(TweenEquations.easeOutBack)
		    		.start(tweenManager);
			
			Tween.to(replayStage.getSprite(), TweenSpriteAccessor.POS_XY, 0.4f)
					.target(background.getX() + 20, background.getY() + background.getHeight() -  150 - 20)
					.ease(TweenEquations.easeOutQuart)
					.start(tweenManager);
			
			Tween.to(continueTo.getSprite(), TweenSpriteAccessor.POS_XY, 0.4f)
					.target(background.getX() + background.getWidth() - 20 - 150, background.getY() + background.getHeight() -  150 - 20)
					.ease(TweenEquations.easeOutQuart)
					.start(tweenManager);
			
			Tween.to(levelCompletedText, TweenSpriteAccessor.POS_XY, 0.4f)
					.target(background.getX() + (background.getWidth() / 2) - levelCompletedText.getWidth() / 2, background.getY() + 10)
					.ease(TweenEquations.easeOutQuart)
					.start(tweenManager);
			
			
			//update STARS tweens
			if(stars < 3){
				starSprite3.setColor(0, 0, 0, 1);
			}
			if(stars < 2){
				starSprite2.setColor(0, 0, 0, 1);
			}
			if(stars < 1){
				starSprite1.setColor(0, 0, 0, 1);
			}
			
			//STAR 1
			Tween.to(starSprite1, TweenSpriteAccessor.POS_XY, 0.4f)
					.target(background.getX() + 50 - starSprite1.getWidth() / 2, background.getY() + 125)
					.delay(0.1f)
					.ease(TweenEquations.easeInOutQuad)
					.start(tweenManager);
				
			Tween.to(starSprite1, TweenSpriteAccessor.ALPHA, 0.4f)
					.target(1)
					.ease(TweenEquations.easeInOutQuad)
					.start(tweenManager);
				
			
			//STAR 2
			Tween.to(starSprite2, TweenSpriteAccessor.POS_XY, 0.4f)
					.target(background.getX() + (background.getWidth() / 2) - starSprite2.getWidth() / 2, background.getY() + 75)
					.delay(0.1f)
					.ease(TweenEquations.easeInOutQuad)
					.start(tweenManager);
		
			Tween.to(starSprite2, TweenSpriteAccessor.ALPHA, 0.4f)
					.target(1)
					.ease(TweenEquations.easeInOutQuad)
					.start(tweenManager);
			
			
			//STAR 3
			Tween.to(starSprite3, TweenSpriteAccessor.POS_XY, 0.4f)
					.target(background.getX() - 50 + background.getWidth() - starSprite3.getWidth() / 2, background.getY() + 125)
					.delay(0.1f)
					.ease(TweenEquations.easeInOutQuad)
					.start(tweenManager);
		
			Tween.to(starSprite3, TweenSpriteAccessor.ALPHA, 0.4f)
					.target(1)
					.ease(TweenEquations.easeInOutQuad)
					.start(tweenManager);

			
			isTriggered = true;
			isActive = true;
		}
	}
}
