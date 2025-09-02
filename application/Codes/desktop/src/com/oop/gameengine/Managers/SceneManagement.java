package com.oop.gameengine.Managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import com.oop.gameengine.Entities.Entity;

import java.util.HashMap;

public class SceneManagement {

    private final HashMap<String, Screen> scenes;
    private final Game game;

    public SceneManagement(Game game) {
        this.scenes = new HashMap<>();
        this.game = game;
    }

    public void addScene(String name, Screen scene) {
        scenes.put(name, scene);
    }

    public void removeScene(String name) {
        // Dispose of the scene when removing it
        Screen removedScene = scenes.remove(name);
        if (removedScene != null) {
            removedScene.dispose();
        }
    }

    public void setScene(String name) {
        Screen scene = scenes.get(name);
        if (scene != null) {
            game.setScreen(scene);
        } else {
            System.out.println("Scene not found: " + name);
        }
    }

    public Screen getScene(String name) {
        return scenes.get(name);
    }

    // Optional: Dispose of all scenes when the game is closing or you need to clean up resources
    public void dispose() {
        for (Screen screen : scenes.values()) {
            if (screen != null) {
                screen.dispose();
            }
        }
        scenes.clear();
    }
    public void prevent_out(Entity entity){
        //Prevent Entity from getting out of screen max width
        if(entity.getxPos() >= Gdx.graphics.getWidth() - entity.getTexture().getWidth()){
            entity.setxPos(Gdx.graphics.getWidth() - entity.getTexture().getWidth());
        }
        if(entity.getxPos() <= 0){
            entity.setxPos(0);
        }
        //Prevent Entity from getting out of screen max height
        if(entity.getyPos() >= Gdx.graphics.getHeight() - entity.getTexture().getHeight()){
            entity.setyPos(Gdx.graphics.getHeight() - entity.getTexture().getHeight());
        }
        if(entity.getyPos() <= 0){
            entity.setyPos(0);
        }
    }
    
    private Screen currentScene; // This holds the reference to the current scene

    public Screen getCurrentScene() {
        return currentScene;
    }
}
