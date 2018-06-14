/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activemq;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.TextMessage;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author mimoun
 */
public class MessageReceiverGateway {

    private final ConnectionFactory connectionFactory;
    private final Connection connection;
    private final Session session;
    private final Destination destination;
    private final MessageConsumer consumer;
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String subject = "JCG_QUEUE";

    /**
     * Constructor for MessageReceiverGateway
     *
     * @throws JMSException
     */
    public MessageReceiverGateway() throws JMSException {
        //Create new Connection, Session, Topic and MessageConsumer
        connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(subject);
        consumer = session.createConsumer(destination);

        //Start the connection
        connection.start();

        //Set MessageListener for Consumer
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message msg) {
                try {
                    TextMessage textMessage = (TextMessage) msg;
                    System.out.println("Received message que'" + textMessage.getText() + "'");
                } catch (JMSException ex) {
                    Logger.getLogger(MessageReceiverGateway.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        connection.start();
    }

    /**
     * set a new MessageListener for the MessageConsumer
     *
     * @param ml
     * @throws JMSException
     */
    public void setListener(MessageListener ml) throws JMSException {
        consumer.setMessageListener(ml);
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
