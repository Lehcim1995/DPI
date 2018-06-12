package activemq.loanbroker;

import bank.RabobankBrokerAppGateway;
import models.bank.BankInterestReply;
import models.bank.BankInterestRequest;
import models.loan.LoanReply;
import models.loan.LoanRequest;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class LoanBrokerFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private static DefaultListModel<JListLine> listModel = new DefaultListModel<>();
    private JList<JListLine> list;

    public HashMap<String, LoanRequest> loanRequests = new HashMap<>();
    public static BrokerLoanAppGateway brokerLoanGateway;
    public static BrokerBankAppGateway brokerAbnBankGateway;
    public static BrokerBankAppGateway brokerRabobankGateway;
    public static BrokerBankAppGateway brokerIngbankGateway;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoanBrokerFrame frame = new LoanBrokerFrame();
                brokerAbnBankGateway = new BrokerBankAppGateway(frame, "abnBankInterestRequestQueue");
                brokerRabobankGateway = new BrokerBankAppGateway(frame, "rabobankInterestRequestQueue");
                brokerIngbankGateway = new BrokerBankAppGateway(frame, "ingBankInterestRequestQueue");
                brokerLoanGateway = new BrokerLoanAppGateway(frame);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public LoanBrokerFrame() {
        setTitle("Loan Broker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{46, 31, 86, 30, 89, 0};
        gbl_contentPane.rowHeights = new int[]{233, 23, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridwidth = 7;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 0;
        contentPane.add(scrollPane, gbc_scrollPane);

        list = new JList<>(listModel);
        scrollPane.setViewportView(list);
    }

    private JListLine getRequestReply(LoanRequest request) {
        for (int i = 0; i < listModel.getSize(); i++) {
            JListLine rr = listModel.get(i);
            if (rr.getLoanRequest() == request) {
                return rr;
            }
        }

        return null;
    }

    public void add(LoanRequest loanRequest, String correlationId) {
        listModel.addElement(new JListLine(loanRequest));

        loanRequests.put(correlationId, loanRequest);
        BankInterestRequest bankInterestRequest = new BankInterestRequest("", loanRequest.getAmount(), loanRequest.getTime());
        this.add(loanRequest, bankInterestRequest);

        int amount = loanRequest.getAmount();
        int time = loanRequest.getTime();

        if (amount <= 100000 && time <= 10) {
            brokerIngbankGateway.sendBankInterestRequest(bankInterestRequest, correlationId);
        } else if (amount < 250000 && time <= 15) {
            brokerRabobankGateway.sendBankInterestRequest(bankInterestRequest, correlationId);
        } else if (amount >= 250000 && amount <= 300000 && time <= 20) {
            brokerAbnBankGateway.sendBankInterestRequest(bankInterestRequest, correlationId);
        }
    }

    private void add(LoanRequest loanRequest, BankInterestRequest bankRequest) {
        JListLine rr = getRequestReply(loanRequest);
        if (rr != null && bankRequest != null) {
            rr.setBankRequest(bankRequest);
            list.repaint();
        }
    }

    public void addReplyAndSend(String correlationId, BankInterestReply bankReply) {
        JListLine rr = getLine(loanRequests.get(correlationId));
        if (rr != null && bankReply != null) {
            rr.setBankReply(bankReply);
            list.repaint();

            brokerLoanGateway.sendLoanReply(new LoanReply(bankReply.getInterest(), bankReply.getQuoteId()), correlationId);
        }
    }

    private JListLine getLine(LoanRequest request) {
        for (int i = 0; i < listModel.getSize(); i++) {
            JListLine rr = listModel.get(i);
            if (rr.getLoanRequest() == request) {
                return rr;
            }
        }
        return null;
    }
}
