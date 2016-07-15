package com.mygdx.game.handlers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.BackgroundLayer;
import com.mygdx.game.entities.Box2DSprite;
import com.mygdx.game.entities.Box2DVehicle;
import com.mygdx.game.main.Game;

public class BackgroundHandler implements Box2DSprite{
	
	ArrayList<BackgroundLayer> backgrounds;
	Box2DVehicle car;
	
	public BackgroundHandler(Box2DVehicle car, float x, float y){
		this.car = car;
		backgrounds = new ArrayList<BackgroundLayer>();
		backgrounds.add(new BackgroundLayer(Game.cm.getTexture("image10.png"), 10f, Game.VWIDTH, Game.VHEIGHT));
		backgrounds.add(new BackgroundLayer(Game.cm.getTexture("image4146.png"), 2.00f, Game.VWIDTH, Game.VHEIGHT));
		backgrounds.add(new BackgroundLayer(Game.cm.getTexture("image4157.png"), 3.00f, Game.VWIDTH, Game.VHEIGHT));
		//backgrounds.add(new BackgroundLayer(Game.cm.getTexture("image4168.png"), 0.3f, car));
		
	}
	
	@Override
	public void update(float dt) {
		for(BackgroundLayer background: backgrounds){
			background.update(dt, car.getBody().getPosition());
		}
		
	}

	@Override
	public void render(SpriteBatch sb) {
		for(BackgroundLayer l: backgrounds){
			l.render(sb);
		}
		
	}

}
