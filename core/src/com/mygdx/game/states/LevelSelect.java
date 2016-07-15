package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.entities.SimpleImageButton;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.main.Game;

public class LevelSelect extends GameState{
	
	private SimpleImageButton sib;
	private int ty,tx;
	
	OrthographicCamera levelCam;
	
	public LevelSelect(GameStateManager m) {
		super(m);
		levelCam = new OrthographicCamera();
		levelCam.setToOrtho(true, Game.VWIDTH, Game.VHEIGHT);
		
		ty = -1;
		tx = -1;
		
		sib = new SimpleImageButton(Game.cm.getTexture("buttonStage1"), 0, 0, 200, 200);
		Gdx.input.setInputProcessor(new InputProcessor(){

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
				//Vector3 input = new Vector3(screenX, screenY, 0);
				//cam.unproject(input);
				tx = screenX;
				ty = screenY;
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
			}});
	}

	@Override
	public void update(float dt) {
		sib.update(tx, ty);
		
		if(sib.isClicked()){
			System.out.println("Clicked!");
		}
		
		tx = -1;
		ty = -1;
	}

	@Override
	public void render() {
		//clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		s.setProjectionMatrix(levelCam.combined);
		
		sib.render(s);
		
		
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
