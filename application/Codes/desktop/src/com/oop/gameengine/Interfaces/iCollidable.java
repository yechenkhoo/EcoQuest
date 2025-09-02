package com.oop.gameengine.Interfaces;

import com.oop.gameengine.Entities.Entity;

public interface iCollidable {
    public boolean checkCollision(Entity other);
}