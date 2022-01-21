package task.marami.Shubhaprada.Models;

import android.annotation.SuppressLint;
import android.icu.text.NumberFormat;

import java.math.BigDecimal;
import java.text.Format;
import java.util.Locale;

public class BookedData {
    String venture,passbook,aplicent,mobile,agent,sector,plotno,plotarea,rate,total;
    @SuppressLint("NewApi")
    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));

    public BookedData(String venture, String passbook, String aplicent, String mobile, String agent, String sector, String plotno, String plotarea, String rate, String total) {
        this.venture = venture;
        this.passbook = passbook;
        this.aplicent = aplicent;
        this.mobile = mobile;
        this.agent = agent;
        this.sector = sector;
        this.plotno = plotno;
        this.plotarea = plotarea;
        this.rate = rate;
        this.total = total;
    }

    public String getVenture() {
        return venture;
    }

    public String getPassbook() {
        return passbook;
    }

    public String getAplicent() {
        return aplicent;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAgent() {
        return agent;
    }

    public String getSector() {
        return sector;
    }

    public String getPlotno() {
        return plotno;
    }

    public String getPlotarea() {
        return plotarea;
    }

    public String getRate() {
        return format.format(new BigDecimal(rate));
    }

    public String getTotal() {
        return format.format(new BigDecimal(total));
    }
}
