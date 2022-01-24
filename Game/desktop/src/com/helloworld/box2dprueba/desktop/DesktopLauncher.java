package com.helloworld.box2dprueba.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.GameApp;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Dungeon Crawler";
		config.width = 1080;
		config.height = 720;
		new LwjglApplication(new GameApp(), config);
	}
}
