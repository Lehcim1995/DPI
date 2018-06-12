package ui.controllers;

import domain.Chat;
import domain.ChatMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class ChatController
{

    @FXML
    public TextArea chatBox;
    @FXML
    private ListView<ChatMessage> chatList;

    public void sendChat(ActionEvent actionEvent)
    {

        String message = chatBox.getText();

        if (message.isEmpty())
        {
            return;
        }

        chatList.getItems().add(new ChatMessage(message));
        chatBox.setText("");
    }


}
