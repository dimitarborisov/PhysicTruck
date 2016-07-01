package com.mygdx.game.handlers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.BackgroundLayer;
import com.mygdx.game.entities.Box2DSprite;
import com.mygdx.game.entities.Car;
import com.mygdx.game.main.Game;

public class BackgroundHandler implements Box2DSprite{
	
	ArrayList<BackgroundLayer> backgrounds;
	
	public BackgroundHandler(Car car){
		backgrounds = new ArrayList<BackgroundLayer>();
		backgrounds.add(new BackgroundLayer(Game.cm.getTexture("skybox"), 1f, car));
		backgrounds.add(new BackgroundLayer(Game.cm.getTexture("layer2"), 0.8f, car));
		backgrounds.add(new BackgroundLayer(Game.cm.getTexture("layer1"), 0.7f, car));
		
	}
	
	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(SpriteBatch sb) {
		for(BackgroundLayer l: backgrounds){
			l.render(sb);
		}
		
	}

}
