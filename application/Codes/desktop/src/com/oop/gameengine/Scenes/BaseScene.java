package com.oop.gameengine.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.oop.gameengine.Managers.*;

public abstract class BaseScene implements Screen {

    protected SimulationLifeCycleManagement game;
    protected SpriteBatch batch;
    protected BitmapFont font;
    protected Stage stage;

    public BaseScene(SimulationLifeCycleManagement game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
    }

    protected abstract void initUI();

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    protected void addButton(String text, float x, float y, ClickListener listener) {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.getFont();
        TextButton button = new TextButton(text, textButtonStyle);
        button.setPosition(x, y);
        button.addListener(listener);
        stage.addActor(button);
    }

    protected void addText(String text) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        //labelStyle.font = game.getFont();
        Label label = new Label(text, labelStyle);
        label.setColor(Color.WHITE);
        label.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() - 20);
        stage.addActor(label);
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
        game.getInputOutputManagement().mouse();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }


}
