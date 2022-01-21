package task.marami.Shubhaprada.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LayoutsDataAdapter {
    ArrayList<ProjectsData> projectsData=new ArrayList<>();;
    JSONArray response;
    JSONObject jsonObject=null;

    public LayoutsDataAdapter(JSONArray response) {
        this.response = response;
        for(int i=0;i<response.length();i++)
        {
            try {
                jsonObject = response.getJSONObject(i);
                ProjectsData p1 = new ProjectsData(jsonObject.getString("VENTUREID"),
                        jsonObject.getString("TITLE"), jsonObject.getString("LINK"),
                        jsonObject.getString("SECTORS"), jsonObject.getString("IMAGE"),
                        jsonObject.getString("EnqyLink"), jsonObject.getString("longitude"),
                        jsonObject.getString("latitude"), jsonObject.getString("totcount"),
                        jsonObject.getString("avl_count"),jsonObject.getString("allo_count"),
                        jsonObject.getString("mort_count"),jsonObject.getString("regs_count"),
                        jsonObject.getString("rese_count"),jsonObject.getString("facing"));
                projectsData.add(p1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setProjectsData(ArrayList<ProjectsData> projectsData) {
        this.projectsData = projectsData;
    }

    public ArrayList<ProjectsData> getProjectsData() {
        return projectsData;
    }
}
