package activemq.loanbroker;

import gateway.Gateway;
import models.bank.BankInterestReply;
import models.bank.BankInterestRequest;

public class BrokerBankAppGateway extends Gateway {
    private LoanBrokerFrame frame;

    public BrokerBankAppGateway(LoanBrokerFrame frame, String senderChannel) {
        super(senderChannel, "bankInterestReplyQueue");
        this.frame = frame;
    }

    public void sendBankInterestRequest(BankInterestRequest request, String correlationId) {
        messageSender.send(gson.toJson(request), correlationId);
    }

    @Override
    protected void receiveMessage(String message, String correlationId) {
        BankInterestReply reply = gson.fromJson(message, BankInterestReply.class);
        frame.addReplyAndSend(correlationId, reply);
    }
}
