package com.oop.gameengine.Managers;

import java.util.Hashtable;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.oop.gameengine.Entities.Player;
import com.oop.gameengine.InputOutput.OutputSound;
import com.oop.gamelayer.scene.*;

public class SimulationLifeCycleManagement extends Game {

	private static SimulationLifeCycleManagement instance;
	private EntityManagement entityManager;
	private SceneManagement sceneManagement;
	private PlayerManagement playerManagement;
	private AIControlManagement aiControlManagement;
	private InputOutputManagement inputOutputManager;
	private BitmapFont font;

	private SimulationLifeCycleManagement() {
		// Private constructor to prevent instantiation outside the class
	}
	public static SimulationLifeCycleManagement getInstance() {
		if (instance == null) {
			instance = new SimulationLifeCycleManagement();
		}
		return instance;
	}
	@Override
	public void create() {

		entityManager = new EntityManagement();
		aiControlManagement = new AIControlManagement(entityManager);

		sceneManagement = new SceneManagement(this);
		
		// load font
		font = new BitmapFont();

		inputOutputManager = new InputOutputManagement();
		// load sound
		loadSounds();
		
		
		// player management = = =
		playerManagement = new PlayerManagement(this);
		playerManagement.addPlayer(new Player(Keys.W, Keys.S, Keys.A, Keys.D, inputOutputManager.getKeyboardManager()));

		// Initialize and register scenes
		sceneManagement.addScene("PlayScene", new PlayScene_1(this));
		sceneManagement.addScene("PlayScene2", new PlayScene_2(this));
		sceneManagement.addScene("Menu", new Menu_1(this));
		sceneManagement.addScene("GameOver", new GameOver_1(this));
		sceneManagement.addScene("Instructions", new Instructions(this));

		// set players

		// Set the initial screen
		sceneManagement.setScene("Menu");
		
	}

	public BitmapFont getFont() {
		return font;
	}

	public SceneManagement getSceneManager() {
		return sceneManagement;
	}

	public AIControlManagement getAiControlManagement() {
		return aiControlManagement;
	}

	public EntityManagement getEntityManager() {
		return entityManager;
	}

	public PlayerManagement getPlayerManagement() {
		return playerManagement;
	}

	public InputOutputManagement getInputOutputManagement() {
		return inputOutputManager;
	}
	
	public void loadSounds() {
		Hashtable<String, OutputSound> sounds = inputOutputManager.getSounds();
		//music (longer duration)
		sounds.put("menu", new OutputSound("audio/menu.mp3", "music"));
		sounds.put("game", new OutputSound("audio/in-game.mp3", "music"));
		
		// sounds (short duration)
		sounds.put("interact", new OutputSound("audio/interact.mp3", "sound"));
		sounds.put("collected", new OutputSound("audio/collected.mp3", "sound"));
		sounds.put("win", new OutputSound("audio/win.mp3", "sound"));
		sounds.put("gameover", new OutputSound("audio/gameover.mp3", "sound"));
	}

	@Override
	public void dispose() {
		if (font != null) {
			font.dispose();
		}
		super.dispose();
		sceneManagement.dispose(); // Ensure sceneManager's dispose method disposes scenes
		inputOutputManager.dispose();
	}

}
