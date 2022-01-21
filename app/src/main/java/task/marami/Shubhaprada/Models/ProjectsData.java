package task.marami.Shubhaprada.Models;

public class ProjectsData {
    String ProjectId,Title,Link,Sector,ImageLink,EnqueryLink,Longitude,latitude,totalcount,available, alotted,mortgage,register,reserved,Facing;

    public ProjectsData(String projectId, String title, String link, String sector, String imageLink, String enqueryLink, String longitude, String latitude, String totalcount, String remaining,
            String alotted, String mortgage, String register, String reserved, String Facing) {
        ProjectId = projectId;
        Title = title;
        Link = link;
        Sector = sector;
        ImageLink = imageLink;
        EnqueryLink = enqueryLink;
        Longitude = longitude;
        this.latitude = latitude;
        this.totalcount=totalcount;
        this.available=remaining;
        this.alotted = alotted;
        this.mortgage = mortgage;
        this.register = register;
        this.reserved=reserved;
        this.Facing = Facing;
    }

    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String projectId) {
        ProjectId = projectId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getSector() {
        return Sector;
    }

    public void setSector(String sector) {
        Sector = sector;
    }

    public String getImageLink() {
        return ImageLink;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getAlotted() {
        return alotted;
    }

    public void setAlotted(String alotted) {
        this.alotted = alotted;
    }

    public String getMortgage() {
        return mortgage;
    }

    public void setMortgage(String mortgage) {
        this.mortgage = mortgage;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getFacing() {
        return Facing;
    }

    public void setFacing(String facing) {
        Facing = facing;
    }

    public void setImageLink(String imageLink) {
        ImageLink = imageLink;
    }

    public String getEnqueryLink() {
        return EnqueryLink;
    }

    public void setEnqueryLink(String enqueryLink) {
        EnqueryLink = enqueryLink;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

}
