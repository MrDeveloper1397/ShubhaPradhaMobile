package task.marami.Shubhaprada.Utils;

import java.util.ArrayList;

import task.marami.Shubhaprada.Models.ApprPassbookList;
import task.marami.Shubhaprada.Models.CollectionHeaderData;
import task.marami.Shubhaprada.Models.CollectionItemData;
import task.marami.Shubhaprada.Models.PaymentTransationData;
import task.marami.Shubhaprada.Models.PlistApprovalCount;
import task.marami.Shubhaprada.Models.PlotCostApproval;
import task.marami.Shubhaprada.Models.PlotMatrixHeader;
import task.marami.Shubhaprada.Models.ProjectsData;
import task.marami.Shubhaprada.Models.ReservationModel;

/**
 * Created by mounika 20/2/2021
 */

public class  Contents {
    //SharedPref Keys Names
    public static final String PREF_KEY_AUTH_VALUE="AUTH_VALUE";
    public static final String PREF_KEY_CURR_NAME="USER_NAME";
    public static final String PREF_KEY_CURR_MOBILE="USER_MOBILE";
    public static final String PREF_KEY_API_TOKEN="API_KEY";
    public static final String PTEF_KEY_USER_TYPE="USER_TYPE";
    public static final String PTEF_KEY_USERID = "USERID";

    //Base Url
   /* public static final String BASE_URL="http://183.82.126.49:7777/ShubhaPrada/";
    public static String imageurl = "http://183.82.126.49:7777/mobilemodule1/";
    public static String company_id="Shu699149781";
*/
    //testing hasini
    public static final String BASE_URL = "http://183.82.54.218:8080/HAsiniEstatesTest/";
    public static String imageurl = "http://183.82.54.218:8080/MiltaskMobileModule/";
    public static String company_id = "Has891721233";

    public static final String SERVER_RETRY="Please Try Again After Some Time";
    public static String No_Internet = "Please Connect  Mobile Data Or Wifi Network";

    public static Double TotalPlotcost=0.0;
    public static Double Totalcommission=0.0;
    public static ProjectsData utiprojectsData;
    public static ArrayList<ProjectsData> utiProjData=new ArrayList<>();
    public static ArrayList<ReservationModel> utiReservationData=new ArrayList<>();
    public static ArrayList<PlistApprovalCount> utiplistdata=new ArrayList<>();
    public static ArrayList<ApprPassbookList> utiPassbookList=new ArrayList<>();
    public static ArrayList<PlotCostApproval> utiPlotCostData=new ArrayList<>();
    public static ArrayList<CollectionHeaderData> uticolheaderdata=new ArrayList<>();
    public static ArrayList<CollectionItemData> uticolitemdata=new ArrayList<>();
    public static ArrayList<PlotMatrixHeader> utiplotmatrixheader=new ArrayList<>();
    public static ArrayList<PaymentTransationData> utitransationData=new ArrayList<>();
}
