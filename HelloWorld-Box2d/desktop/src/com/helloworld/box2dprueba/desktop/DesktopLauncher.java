package com.helloworld.box2dprueba.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.helloworld.box2dprueba.JuegoPrueba;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Hello World";
		config.width = 720;
		config.height = 480;
		new LwjglApplication(new JuegoPrueba(), config);
	}
}
