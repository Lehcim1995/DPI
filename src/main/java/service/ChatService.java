package service;

import activemq.ApplicationBroker;
import bean.ChatBean;
import domain.ChatMessage;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class ChatService
{

    //Main broker for the messaging application
    private ApplicationBroker gateway;

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
    public void sendMessage(ChatMessage message, String currentChannel) throws JMSException, InterruptedException
    {
        gateway.sendMessageObject(currentChannel, message);
    }

    /**
     * Subscribe to a new channel
     */
    public void addChannel(String channel)
    {
        if (gateway.topics.get(channel) == null)
        {
            try
            {
                gateway.createNewChannel(channel);
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
     * @param channel
     */
    public void openChannel(String channel) {
        gateway.retrieveChannels(channel);
//        currentChannel = channel;
    }
}
