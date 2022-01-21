package task.marami.Shubhaprada.Models;

public class PaymentTransationData {
    String accCode,vouchNo,paymentDate,AgentCode,AgentName,cheqDDNo,cheqDate,Amount;

    public PaymentTransationData(String accCode, String vouchNo, String paymentDate, String agentCode, String agentName, String cheqDDNo, String cheqDate, String amount) {
        this.accCode = accCode;
        this.vouchNo = vouchNo;
        this.paymentDate = paymentDate;
        AgentCode = agentCode;
        AgentName = agentName;
        this.cheqDDNo = cheqDDNo;
        this.cheqDate = cheqDate;
        Amount = amount;
    }

    public String getAccCode() {
        return accCode;
    }

    public String getVouchNo() {
        return vouchNo;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getAgentCode() {
        return AgentCode;
    }

    public String getAgentName() {
        return AgentName;
    }

    public String getCheqDDNo() {
        return cheqDDNo;
    }

    public String getCheqDate() {
        return cheqDate;
    }

    public String getAmount() {
        return Amount;
    }
}
