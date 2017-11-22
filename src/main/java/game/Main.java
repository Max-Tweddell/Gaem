package game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.ecs.Entity;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.input.MouseButton;
import com.almasb.fxgl.entity.component.CollidableComponent;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.event.Event.*;

import java.util.Map;

import com.almasb.fxgl.physics.PhysicsWorld;

public class Main extends GameApplication {

    private int score = 0;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Hello!");
    }

    private GameEntity player;
    private GameEntity brick;
    private int enemyCount = 20;


    @Override
    public void initGame() {

        getMasterTimer().runAtInterval(this::run, Duration.seconds(1));

        player = Entities.builder()
                .at(300, 300)
                .viewFromNode(new Rectangle(25, 25, Color.BLUE))

                .buildAndAttach(getGameWorld());
        brick = Entities.builder()
                .at(100, 100)
                .viewFromNode(new Rectangle(25, 25, Color.RED))
                .buildAndAttach(getGameWorld());

    }

    @Override
    protected void initInput() {
        Input input = getInput(); // get input service


        input.addAction(new UserAction("Shoot") {
            @Override
            protected void onActionBegin() {
                State.targetX = input.getMouseXUI();
                State.targetY = input.getMouseYUI();
                getGameWorld().spawn("Bullet", player.getX(), player.getY());
            }
        }, KeyCode.SPACE);



        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                player.translateX(5); //move right 5 pixels
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                player.translateX(-5); //move right 5 pixels
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                player.translateY(5); //move right 5 pixels
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.S);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                player.translateY(-5); //move right 5 pixels
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.W);

    }

    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50);
        textPixels.setTranslateY(100);
        getGameScene().addUINode(textPixels);
        textPixels.textProperty().bind(getGameState().intProperty("pixelsMoved").asString());
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
        vars.put("Score", 0);
        vars.put("enemies", 0);
    }

    @Override
    protected void initPhysics() {
        PhysicsWorld physicsWorld = getPhysicsWorld();

        physicsWorld.addCollisionHandler(new CollisionHandler(GaemType.BULLET, GaemType.ENEMY) {
            @Override
            protected void onCollisionBegin(Entity bullet, Entity enemy) {
                bullet.removeFromWorld();
                enemy.removeFromWorld();

                getGameState().increment("enemies", -1);
                getGameState().increment("Score", +1);
                enemyCount = enemyCount - 1;
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void run() {
        int numEnemies = getGameState().getInt("enemies");
        if (enemyCount == 0) {
            Text textPixels = new Text(200,200, "you win");
            getGameScene().addUINode(textPixels);
        }

        if (numEnemies < 7) {
            getGameWorld().spawn("Enemy",
                    FXGLMath.random(0, getWidth() - 40),
                    FXGLMath.random(0, getHeight() / 2 - 40)
            );

            getGameState().increment("enemies", +1);
        }
    }
}
