package com.mygdx.game.states;

import static com.mygdx.game.handlers.Box2DVariables.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.entities.Box2DTerrain;
import com.mygdx.game.entities.Box2DVehicle;
import com.mygdx.game.entities.BoxLoad;
import com.mygdx.game.entities.DummyStage;
import com.mygdx.game.entities.FarmTruck;
import com.mygdx.game.entities.SimpleImageButton;
import com.mygdx.game.handlers.BackgroundHandler;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.main.Game;

public class LevelSelect extends GameState {

	private ArrayList<SimpleImageButton> sib;
	private int ty, tx;
	BackgroundHandler bh;

	private World world;
	private Box2DVehicle car;
	private Box2DTerrain terrain1;
	private Box2DTerrain terrain2;
	private ArrayList<BoxLoad> truckLoad;
	Array<Body> bodies;
	ShapeRenderer shapeRenderer;
	
	
	OrthographicCamera levelCam;

	public LevelSelect(GameStateManager m) {
		super(m);
		
		shapeRenderer = new ShapeRenderer();
		
		levelCam = new OrthographicCamera();
		levelCam.setToOrtho(true, Game.VWIDTH, Game.VHEIGHT);

		ty = -1;
		tx = -1;

		// add buttons
		sib = new ArrayList<SimpleImageButton>();
		sib.add(new SimpleImageButton(Game.cm.getTexture("buttonStage1"), "1", 40, 13, 280, 280, false, true));
		sib.add(new SimpleImageButton(Game.cm.getTexture("buttonStage2"), "2", 360, 13, 280, 280, false, true));
		sib.add(new SimpleImageButton(Game.cm.getTexture("buttonStage3"), "3", 680, 13, 280, 280, false, true));
		sib.add(new SimpleImageButton(Game.cm.getTexture("buttonStage4"), "4", 40, 306, 280, 280, false, true));
		sib.add(new SimpleImageButton(Game.cm.getTexture("buttonStage5"), "5", 360, 306, 280, 280, false, true));
		sib.add(new SimpleImageButton(Game.cm.getTexture("buttonStage6"), "6", 680, 306, 280, 280, false, true));

		// create background
		initializeWorld();
		createTruck();
		createStage();
		createBoxes();
		bh = new BackgroundHandler(car);

		Timer.schedule(new Task() {

			@Override
			public void run() {
				car.getBody().setLinearVelocity(2, 0);
			}

		}, 1, 0.1f);

		Gdx.input.setInputProcessor(new InputProcessor() {

			@Override
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				// TODO Auto-generated method stub
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
				// Vector3 input = new Vector3(screenX, screenY, 0);
				// cam.unproject(input);
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
		});
	}

	@Override
	public void update(float dt) {
		world.step(dt, 6, 2);
		bh.update(dt);

		// update camera
		// follow player sprite
		cam.position.set((car.getBody().getPosition().x * PPM) + 200, (car.getBody().getPosition().y * PPM) + 100, 0);
		cam.update();

		// update buttons
		for (SimpleImageButton iButton : sib) {
			iButton.update(tx, ty);
		}

		// check if button is clicked
		for (SimpleImageButton iButton : sib) {
			if (iButton.isClicked()) {
				Play.STAGESELECTED = iButton.getStageSelected();
				m.setState(GameStateManager.PLAY);

			}
		}

		// reset click position
		tx = -1;
		ty = -1;

		// update world position
		Sprite sprite1 = terrain1.getSpriteTerrain();
		Sprite sprite2 = terrain2.getSpriteTerrain();

		if (sprite1.getX() + sprite1.getWidth() / 2 - cam.position.x < 0) {
			terrain2.moveTerrain((sprite1.getX() + sprite1.getWidth()) / PPM, sprite2.getY() / PPM, 0);
		} else {
			terrain2.moveTerrain((sprite1.getX() - sprite2.getWidth()) / PPM, sprite2.getY() / PPM, 0);
		}

		sprite1 = terrain1.getSpriteTerrain();
		sprite2 = terrain2.getSpriteTerrain();

		if (sprite2.getX() + sprite2.getWidth() / 2 - cam.position.x < 0) {
			terrain1.moveTerrain((sprite2.getX() + sprite2.getWidth()) / PPM, sprite1.getY() / PPM, 0);
		} else {
			terrain1.moveTerrain((sprite2.getX() - sprite1.getWidth()) / PPM, sprite1.getY() / PPM, 0);
		}

	}

	@Override
	public void render() {
		// clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

		s.setProjectionMatrix(cam.combined);

		bh.render(s);
		terrain1.render(s);
		terrain2.render(s);
		for (BoxLoad t : truckLoad) {
			t.render(s);
		}

		car.render(s);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		shapeRenderer.setProjectionMatrix(levelCam.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(0 / 255.0f, 0 / 255.0f, 0 / 255.0f, 0.4f);
		shapeRenderer.rect(0, 0, Game.VWIDTH, Game.VHEIGHT);
		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		s.setProjectionMatrix(levelCam.combined);

		for (SimpleImageButton iButton : sib) {
			iButton.render(s);
		}

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}


	private void createBoxes() {

		truckLoad.add(new BoxLoad(world, 40, 40, 125 / PPM, 400 / PPM));

		truckLoad.add(new BoxLoad(world, 40, 40, 130 / PPM, 300 / PPM));
		truckLoad.add(new BoxLoad(world, 40, 40, 90 / PPM, 300 / PPM));

		truckLoad.add(new BoxLoad(world, 40, 40, 130 / PPM, 350 / PPM));
		truckLoad.add(new BoxLoad(world, 40, 40, 90 / PPM, 350 / PPM));

	}

	private void createStage() {
		// track0
		terrain1 = new DummyStage(world, 0 / PPM, 0 / PPM, 10);
		terrain2 = new DummyStage(world, 1500 / PPM, 0 / PPM, 10);
	}

	private void createTruck() {
		car = new FarmTruck(world, 150 / PPM, 130 / PPM, 2);
	}

	private void initializeWorld() {

		truckLoad = new ArrayList<BoxLoad>();

		world = new World(new Vector2(0, -9.8f), false);
		// world.setContactListener(new MyContactListener(m));
		bodies = new Array<Body>();
	}

}
