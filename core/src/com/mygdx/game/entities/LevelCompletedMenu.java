package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.main.Game;

public class LevelCompletedMenu {
	private SimpleImageButton replayStage;
	private SimpleImageButton continueTo;
	
	private Sprite background;
	
	private Sprite starSprite1;
	private Sprite starSprite2;
	private Sprite starSprite3;
	
	private Sprite levelCompletedText;
	
	private OrthographicCamera levelCam;
	
	private int stars;
	
	private boolean isActive;
	private boolean isTriggered;

	
	public LevelCompletedMenu(){
		isActive = false;
		isTriggered = false;
		
		Texture texture = Game.cm.getTexture("levelCompletedBackground");
		texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		background = new Sprite(texture);
		background.setSize(345, 400);
		background.setPosition((Game.VWIDTH / 2) - (background.getWidth() / 2), (Game.VHEIGHT / 2) - (background.getHeight() / 2));
		
		levelCam = new OrthographicCamera();
		levelCam.setToOrtho(true, Game.VWIDTH, Game.VHEIGHT);
		levelCam.update();
		
		replayStage = new SimpleImageButton(Game.cm.getTexture("levelCompletedReplay"),
											background.getX() + 20, 
											background.getY() + background.getHeight() -  150 + 75, 
											150, 150, 
											false, false);
		
		continueTo = new SimpleImageButton(Game.cm.getTexture("levelCompletedContinue"),
				background.getX() + background.getWidth() - 20 - 150, 
				background.getY() + background.getHeight() -  150 + 75, 
				150, 150, 
				false, false);
		
		
		texture = Game.cm.getTexture("star");
		texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		starSprite1 = new Sprite(texture);
		starSprite1.setSize(150, 150);
		starSprite1.flip(false, true);
		starSprite1.setPosition(background.getX() + 50 - starSprite1.getWidth() / 2, background.getY() + 125);
		
		
		starSprite2 = new Sprite(texture);
		starSprite2.setSize(150, 150);
		starSprite2.flip(false, true);
		starSprite2.setPosition(background.getX() + (background.getWidth() / 2) - starSprite2.getWidth() / 2, background.getY() + 75);
		
		starSprite3 = new Sprite(texture);
		starSprite3.setSize(150, 150);
		starSprite3.flip(false, true);
		starSprite3.setPosition(background.getX() - 50 + background.getWidth() - starSprite3.getWidth() / 2, background.getY() + 125);
	
		//levelCompleted.png
		texture = Game.cm.getTexture("levelCompleted");
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		levelCompletedText = new Sprite(texture);
		levelCompletedText.setSize(335, 33);
		levelCompletedText.flip(false, true);
		levelCompletedText.setPosition(background.getX() + (background.getWidth() / 2) - levelCompletedText.getWidth() / 2, background.getY() + 10);
	}


	public void render(SpriteBatch sb){
		if(isActive){
			sb.setProjectionMatrix(levelCam.combined);
			
			sb.begin();
			background.draw(sb);
			
			if(stars > 0){
				starSprite1.draw(sb);
			}
			if(stars > 1){
				starSprite2.draw(sb);
			}
			if(stars > 2){
				starSprite3.draw(sb);
			}

			
			levelCompletedText.draw(sb);
			sb.end();
			
			replayStage.render(sb);
			continueTo.render(sb);
		}
	}
	
	public void update(float x, float y){
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
		this.stars = stars;
		
		if(!isTriggered){
			//tween engine
			
			
			isTriggered = true;
			isActive = true;
		}
	}
}
