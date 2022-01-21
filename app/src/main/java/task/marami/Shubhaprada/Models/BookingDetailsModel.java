package task.marami.Shubhaprada.Models;

public class BookingDetailsModel {
    String AgentCd,PlotNo,PlotArea,MemberId,Name,sector,AssociateName;

    public BookingDetailsModel(String agentCd, String plotNo, String plotArea, String memberId, String name, String sector, String AssociateName) {
        AgentCd = agentCd;
        PlotNo = plotNo;
        PlotArea = plotArea;
        MemberId = memberId;
        Name = name;
        this.sector = sector;
        this.AssociateName = AssociateName;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getAgentCd() {
        return AgentCd;
    }

    public String getAssociateName() {
        return AssociateName;
    }

    public void setAgentCd(String agentCd) {
        AgentCd = agentCd;
    }

    public String getPlotNo() {
        return PlotNo;
    }

    public void setPlotNo(String plotNo) {
        PlotNo = plotNo;
    }

    public String getPlotArea() {
        return PlotArea;
    }

    public void setPlotArea(String plotArea) {
        PlotArea = plotArea;
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String memberId) {
        MemberId = memberId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
