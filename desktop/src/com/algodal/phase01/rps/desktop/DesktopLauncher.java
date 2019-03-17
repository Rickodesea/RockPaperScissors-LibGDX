package com.algodal.phase01.rps.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.algodal.phase01.rps.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 90 * 4;
		config.height = 160 * 4;
		new LwjglApplication(new Game(), config);
	}
}
