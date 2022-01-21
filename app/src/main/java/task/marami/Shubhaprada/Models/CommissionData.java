package task.marami.Shubhaprada.Models;

import android.annotation.SuppressLint;
import android.icu.text.NumberFormat;

import java.math.BigDecimal;
import java.text.Format;
import java.util.Locale;

public class CommissionData {
    String Id,Name,Cadre,Level,Commiss,TotalPayble,CommCalc,ComDiscount,GrossPayable;
    @SuppressLint("NewApi")
    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
    public CommissionData(String id, String name, String cadre, String level, String commiss, String totalPayble, String CommCalc, String ComDiscount,String GrossPayable) {
        this.Id = id;
        this.Name = name;
        this.Cadre = cadre;
        this.Level = level;
        this.Commiss = commiss;
        this.TotalPayble = totalPayble;
        this.CommCalc=CommCalc;
        this.ComDiscount=ComDiscount;
        this.GrossPayable=GrossPayable;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCadre() {
        return Cadre;
    }

    public void setCadre(String cadre) {
        Cadre = cadre;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getCommiss() {
        return Commiss;
    }

    public void setCommiss(String commiss) {
        Commiss = commiss;
    }

    public String getTotalPayble() {
        return TotalPayble;
    }

    public void setTotalPayble(String totalPayble) {
        TotalPayble = format.format(new BigDecimal(totalPayble)).toString();
    }

    public String getCommCalc() {
        return CommCalc;
    }

    public void setCommCalc(String commCalc) {
        CommCalc = commCalc;
    }

    public String getComDiscount() {
        return ComDiscount;
    }

    public void setComDiscount(String comDiscount) {
        ComDiscount = format.format(new BigDecimal(comDiscount)).toString();;
    }

    public String getGrossPayable() {
        return GrossPayable;
    }

    public void setGrossPayable(String grossPayable) {
        GrossPayable = grossPayable;
    }
}
