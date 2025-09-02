package com.oop.gameengine.InputOutput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class InputKeyboard {
	// INPUT HANDLING
	protected int key;
	protected String pressLabel;
	protected String actionID;
	// on click, do what? (event handling)
	public InputKeyboard() {}
	public InputKeyboard(int keypress, String label, String action) {
		this.key = keypress;
		this.pressLabel = label;
		this.actionID = action;
	}

	// features: get key press
	public boolean isLeftKeyPressed() {
		return Gdx.input.isKeyPressed(Keys.LEFT);
	}
	public boolean isLeftKeyJustPressed() {
		return Gdx.input.isKeyJustPressed(Keys.LEFT);
	}
	public boolean isRightKeyPressed() {
		return Gdx.input.isKeyPressed(Keys.RIGHT);
	}
	public boolean isUpKeyPressed() {
		return Gdx.input.isKeyPressed(Keys.UP);
	} 
	public boolean isDownKeyPressed() {
		return Gdx.input.isKeyPressed(Keys.DOWN);
	}
	public boolean isEscapeKeyPressed() {
		return Gdx.input.isKeyJustPressed(Keys.ESCAPE);
	}
	public boolean isSpaceKeyPressed() {
		return Gdx.input.isKeyJustPressed(Keys.SPACE);
	}
	public boolean isWKeyPressed() {
		return Gdx.input.isKeyPressed(Keys.W);
	}
	public boolean isAKeyPressed() {
		return Gdx.input.isKeyPressed(Keys.A);
	}
	public boolean isSKeyPressed() {
		return Gdx.input.isKeyPressed(Keys.S);
	}
	public boolean isDKeyPressed() {
		return Gdx.input.isKeyPressed(Keys.D);
	}
	
	/**
	 *  checks if the specific key is pressed
	 * @param keybind
	 * @return boolean (true/false)
	 */
	public boolean checkisKeyPressed(int keybind) {
		return Gdx.input.isKeyPressed(keybind);
	}
	public boolean checkisKeyJustPressed(int keybind) {
		return Gdx.input.isKeyJustPressed(keybind);
	}
	
	/**
	 * checks if the movement key is pressed, mainly for sound output
	 * @return boolean (true/false)
	 */
	public boolean isMovementKeyPressed() {
		return Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.DOWN);
	}
	
	public int getPressedMovementKey() {
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            return Keys.LEFT;
        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            return Keys.RIGHT;
        } else if (Gdx.input.isKeyPressed(Keys.UP)) {
            return Keys.UP;
        } else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            return Keys.DOWN;
        }
        return -1; // Return -1 if no movement key is pressed
    }

	// setter getters
	public int getKeyPress() {
		return key;
	}

	public String getPressLabel() {
		return pressLabel;
	}

	public String getActionID() {
		return actionID;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public void setPressLabel(String pressLabel) {
		this.pressLabel = pressLabel;
	}

	public void setActionID(String actionID) {
		this.actionID = actionID;
	}

}
