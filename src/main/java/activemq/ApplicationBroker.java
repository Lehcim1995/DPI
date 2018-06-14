/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activemq;

import com.google.gson.Gson;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationBroker
{

    //Maps for topics
    public Map<String, List<String>> topics;
    public Map<String, TopicSenderGateway> topicProducer;
    public Map<String, TopicReceiverGateway> topicReceiver;
    //Chatlist for topics
    private List<String> chatList = new ArrayList<>();

    /**
     * Constructor for ApplicationBroker
     *
     * @throws JMSException
     */
    public ApplicationBroker() throws JMSException
    {
        this.topics = new HashMap<>();
        this.topicProducer = new HashMap<>();
        this.topicReceiver = new HashMap<>();
    }

    /**
     * Close all connections
     *
     * @throws JMSException
     */
    public void closeAll() throws JMSException
    {
        //Closing all producers
        for (Map.Entry<String, TopicSenderGateway> entry : topicProducer.entrySet())
        {
            entry.getValue()
                 .close();
        }

        //Closing al receivers
        for (Map.Entry<String, TopicReceiverGateway> entry : topicReceiver.entrySet())
        {
            entry.getValue()
                 .close();
        }
    }

    /**
     * Create MessageProducer and MessageListener for he new channel
     *
     * @param channel
     * @throws JMSException
     */
    public void createNewChannel(final String channel) throws JMSException
    {
        //Add all the channel needed components to their desired map
        topicProducer.put(channel, new TopicSenderGateway(channel));
        topicReceiver.put(channel, new TopicReceiverGateway(channel));
        topics.put(channel, new ArrayList<>());

        //Set MessageListener for the TopicReceiverGateway
        topicReceiver.get(channel)
                     .setListener(msg -> {
                         try
                         {
                             TextMessage textMessage = (TextMessage) msg;
                             topics.get(channel)
                                   .add(msg.getJMSMessageID() + "    :" + textMessage.getText());
                             retrieveChannels(channel);
                         }
                         catch (JMSException ex)
                         {
                             Logger.getLogger(ApplicationBroker.class.getName())
                                   .log(Level.SEVERE, null, ex);
                         }
                     });
    }

    /**
     * Refresh chatList for displaying reasons
     *
     * @param channel
     */
    public void retrieveChannels(String channel)
    {
        this.chatList = topics.get(channel);
    }

    /**
     * Send a TextMessage to the desired channel
     *
     * @param channel
     * @param text
     * @throws JMSException
     * @throws InterruptedException
     */
    public void sendMessage(
            String channel,
            String text) throws JMSException, InterruptedException
    {
        topicProducer.get(channel)
                     .sendMessage(text);
        Thread.sleep(500);
        retrieveChannels(channel);
    }

    /**
     * Send a TextMessage to the desired channel
     *
     * @param channel
     * @throws JMSException
     * @throws InterruptedException
     */
    public void sendMessageObject(
            String channel,
            Object object) throws JMSException, InterruptedException
    {

        Gson gson = new Gson();

        topicProducer.get(channel)
                     .sendMessage(gson.toJson(object));
        Thread.sleep(500);
        retrieveChannels(channel);
    }

    /* Default getters and setters below */
    public Map<String, List<String>> getTopics()
    {
        return topics;
    }

    public void setTopics(Map<String, List<String>> topics) {
        this.topics = topics;
    }

    public List<String> getChatList()
    {
        return chatList;
    }

    public void setChatList(ArrayList<String> chatList)
    {
        this.chatList = chatList;
    }
}
