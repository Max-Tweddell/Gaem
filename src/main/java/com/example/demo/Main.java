package com.example.demo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.Map;

public class Main extends GameApplication {
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Hello!");
    }

    private GameEntity player;
    private GameEntity brick;




    @Override
    public void initGame() {
        player = Entities.builder()
                .at(300,300)
                .viewFromNode(new Rectangle(25, 25, Color.BLUE))
                .buildAndAttach(getGameWorld());
        brick = Entities.builder()
                .at(100,100)
                .viewFromNode(new Rectangle(25, 25, Color.RED))
                .buildAndAttach(getGameWorld());

    }

    @Override
    protected void initInput() {
        Input input = getInput(); // get input service

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
    }
    public static void main(String[] args) {
        launch(args);
    }
}
