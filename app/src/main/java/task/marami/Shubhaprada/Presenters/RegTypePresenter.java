package task.marami.Shubhaprada.Presenters;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import task.marami.Shubhaprada.Interfaces.IRegType;
import task.marami.Shubhaprada.Models.EmployeeReg;
import task.marami.Shubhaprada.Models.UserData;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.MySingleton;
import task.marami.Shubhaprada.Utils.StoragePrefer;
import task.marami.Shubhaprada.Utils.ValidatorData;
import task.marami.Shubhaprada.Utils.VolleyErrorHandler;


public class RegTypePresenter implements IRegType.RegTypePresenter {
    Context context;
    IRegType.RegTypeView regTypeView;
    UserData userData;
    EmployeeReg employeeReg;
    private StoragePrefer sp;

    public RegTypePresenter(Context context, IRegType.RegTypeView regTypeView) {
        this.context = context;
        this.regTypeView = regTypeView;
    }

    @Override
    public void onUserReg(String AgentId) {
        if (AgentId.trim().isEmpty()) {
            regTypeView.onError("Please Enter Agent Id");
        } else {
            String Url = Contents.BASE_URL + "User_Reg.php?AgentCd=" + AgentId;
            employeeReg = new EmployeeReg(AgentId, null, null);
            StoragePrefer sp = new StoragePrefer(context);
            sp.sprStoreString(Contents.PTEF_KEY_USERID, AgentId);
            sp.sprStoreString(Contents.PTEF_KEY_USER_TYPE, "user");
            Log.d("UserUrl", Url);
            ServerConnection(Url, "UserReg");
        }
    }
    @Override
    public void OtherReg(UserData user) {
        userData=user;
        if(!ValidatorData.nameValidator(userData.getName()))
        {
            regTypeView.onError("Name Should Be in 4 to 16 characters");
        }
        else if(!ValidatorData.numberValidator(userData.getNumber()))
        {
            regTypeView.onError("Please Enter Valid Mobile Number");
        }
        else
        {
            String Url= Contents.BASE_URL+"OthersReg?User_Name="+user.getName().toString()+"&User_Mob="+user.getNumber().toString()+"&Company_Id="+Contents.company_id;
            ServerConnection(Url,"OthersReg");
        }
    }
    @Override
    public void onEmpProcess(String id) {
        if (id.trim().isEmpty()) {
            regTypeView.onError("Please Enter Employee Id");
        } else {
            employeeReg = new EmployeeReg(id, null, null);
            String Url = Contents.BASE_URL + "get_Emp_data?EmpId=" + id;
            sp = new StoragePrefer(context);
            sp.sprStoreString(Contents.PTEF_KEY_USERID,id);
            sp.sprStoreString(Contents.PTEF_KEY_USER_TYPE, "Employee");
            ServerConnection(Url, "Process");
        }
    }
    @Override
    public void onEmpReg(EmployeeReg emp) {
        this.employeeReg = emp;
        StoragePrefer sc = new StoragePrefer(context.getApplicationContext());
        if (sc.getSprString(Contents.PTEF_KEY_USER_TYPE).equals("user")) {
            sc.sprStoreString(Contents.PREF_KEY_CURR_NAME, emp.getName().trim());
            String Url = Contents.BASE_URL + "Agent_Reg.php?Emp_No=" + emp.getId().trim() + "&Emp_Name=" + emp.getName().trim() + "&Emp_Mob=" + emp.getMobile().trim();
            Log.d("Url", Url);
            ServerConnection(Url, "Register");
        } else {
            sc.sprStoreString(Contents.PREF_KEY_CURR_NAME, emp.getName().trim());
            String Url = Contents.BASE_URL + "Employee_reg.php?Emp_No=" + emp.getId().trim() + "&Emp_Name=" + emp.getName().trim() + "&Emp_Mob=" + emp.getMobile().trim();
            ServerConnection(Url, "Register");
        }
    }

    void ServerConnection(String Url, final String callback) {
        regTypeView.onstartprog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        regTypeView.onStopProg();
                        StoragePrefer sc = new StoragePrefer(context.getApplicationContext());
                        switch (callback) {
                            case "UserReg":
                                onProcessSuccess(response, "user");
                                sc.sprStoreString(Contents.PTEF_KEY_USER_TYPE, "user");
                                break;
                            case "Process":
                                onProcessSuccess(response, "emp");
                                sc.sprStoreString(Contents.PTEF_KEY_USER_TYPE, "Employee");
                                break;
                            case "OthersReg":
                                onOthersResponse(response);
                                sc.sprStoreString(Contents.PTEF_KEY_USER_TYPE, "others");
                                break;
                            case "Register":
                                onRegisterSuccess(response);
                                break;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                regTypeView.onStopProg();
                regTypeView.onError(VolleyErrorHandler.onVolleyError(error));
            }
        });
        MySingleton.getsInstance(context).getRequestQueue().add(stringRequest);
    }
    void onOthersResponse(String response) {
        if (response.trim().equals("fail")) {
            regTypeView.onError(Contents.SERVER_RETRY);
        } else {
            String phase[] = response.split(",");
            StoragePrefer sc = new StoragePrefer(context.getApplicationContext());
            sc.sprStoreString(Contents.PREF_KEY_API_TOKEN, phase[0]);
            sc.sprStoreString(Contents.PTEF_KEY_USER_TYPE, "others");
            sc.sprStoreBoolean(Contents.PREF_KEY_AUTH_VALUE, true);
            sc.sprStoreString(Contents.PREF_KEY_CURR_MOBILE, userData.getNumber());
            regTypeView.onUserRegSuccess(phase[1]);
        }
    }
    void onProcessSuccess(String response, String usertype) {
        if (response.equals("not_employee")) {
            regTypeView.onError("Your Id Dosen't Exist");
        } else if (response.equals("no_number")) {
            regTypeView.onError("Please Contact Office Mobile Number Dosen't Exist");
        } else {
            String phase[] = response.split(",");
            employeeReg.setName(phase[0]);
            employeeReg.setMobile(phase[1]);
            StoragePrefer sc = new StoragePrefer(context.getApplicationContext());
            sc.sprStoreString(Contents.PREF_KEY_CURR_MOBILE, phase[1]);
            sc.sprStoreString(Contents.PTEF_KEY_USER_TYPE,usertype);
            regTypeView.onEmpProcessSuccess(employeeReg, usertype);
        }
    }

    void onRegisterSuccess(String response) {
        if (response.equals("fail")) {
            regTypeView.onError(Contents.SERVER_RETRY);
        } else if (response.equals("not_employee")) {
            regTypeView.onError("Please Contact Office");
        } else {
            String phase[] = response.split(",");
            StoragePrefer sc = new StoragePrefer(context.getApplicationContext());
            sc.sprStoreString(Contents.PREF_KEY_API_TOKEN, phase[0]);
            regTypeView.onEmpRegSuccess(phase[1]);
        }
    }
}
