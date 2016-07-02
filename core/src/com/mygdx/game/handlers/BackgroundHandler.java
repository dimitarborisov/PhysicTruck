package com.mygdx.game.handlers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.BackgroundLayer;
import com.mygdx.game.entities.Box2DSprite;
import com.mygdx.game.entities.Car;
import com.mygdx.game.main.Game;

public class BackgroundHandler implements Box2DSprite{
	
	ArrayList<BackgroundLayer> backgrounds;
	
	public BackgroundHandler(Car car, float x, float y){
		backgrounds = new ArrayList<BackgroundLayer>();
		backgrounds.add(new BackgroundLayer(Game.cm.getTexture("image10.png"), 1f, Game.VWIDTH, Game.VHEIGHT, car));
		backgrounds.add(new BackgroundLayer(Game.cm.getTexture("image4146.png"), 0.95f, Game.VWIDTH, Game.VHEIGHT, car));
		backgrounds.add(new BackgroundLayer(Game.cm.getTexture("image4157.png"), 0.70f, Game.VWIDTH, Game.VHEIGHT, car));
		//backgrounds.add(new BackgroundLayer(Game.cm.getTexture("image4168.png"), 0.3f, car));
		
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
