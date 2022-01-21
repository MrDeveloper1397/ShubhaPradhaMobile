package task.marami.Shubhaprada.Interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import task.marami.Shubhaprada.Models.AssociateTreeTeamWise;
import task.marami.Shubhaprada.Models.CadresCountAssociates;


public interface IAssociateTree {
    interface presenter {
        void onLoad(String venture);
        void onAssociateSearch(String venture, String AssociateID);
        void onChildItems(String Cadre, String venture);
    }
    interface view {
        void onStartProg();
        void onStopProg();
        void OnError(String msg);
        void onSuccess(String usertype, ArrayList<String> cca, HashMap<String, ArrayList<AssociateTreeTeamWise>> childs, ArrayList<CadresCountAssociates> cadresCountAssociatesArrayList);
    }
}
