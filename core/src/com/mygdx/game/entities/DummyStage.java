package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.handlers.BodyEditorLoader;
import com.mygdx.game.handlers.Box2DVariables;
import com.mygdx.game.main.Game;

public class DummyStage extends Box2DTerrain {
	private Body terrain;

	Vector2 terrainOrigin;
	Vector2 flagOrigin;

	float scale;

	Texture textureTerrain;

	Sprite spriteTerrain;

	float x, y;

	public DummyStage(World w, float x, float y, float scale) {
		this.scale = scale;
		this.x = x;
		this.y = y;

		FixtureDef terrainFixture = new FixtureDef();
		terrainFixture.friction = 2f;

		textureTerrain = Game.cm.getTexture("track0");
		textureTerrain.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		spriteTerrain = new Sprite(textureTerrain);
		spriteTerrain.setSize(scale * Box2DVariables.PPM,
				(scale * spriteTerrain.getHeight() / spriteTerrain.getWidth()) * Box2DVariables.PPM);

		// body of the truck
		// 0. Create a loader for the file saved from the editor.
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("models/track0,0.json"));

		// 1. Create a BodyDef, as usual.
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set(x, y);

		// 3. Create a Body, as usual.
		terrain = w.createBody(bd);
		// 4. Create the body fixture automatically by using the loader.
		loader.attachFixture(terrain, "track", terrainFixture, scale);
		terrainOrigin = loader.getOrigin("track", scale).cpy();

		// Create finish flag
		// 0. Create a loader for the file saved from the editor.
		//BodyEditorLoader flagLoader = new BodyEditorLoader(Gdx.files.internal("models/finish.json"));

	}

	@Override
	public void update(float dt) {
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		Vector2 terrainPos = terrain.getPosition().sub(terrainOrigin);
		spriteTerrain.setPosition(terrainPos.x * Box2DVariables.PPM, terrainPos.y * Box2DVariables.PPM);
		spriteTerrain.setOrigin(terrainOrigin.x * Box2DVariables.PPM, terrainOrigin.y * Box2DVariables.PPM);
		spriteTerrain.setRotation(terrain.getAngle() * MathUtils.radiansToDegrees);

		spriteTerrain.draw(sb);
		sb.end();

	}

	@Override
	public Body getTerrain() {
		return this.terrain;
	}
	
	public Sprite getSpriteTerrain(){
		return spriteTerrain;
	}

	@Override
	public Body getFinish() {
		return null;
	}
	
	@Override
	public void moveTerrain(float x, float y, float a) {
		terrain.setTransform(x ,y , a);
		
		Vector2 terrainPos = terrain.getPosition().sub(terrainOrigin);
		spriteTerrain.setPosition(terrainPos.x * Box2DVariables.PPM, terrainPos.y * Box2DVariables.PPM);
		spriteTerrain.setOrigin(terrainOrigin.x * Box2DVariables.PPM, terrainOrigin.y * Box2DVariables.PPM);
		spriteTerrain.setRotation(terrain.getAngle() * MathUtils.radiansToDegrees);
	}
	
}

