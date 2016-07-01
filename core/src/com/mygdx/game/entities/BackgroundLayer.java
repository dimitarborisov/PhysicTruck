package com.mygdx.game.entities;

import static com.mygdx.game.handlers.Box2DVariables.PPM;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.handlers.Box2DVariables;
import com.mygdx.game.main.Game;

public class BackgroundLayer {
	Texture texture;
	float distance;
	Sprite sprite;
	Sprite sprite2;
	OrthographicCamera cam;
	Car car;
	SpriteBatch s;
	
	public BackgroundLayer(Texture texture, float distance, Car car) {
		s = new SpriteBatch();
		this.texture = texture;
		this.distance = distance;
		this.car = car;
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Game.VWIDTH, Game.VHEIGHT);
		
		sprite = new Sprite(texture);
		sprite2 = new Sprite(texture);
		
		
		sprite.setSize(Game.VWIDTH, Game.VHEIGHT);
		sprite.setX(sprite.getX() - sprite.getWidth() / 2);
		sprite.setY(cam.position.y);
		
        sprite2.setSize(Game.VWIDTH, Game.VHEIGHT);
		//sprite2.setX(sprite.getX() + sprite.getWidth());
		sprite2.setY(sprite.getY());
		
		sprite.setY(cam.position.y / Box2DVariables.PPM - sprite.getHeight() / 2);
		sprite2.setY(sprite.getY());
		
	}

	public void render(SpriteBatch sb){
		//System.out.println(sprite.getX() + sprite.getWidth() - cam.position.x);
		//follow player sprite
		cam.position.set((this.car.getBody().getPosition().x * PPM) / distance,
						(this.car.getBody().getPosition().y * PPM),
						0);
		cam.update();
		
		s.setProjectionMatrix(cam.combined);
		
		s.begin();
		//System.out.println(cam.position.y);
		if ( sprite.getX() + sprite.getWidth() / 2 - cam.position.x < 0 )
		{
			sprite2.setX(sprite.getX() + sprite.getWidth());
		}else{
			sprite2.setX(sprite.getX() - sprite2.getWidth());
		}
		
		if ( sprite2.getX() + sprite2.getWidth() / 2 - cam.position.x < 0 )
		{
			sprite.setX(sprite2.getX() + sprite2.getWidth());
		}else{
			sprite.setX(sprite2.getX() - sprite.getWidth());
		}
		
		sprite2.draw(s);
		sprite.draw(s);
		
		s.end();
	      
	      
	      
	}
	
	public void update(float step){
		
	}
}
