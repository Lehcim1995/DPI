package bean;

import activemq.ApplicationBroker;
import domain.ChatMessage;
import domain.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.jms.JMSException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@SessionScoped
@Named(value = "chat")
public class ChatBean implements Serializable
{
    private static final long serialVersionUID = 3254181235309041386L;

    //Variables for JSF binding
    private String message = "";
    private String channel = "";
    private String currentChannel = "<No channel selected>";

    private List<ChatMessage> chatMessages;

    private User user;

    //Main broker for the messaging application
    private ApplicationBroker gateway;

    /**
     * Setting up the ApplicationBroker
     */
    @PostConstruct
    public void onload()
    {

        try
        {
            gateway = new ApplicationBroker();
        }
        catch (JMSException ex)
        {
            Logger.getLogger(ChatBean.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Close all connections before closing down
     */
    @PreDestroy
    public void ondestroy()
    {
        try
        {
            gateway.closeAll();
        }
        catch (JMSException ex)
        {
            Logger.getLogger(ChatBean.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Send a message to your current channel
     *
     * @throws JMSException
     * @throws InterruptedException
     */
    public void sendMessage() throws JMSException, InterruptedException
    {
        ChatMessage chatMessage = new ChatMessage(user, null, message);

        gateway.sendMessageObject(currentChannel, chatMessage);
        this.message = "";
    }

    /**
     * Subscribe to a new channel
     */
    public void addChannel()
    {
        if (gateway.topics.get(channel) == null)
        {
            try
            {
                gateway.createNewChannel(channel);
                this.channel = "";
            }
            catch (JMSException ex)
            {
                Logger.getLogger(ChatBean.class.getName())
                      .log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            System.out.print("Trying to create duplicate channel");
        }
    }

    /**
     * Open an existing channel
     *
//     * @param channel
     */
    public void openChannel(String channel) {
        gateway.retrieveChannels(channel);
        currentChannel = channel;
    }

    /* Default getters and setters below */
    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getChannel()
    {
        return channel;
    }

    public void setChannel(String channel)
    {
        this.channel = channel;
    }

    public ApplicationBroker getGateway()
    {
        return gateway;
    }

    public void setGateway(ApplicationBroker gateway)
    {
        this.gateway = gateway;
    }

    public String getCurrentChannel()
    {
        return currentChannel;
    }

    public void setCurrentChannel(String currentChannel)
    {
        this.currentChannel = currentChannel;
    }

    public List<ChatMessage> getChatMessages()
    {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessage> chatMessages)
    {
        this.chatMessages = chatMessages;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
