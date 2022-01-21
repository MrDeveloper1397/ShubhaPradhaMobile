package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VacantListDataAdp {
        ArrayList<VacantListItems> projectsData=new ArrayList<>();;
        JSONArray response;
        JSONObject jsonObject=null;

        public VacantListDataAdp(JSONArray response) {
            this.response = response;
            for(int i=0;i<response.length();i++)
            {
                try {
                    jsonObject = response.getJSONObject(i);
                    VacantListItems p1 = new VacantListItems(jsonObject.getString("plotnum"),
                            jsonObject.getString("plotarea"),
                            jsonObject.getString("facing"));

                    projectsData.add(p1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        public void setProjectsData(ArrayList<VacantListItems> projectsData) {
            this.projectsData = projectsData;
        }

        public ArrayList<VacantListItems> getProjectsData() {
            return projectsData;
        }
    }

