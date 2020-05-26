package de.moleon.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.moleon.planer.client.libgdx.SpaceExplorerPlaner;
import de.moleon.planer.client.network.supers.ServerConnection;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Space Explorer Planer";
		config.foregroundFPS = 0;
		config.width = 1280;
		config.height = 720;
		config.vSyncEnabled = false;
		config.backgroundFPS = 60;
		
		new LwjglApplication(new SpaceExplorerPlaner(), config);
	}
}
