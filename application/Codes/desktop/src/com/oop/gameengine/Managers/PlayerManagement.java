package com.oop.gameengine.Managers;

import java.util.ArrayList;
import com.badlogic.gdx.Input.Keys;
import com.oop.gameengine.Entities.Player;
import com.oop.gameengine.InputOutput.InputKeyboard;


public class PlayerManagement {

    protected Player player1, player2;
    protected ArrayList<Player> playerList;
    protected final SimulationLifeCycleManagement game;
    protected InputKeyboard keyboardManager;
    
    public PlayerManagement(SimulationLifeCycleManagement game){
    	this.game = game;
    	this.keyboardManager = game.getInputOutputManagement().getKeyboardManager();
    	this.playerList = new ArrayList<Player> ();
    	setPlayers();
	}
    
    public ArrayList<Player> getPlayers() {
    	return playerList;
    }
    
    public void addPlayer(Player player) {
    	playerList.add(player);
    }
    
    public void removePlayer(Player player) {
    	playerList.remove(player);
    }

    private void setPlayers() {
    	player1 = new Player(Keys.W, Keys.S, Keys.A, Keys.D, this.keyboardManager);
    	player2 = new Player(Keys.UP, Keys.DOWN, Keys.LEFT, Keys.RIGHT, this.keyboardManager);
    }
    
	public Player getPlayer1() {
    	return player1;
    }
    
    public Player getPlayer2() {
    	return player2;
    }
}
