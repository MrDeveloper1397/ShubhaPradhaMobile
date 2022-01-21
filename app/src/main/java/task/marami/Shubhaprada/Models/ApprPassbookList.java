package task.marami.Shubhaprada.Models;

public class ApprPassbookList {
    String member_id,member_name,date_of_join,plot_appr,cost_appr,comm_appr,discou_appr;

    public ApprPassbookList(String member_id, String member_name, String date_of_join, String plot_appr, String cost_appr, String comm_appr, String discou_appr) {
        this.member_id = member_id;
        this.member_name = member_name;
        this.date_of_join = date_of_join;
        this.plot_appr = plot_appr;
        this.cost_appr = cost_appr;
        this.comm_appr = comm_appr;
        this.discou_appr = discou_appr;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getDate_of_join() {
        return date_of_join;
    }

    public void setDate_of_join(String date_of_join) {
        this.date_of_join = date_of_join;
    }

    public String getPlot_appr() {
        return plot_appr;
    }

    public void setPlot_appr(String plot_appr) {
        this.plot_appr = plot_appr;
    }

    public String getCost_appr() {
        return cost_appr;
    }

    public void setCost_appr(String cost_appr) {
        this.cost_appr = cost_appr;
    }

    public String getComm_appr() {
        return comm_appr;
    }

    public void setComm_appr(String comm_appr) {
        this.comm_appr = comm_appr;
    }

    public String getDiscou_appr() {
        return discou_appr;
    }

    public void setDiscou_appr(String discou_appr) {
        this.discou_appr = discou_appr;
    }
}
