package ui.controllers;

import javafx.event.ActionEvent;
import ui.SceneLoader;

import java.io.IOException;

public class LoginController
{
    private SceneLoader sceneLoader;

    public LoginController(SceneLoader sceneLoader) {
    }

    public void loginAction(ActionEvent actionEvent)
    {


        try
        {
            sceneLoader.loadScene("chat_selector");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("Button lol");


    }
}
