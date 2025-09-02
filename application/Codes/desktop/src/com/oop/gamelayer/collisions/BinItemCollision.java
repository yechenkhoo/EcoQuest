package com.oop.gamelayer.collisions;

import com.oop.gameengine.Entities.Entity;
import com.oop.gameengine.Managers.CollisionManagement;
import com.oop.gamelayer.entities.Bin;
import com.oop.gamelayer.entities.Items;
import com.badlogic.gdx.Gdx;

import java.util.List;
import java.util.Random;

public class BinItemCollision extends CollisionManagement {
    private Random random = new Random();

    @Override
    protected void handleCollision(Entity bin, Entity item, List<Entity> entitiesToRemove) {
        if (bin instanceof Bin && item instanceof Items) {
            // Generate a new random X position within the screen width
            float newXPos = random.nextFloat() * Gdx.graphics.getWidth();
            // Reset the Y position to reappear at the top of the screen
            float newYPos = Gdx.graphics.getHeight();

            // Update the item's position without removing it from the game
            ((Items) item).setxPos(newXPos);
            ((Items) item).setyPos(newYPos);

            // Update the bin's score with the item's score
            bin.setScore(((Items) item).getScore());
            System.out.println("BIN current score "+ bin.getScore());
            System.out.println("ITEM score " + item.getScore());
        }
    }
}
