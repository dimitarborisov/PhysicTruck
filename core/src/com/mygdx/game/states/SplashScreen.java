package com.mygdx.game.states;

import static com.mygdx.game.handlers.Box2DVariables.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.entities.Box2DLoad;
import com.mygdx.game.entities.Box2DTerrain;
import com.mygdx.game.entities.Box2DVehicle;
import com.mygdx.game.entities.BoxLoad;
import com.mygdx.game.entities.DummyStage;
import com.mygdx.game.entities.FarmTruck;
import com.mygdx.game.entities.tweenEntities.TweenBox2DVehicleAccessor;
import com.mygdx.game.entities.tweenEntities.TweenSpriteAccessor;
import com.mygdx.game.handlers.BackgroundHandler;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.main.Game;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class SplashScreen extends GameState{

	BackgroundHandler bh;

	private final TweenManager tweenManager = new TweenManager();
	
	private World world;
	private Box2DVehicle car;
	private Box2DTerrain terrain1;
	private Box2DTerrain terrain2;
	private ArrayList<Box2DLoad> truckLoad;
	Array<Body> bodies;
	ShapeRenderer shapeRenderer;
	Sprite pressEnterSprite;
	
	
	OrthographicCamera splashCam;

	public SplashScreen(GameStateManager m) {
		super(m);
		
		Texture tTexture = Game.cm.getTexture("press_enter");
		tTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		pressEnterSprite = new Sprite(tTexture);
		pressEnterSprite.flip(false, true);
		pressEnterSprite.setSize(pressEnterSprite.getWidth() * 0.3f, pressEnterSprite.getHeight() * 0.3f);
		pressEnterSprite.setAlpha(0);
		
		pressEnterSprite.setPosition((Game.VWIDTH / 2 - pressEnterSprite.getWidth() / 2) + 20 , 20);
		
		shapeRenderer = new ShapeRenderer();
		
		splashCam = new OrthographicCamera();
		splashCam.setToOrtho(true, Game.VWIDTH, Game.VHEIGHT);
		
		
		// create background
		initializeWorld();
		createTruck();
		createStage();
		createBoxes();
		bh = new BackgroundHandler(car);

		//TIMER SETTINGS
		Timer.schedule(new Task() {
			float i = 0;
			@Override
			public void run() {
				
				car.getBody().setLinearVelocity(i, 0);
				
				if(i < 2.0){
					i += 0.2;
				}
			}

		}, 1, 0.1f);
		
		tweenSetting();
		
		Gdx.input.setInputProcessor(new InputProcessor() {

			@Override
			public boolean keyDown(int keycode) {
				
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				if(keycode == Keys.ENTER){
					getStateManager().setTransition(GameStateManager.RIGHTLEFT ,SplashScreen.this , GameStateManager.LEVELSELECT, true, true);
				}
				return true;
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
				//tweenManager.killAll();
				//getStateManager().setState(getStateManager().LEVELSELECT);
				
				getStateManager().setTransition(GameStateManager.RIGHTLEFT ,SplashScreen.this , GameStateManager.LEVELSELECT, true, false);
				
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
		tweenManager.update(dt);
		world.step(dt, 6, 2);
		bh.update(dt);

		// update camera
		// follow player sprite
		cam.position.set((car.getBody().getPosition().x * PPM) + 200, (car.getBody().getPosition().y * PPM) + 100, 0);
		cam.update();


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
		for (Box2DLoad t : truckLoad) {
			t.render(s);
		}

		car.render(s);
		
		s.setProjectionMatrix(splashCam.combined);
		s.begin();
		pressEnterSprite.draw(s);
		s.end();
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}


	private void createBoxes() {
		
		truckLoad.add(new BoxLoad(world, 40, 40, 130 / PPM, 300 / PPM));
		truckLoad.add(new BoxLoad(world, 40, 40, 90 / PPM, 300 / PPM));

		truckLoad.add(new BoxLoad(world, 40, 40, 130 / PPM, 350 / PPM));
		truckLoad.add(new BoxLoad(world, 40, 40, 90 / PPM, 350 / PPM));
		
		truckLoad.add(new BoxLoad(world, 40, 40, 125 / PPM, 400 / PPM));
	
	}

	private void createStage() {
		// track0
		terrain1 = new DummyStage(world, 0 / PPM, 0 / PPM, 10);
		terrain2 = new DummyStage(world, terrain1.getSpriteTerrain().getWidth() / PPM, 0 / PPM, 10);
	}

	private void createTruck() {
		car = new FarmTruck(world, 160.5f / PPM, 200 / PPM, 2);
	}

	private void initializeWorld() {

		truckLoad = new ArrayList<Box2DLoad>();

		world = new World(new Vector2(0, -9.8f), false);
		bodies = new Array<Body>();
	}

	private void tweenSetting(){
		//TWEEN SETTINGS
		Tween.setCombinedAttributesLimit(6);
		Tween.registerAccessor(Sprite.class, new 
		                TweenSpriteAccessor()); 
					
		Tween.to(pressEnterSprite, TweenSpriteAccessor.ALPHA , 0.5f)
				.target(1f)
				.repeatYoyo(-1, 0.3f)
				.start(tweenManager);
				
		Tween.to(car.getBodySprite(), TweenSpriteAccessor.ALPHA, 1f)
				.target(1f)
				.start(tweenManager);
				
		Tween.to(car.getLeftWheelSprite(), TweenSpriteAccessor.ALPHA, 1f)
				.target(1f)
				.start(tweenManager);
				
		for(Box2DLoad bl: truckLoad){
			Tween.to(bl.getLoadSprite(), TweenSpriteAccessor.ALPHA, 1f)
					.target(1f)
					.start(tweenManager);
				}
	}
}
