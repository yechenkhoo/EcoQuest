package com.oop.gameengine.Managers;

import com.oop.gameengine.Entities.Entity;
import com.oop.gameengine.Interfaces.iCollidable;

import java.util.ArrayList;
import java.util.List;

public class CollisionManagement {
	// Flag to track if collision has been detected
    protected boolean collisionDetected = false; 

    public boolean checkCollisions(List<Entity> entities) {
        // Clear the flag at the start of each collision check
        collisionDetected = false;

        List<Entity> entitiesToRemove = new ArrayList<>();

        for (Entity entity1 : entities) {
            for (Entity entity2 : entities) {
                if (entity1 != entity2 && entity1 instanceof iCollidable && entity2 instanceof iCollidable) {
                    if (((iCollidable) entity1).checkCollision(entity2)) {
                        handleCollision(entity1, entity2, entitiesToRemove);
                        collisionDetected = true;
                    }
                }
            }
        }

        // Remove entities that need to be removed
        entities.removeAll(entitiesToRemove);
        return collisionDetected;
    }

    protected void handleCollision(Entity entity1, Entity entity2, List<Entity> entitiesToRemove) {
        // To be implemented in subclasses
    }
}
