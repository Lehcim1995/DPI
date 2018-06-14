/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activemq;

import javax.jms.Session;
import javax.jms.Connection;
import javax.jms.TextMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author mimoun
 */
public class MessageSenderGateway {

    private final ConnectionFactory connectionFactory;
    private final Connection connection;
    private final Session session;
    private final Destination destination;
    private final MessageProducer producer;
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String subject = "JCG_QUEUE";

    /**
     * Constructor for MessageSenderGateway
     *
     * @throws JMSException
     */
    public MessageSenderGateway() throws JMSException {
        connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(subject);

        connection.start();

        producer = session.createProducer(destination);
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
