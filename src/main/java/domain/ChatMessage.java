package domain;

import java.util.List;

public class ChatMessage
{
    private User sender;
    private List<User> recievers;
    private String message;

    public ChatMessage(
            User sender,
            List<User> recievers,
            String message)
    {
        this.sender = sender;
        this.recievers = recievers;
        this.message = message;
    }

    public ChatMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        //TODO add time
        return sender + ": " + message;
    }
}
