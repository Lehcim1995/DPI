package activemq.loanbroker;

import gateway.Gateway;
import models.loan.LoanReply;
import models.loan.LoanRequest;

public class BrokerLoanAppGateway extends Gateway {
    private LoanBrokerFrame frame;

    public BrokerLoanAppGateway(LoanBrokerFrame frame) {
        super("loanReplyQueue", "loanRequestQueue");
        this.frame = frame;
    }

    public void sendLoanReply(LoanReply reply, String correlationId) {
        messageSender.send(gson.toJson(reply), correlationId);
    }

    @Override
    protected void receiveMessage(String message, String correlationId) {
        LoanRequest loanRequest = gson.fromJson(message, LoanRequest.class);
        frame.add(loanRequest, correlationId);
    }
}
