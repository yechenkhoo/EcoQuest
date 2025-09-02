package com.oop.gameengine.Entities;

import com.badlogic.gdx.Gdx;
import com.oop.gameengine.Managers.SimulationLifeCycleManagement;
import com.oop.gameengine.Entities.Entity;
import com.oop.gameengine.InputOutput.InputKeyboard;


public class Player {

    protected InputKeyboard inputKeyboard;
    protected Entity player;
    protected int moveLeft, moveRight, moveUp, moveDown, action;
    protected int score = 0;
    protected boolean horizontal = false, vertical = false;
    
    public Player(int up, int down, int left, int right, InputKeyboard keyboard){
    	this.moveUp = up;
    	this.moveDown = down;
    	this.moveLeft = left;
    	this.moveRight = right;
        this.inputKeyboard = keyboard;
    }
    
    public Player(Entity player, InputKeyboard keyboard){
    	this.player = player;
    	this.inputKeyboard = keyboard;
    } 
    
    public Player(Entity player, int left, int right, InputKeyboard keyboard){
    	this.player = player;
    	this.moveLeft = left;
    	this.moveRight = right;
    	this.inputKeyboard = keyboard;
    }
    
    public Player(Entity player, int up, int down, int left, int right, InputKeyboard keyboard){
    	this.player = player;
    	this.moveUp = up;
    	this.moveDown = down;
    	this.moveLeft = left;
    	this.moveRight = right;
    	this.inputKeyboard = keyboard;
    }
    
    public Player setDetails(Entity entity, String dimensions) {
    	this.setMovement(dimensions);
    	this.player = entity;
    	return this;
    }
    
    public void setMovement(String dimensions) {
    	if (dimensions == "horizontal") {
    		horizontal = true;
    		vertical = false;
    	} 
    	else if (dimensions == "vertical") {
    		horizontal = false;
    		vertical = true;
    	} else {
    		horizontal = true;
    		vertical = true;
    	}
    	
    }
    
    public void userMovement() {	
    	if (horizontal && this.inputKeyboard.checkisKeyPressed(moveLeft)) {
    		left();
    	}
    	if (horizontal && this.inputKeyboard.checkisKeyPressed(moveRight)) {
    		right();
    	}
    	if (vertical && this.inputKeyboard.checkisKeyPressed(moveUp)) {
    		up();
    	}
    	if (vertical && this.inputKeyboard.checkisKeyPressed(moveDown)) {
    		down();
    	}
    	if (this.inputKeyboard.checkisKeyPressed(action)) {
    		action();
    	}
        if (this.inputKeyboard.checkisKeyPressed(action)) {
            action();
        }
    }

    public int addScore(int score) {
    	this.score += score;
    	return this.score;
    }
    
    public int updateScore(int score) {
    	this.score = score;
    	return this.score;
    }
    
    public int getScore() {
    	return this.score;
	}
    
    public int resetScore() {
    	this.score = 0;
    	return this.score;
    }
    
    public void setKeybind(String action, int keybind) {
		switch(action) {
			case "left":
				this.moveLeft = keybind;
			case "right":
				this.moveRight = keybind;
			case "up":
				this.moveUp = keybind;
			case "down":
				this.moveDown = keybind;
			case "action":
				this.action = keybind;
		}
    	
    }
    
    public void setKeybind(int moveLeft, int moveRight, int moveUp, int moveDown){
        this.moveLeft = moveLeft;
        this.moveRight = moveRight;
        this.moveUp = moveUp;
        this.moveDown = moveDown;
    }
    
    public void setKeybind(int moveLeft, int moveRight, int moveUp, int moveDown, int action){
        this.moveLeft = moveLeft;
        this.moveRight = moveRight;
        this.moveUp = moveUp;
        this.moveDown = moveDown;
        this.action = action;
    }
    
    public void left() {
//    	System.out.println(" = = = its moving left = = =");
    	float x = this.player.getxPos() - player.getSpeed() * Gdx.graphics.getDeltaTime();
        player.setxPos(x);
    }
    public void right() {
//    	System.out.println(" = = = its moving right = = =");
    	float x = this.player.getxPos() + player.getSpeed() * Gdx.graphics.getDeltaTime();
        player.setxPos(x);
    }
    public void up() {
//    	System.out.println(" = = = its moving up = = =");
    	float x = this.player.getyPos() + player.getSpeed() * Gdx.graphics.getDeltaTime();
        player.setyPos(x);
    }
    public void down() {
//    	System.out.println(" = = = its moving down = = =");
    	float x = this.player.getyPos() - player.getSpeed() * Gdx.graphics.getDeltaTime();
        player.setyPos(x);
    }
    public void action() {
//    	System.out.println(" = = = its doing action = = =");
    }
}
