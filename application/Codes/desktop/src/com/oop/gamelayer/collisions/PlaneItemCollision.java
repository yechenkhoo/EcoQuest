package com.oop.gamelayer.collisions;

import com.oop.gameengine.Entities.Entity;
import com.oop.gameengine.Managers.CollisionManagement;
import com.oop.gamelayer.entities.Bin;
import com.oop.gamelayer.entities.Items;

import java.util.List;


public class PlaneItemCollision extends CollisionManagement {
	
	@Override
    protected void handleCollision(Entity entity1, Entity entity2, List<Entity> entitiesToRemove) {
        if (entity1 instanceof Bin && entity2 instanceof Items) {
            // If the item collides with the bin, remove only the item
            entitiesToRemove.add(entity2);
            entity1.setScore(entity2.getScore());
        }
	}

}
