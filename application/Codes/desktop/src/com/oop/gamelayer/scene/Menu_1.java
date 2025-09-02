package com.oop.gamelayer.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Scaling;
import com.oop.gameengine.Managers.EntityManagement;
import com.oop.gameengine.Managers.SimulationLifeCycleManagement;
import com.oop.gameengine.Scenes.BaseScene;

public class Menu_1 extends BaseScene{

    private Image backgroundImage;
    private Skin skin;
    private EntityManagement em;

    public Menu_1(SimulationLifeCycleManagement game) {
        super(game);
        this.em = game.getEntityManager();
    }

    @Override
    protected void initUI() {
    	System.out.println("menu scene loaded");
        // add background music
        game.getInputOutputManagement().startSound("menu", 0.3f);

    	// set background = = =
        backgroundImage = new Image(new Texture(Gdx.files.internal("background/camp2.jpg")));
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        // setup table + skin settings = = =
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        skin = new Skin(Gdx.files.internal("skin/custom.json"));

        // load title = = =
        Texture titleTexture = new Texture(Gdx.files.internal("background/game_title.png"));

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
        container.maxWidth(width); // Set maximum width constraint, replace maxWidth with your desired maximum width
        root.add(container).padBottom(20).height(height);

        root.row();
        TextButton play = new TextButton("Start Game", skin);
        root.add(play).padBottom(10);
        play.addCaptureListener(new ChangeListener() {
        	@Override
        	public void changed(ChangeEvent event, Actor actor) {
        		em.clearEntities();
        		game.getInputOutputManagement().stopSound("menu");
        		
        		// load PlayScene items = = =
        		game.getSceneManager().removeScene("PlayScene");
                PlayScene_1 newPlayScene = new PlayScene_1(game); // Create a new instance of PlayScene_1
                game.getSceneManager().addScene("PlayScene", newPlayScene); // Add the new instance to the scene manager
                game.getSceneManager().setScene("PlayScene"); // Set the new PlayScene_1 as the current scene
                
                System.out.println("Game reset and transitioning to PlayScene_1.");
            }
        });
        
        root.row();
        TextButton instructions = new TextButton("Instructions", skin);
        root.add(instructions).padBottom(10);
        instructions.addCaptureListener(new ChangeListener() {
        	@Override
        	public void changed(ChangeEvent event, Actor actor) {
        		em.clearEntities();
                // Set the new PlayScene_1 as the current scene
                game.getSceneManager().setScene("Instructions");
                
                System.out.println("Game reset and transitioning to PlayScene_1.");
            }
        });
        
        root.row();
        TextButton exit = new TextButton("End Game", skin);
        root.add(exit).padBottom(10);
        exit.addCaptureListener(new ChangeListener() {
        	@Override
        	public void changed (ChangeEvent event, Actor actor) {
        		System.out.println("Close button clicked");
        		Gdx.app.exit();
        	}
        });
        
    }

    @Override
    public void show() {
        super.show();
        initUI();
    }
}
