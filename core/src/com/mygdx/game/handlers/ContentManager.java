package com.mygdx.game.handlers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class ContentManager {

	private HashMap<String, Preferences> prefs;
	private HashMap<String, Texture> textures;
	private HashMap<String, Sound> sounds;
	private HashMap<String, Music> music;
	private HashMap<String, FileHandle> model;
	
	public ContentManager(){
		textures = new HashMap<String, Texture>();
		prefs = new HashMap<String, Preferences>();
		
		sounds = new HashMap<String, Sound>();
		music = new HashMap<String, Music>();
		
		model = new HashMap<String, FileHandle>();
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
	
	
	//PREFERENCES
	public void loadPreferences(String preferences, String key){
		Preferences pref = Gdx.app.getPreferences(preferences);
		prefs.put(key, pref);
	}
	
	public Preferences getPref(String key){
		return prefs.get(key);
	}
	
	
	//SOUND
	public void loadSound(String path, String key){
		Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
		sounds.put(key, sound);
	}

	public Sound getSound(String key){
		return sounds.get(key);
	}
	
	
	//MUSIC
	public void loadMusic(String path, String key){
		Music m = Gdx.audio.newMusic(Gdx.files.internal(path));
		music.put(key, m);
	}
	
	public Music getMusic(String key){
		return music.get(key);
	}

	
	//MODEL FILES
	public void loadModel(String path, String key){
		FileHandle f = Gdx.files.internal(path);
		model.put(key, f);
	}
	
	public FileHandle getModel(String key){
		return model.get(key);
	}

}
