package task.marami.Shubhaprada.Models;

public class HomeData {
    String title,context,project_title,imageurl,project_id,link,sectors,enq_url,allo_count,avl_count,mort_count,regs_count,rese_count,totcount,facing;

    public HomeData(String title, String context, String project_title, String imageurl, String project_id, String link, String sectors, String enq_url,
            String allo_count, String avail_count, String mort_count, String regs_count, String rese_count, String totcount, String facing) {
        this.title = title;
        this.context = context;
        this.project_title = project_title;
        this.imageurl = imageurl;
        this.project_id = project_id;
        this.link = link;
        this.sectors = sectors;
        this.enq_url = enq_url;
        this.allo_count = allo_count;
        this.avl_count = avail_count;
        this.mort_count = mort_count;
        this.regs_count = regs_count;
        this.rese_count = rese_count;
        this.totcount = totcount;
        this.facing = facing;

    }

    public String getAllo_count() {
        return allo_count;
    }

    public void setAllo_count(String allo_count) {
        this.allo_count = allo_count;
    }

    public String getAvl_count() {
        return avl_count;
    }

    public void setAvl_count(String avl_count) {
        this.avl_count = avl_count;
    }

    public String getMort_count() {
        return mort_count;
    }

    public void setMort_count(String mort_count) {
        this.mort_count = mort_count;
    }

    public String getRegs_count() {
        return regs_count;
    }

    public void setRegs_count(String regs_count) {
        this.regs_count = regs_count;
    }

    public String getRese_count() {
        return rese_count;
    }

    public void setRese_count(String rese_count) {
        this.rese_count = rese_count;
    }

    public String getTotcount() {
        return totcount;
    }

    public void setTotcount(String totcount) {
        this.totcount = totcount;
    }

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getProject_title() {
        return project_title;
    }

    public void setProject_title(String project_title) {
        this.project_title = project_title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSectors() {
        return sectors;
    }

    public void setSectors(String sectors) {
        this.sectors = sectors;
    }

    public String getEnq_url() {
        return enq_url;
    }

    public void setEnq_url(String enq_url) {
        this.enq_url = enq_url;
    }
}
