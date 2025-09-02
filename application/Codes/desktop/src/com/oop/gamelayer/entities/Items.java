package com.oop.gamelayer.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oop.gameengine.Entities.Entity;
import com.oop.gameengine.Interfaces.iCollidable;

public class Items extends Entity implements iCollidable {
    private boolean isRecyclable;    
    private float rotationAngle;
    private float rotationSpeed;
    private float scale = 0.1f;
    private float maxScale = 1.0f;
    private float scaleStep = 0.05f;
    private boolean isShrinking = false;
    private String currentSceneName;

    public Items() {
        // Default Constructor
    }

    public Items(String f, float x, float y, float speed, boolean isAIControlled, boolean isRecyclable, float rotationSpeed, String currentSceneName) {
        super(f, x, y, speed, isAIControlled);
        this.isRecyclable = isRecyclable;
        this.rotationSpeed = rotationSpeed;
        this.rotationAngle = 0;
    }

    public boolean isRecyclable() {
        return isRecyclable;
    }
    
    public void setRecyclable(boolean isRecyclable) {
        this.isRecyclable = isRecyclable;
    }
    
	public float getScale() {
	    return scale;
	}
	public void setScale(float scale) {
	    this.scale = scale;
	}
	
	public void startShrinking() {
	    isShrinking = true;
	}

    @Override
    public void draw(SpriteBatch batch) {
        float originX = tex.getWidth() / 2f;
        float originY = tex.getHeight() / 2f;

        batch.draw(tex, getxPos() - originX, getyPos() - originY, originX, originY, tex.getWidth(), tex.getHeight(), scale, scale, rotationAngle, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
    }

    public void update() {
        rotationAngle += rotationSpeed;
        if (rotationAngle > 360) rotationAngle -= 360;

        if (!isShrinking && scale < maxScale) {
            scale += scaleStep;
            if (scale > maxScale) scale = maxScale;
        }
        
        if (isShrinking && scale > 0) {
            scale -= scaleStep;
            if (scale < 0) scale = 0;
            if (scale == 0) {
                // Optionally, you can remove the item from the game here
            }
        }
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
        if (other instanceof Bin) {
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

        if ("PlayScene1".equals(currentSceneName)) {
            return checkCollisionPlayScene1(other, thisBounds, otherBounds);
        } else if ("PlayScene2".equals(currentSceneName)) {
            return checkCollisionPlayScene2(other, thisBounds, otherBounds);
        }
        // Handle other scenes or return false if scene is not recognized
        return false;
    }

}
