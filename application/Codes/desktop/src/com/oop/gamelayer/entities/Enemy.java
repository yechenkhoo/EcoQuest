package com.oop.gamelayer.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oop.gameengine.Entities.Entity;
import com.oop.gameengine.Interfaces.iCollidable;

public class Enemy extends Entity implements iCollidable{
	private String currentSceneName;
	public Enemy() {
        // Default Constructor
    }

    public Enemy(String f, float x, float y, float speed, boolean isAIControlled, String currentSceneName) {
        super(f, x, y, speed, isAIControlled);
    }

    
    public void update() {
        
    }

    private void calculateEntityBounds(Entity entity, float[] bounds) {
        bounds[0] = entity.getxPos(); // left
        bounds[1] = entity.getxPos() + entity.getTexture().getWidth(); // right
        bounds[2] = entity.getyPos() + entity.getTexture().getHeight(); // top
        bounds[3] = entity.getyPos(); // bottom
        bounds[4] = entity.getyPos() + entity.getTexture().getHeight() / 2f; // centerY
    }
    
	
    private boolean checkCollisionPlayScene2(Entity other, float[] thisBounds, float[] otherBounds) {
        if (other instanceof Bin) {
            // Check for horizontal overlap
            boolean horizontalOverlap = thisBounds[1] > otherBounds[0] && thisBounds[0] < otherBounds[1];
            
            // Check for vertical overlap
            boolean verticalOverlap = thisBounds[3] < otherBounds[2] && thisBounds[2] > otherBounds[3];
            
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
     // Calculate bounds for this entity
        calculateEntityBounds(this, thisBounds);
        // Calculate bounds for the other entity
        calculateEntityBounds(other, otherBounds);

        if ("PlayScene2".equals(currentSceneName)) {
        	System.out.println("If else");
            return checkCollisionPlayScene2(other, thisBounds, otherBounds);
        }
        // Handle other scenes or return false if scene is not recognized
        return false;
    }

}
