package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;

public class SceneLoader
{
    private final static String SCENE_LOCATION = "scenes/";
    private final static String SCENE_EXTENSION = ".fxml";

    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene main;

    public SceneLoader(Scene main) {
        this.main = main;
    }

    public Pane loadScene(String name) throws IOException
    {
        return FXMLLoader.load(getClass().getResource(SCENE_LOCATION + name + SCENE_EXTENSION));
    }

    protected void addScreen(
            String name,
            Pane pane)
    {
        screenMap.put(name, pane);
    }

    protected void removeScreen(String name) {
        screenMap.remove(name);
    }

    protected void activate(String name) {
        main.setRoot(screenMap.get(name));
    }
}
