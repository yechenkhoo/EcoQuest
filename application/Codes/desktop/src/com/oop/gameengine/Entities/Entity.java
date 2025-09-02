package com.oop.gameengine.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.oop.gameengine.Interfaces.*;
import com.badlogic.gdx.graphics.Texture;

public abstract class Entity {
	private SpriteBatch batch;
	private float xPos, yPos;
	private float speed;
	private Color color;
	public Texture tex;
	private boolean IsAIControlled;
	private int score = 0;

	public Entity() {
	} // Default Constructor

	public Entity(String tex, float xPos, float yPos, float speed, boolean IsAIControlled) {
		this.tex = new Texture(Gdx.files.internal(tex));
		this.xPos = xPos;
		this.yPos = yPos;
		this.speed = speed;
		this.IsAIControlled = IsAIControlled;
	}

	public Entity(float xPos, float yPos, float speed, boolean IsAIControlled) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.speed = speed;
		this.IsAIControlled = IsAIControlled;
	}

	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public float getScore() {
		return this.score;
	}

	public void setScore(float change) {
		this.score += change;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Texture getTexture() {
		return tex;
	}

	public void setTexture(Texture t) {
		tex = t;
	}

	public boolean getIsAIControlled() {
		return this.IsAIControlled;
	}

	public void setIsAIControlled(boolean IsAIControlled) {
		this.IsAIControlled = IsAIControlled;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void draw(ShapeRenderer shape) {
	}

	public void draw(SpriteBatch batch) {
		batch.draw(getTexture(), getxPos(), getyPos(), getTexture().getWidth(), getTexture().getHeight());
	}

	public abstract void update();

}
