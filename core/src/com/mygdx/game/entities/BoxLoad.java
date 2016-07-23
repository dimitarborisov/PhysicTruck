package com.mygdx.game.entities;

import static com.mygdx.game.handlers.Box2DVariables.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.handlers.Box2DVariables;
import com.mygdx.game.main.Game;

public class BoxLoad extends Box2DLoad{
	private Body box;
	
	private Texture texture;
	private Sprite sprite;
	
	private	float width, height;

	public BoxLoad(World w, float width, float height, float x, float y){
		this(w, 1, width, height, x, y);
	}
	public BoxLoad(World w, float weight ,float width, float height, float x, float y){
		this.width = width;
		this.height = height;
		
		//load fixtures
		texture = Game.cm.getTexture("Box");
		texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
				
		//boxes
		FixtureDef boxFixture = new FixtureDef();
		boxFixture.friction = 2f;
		boxFixture.density = weight;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((width / 2) / PPM , (height / 2) / PPM);
		
		boxFixture.shape = shape;
		
		//create sprites
		sprite = new Sprite(texture);
		
		// 1. Create a BodyDef, as usual.
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.position.set(x , y);
		
		// 3. Create a Body, as usual.
		box = w.createBody(bd);
		
		// 4. Create the body fixture
		box.createFixture(boxFixture);
		
		//5. Box set USER DATA!
		box.setUserData(new MyUserData());
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
			sprite.setSize(width + 4f, height + 4f);
			
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
			
			sprite.setPosition(box.getPosition().x * Box2DVariables.PPM - sprite.getWidth() / 2,
							   box.getPosition().y * Box2DVariables.PPM - sprite.getHeight() / 2);
			
			sprite.setRotation(box.getAngle() * MathUtils.radiansToDegrees);
		
			sprite.draw(sb);

		sb.end();
		
	}
	
	@Override
	public Body getLoad() {
		return box;
	}
	
	@Override
	public Sprite getLoadSprite() {
		return sprite;
	}
	
	@Override
	public void move(float x, float y, float angle) {
		box.setTransform(x, y, angle);
	}
	
	
}
