package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.handlers.MyUserData;

public class TruckLoad {
	private Body box;
	Vector2 terrainOrigin;

	public TruckLoad(World w, FixtureDef boxFixture, String userData, float x, float y){
		
		// 1. Create a BodyDef, as usual.
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.position.set(x , y);
		
		// 3. Create a Body, as usual.
		box = w.createBody(bd);
		

		// 4. Create the body fixture
		Fixture f = box.createFixture(boxFixture);
		f.setUserData(userData);
	}
	
	public TruckLoad(World w, FixtureDef boxFixture, MyUserData userData, float x, float y){
		
		// 1. Create a BodyDef, as usual.
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.position.set(x , y);
		
		// 3. Create a Body, as usual.
		box = w.createBody(bd);
		

		// 4. Create the body fixture
		Fixture f = box.createFixture(boxFixture);
		f.setUserData(userData);
	}
	
}
