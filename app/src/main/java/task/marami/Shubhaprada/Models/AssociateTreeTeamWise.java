package task.marami.Shubhaprada.Models;

public class AssociateTreeTeamWise {
    String AgentCd,AgentName,AgentCadre,WorkUnder,PanNo,Mobile;

    public AssociateTreeTeamWise(String agentCd, String agentName, String agentCadre, String workUnder, String panNo, String mobile) {
        AgentCd = agentCd;
        AgentName = agentName;
        AgentCadre = agentCadre;
        WorkUnder = workUnder;
        PanNo = panNo;
        Mobile = mobile;
    }

    public String getAgentCd() {
        return AgentCd;
    }

    public void setAgentCd(String agentCd) {
        AgentCd = agentCd;
    }

    public String getAgentName() {
        return AgentName;
    }

    public void setAgentName(String agentName) {
        AgentName = agentName;
    }

    public String getAgentCadre() {
        return AgentCadre;
    }

    public void setAgentCadre(String agentCadre) {
        AgentCadre = agentCadre;
    }

    public String getWorkUnder() {
        return WorkUnder;
    }

    public void setWorkUnder(String workUnder) {
        WorkUnder = workUnder;
    }

    public String getPanNo() {
        return PanNo;
    }

    public void setPanNo(String panNo) {
        PanNo = panNo;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

}
