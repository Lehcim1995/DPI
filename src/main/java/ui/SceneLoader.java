package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneLoader
{
    private final static String SCENE_LOCATION = "scenes/";
    private final static String SCENE_EXTENSION = ".fxml";

    public Scene loadScene(String name) throws IOException
    {
        return FXMLLoader.load(getClass().getResource( SCENE_LOCATION + name + SCENE_EXTENSION));
    }
}
