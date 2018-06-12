package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Client extends Application
{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        URL login = getClass().getResource("scenes/Login.fxml");

        Parent root = FXMLLoader.load(login);

        Scene scene = new Scene(root, 750, 750);

        SceneLoader screenController = new SceneLoader(scene, primaryStage);
        screenController.addScreen("login", screenController.loadScene("Login"));
        screenController.addScreen("client", screenController.loadScene("Client"));
        screenController.addScreen("chat", screenController.loadScene("Chat"));
        screenController.addScreen("chat_selector", screenController.loadScene("ChatSelector"));
        screenController.activate("chat");
    }
}
