package com.mygdx.game.handlers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ContentManager {

	
	private HashMap<String, Texture> textures;
	
	public ContentManager(){
		textures = new HashMap<String, Texture>();
	}
	
	public void loadTexture(String path, String key){
		Texture t = new Texture(Gdx.files.internal(path), true);
		textures.put(key, t);
	}
	
	public Texture getTexture(String key){
		return textures.get(key);
	}
	
	public void disposeTexture(String key){
		Texture t = textures.get(key);
		if(t != null){
			t.dispose();
		}
	}
	
}
