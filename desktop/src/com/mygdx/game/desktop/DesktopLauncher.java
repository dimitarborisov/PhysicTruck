package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.main.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.resizable = false;
		config.title = Game.TITLE;
		config.height = Game.VHEIGHT * Game.VSCALE;
		config.width = Game.VWIDTH * Game.VSCALE;
		//config.fullscreen = true;
		config.vSyncEnabled = true;
		
		new LwjglApplication(new Game(), config);
	}
}
