package com.oop.gamelayer.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.oop.gameengine.Managers.*;
import com.oop.gameengine.Entities.*;
import com.oop.gameengine.Scenes.BaseScene;
import com.oop.gamelayer.collisions.*;
import com.oop.gamelayer.entities.*;

import java.util.List;
import java.util.Random;

public class PlayScene_2 extends BaseScene {

	private EntityManagement em;
	private SceneManagement sceneManagement;
	private AIControlManagement aiControlManagement;
	private final int NumberofPowerUps = 1;

	// entities = = =
	private Entity bin, enemy;
	private Player player;

	// background
	private Animation<TextureRegion> backgroundAnimation;
	private float animationTime = 0;

	// collision
	private PlaneItemCollision planeItemCollision;
	private PowerupCollision powerupCollision;
	private PlaneEnemyCollision planeEnemyCollision;

	// pause window
	private boolean paused = false;
	private final Window pauseMenu;
	private TextButton continueBtn, exitBtn;
	private Skin skin;
	private Label timerLabel, scoreLabel;
	private long startTime;

	private Random random;
    private String[] recyclableTextures = {"plastic.png", "can2.png", "glass.png", "cardboard.png", "metal.png"};
    private String[] nonRecyclableTextures = {"drink2.png", "donut.png", "sushi.png", "pancake.png"};
    private String powerupTexture = "speed.png";
    
    private Timer.Task spawnTask;

	public PlayScene_2(SimulationLifeCycleManagement game) {
		super(game);
		this.em = game.getEntityManager();
		this.aiControlManagement = game.getAiControlManagement();
		this.sceneManagement = new SceneManagement(game);

		this.player = game.getPlayerManagement().getPlayers().get(0);

		// pause menu = = =
		this.skin = new Skin(Gdx.files.internal("skin/custom.json"));
		this.pauseMenu = new Window("", skin);

		this.random = new Random();

		// collision items = = =
		planeItemCollision = new PlaneItemCollision();
		powerupCollision = new PowerupCollision();
		planeEnemyCollision = new PlaneEnemyCollision();

		// load timer = = =
		this.startTime = System.currentTimeMillis(); // Initialize startTime

		initializeBackgroundAnimation();
		initUI(); // Initialize UI elements
	}

	/* load background animation */
	private void initializeBackgroundAnimation() {
		Array<TextureRegion> frames = new Array<>();
		int frameCount = 4; // Adjust based on your number of frames
		for (int i = 1; i <= frameCount; i++) {
			frames.add(new TextureRegion(new Texture(Gdx.files.internal("blue" + i + ".png"))));
		}
		backgroundAnimation = new Animation<>(0.5f, frames, Animation.PlayMode.LOOP);
	}

	public void resetGameState() {
		em.clearEntities();
		this.startTime = System.currentTimeMillis();
	}

	public void spawnMovement() {
		spawnTask = Timer.schedule(new Task() {
			@Override
			public void run() {
				// Clear previous items before spawning new ones
				em.clearItemsOfType(Items.class); // Implement this method in EntityManager to remove specific type of entities

				// Spawn 5 new items
				for (int i = 0; i < 5; i++) {
					spawnItem();
				}
			}
		}, 0, 2); // Initial delay of 0, repeat every 2 seconds
	}

	private void spawnItem() {
		// Randomly select an item texture
		String itemTexture = "";
		boolean isRecyclable = random.nextBoolean();
		if (isRecyclable) {
			itemTexture = recyclableTextures[random.nextInt(recyclableTextures.length)];
		} else {
			itemTexture = nonRecyclableTextures[random.nextInt(nonRecyclableTextures.length)];
		}

		Vector2 randomPosition = aiControlManagement.getRandomPosition(); // Assuming aiControlManagement is accessible here

		// Use the randomPosition for setting the position of the item
		Items newItem = new Items(itemTexture, randomPosition.x, randomPosition.y, 1, false, isRecyclable, 0,
				"PlayScene2");

		int score = (isRecyclable) ? 5 : -5;
		newItem.setScore(score);
		em.createEntity(newItem);
	}

	private void spawnEnemy() {
		// Get random position within the game screen
		Vector2 randomPosition = aiControlManagement.getRandomPosition();

		// Spawn a new enemy at the random position
		enemy = new Enemy("fire.png", randomPosition.x, randomPosition.y, 100, true, "PlayScene2");
		int score2 = -5;
		enemy.setScore(score2);
		em.createEntity(enemy);

		aiControlManagement.chase(enemy, bin, 2); // Make the enemy start chasing the bin immediately
	}

	@Override
	public void hide() {
		super.hide();
		if (spawnTask != null) {
			spawnTask.cancel();
		}
		// Perform any other cleanup or state saving necessary for this scene.
	}

	@Override
	public void show() {
		super.show(); // Call to BaseScene's show method
		Random random = new Random();

		spawnMovement();

		// Create PowerUps objects
		for (int i = 0; i < NumberofPowerUps; i++) {
			int xPos = random.nextInt(600);
			int speed = random.nextInt(5) + 2;
			PowerUps powerUp = new PowerUps(powerupTexture, xPos, 0, speed, true, PowerUps.PowerupType.SPEED_UP,
					"PlayScene2");
			em.createEntity(powerUp);
		}

		// player user management
		bin = new Bin("plane.png", 0, 0, 400, false, "PlayScene2");
		player.setDetails(bin, "");

		em.createEntity(bin);
		enemy = new Enemy("fire.png", 200, 200, 100, true, "PlayScene2");
		int score2 = -5;
		enemy.setScore(score2);
		em.createEntity(enemy);

		// Inside show() method after existing code
		Table statusTable = new Table(skin);
		statusTable.bottom().pad(10).right();
		statusTable.setFillParent(true);

		Label.LabelStyle timerLabelStyle = new Label.LabelStyle();
		timerLabelStyle.font = font;
		timerLabelStyle.fontColor = Color.BLACK; // Set the color to white for visibility

		timerLabel = new Label("", timerLabelStyle);
		timerLabel.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 50); 
		// add score and time table at bottom right

		scoreLabel = new Label("", timerLabelStyle);
		statusTable.add(timerLabel);
		statusTable.row();
		statusTable.add(scoreLabel);

		stage.addActor(statusTable); // Assuming you have a stage set up for UI elements
	}

	@Override
	protected void initUI() {
		font.getData().setScale(2); // Increase the scale for bigger text. Adjust as needed.

		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = font;
		labelStyle.fontColor = Color.BLACK; // Ensure text color is white for visibility

		Label label = new Label("Level 2", labelStyle);
		label.setPosition((Gdx.graphics.getWidth() - label.getWidth()) / 2, Gdx.graphics.getHeight() - label.getHeight() - 20); // Adjust Y for top margin
		stage.addActor(label);

		loadPauseWindow();
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
			// do general update for different events
			game.getInputOutputManagement().startSound("game");
			generalUpdate();
		}

		// others = = =
		animationTime += delta;
		TextureRegion currentFrame = backgroundAnimation.getKeyFrame(animationTime);
		super.stage.act(delta);

		this.batch.begin();
		// Entity drawing logic
		this.batch.draw(currentFrame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		for (Entity entity : em.getEntityList()) {
			entity.draw(batch);
		}
		this.batch.end();

		em.update();
		super.stage.draw();

		// Additional logic from PlayScreen can be incorporated here as needed
		sceneManagement.prevent_out(bin);

		long elapsed = System.currentTimeMillis() - startTime;
		long remaining = 90000 - elapsed; // 3 minutes - elapsed time in milliseconds
		if (remaining <= 0) {
			em.clearEntities();
			game.getInputOutputManagement().stopSound("game");
			// Time is up; transition directly to GameOver scene
			player.addScore((int) bin.getScore());
			game.setScreen(new GameOver_1(game));
		} else {
			// If time has not run out, then check if score is >= 100
			if (bin.getScore() >= 20) {
				// Score condition met, update score (if needed) and transition to PlayScene_2
				player.addScore((int) bin.getScore()); // This line is optional based on whether you want to add more
														// score here
				em.clearEntities();
				game.getInputOutputManagement().stopSound("game");
				game.setScreen(new Win(game));
			} else if (bin.getScore() <= -20){
				game.getInputOutputManagement().stopSound("game");
				game.setScreen(new GameOver_1(game));
			} else {
				// If the score is less than 100, game continues, so update the timer display
				int minutes = (int) (remaining / 60000);
				int seconds = (int) (remaining % 60000 / 1000);
				timerLabel.setText(String.format("%02d:%02d", minutes, seconds)); // Update the timer label
			}
		}
		scoreLabel.setText(String.format("Current score: %02d", (int) bin.getScore()));

		// Make sure to update and draw your stage if you haven't already
		// For example, if using Scene2D.ui Stage for UI elements:
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		super.stage.draw();
	}

	// xh functions = = =
	public void loadPauseWindow() {
		// add items to pause window
		continueBtn = new TextButton("Continue", skin);
		exitBtn = new TextButton("Main Menu", skin);

		pauseMenu.padTop(64);
		pauseMenu.add(continueBtn).padBottom(10).row();
		pauseMenu.add(exitBtn).padBottom(10).row();
		pauseMenu.setSize(stage.getWidth() / 1.5f, stage.getHeight() / 1.5f);
		pauseMenu.setPosition((stage.getWidth() / 2) - (pauseMenu.getWidth() / 2),
				(stage.getHeight() / 2) - (pauseMenu.getHeight() / 2));
		pauseMenu.setMovable(false);
	}

	public boolean runPauseMenu() {
		game.getInputOutputManagement().stopSound("game");
		boolean status = false;
		stage.addActor(pauseMenu);
		pauseMenu.setVisible(true);

		continueBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("going back");
				paused = false;
				pauseMenu.remove();
			}
		});
		exitBtn.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
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

	// updates player input, handles events accordingly (input event, collision
	// event, etc)
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
		if (planeItemCollision.checkCollisions(entities)) {
        	game.getInputOutputManagement().startSound("collected");

		}
		powerupCollision.checkCollisions(entities);
		if (planeEnemyCollision.checkCollisions(entities)) {
			// Respawn enemy upon collision
			spawnEnemy();

		}

		aiControlManagement.chase(enemy, bin, 2);

		player.userMovement();
	}

	@Override
	public void dispose() {
		// Dispose of the batch if it's not null
		if (batch != null) {
			batch.dispose();
		}

		// Dispose of the skin if it's not null
		if (skin != null) {
			skin.dispose();
		}

		// Dispose of fonts
		if (font != null) {
			font.dispose();
		}

		// If BaseScene or any other managed resources need disposing, call
		// super.dispose();
		super.dispose();

		// Additional disposable resources here...
	}

}
