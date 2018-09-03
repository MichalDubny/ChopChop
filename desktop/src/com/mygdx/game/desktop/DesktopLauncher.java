package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.GameMain;
import gameInfo.GameInfo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameInfo.WIDTH * GameInfo.MULTIPLE_SIZE;
		config.height = GameInfo.HEIGHT * GameInfo.MULTIPLE_SIZE;
		new LwjglApplication(new GameMain(), config);
	}
}
