package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.entities.SimpleImageButton;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.main.Game;

public class Options extends GameState{

	private Sprite backgroundSprite;
	
	private SimpleImageButton returnButton;
	
	float tx, ty;
	
	public Options(GameStateManager m) {
		super(m);
		
		tx = -1;
		ty = -1;
		
		cam.setToOrtho(true, Game.VWIDTH, Game.VHEIGHT);
		
		Texture texture = Game.cm.getTexture("image10.png");
		
		
		backgroundSprite = new Sprite(texture);
		
		backgroundSprite.setPosition(0, 0);
		backgroundSprite.setSize(Game.VWIDTH, Game.VHEIGHT);
		backgroundSprite.flip(false, true);
	
		
		returnButton = new SimpleImageButton(Game.cm.getTexture("upButton"),
												Game.VWIDTH - 150 - 30, 
												Game.VHEIGHT - 150 - 30, 
												150, 150, false, true);
	
		
		Gdx.input.setInputProcessor(new InputProcessor() {

			@Override
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				if(keycode == Keys.ESCAPE){
					getStateManager().setTransition(GameStateManager.SLIDETOTOP, Options.this, GameStateManager.LEVELSELECT, true, false);
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
		returnButton.update(tx, ty);
		
		if(returnButton.isClicked()){
			getStateManager().setTransition(GameStateManager.SLIDETOTOP, Options.this, GameStateManager.LEVELSELECT, true, false);
		}
		
		tx = -1;
		ty = -1;
		
	}

	@Override
	public void render() {
		s.setProjectionMatrix(cam.combined);
		
		s.begin();
		backgroundSprite.draw(s);
		s.end();
		
		returnButton.render(s);
	}

	@Override
	public void dispose() {
	}

}
