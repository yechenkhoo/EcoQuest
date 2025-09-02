package com.oop.gamelayer.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Scaling;
import com.oop.gameengine.Entities.Player;
import com.oop.gameengine.Managers.EntityManagement;
import com.oop.gameengine.Managers.SimulationLifeCycleManagement;
import com.oop.gameengine.Scenes.BaseScene;

public class GameOver_1 extends BaseScene {

	private Image backgroundImage;
	private Skin skin;
	private EntityManagement em;
	private Player player;
	private BitmapFont font;
	private Label scoreLabel;

	public GameOver_1(SimulationLifeCycleManagement game) {
		super(game);
		this.em = game.getEntityManager();
		this.player = game.getPlayerManagement().getPlayers().get(0);
		
		initUI();
	}

	@Override
	protected void initUI() {
		// add background music
		game.getInputOutputManagement().startSound("gameover");

		// set background = = =
		backgroundImage = new Image(new Texture(Gdx.files.internal("background/gameover_bg.png")));
		backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.addActor(backgroundImage);

		loadMenu();

	}

	@Override
	public void show() {
		super.show();
		initUI();
	}
	
	// load the table for gameover screen
	public void loadMenu() {
		// setup table + skin settings = = =
		Table root = new Table();
		root.setFillParent(true);
		stage.addActor(root);
		skin = new Skin(Gdx.files.internal("skin/custom.json"));

		// load title = = =
		Texture titleTexture = new Texture(Gdx.files.internal("background/lose_title.png"));

		// set image max height + width
		float originalWidth = titleTexture.getWidth();
		float originalHeight = titleTexture.getHeight();
		float aspectRatio = originalHeight / originalWidth;
		float width = (float) (Gdx.graphics.getWidth() * 0.8);
		float height = width * aspectRatio;

		// set title and add to the container
		Image title = new Image(titleTexture);
		title.setScaling(Scaling.fit);
		// add to container to add to table
		Container<Image> container = new Container<>(title);
		container.maxWidth(width); // Set maximum width constraint, replace maxWidth with your desired maximum
									// width
		root.add(container).padBottom(10).height(height);
		
		root.row();
		// add score text
		font = new BitmapFont();
		Label.LabelStyle timerLabelStyle = new Label.LabelStyle(); 
        timerLabelStyle.font = font; 
        timerLabelStyle.fontColor = Color.BLACK; // Set the color to white for visibility 
        
        scoreLabel = new Label("", timerLabelStyle);
        System.out.println("PLAYER SCORE: "+ player.getScore());
        scoreLabel.setText(String.format("Your score is: %02d", (int)player.getScore()));
        root.add(scoreLabel).padBottom(10);

        // add play button
		root.row();
		TextButton play = new TextButton("Replay", skin);
		root.add(play).padBottom(10);
		play.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.getSceneManager().removeScene("PlayScene");
				em.clearEntities();
				player.resetScore();
				// Create a new instance of PlayScene_1
				PlayScene_1 newPlayScene = new PlayScene_1(game);

				// Add the new instance to the scene manager
				game.getSceneManager().addScene("PlayScene", newPlayScene);
				// Set the new PlayScene_1 as the current scene
				game.getSceneManager().setScene("PlayScene");

				System.out.println("Game reset and transitioning to PlayScene_1.");
			}
		});

		root.row();
		TextButton menu = new TextButton("Main Menu", skin);
		root.add(menu).padBottom(10);
		menu.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				em.clearEntities();
				player.resetScore();
				
				game.getSceneManager().setScene("Menu");

				System.out.println("Going back to main menu.");
			}
		});
		
		root.row();
		TextButton exit = new TextButton("End Game", skin);
		root.add(exit).padBottom(10);
		exit.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Close button clicked");
				Gdx.app.exit();
			}
		});
	}
}
