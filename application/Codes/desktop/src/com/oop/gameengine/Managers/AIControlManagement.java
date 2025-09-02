package com.oop.gameengine.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.oop.gameengine.Entities.Entity;
import com.oop.gamelayer.entities.Items;

import java.util.List;
import java.util.Random;

public class AIControlManagement {
    protected EntityManagement entityManager;
    private Random random;
    private Vector2 playerPosition, aiPosition;
    private String[] recyclableTextures = { "plastic.png", "can1.png", "glass.png", "plastic_bottle.png", "paper.png" };
    private String[] nonRecyclableTextures = { "burger1.png", "drink.png", "banana.png", "trash_bag.png", "pizza.png" };

    // Constructor
    public AIControlManagement(EntityManagement entityManager) {
        this.entityManager = entityManager;
        this.random = new Random();
    }

    // Method to update AI movements for AI-controlled entities
    public void AIMovements() {
        List<Entity> entities = entityManager.getEntityList();
        for (Entity entity : entities) {
            if (entity.getIsAIControlled()) {
                float newYPos = entity.getyPos() - entity.getSpeed();
                if (newYPos <= 0) { // Entity reaches the bottom
                    newYPos = Gdx.graphics.getHeight();
                    float newXPos = random.nextInt(800);
                    entity.setxPos(newXPos);
                    entity.setyPos(newYPos);
                    // Change the texture to a new one from the array
                    Texture newTexture = new Texture(Gdx.files.internal(getRandomTextureForEntity(entity)));
                    entity.setTexture(newTexture);
                } else {
                    entity.setyPos(newYPos);
                }
            }
        }
    }

    // Helper method to get a random texture path for an entity
    private String getRandomTextureForEntity(Entity entity) {
        Random rand = new Random();
        // You can customize this logic to choose between recyclable and non-recyclable textures based on the entity type
        // For simplicity, this example randomly picks from either array
        if (entity instanceof Items) { // Assuming 'Items' is your specific Entity subclass that can be recyclable or not
            Items item = (Items)entity;
            if (item.isRecyclable()) { // Assuming there's a method to check if an item is recyclable
                return recyclableTextures[rand.nextInt(recyclableTextures.length)];
            } else {
                return nonRecyclableTextures[rand.nextInt(nonRecyclableTextures.length)];
            }
        }
        // Default or fallback texture path
        return "speed.png"; // Ensure this is a valid path or handle this case appropriately
    }
    
    public void setai(Entity ai) {
    	aiPosition = new Vector2(ai.getxPos(), ai.getyPos());
    }
    public Vector2 getai() {
    	return aiPosition;
    }
    
    public void setPlayer(Entity player) {
    	this.playerPosition = new Vector2(player.getxPos(), player.getyPos());
    }
    public Vector2 getPlayer() {
    	return playerPosition;
    }
    
    public void chase(Entity ai, Entity player, int speed) {
    	setVectors(ai, player);
    	float deltax = playerPosition.x - aiPosition.x;
        float deltay = playerPosition.y - aiPosition.y;
        
        float distance = (float) Math.sqrt(deltax * deltax + deltay * deltay);

        if (distance > 0) {
        	float unixX =  deltax/distance;
        	float unixY =  deltay/distance;
            float velocityX = aiPosition.x + unixX * speed;
            float velocityY = aiPosition.y + unixY * speed;
            
        	float deltaTime = Gdx.graphics.getDeltaTime();
            ai.setxPos(velocityX += unixX * deltaTime);
            ai.setyPos(velocityY += unixY * deltaTime);
        }
    }
    
    public void setVectors(Entity ai, Entity player) {
    	this.aiPosition = new Vector2(ai.getxPos(), ai.getyPos());
    	this.playerPosition = new Vector2(player.getxPos(), player.getyPos());
    }
    
    public Vector2 getRandomPosition() {
        float x = random.nextFloat() * Gdx.graphics.getWidth();
        float y = random.nextFloat() * Gdx.graphics.getHeight();
        return new Vector2(x, y);
    }
    
}
