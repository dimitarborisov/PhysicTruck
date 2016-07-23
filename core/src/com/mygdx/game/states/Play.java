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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.entities.Box2DTerrain;
import com.mygdx.game.entities.Box2DVehicle;
import com.mygdx.game.entities.BoxLoad;
import com.mygdx.game.entities.LevelCompletedMenu;
import com.mygdx.game.handlers.BackgroundHandler;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.handlers.MyContactListener;
import com.mygdx.game.handlers.StageManager;
import com.mygdx.game.main.Game;

public class Play extends GameState {
	private World world;
	private Box2DDebugRenderer box2dDebug;
	private boolean debug;
	private OrthographicCamera b2cam;

	private Box2DVehicle car;
	private Box2DTerrain terrain;
	private ArrayList<BoxLoad> truckLoad;
	Array<Body> bodies;
	BackgroundHandler bh;

	
	
	LevelCompletedMenu lcm;
	private float ty, tx;
	
	private float timeSlow;
	
	public static int STAGESELECTED = -1;

	public Play(GameStateManager m) {
		super(m);
		debug = false;
		
		timeSlow = 1.0f;
		
		// System.out.println("starting stage: " + STAGESELECTED);

		lcm = new LevelCompletedMenu();
		ty = -1;
		tx = -1;

		initializeWorld();
		createTruck();
		createStage();
		createBoxes();
		bh = new BackgroundHandler(car);

		// set inputProcessors

		Gdx.input.setInputProcessor(new InputMultiplexer(new InputProcessor() {

			@Override
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				if (keycode == Keys.ESCAPE) {
					getStateManager().setState(GameStateManager.PLAY);
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
				tx = screenX;
				ty = screenY;

				return true;
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
			}
		}, car));
	}

	@Override
	public void update(float dt) {
		
		world.step(dt * timeSlow, 6, 2);
		bh.update(dt * timeSlow);

		// update camera
		// follow player sprite

		cam.position.set((car.getBody().getPosition().x * PPM), (car.getBody().getPosition().y * PPM), 0);
		cam.update();

		// follow box2d body
		b2cam.position.set(car.getBody().getPosition().x, car.getBody().getPosition().y, 0);
		b2cam.update();

		// update win screen
		lcm.update(dt, tx, ty);
		
		if(lcm.continueIsClicked()){
			getStateManager().setTransition(GameStateManager.RIGHTLEFT ,Play.this , GameStateManager.LEVELSELECT, true, false);
		}
		
		if(lcm.replayIsClicked()){
			m.setState(GameStateManager.PLAY);
		}

		// reset click position
		tx = -1;
		ty = -1;

	}
	
	public void finishStage(boolean finished){
		//true finished at the flag
		//false failed somewhere :(
		
		//TIMER SETTINGS
		Timer.schedule(new Task() {
			
			@Override
			public void run() {

				if(timeSlow > 0.05f){
					timeSlow -= 0.01f;
					System.out.println(timeSlow);
				}
				
			}

		}, 0.01f, 0.01f);
		
		
		lcm.trigger(1);
	
	}

	@Override
	public void render() {
		// clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

		s.setProjectionMatrix(cam.combined);

		bh.render(s);
		terrain.render(s);
		for (BoxLoad t : truckLoad) {
			t.render(s);
		}

		car.render(s);

		if (debug) {
			// debug box2d
			box2dDebug.render(world, b2cam.combined);
		}

		lcm.render(s);

	}

	private void createBoxes() {
		truckLoad = StageManager.getLoad(world, StageManager.getStageNum(STAGESELECTED));
	}

	private void createStage() {
		terrain = StageManager.getTerrain(world, StageManager.getStageNum(STAGESELECTED));
	}

	private void createTruck() {
		car = StageManager.getVehicle(world, StageManager.getStageNum(STAGESELECTED));
	}

	private void initializeWorld() {
		world = new World(new Vector2(0, -9.8f), false);
		world.setContactListener(new MyContactListener(this));
		box2dDebug = new Box2DDebugRenderer();
		bodies = new Array<Body>();

		// camera setup
		b2cam = new OrthographicCamera();
		b2cam.setToOrtho(false, (Game.VWIDTH) / PPM, (Game.VHEIGHT) / PPM);
	}

	@Override
	public void dispose() {
	}

}
