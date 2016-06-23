package com.mygdx.game.states;

import static com.mygdx.game.handlers.Box2DVariables.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Car;
import com.mygdx.game.entities.Terrain0;
import com.mygdx.game.entities.TruckLoad;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.handlers.MyContactListener;
import com.mygdx.game.handlers.MyUserData;
import com.mygdx.game.main.Game;

public class Play extends GameState{

	private World world;
	private Box2DDebugRenderer box2dDebug;
	
	private OrthographicCamera b2cam;
	private Car car;
	private Terrain0 terrain;
	private ArrayList<TruckLoad> truckLoad;
	
	public Play(GameStateManager m) {
		super(m);
		
		truckLoad = new ArrayList<TruckLoad>();
		
		world = new World(new Vector2(0,-9.8f), false);
		world.setContactListener(new MyContactListener());
		box2dDebug = new Box2DDebugRenderer();
		
		//floor
		//BodyDef bdef = new BodyDef();
		//bdef.position.set(500 / PPM, 100 / PPM);
		//bdef.type = BodyType.StaticBody;
		//Body body = world.createBody(bdef);
		
		//PolygonShape shape = new PolygonShape();
		//shape.setAsBox(400 / PPM, 10 / PPM);
		//FixtureDef fdef = new FixtureDef();
		//fdef.shape = shape;
		//body.createFixture(fdef);
		
		
		//camera setup
		b2cam = new OrthographicCamera();
		b2cam.setToOrtho(false, (Game.VWIDTH + 100) / PPM, (Game.VHEIGHT + 100) / PPM);
		
		
		FixtureDef fixtureDef = new FixtureDef();
		FixtureDef wheelFixtureDef = new FixtureDef();
		
		//truck
		fixtureDef.density = 5;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.3f;
		
		
		//wheel
		wheelFixtureDef.density = fixtureDef.density + 2.5f;
		wheelFixtureDef.friction = 5f;
		
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.4f;
		
		car = new Car(world, fixtureDef, wheelFixtureDef, 4, 2, 10);
		
		
		//track0
		FixtureDef terrainFixture = new FixtureDef();
		terrainFixture.friction = 0.3f;
		
		terrain = new Terrain0(world, terrainFixture, "track", 4, 2, 10);
		
		//boxes
		FixtureDef boxFixture = new FixtureDef();
		boxFixture.friction = 1f;
		boxFixture.density = 10f;
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(20 / PPM, 20 / PPM);
		boxFixture.shape = shape;
			
	    truckLoad.add( new TruckLoad(world, boxFixture, new MyUserData("box", false), 400f / PPM, 250 / PPM) );
		truckLoad.add( new TruckLoad(world, boxFixture, new MyUserData("box", false), 375f / PPM, 250 / PPM) );
		truckLoad.add( new TruckLoad(world, boxFixture, new MyUserData("box", false), 387.5f / PPM, 275 / PPM) );
		
		//set inputProcessors
		Gdx.input.setInputProcessor(new InputMultiplexer(new InputProcessor(){

			@Override
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				if(keycode == Keys.ESCAPE){
					getStateManager().setState(getStateManager().PLAY);
					return true;
				}
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				// TODO Auto-generated method stub
				return false;
			}},car));
	}

	@Override
	public void update(float dt) {
		world.step(dt, 6, 2);
		b2cam.position.set(car.getBody().getPosition().x, car.getBody().getPosition().y, 0);
		b2cam.update();
		
	}

	@Override
	public void render() {
		//clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		//debug box2d
		box2dDebug.render(world, b2cam.combined);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}
	
}
