package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.handlers.BodyEditorLoader;

public class Terrain0 {
	private Body terrain;
	Vector2 terrainOrigin;


	public Terrain0(World w, FixtureDef terrainFixture, String name, float x, float y, float scale){
		
		//body of the truck
		// 0. Create a loader for the file saved from the editor.
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("models/track0.json"));

		// 1. Create a BodyDef, as usual.
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set(x , y);
		
		// 3. Create a Body, as usual.
		terrain = w.createBody(bd);
		
		
		// 4. Create the body fixture automatically by using the loader.
		loader.attachFixture(terrain, name, terrainFixture, scale, "Terrain");
		terrainOrigin = loader.getOrigin(name, scale).cpy();
		
		
	}
}
