/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activemq;

import javax.jms.Topic;
import javax.jms.Session;
import javax.jms.Connection;
import javax.jms.TextMessage;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author mimoun
 */
public class TopicSenderGateway {

    private final ConnectionFactory connectionFactory;
    private final Connection connection;
    private final Session session;
    private final Topic topic;
    private final MessageProducer producer;
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    /**
     * Constructor for TopicSenderGateway
     *
     * @param channel
     * @throws JMSException
     */
    public TopicSenderGateway(String channel) throws JMSException {
        //Create new Connection, Session, Topic and Producer
        connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        topic = session.createTopic(channel);

        //Start connection
        connection.start();

        //Create MessageProducer
        producer = session.createProducer(topic);
    }

    /**
     * Use the MessageProducer to send a TextMessage
     *
     * @param text
     * @throws JMSException
     */
    public void sendMessage(String text) throws JMSException {
        TextMessage messageText = session.createTextMessage(text);

        producer.send(messageText);
    }

    /**
     * Close connection when it is not needed anymore
     *
     * @throws JMSException
     */
    public void close() throws JMSException {
        connection.close();
    }
}
