package activemq.gateway;


import com.google.gson.Gson;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public abstract class Gateway {
    protected MessageSenderGateway messageSender;
    protected MessageReceiverGateway messageReceiver;
    protected Gson gson;

    public Gateway(String senderChannel, String receiverChannel) {
        messageSender = new MessageSenderGateway(senderChannel);
        messageReceiver = new MessageReceiverGateway(receiverChannel);
        gson = new Gson();

        messageReceiver.setListener(message -> {
            if (message instanceof TextMessage) {
                try {
                    String json = ((TextMessage) message).getText();
                    receiveMessage(json, message.getJMSCorrelationID());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected abstract void receiveMessage(String message, String correlationId);
}
