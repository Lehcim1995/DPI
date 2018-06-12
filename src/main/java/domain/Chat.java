package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chat
{
    private List<User> users;
    private List<ChatMessage> messageList;

    public Chat(User user1, User user2)
    {
        users = Arrays.asList(user1, user2);
        messageList = new ArrayList<>();
    }
}
