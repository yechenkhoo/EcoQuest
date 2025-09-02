package com.oop.gamelayer.entities;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.oop.gameengine.Entities.Entity;
import com.oop.gameengine.Interfaces.*;

public class Bin extends Entity implements iCollidable {

    private String currentSceneName;
    private float currentSpeed;

    public Bin(String f, float x, float y, float speed, Boolean isAIControlled, String currentSceneName) {
        super(f, x, y, speed, isAIControlled);
        this.currentSceneName = currentSceneName;
    }

    public void increaseSpeed(float amount) {
        currentSpeed = getSpeed(); // Store the current speed
        setSpeed(getSpeed() + amount);

        // Schedule a task to reset the speed after 5 seconds
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // Reset the speed after 5 seconds
                setSpeed(currentSpeed);
            }
        };

        // Create a timer and schedule the task to execute after 5 seconds
        Timer timer = new Timer();
        timer.schedule(task, 5000); // 5000 milliseconds = 5 seconds
    }

    private void calculateEntityBounds(Entity entity, float[] bounds) {
        bounds[0] = entity.getxPos(); // left
        bounds[1] = entity.getxPos() + entity.getTexture().getWidth(); // right
        bounds[2] = entity.getyPos() + entity.getTexture().getHeight(); // top
        bounds[3] = entity.getyPos(); // bottom
        bounds[4] = entity.getyPos() + entity.getTexture().getHeight() / 2f; // centerY
    }
    
    private boolean checkCollisionPlayScene1(Entity other, float[] thisBounds, float[] otherBounds) {
        // Collision handling logic for PlayScene1
        // Example:
        if (other instanceof Items || other instanceof PowerUps) {
        	if (otherBounds[3] <= thisBounds[2] && otherBounds[3] >= thisBounds[4]) {
                if (otherBounds[0] >= thisBounds[0] && otherBounds[1] <= thisBounds[1]) {
                    return true; // Collision detected
                }
            }
        }
        
        // No collision detected
        return false;
    }

    private boolean checkCollisionPlayScene2(Entity other, float[] thisBounds, float[] otherBounds) {
        if (other instanceof Items || other instanceof PowerUps || other instanceof Enemy) {
            // Check for horizontal overlap
            boolean horizontalOverlap = otherBounds[1] > thisBounds[0] && otherBounds[0] < thisBounds[1];
            
            // Check for vertical overlap
            boolean verticalOverlap = otherBounds[3] < thisBounds[2] && otherBounds[2] > thisBounds[3];
            
            // Collision detected if there's both horizontal and vertical overlap
            if (horizontalOverlap && verticalOverlap) {
                return true;
            }
        }
        
        // No collision detected
        return false;
    }


    public boolean checkCollision(Entity other) {
    	float[] thisBounds = new float[5];
        float[] otherBounds = new float[5];

        calculateEntityBounds(this, thisBounds);
        calculateEntityBounds(other, otherBounds);

        if ("PlayScene1".equals(currentSceneName)) {
            return checkCollisionPlayScene1(other, thisBounds, otherBounds);
        } else if ("PlayScene2".equals(currentSceneName)) {
            return checkCollisionPlayScene2(other, thisBounds, otherBounds);
        }

        return false;
    }


    @Override
    public void update() {
        // TODO Auto-generated method stub
    }
}
