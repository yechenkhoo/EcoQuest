package com.oop.gamelayer.collisions;

import com.oop.gameengine.Entities.Entity;
import com.oop.gameengine.Managers.CollisionManagement;
import com.oop.gamelayer.entities.Bin;
import com.oop.gamelayer.entities.Enemy;

import java.util.List;

public class PlaneEnemyCollision extends CollisionManagement {

    @Override
    protected void handleCollision(Entity plane, Entity enemy, List<Entity> entitiesToRemove) {

    	if (plane instanceof Bin && enemy instanceof Enemy) {
            entitiesToRemove.add(enemy);
            plane.setScore(enemy.getScore());
        }
    }
}
