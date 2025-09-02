package com.oop.gamelayer.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.oop.gameengine.Managers.*;
import com.oop.gameengine.Entities.*;
import com.oop.gameengine.Scenes.BaseScene;
import com.oop.gamelayer.collisions.BinItemCollision;
import com.oop.gamelayer.collisions.PowerupCollision;
import com.oop.gamelayer.entities.*;

import java.util.List;
import java.util.Random;

public class PlayScene_1 extends BaseScene{
	// check if there is a need for batch, shape? because Stage already has batch
	private SpriteBatch batch;
    private EntityManagement em;
    private SceneManagement sceneManagement;
    private AIControlManagement aiControlManagement;
    private final int NumberofItems = 6;
    private final int NumberofPowerUps = 2; 
    private Entity[] items = new Entity[NumberofItems];
    private Entity bin;
    private Player player;
    private BitmapFont font;
    private Animation<TextureRegion> backgroundAnimation;
    private float animationTime = 0;
    protected EntityManagement entityManager;
    private BinItemCollision binItemCollision;
    private PowerupCollision powerupCollision;
    boolean paused = false;
    final Window pauseMenu;
    TextButton continueBtn, exitBtn;
    private Skin skin;
    private Label timerLabel, scoreLabel;
    private long startTime;

    public PlayScene_1(SimulationLifeCycleManagement game) {
        super(game);
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.em = game.getEntityManager();
        this.aiControlManagement = game.getAiControlManagement();
        this.sceneManagement = new SceneManagement(game);
        // pause menu = = =
        this.skin = new Skin(Gdx.files.internal("skin/custom.json"));
		this.pauseMenu = new Window("", skin);
		this.player = game.getPlayerManagement().getPlayers().get(0);

		// collision items = = =
		binItemCollision = new BinItemCollision();
	    powerupCollision = new PowerupCollision();
        entityManager = new EntityManagement();
        
        // load timer = = =
        this.startTime = System.currentTimeMillis(); // Initialize startTime
        
        initializeBackgroundAnimation();
        initUI(); // Initialize UI elements
    }

    private void initializeBackgroundAnimation() {
        Array<TextureRegion> frames = new Array<>();
        int frameCount = 4; // Adjust based on your number of frames
        for (int i = 1; i <= frameCount; i++) {
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("frame" + i + ".png"))));
        }
        backgroundAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
    }
    
    public void resetGameState() {
        em.clearEntities();
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void show() {
        super.show(); // Call to BaseScene's show method
        Random random = new Random();

        // Array of possible item textures
        String[] recyclableTextures = { "plastic.png", "can1.png", "glass.png", "plastic_bottle.png", "paper.png" };
        String[] nonRecyclableTextures = { "burger1.png", "drink.png", "banana.png", "trash_bag.png", "pizza.png"};
        String powerupTexture = "speed.png";
        
        // Ensure at least 3 recyclable items
        int guaranteedRecyclableItems = 3;

        // Initialize entities like items and bin
        for (int i = 0; i < NumberofItems; i++) {
            int xPos = random.nextInt(600);
            int speed = random.nextInt(5) + 2;

            // Decide if the item is recyclable or not
            boolean isRecyclable = i < guaranteedRecyclableItems; // First three items are guaranteed to be recyclable
            String itemTexture;
            if (isRecyclable) { // This condition ensures the first three items are recyclable
                itemTexture = recyclableTextures[random.nextInt(recyclableTextures.length)];
            } else { // For the rest, decide randomly
                if (random.nextBoolean()) {
                    itemTexture = recyclableTextures[random.nextInt(recyclableTextures.length)];
                } else {
                    itemTexture = nonRecyclableTextures[random.nextInt(nonRecyclableTextures.length)];
                    isRecyclable = false;
                }
            }

            items[i] = new Items(itemTexture, xPos, 0, speed, true, isRecyclable, 1, "PlayScene1");
            if (isRecyclable) {
                items[i].setScore(5); // each recyclable item + 5 points
            } else {
                items[i].setScore(-5); // non-recyclable item -5 points
            }

            em.createEntity(items[i]);
        }
        
        // Create PowerUps objects
        for (int i = 0; i < NumberofPowerUps; i++) {
            int xPos = random.nextInt(600);
            int speed = random.nextInt(5) + 2;
            PowerUps powerUp = new PowerUps(powerupTexture, xPos, 0, speed, true, PowerUps.PowerupType.SPEED_UP, "PlayScene1");
            em.createEntity(powerUp);
        }
        
        // player user management
        bin = new Bin("bin.png", 0, 10, 400, false, "PlayScene1");
        // playerControlManagement onto bin and recyclebin
        player.setDetails(bin, "horizontal"); // default score: 0
        em.createEntity(bin);
        
        // Inside show() method after existing code 
        Table statusTable = new Table(skin);
        statusTable.bottom().pad(10).right();
        statusTable.setFillParent(true);
        
        Label.LabelStyle timerLabelStyle = new Label.LabelStyle(); 
        timerLabelStyle.font = font; 
        timerLabelStyle.fontColor = Color.BLACK; // Set the color to white for visibility 
 
        timerLabel = new Label("", timerLabelStyle); 
        timerLabel.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 50); // Adjust the Y position based on your UI 
        // add score and time table at bottom right

        scoreLabel = new Label("", timerLabelStyle);
        statusTable.add(timerLabel);
        statusTable.row();
        statusTable.add(scoreLabel);
        
        stage.addActor(statusTable); // Assuming you have a stage set up for UI elements
    }

    @Override
    protected void initUI() {
    	// LOAD SOUNDS = = =
    	// game.getInputOutputManagement().startSound("game");
    	
    	font.getData().setScale(2); // Increase the scale for bigger text. Adjust as needed.

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK; // Ensure text color is white for visibility

        // Assuming your stage is set up to use screen coordinates
        Label label = new Label("Level 1", labelStyle);
        label.setPosition((Gdx.graphics.getWidth() - label.getWidth()) / 2, Gdx.graphics.getHeight() - label.getHeight() - 20); // Adjust Y for top margin
        stage.addActor(label);
        
        // add items to pause window
    	continueBtn = new TextButton("Continue Game", skin);
    	exitBtn = new TextButton("Main Menu", skin);
    	
    	pauseMenu.padTop(64); // set by width of window required ??
    	pauseMenu.add(continueBtn).padBottom(10).row();
    	pauseMenu.add(exitBtn).padBottom(10).row();
    	pauseMenu.setSize(stage.getWidth() / 1.5f, stage.getHeight() / 1.5f);
    	pauseMenu.setPosition((stage.getWidth() / 2) - (pauseMenu.getWidth() / 2), (stage.getHeight() / 2) - (pauseMenu.getHeight() / 2));
    	pauseMenu.setMovable(false);
        
    }

    @Override
    public void render(float delta) {
    	if (paused) {
    		// load pause menu
    		if (runPauseMenu()) { // runPauseMenu returns true if esc is pressed again, else is false
    			paused = false;
    			// because libgdx works very fast, so need the pause for it to render ??
    			try {
    				Thread.sleep(100);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
    	} else {
    		game.getInputOutputManagement().startSound("game");
    		// do general update
    		generalUpdate();
    	}
    	
    	// others = = =
        animationTime += delta;
        TextureRegion currentFrame = backgroundAnimation.getKeyFrame(animationTime);
        super.stage.act(delta);

        batch.begin();
        batch.draw(currentFrame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Entity drawing logic
        for (Entity entity : em.getEntityList()) {
            entity.draw(batch);
        }
        
        // TRYING CUSTOM TEXT
//        String testString = "Level 1";
//        GlyphLayout stringLayout = new GlyphLayout(font, testString);
//        font.draw(batch, testString, Gdx.graphics.getWidth() / 2 - stringLayout.width / 2, Gdx.graphics.getHeight() - 10);
        /// - - -
        
        batch.end();
    	
        em.update();
        super.stage.draw();

        
        // Additional logic from PlayScreen can be incorporated here as needed
        sceneManagement.prevent_out(bin);
        
        long elapsed = System.currentTimeMillis() - startTime; 
        long remaining = 90000 - elapsed; // 3 minutes - elapsed time in milliseconds 
     // First, check if time has run out
        if (remaining <= 0) {
            em.clearEntities();
            game.getInputOutputManagement().stopSound("game");
            // Time is up; transition directly to GameOver scene
            game.setScreen(new GameOver_1(game));
        } else {
            // If time has not run out, then check if score is >= 100
            if (bin.getScore() >= 20) {
                // Score condition met, update score (if needed) and transition to PlayScene_2
                player.addScore((int)bin.getScore()); // This line is optional based on whether you want to add more score here
                em.clearEntities();
                game.setScreen(new PlayScene_2(game));
            } else if (bin.getScore() <= -20){
            	game.getInputOutputManagement().stopSound("game");
				game.setScreen(new GameOver_1(game));
			} else {
                // If the score is less than 100, game continues, so update the timer display
                int minutes = (int)(remaining / 60000);
                int seconds = (int)(remaining % 60000 / 1000);
                timerLabel.setText(String.format("%02d:%02d", minutes, seconds)); // Update the timer label
            }
        }


    	scoreLabel.setText(String.format("Current score: %02d", (int)bin.getScore()));
         
        // Make sure to update and draw your stage if you haven't already 
        // For example, if using Scene2D.ui Stage for UI elements: 
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); 
        super.stage.draw();
    }
    
    public boolean runPauseMenu() {
    	game.getInputOutputManagement().pauseSound("game"); // pause
    	boolean status = false;
    	stage.addActor(pauseMenu);
    	pauseMenu.setVisible(true);
    	
    	continueBtn.addListener(new ClickListener() {
        	@Override
        	public void clicked (InputEvent event, float x, float y) {
        		paused = false;
        		pauseMenu.remove();
        	}
        });
    	exitBtn.addCaptureListener(new ChangeListener() {
        	@Override
        	public void changed (ChangeEvent event, Actor actor) {
        		System.out.println("Close button clicked");
        		game.setScreen(new Menu_1(game));
        	}
        });
    	
    	if (game.getInputOutputManagement().getKeyboardManager().isEscapeKeyPressed()) {
    		// get into pause screen
    		pauseMenu.remove();
    		status = true;
    	}
    	return status;
    }
    
    public void generalUpdate() {
    	// check if escapekey is pressed
    	if (game.getInputOutputManagement().getKeyboardManager().isEscapeKeyPressed()) {
    		paused = true;
    		// because libgdx works very fast, so need the pause for it to render ??
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	
    	// IO sound play
    	game.getInputOutputManagement().playOnPress();
    	
    	ScreenUtils.clear(0, 0, 0, 1); // Clear the screen with a black color
        List<Entity> entities = em.getEntityList();
        
        powerupCollision.checkCollisions(entities);
        if (binItemCollision.checkCollisions(entities)) {
        	game.getInputOutputManagement().startSound("collected");
        }
        
        
        aiControlManagement.AIMovements();
        player.userMovement();
    }
    
    @Override
    public void dispose() {
        // Dispose of the SpriteBatch
        if (batch != null) {
            batch.dispose();
        }

        // Dispose of the BitmapFont
        if (font != null) {
            font.dispose();
        }

        // Note: Stage (if used) also needs to be disposed
        if (stage != null) {
            stage.dispose();
        }

        // If using skins, remember to dispose of them too
        if (skin != null) {
            skin.dispose();
        }

        // Dispose other disposable resources here...

        // Call super.dispose() if it's not an empty implementation or specifically required
        // super.dispose();
    }
    
}
