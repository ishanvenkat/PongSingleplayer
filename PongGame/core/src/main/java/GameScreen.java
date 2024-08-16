package com.ponggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen{
    
    final Pong game;

    private Texture paddleImage;
    private Rectangle paddle;
    private Texture ballImage;
    private Rectangle ball;
    private OrthographicCamera camera;
    private int ballDirX;
    private int ballDirY;
    private Skin skin;
    private Stage stage;
    private Label score;
    private int scoreCount;


    public GameScreen(final Pong game) {
        this.game = game;
        

        paddleImage = new Texture(Gdx.files.internal("paddle.png"));
        ballImage = new Texture(Gdx.files.internal("ball.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        paddle = new Rectangle();
        paddle.width = 40;
        paddle.height = 2;
        paddle.x = 800 / 2 - 5/2;
        paddle.y = 100;

        ball = new Rectangle();
        ball.width = 10;
        ball.height = 10;
        ball.x = 800 / 2 - 5/2;
        ball.y = 300;
        
        ballDirX = 250;
        ballDirY = 250;
        
        skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
      
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        skin.get(LabelStyle.class);
        score = new Label("Score : " + scoreCount, skin);
        root.add(score);
        score.setColor(Color.WHITE);

    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        stage.act();
        stage.draw();
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(paddleImage, paddle.x, paddle.y);
        game.batch.draw(ballImage, ball.x, ball.y);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            paddle.x -= 400 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            paddle.x += 400 * Gdx.graphics.getDeltaTime();
        }

        if (ball.y > 480 - 10) {
            ballDirY = -ballDirY;
            drawBall();
        } else if (ball.x > 800 - 10) {
            ballDirX = -ballDirX;
            drawBall();
        } else if (ball.y < 0) {
            ballDirX = 0;
            ballDirY = 0;
        } else if (ball.x < 0) {
            ballDirX = -ballDirX;
            drawBall();
        } else {
            drawBall();
        }

        if (ball.overlaps(paddle)) {
            scoreCount += 1;
            score.setText("Score: " + scoreCount);
            ballDirY = -ballDirY;
            drawBall();
        }

        if (paddle.x < 0) {
            paddle.x += 400 * Gdx.graphics.getDeltaTime();
        }
        if (paddle.x > 800 - 40) {
            paddle.x -= 400 * Gdx.graphics.getDeltaTime();
        }

    }

    public void drawBall() {
        ball.x += ballDirX * Gdx.graphics.getDeltaTime();
        ball.y += ballDirY * Gdx.graphics.getDeltaTime();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}
