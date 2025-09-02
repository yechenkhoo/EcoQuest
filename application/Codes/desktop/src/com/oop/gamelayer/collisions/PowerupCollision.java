package com.oop.gamelayer.collisions;

import com.oop.gameengine.Entities.Entity;
import com.oop.gameengine.Managers.CollisionManagement;
import com.oop.gamelayer.entities.Bin;
import com.oop.gamelayer.entities.PowerUps;

import java.util.List;

public class PowerupCollision extends CollisionManagement {

    @Override
    protected void handleCollision(Entity bin, Entity powerup, List<Entity> entitiesToRemove) {
        if (bin instanceof Bin && powerup instanceof PowerUps) {
            PowerUps powerUp = (PowerUps) powerup;
            Bin recycleBin = (Bin) bin;
            if (powerUp.getPowerupType() == PowerUps.PowerupType.SPEED_UP) {
                // Increase the speed of the bin by 100 (adjust as needed)
             
            	recycleBin.increaseSpeed(500); // Adjust speed increase as needed
                entitiesToRemove.add(powerup); // Remove the power-up from the game
            }
        }
    }
}
