package com.oop.gameengine.Managers;

import com.oop.gameengine.InputOutput.*;
import java.util.Hashtable;
import java.util.UUID;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class InputOutputManagement extends ApplicationAdapter {
	// INPUT HANDLING
	protected InputKeyboard keyManager = new InputKeyboard();
	protected InputMouse mouseManager = new InputMouse();
	protected OutputSound soundManager = new OutputSound();
	protected boolean isKeyPressed, isSoundPlayed = false;
	protected static Hashtable<UUID, InputKeyboard> keybinds = new Hashtable<UUID, InputKeyboard>();
	protected static Hashtable<String, OutputSound> sounds = new Hashtable<String, OutputSound>();

	public InputKeyboard getKeyboardManager() {
		return keyManager;
	}

	public OutputSound getSoundManager() {
		return soundManager;
	}

	// constructors = = =
	public InputOutputManagement() {
//		System.out.println("I/O == Loading InputOutput Manager!");
		
	}


	public void startSound(String label) {
		sounds.get(label).play();
	}
	public void startSound(String label, float vol) {
		sounds.get(label).play(vol);
	}
	public void stopSound(String label) {
		sounds.get(label).stop();
	}
	public void pauseSound(String label) {
		sounds.get(label).pause();
	}

	// LISTENER SOUND functions ==
	public void mouse() {
		mousePress();
		mouseReleased();
	}

	public void playOnPress() {
		if (keyManager.isMovementKeyPressed()) {
			Gdx.input.setInputProcessor(new InputAdapter() {
				@Override
				public boolean keyDown(int keycode) {
					if (keyManager.isMovementKeyPressed() && !isKeyPressed) { // Change to your desired key
//						System.out.println("I/O == Player is moving!");
//						sounds.get("walk").play();
						isKeyPressed = true;
					}
					return true;
				}

				@Override
				public boolean keyUp(int keycode) {
					if (!keyManager.isMovementKeyPressed()) { // Change to your desired key
//						System.out.println("I/O == Player stopped moving!");
//						sounds.get("walk").stop();
						isKeyPressed = false; // reset flag
					}
					return true;
				}
			});
		} // TODO: if key pressed

	}

	public void mousePress() {
		// mouse movement handling
		if (mouseManager.isTouched() && !isSoundPlayed) {
//			System.out.println("I/O == player clicked mouse");
			sounds.get("interact").play();
			isSoundPlayed = true;
		}
	}

	public void mouseReleased() {
		// mouse movement handling
		if (!Gdx.input.isTouched()) {
			isSoundPlayed = false;
		}
	}

	public Hashtable<String, OutputSound> getSounds() {
		return sounds;
	}
	
	public void getSounds(Hashtable<String, OutputSound> newSound) {
		sounds = newSound;
	}
}
