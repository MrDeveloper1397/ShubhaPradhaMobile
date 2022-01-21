package task.marami.Shubhaprada.Models;

public class CollectionItemData {
    String venture_cd,venture_name,type_acount,venture_collection;

    public CollectionItemData(String venture_cd,String venture_name, String type_acount,String venture_collection) {
        this.venture_cd=venture_cd;
        this.venture_name=venture_name;
        this.type_acount = type_acount;
        this.venture_collection = venture_collection;
    }

    public String getVenture_name() {
        return venture_name;
    }

    public void setVenture_name(String venture_name) {
        this.venture_name = venture_name;
    }

    public String getVenture_collection() {
        return venture_collection;
    }

    public void setVenture_collection(String venture_collection) {
        this.venture_collection = venture_collection;
    }

    public String getVenture_cd() {
        return venture_cd;
    }

    public void setVenture_cd(String venture_cd) {
        this.venture_cd = venture_cd;
    }

    public String getType_acount() {
        return type_acount;
    }

    public void setType_acount(String type_acount) {
        this.type_acount = type_acount;
    }
}
