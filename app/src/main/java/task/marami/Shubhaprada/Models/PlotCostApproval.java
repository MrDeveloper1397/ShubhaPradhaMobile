package task.marami.Shubhaprada.Models;

import android.annotation.SuppressLint;
import android.icu.text.NumberFormat;

import java.text.Format;
import java.util.Locale;

public class PlotCostApproval {
    String member_id,sector_cd,plot_no,plot_area,pcateg,facing,rate_per_sqr,admin_fee,total_cost,dev_charges,
    cost_premium,bsp4,bsp6,others,campus_fund,camp_discount,discount,rete_calc,sp_premium,premium,member_rec;

    @SuppressLint("NewApi")
    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));


    public PlotCostApproval(String member_id, String sector_cd, String plot_no,
                            String plot_area, String pcateg, String facing,
                            String rate_per_sqr, String admin_fee, String total_cost,
                            String dev_charges, String cost_premium, String bsp4,
                            String bsp6, String others, String campus_fund,
                            String camp_discount, String discount,
                            String rete_calc, String sp_premium, String premium, String member_rec) {
        this.member_id = member_id;
        this.sector_cd = sector_cd;
        this.plot_no = plot_no;
        this.plot_area = plot_area;
        this.pcateg = pcateg;
        this.facing = facing;
        this.rate_per_sqr = rate_per_sqr;
        this.admin_fee = admin_fee;
        this.total_cost = total_cost;
        this.dev_charges = dev_charges;
        this.cost_premium = cost_premium;
        this.bsp4 = bsp4;
        this.bsp6 = bsp6;
        this.others = others;
        this.campus_fund = campus_fund;
        this.camp_discount = camp_discount;
        this.discount = discount;
        this.rete_calc = rete_calc;
        this.sp_premium = sp_premium;
        this.premium = premium;
        this.member_rec = member_rec;
    }

    public String getMember_rec() {
        return member_rec;
    }

    public void setMember_rec(String member_rec) {
        this.member_rec = member_rec;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getSector_cd() {
        return sector_cd;
    }

    public void setSector_cd(String sector_cd) {
        this.sector_cd = sector_cd;
    }

    public String getPlot_no() {
        return plot_no;
    }

    public void setPlot_no(String plot_no) {
        this.plot_no = plot_no;
    }

    public String getPlot_area() {
        return plot_area;
    }

    public void setPlot_area(String plot_area) {
        this.plot_area = plot_area;
    }

    public String getPcateg() {
        return pcateg;
    }

    public void setPcateg(String pcateg) {
        this.pcateg = pcateg;
    }

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }

    public String getRate_per_sqr() {
        return rate_per_sqr;
    }

    public void setRate_per_sqr(String rate_per_sqr) {
        this.rate_per_sqr = rate_per_sqr;
    }

    public String getAdmin_fee() {
        return admin_fee;
    }

    public void setAdmin_fee(String admin_fee) {
        this.admin_fee = admin_fee;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    public String getDev_charges() {
        return dev_charges;
    }

    public void setDev_charges(String dev_charges) {
        this.dev_charges = dev_charges;
    }

    public String getCost_premium() {
        return cost_premium;
    }

    public void setCost_premium(String cost_premium) {
        this.cost_premium = cost_premium;
    }

    public String getBsp4() {
        return bsp4;
    }

    public void setBsp4(String bsp4) {
        this.bsp4 = bsp4;
    }

    public String getBsp6() {
        return bsp6;
    }

    public void setBsp6(String bsp6) {
        this.bsp6 = bsp6;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getCampus_fund() {
        return campus_fund;
    }

    public void setCampus_fund(String campus_fund) {
        this.campus_fund = campus_fund;
    }

    public String getCamp_discount() {
        return camp_discount;
    }

    public void setCamp_discount(String camp_discount) {
        this.camp_discount = camp_discount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getRete_calc() {
        return rete_calc;
    }

    public void setRete_calc(String rete_calc) {
        this.rete_calc = rete_calc;
    }

    public String getSp_premium() {
        return sp_premium;
    }

    public void setSp_premium(String sp_premium) {
        this.sp_premium = sp_premium;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }
}
