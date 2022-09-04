package fr.saphyr.ce;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public final class DesktopLauncher {

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowIcon("icons/icon.png");
		config.setTitle("Crow Emblem");
		config.setMaximized(true);
		new Lwjgl3Application(new CrowEmblem(), config);
	}
}
