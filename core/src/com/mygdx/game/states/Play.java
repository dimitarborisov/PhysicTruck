package com.mygdx.game.states;

import static com.mygdx.game.handlers.Box2DVariables.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.mygdx.game.entities.SimpleImageButton;
import com.mygdx.game.entities.tweenEntities.TweenSpriteAccessor;
import com.mygdx.game.handlers.BackgroundHandler;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.handlers.MyContactListener;
import com.mygdx.game.handlers.StageManager;
import com.mygdx.game.main.Game;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

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
	Preferences prefs;
	
	private final TweenManager tweenManager = new TweenManager();
	
	private SimpleImageButton backButton;
	private Sprite hudBoxes;
	BitmapFont font;
	GlyphLayout layout;
	
	LevelCompletedMenu lcm;
	private float ty, tx;
	
	private float timeSlow;
	
	public static int STAGESELECTED = -1;

	public Play(GameStateManager m) {
		super(m);
		debug = false;
		
		initializeWorld();
		createTruck();
		createStage();
		createBoxes();
		bh = new BackgroundHandler(car);
		
		cam.setToOrtho(false, Game.VWIDTH, Game.VHEIGHT);
		hud.setToOrtho(true, Game.VWIDTH, Game.VHEIGHT);
		
		//HUD---------------
		backButton = new SimpleImageButton(Game.cm.getTexture("backButton"), 
															30, Game.VHEIGHT - 150 -20, 
															150, 150,
															false, true);
		
		Texture texture = Game.cm.getTexture("hudBoxes");
		texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		hudBoxes = new Sprite(texture);
		hudBoxes.setSize(50, 50);
		hudBoxes.setPosition(Game.VWIDTH / 2 - hudBoxes.getWidth(), 10);
		
		//Text setup
		font = new BitmapFont(Gdx.files.internal("fonts/Burnstown_Dam.fnt"), true);
		layout = new GlyphLayout(); //dont do this every frame!
		layout.setText(font , truckLoad.size() + "/" + truckLoad.size());
		
		//TIMESLOW-----------
		timeSlow = 1.0f;
		
		Tween.setCombinedAttributesLimit(4);
		Tween.registerAccessor(Sprite.class, new TweenSpriteAccessor());
		
		//load the stars file
		prefs = Gdx.app.getPreferences("stagesStar");
		// System.out.println("starting stage: " + STAGESELECTED);

		lcm = new LevelCompletedMenu();
		ty = -1;
		tx = -1;


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
					getStateManager().setTransition(GameStateManager.RIGHTLEFT ,Play.this , GameStateManager.LEVELSELECT, true, false);
					return true;
				}
				
				if (keycode == Keys.BACKSPACE) {
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
		tweenManager.update(dt);
		
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
		
		backButton.update(tx, ty);
		
		if(backButton.isClicked()){
			getStateManager().setTransition(GameStateManager.RIGHTLEFT ,Play.this , GameStateManager.LEVELSELECT, true, false);
		}

		
		// reset click position
		tx = -1;
		ty = -1;

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
		
		s.setProjectionMatrix(hud.combined);
		backButton.render(s);
		
		s.begin();
		hudBoxes.draw(s);
		

		font.draw(s, layout
				, hudBoxes.getX() + hudBoxes.getWidth() + 5
				, hudBoxes.getY());
		
		s.end();
		
		lcm.render(s);
	}
	
	public void finishStage(boolean finished, int cratesOut){
		//true finished at the flag
		//false failed somewhere :(
		
		//HIDE BUTTONS
		Tween.to(backButton.getSprite(), TweenSpriteAccessor.POS_XY, 0.5f)
					.targetRelative(0, + 150 + 20)
					.ease(TweenEquations.easeInOutQuad)
					.start(tweenManager);
		
		
		//TIMER SETTINGS
		Timer.schedule(new Task() {
			
			@Override
			public void run() {

				if(timeSlow > 0.04f){
					timeSlow -= 0.01f;
					//System.out.println(timeSlow);
				}
				
			}

		}, 0.01f, 0.015f);
		
		float sRatio = (float) 3 / truckLoad.size();
		int stars = 3 - (int) Math.ceil(cratesOut * sRatio);
		
		//get previous stars
		int prevStars = prefs.getInteger(Integer.toString(STAGESELECTED + 1), 0);
		
		//save current stars if the stage is finished correctly and the stars are more than the saved ones
		if(prevStars < stars && finished){
			prefs.putInteger(Integer.toString(STAGESELECTED + 1), stars);
			prefs.flush();
		}
		
		lcm.trigger(finished, stars);
	
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

	public void updateBoxCounter(int boxesDropped){
		layout.setText(font, (truckLoad.size() - boxesDropped) + "/" + truckLoad.size());
	}
	
	@Override
	public void dispose() {
	}

}
