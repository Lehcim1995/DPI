package service;

import domain.Chat;
import domain.ChatMessage;
import domain.User;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatService
{


    public boolean Chat(User from, User too, String chat)
    {

        ChatMessage cm = new ChatMessage(from, Arrays.asList(too), chat);

        // TODO send this message

        return false;
    }
}
