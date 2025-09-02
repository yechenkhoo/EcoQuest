package com.oop.gamelayer.scene;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.oop.gameengine.Managers.*;
import com.oop.gameengine.Scenes.BaseScene;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Instructions extends BaseScene {
	private ArrayList<Image> slides;
	private Image currentpage;
	private Skin skin;
	private int count = 0, instructionCount = 8;
	private Texture[] backgroundImages = new Texture[instructionCount];
	private Table root;
	private TextButton backMenu, play;

	public Instructions(SimulationLifeCycleManagement game) {
		super(game);
		slides = new ArrayList<Image>();
		skin = new Skin(Gdx.files.internal("skin/custom.json")); // Load the skin
		for (int i = 0; i < instructionCount; i++) {
            backgroundImages[i] = new Texture(Gdx.files.internal(String.format("background/instructions/slide%d.png", i)));
        }
        
        // Initialize first slide as current page
        currentpage = new Image(backgroundImages[0]);
		
        createTable();
        initUI();
	}

	@Override
	protected void initUI() {
		System.out.println("Instructions scene loaded");

		// stage
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        
        // Add current page to the stage
        stage.addActor(currentpage);
        createTable(); // This should just set up the table, not add actors
        stage.addActor(root);
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void render(float delta) {
		if (count == 0) {
			root.add(backMenu).padBottom(30);
		} else if (count == instructionCount - 1) {
			root.add(play).padBottom(30);
		} else {
			root.clear();
		}
		
//		exit.setVisible(count == 0);
//		play.setVisible(count == backgroundImages.length - 1);

		if (Gdx.input.isKeyJustPressed(Keys.LEFT) && count > 0) {
            count -= 1;
        } else if (Gdx.input.isKeyJustPressed(Keys.RIGHT) && count < instructionCount - 1) {
            count += 1;
        }
		
		currentpage.setDrawable(new TextureRegionDrawable(new TextureRegion(backgroundImages[count])));
		
		stage.act(delta);
		stage.draw();
	}

	public void renderBackground() {
		for (int i = 0; i < backgroundImages.length; i++) {
			backgroundImages[i] = new Texture(
					Gdx.files.internal(String.format("background/instructions/slide%d.png", i)));
		}

		currentpage = new Image(backgroundImages[0]);
	}
	
    public void createTable() {
        // Set up the table, but do not add actors here. This should only set up the layout
        root = new Table();
        root.setFillParent(true);
        root.bottom();
        
        backMenu = new TextButton("Back to Menu", skin);
		backMenu.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.getSceneManager().setScene("Menu");
			}
		});
		
		play = new TextButton("Play Game!", skin);
		play.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.getSceneManager().removeScene("PlayScene");
				game.getInputOutputManagement().stopSound("menu");

				// Create a new instance of PlayScene_1
				PlayScene_1 newPlayScene = new PlayScene_1(game);

				// Add the new instance to the scene manager
				game.getSceneManager().addScene("PlayScene", newPlayScene);

				// Set the new PlayScene_1 as the current scene
				game.getSceneManager().setScene("PlayScene");

				System.out.println("Game reset and transitioning to PlayScene_1.");
			}
		});
        
    }

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true); // Update viewport size
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}