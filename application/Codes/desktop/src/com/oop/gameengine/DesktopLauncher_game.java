package com.oop.gameengine;


import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.oop.gameengine.Managers.SimulationLifeCycleManagement;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher_game{
	public static void main (String[] arg){
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("INF1009-GameEngine");
		config.setWindowedMode(1080, 720);
		// can insert custom width and height of the window.
		// config.setWidth(100); <- idk if this works
		//new Lwjgl3Application(new SimulationLifeCycleManagement(), config);


		SimulationLifeCycleManagement instance = SimulationLifeCycleManagement.getInstance();
		new Lwjgl3Application(instance,config);
	}
}
