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

    @Override
    public void initGame() {
        player = Entities.builder()
                .at(300,300)
                .viewFromNode(new Rectangle(25, 25, Color.BLUE))
                .buildAndAttach(getGameWorld());

    }
    public static void main(String[] args) {
        launch(args);
    }
}
