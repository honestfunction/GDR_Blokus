package com.gdr.blokus.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gdr.blokus.blokus;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "gDevRock Blokus";
		config.width = 800;
		config.height = 480;
		config.resizable =false;
		new LwjglApplication(new blokus(), config);
	}
}
