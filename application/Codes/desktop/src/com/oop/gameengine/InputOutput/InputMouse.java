package com.oop.gameengine.InputOutput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class InputMouse extends InputAdapter{
	
	@Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("Mouse button pressed at: (" + screenX + ", " + screenY + ")");
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("Mouse button released at: (" + screenX + ", " + screenY + ")");
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("Mouse dragged at: (" + screenX + ", " + screenY + ")");
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        System.out.println("Mouse moved to: (" + screenX + ", " + screenY + ")");
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        System.out.println("Mouse scrolled: (" + amountX + ", " + amountY + ")");
        return true;
    }
    
    public boolean isTouched() {
    	return Gdx.input.justTouched();
    }

}
