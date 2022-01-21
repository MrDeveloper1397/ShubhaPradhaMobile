package task.marami.Shubhaprada;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import task.marami.Shubhaprada.Interfaces.IRegType;
import task.marami.Shubhaprada.Models.EmployeeReg;
import task.marami.Shubhaprada.Models.UserData;
import task.marami.Shubhaprada.Presenters.RegTypePresenter;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;

public class RegType extends AppCompatActivity
        implements View.OnClickListener,IRegType.RegTypeView {

    Button btnUserSave, btnEmpProcc, btnEmpReg, btnUserType, btnEmpType,btnOthers;
    EditText editUserName, editUserMobile, editEmpId, editEmpName, editEmpMobile;
    TextView txtUserTitle, txtEmpTitle, txtAlertMsg,txtOthersTitle;
    ProgressBar progressBar;
    RegTypePresenter regTypePresenter;
    EmployeeReg empreg;
    View view;
    String status="user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_type);
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimary)));
        }
        init();
        regTypePresenter = new RegTypePresenter(this, this);
    }

    void init() {
        btnUserType = (Button) findViewById(R.id.btn_type_user);
        btnEmpType = (Button) findViewById(R.id.btn_type_emp);
        btnOthers = findViewById(R.id.btn_type_others);
        txtUserTitle = (TextView) findViewById(R.id.txt_user_reg_title);
        txtEmpTitle = (TextView) findViewById(R.id.txt_emp_reg_title);
        txtOthersTitle = findViewById(R.id.txt_guest_reg_title);
        // txtAlertMsg = (TextView) findViewById(R.id.txt_emp_alert_msg);
        editUserName = (EditText) findViewById(R.id.edt_user_name);
        editUserMobile = (EditText) findViewById(R.id.edt_user_phone);
        editEmpId = (EditText) findViewById(R.id.edt_emp_id);
        editEmpName = (EditText) findViewById(R.id.edt_emp_name);
        editEmpMobile = (EditText) findViewById(R.id.edt_emp_phone);
        btnUserSave = (Button) findViewById(R.id.btn_user_save);
        btnEmpProcc = (Button) findViewById(R.id.btn_emp_procced);
        btnEmpReg = (Button) findViewById(R.id.btn_emp_register);
        progressBar = (ProgressBar) findViewById(R.id.prog_reg_type);

        btnUserType.setOnClickListener(this);
        btnEmpType.setOnClickListener(this);
        btnUserSave.setOnClickListener(this);
        btnEmpProcc.setOnClickListener(this);
        btnEmpReg.setOnClickListener(this);
        btnOthers.setOnClickListener(this);
        onUserActive();
    }

    @Override
    public void onClick(View v) {
        view = v;
        switch (v.getId()) {
            case R.id.btn_type_user:
                status = "user";
                onUserActive();
                break;
            case R.id.btn_type_emp:
                onEmpActive();
                break;
            case R.id.btn_type_others:
                status ="others";
                onOthersActive();
                break;
            case R.id.btn_user_save:
                if(status.equals("user")) {
                    regTypePresenter.onUserReg(editEmpId.getText().toString().trim());
                }
                else{
                    UserData userData = new UserData(editUserName.getText().toString().trim(),editUserMobile.getText().toString().trim());
                    regTypePresenter.OtherReg(userData);
                }
                break;
            case R.id.btn_emp_procced:
                regTypePresenter.onEmpProcess(editEmpId.getText().toString());
                break;
            case R.id.btn_emp_register:
                EmployeeReg emp = new EmployeeReg(editEmpId.getText().toString(), editEmpName.getText().toString(), empreg.getMobile());
                regTypePresenter.onEmpReg(emp);
                break;
        }
    }

    void onUserActive() {
        onAllVisability();
        btnUserType.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnUserType.setTextColor(Color.WHITE);
        btnEmpType.setBackgroundColor(Color.TRANSPARENT);
        btnEmpType.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnOthers.setBackgroundColor(Color.TRANSPARENT);
        btnOthers.setTextColor(getResources().getColor(R.color.colorPrimary));
        txtOthersTitle.setVisibility(View.GONE);
        txtEmpTitle.setVisibility(View.GONE);
        txtUserTitle.setVisibility(View.VISIBLE);
        editEmpId.setText("");
        editEmpId.setVisibility(View.VISIBLE);
        btnUserSave.setVisibility(View.VISIBLE);
    }
    void onOthersActive() {
        onAllVisability();
        btnOthers.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnOthers.setTextColor(Color.WHITE);
        btnEmpType.setBackgroundColor(Color.TRANSPARENT);
        btnEmpType.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnUserType.setBackgroundColor(Color.TRANSPARENT);
        btnUserType.setTextColor(getResources().getColor(R.color.colorPrimary));
        txtEmpTitle.setVisibility(View.GONE);
        txtUserTitle.setVisibility(View.GONE);
        txtOthersTitle.setVisibility(View.VISIBLE);
        editUserName.setVisibility(View.VISIBLE);
        editUserMobile.setVisibility(View.VISIBLE);
        btnUserSave.setVisibility(View.VISIBLE);
    }
    void onEmpActive() {
        onAllVisability();
        txtEmpTitle.setVisibility(View.VISIBLE);
        btnEmpType.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnEmpType.setTextColor(Color.WHITE);
        btnUserType.setBackgroundColor(Color.TRANSPARENT);
        btnUserType.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnOthers.setBackgroundColor(Color.TRANSPARENT);
        btnOthers.setTextColor(getResources().getColor(R.color.colorPrimary));
        txtEmpTitle.setVisibility(View.VISIBLE);
        editEmpId.setVisibility(View.VISIBLE);
        editEmpId.setText("");
        btnEmpProcc.setVisibility(View.VISIBLE);
        txtUserTitle.setVisibility(View.GONE);
        txtOthersTitle.setVisibility(View.GONE);
    }

    void onAllVisability() {
        txtEmpTitle.setVisibility(View.GONE);
        editEmpId.setVisibility(View.GONE);
        btnEmpProcc.setVisibility(View.GONE);
        txtUserTitle.setVisibility(View.GONE);
        editUserName.setVisibility(View.GONE);
        editUserMobile.setVisibility(View.GONE);
        btnUserSave.setVisibility(View.GONE);
        //  txtAlertMsg.setVisibility(View.GONE);
        editEmpName.setVisibility(View.GONE);
        editEmpMobile.setVisibility(View.GONE);
        btnEmpReg.setVisibility(View.GONE);
        txtOthersTitle.setVisibility(View.GONE);
    }

    @Override
    public void onstartprog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopProg() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onUserRegSuccess(String response) {
        Intent nextScr = new Intent(this, OTPActivity.class);
        nextScr.putExtra("OtpValue", response);
        startActivity(nextScr);
    }

    @Override
    public void onEmpProcessSuccess(EmployeeReg emp, String userType) {
        empreg = emp;
        onAllVisability();
        if (userType.equals("user")) {
            txtEmpTitle.setVisibility(View.GONE);
            txtUserTitle.setVisibility(View.VISIBLE);
        } else {
            txtEmpTitle.setVisibility(View.VISIBLE);
        }

        editEmpId.setVisibility(View.VISIBLE);
        editEmpId.setText(emp.getId());
        editEmpId.setEnabled(false);
        editEmpName.setText(emp.getName());
        editEmpName.setVisibility(View.VISIBLE);
        editEmpName.setEnabled(false);
        editEmpMobile.setText(emp.getMobile().charAt(0) + "XXXXXX" + emp.getMobile().charAt(7) + emp.getMobile().charAt(8) + emp.getMobile().charAt(9));
        editEmpMobile.setVisibility(View.VISIBLE);
        editEmpMobile.setEnabled(false);
        // txtAlertMsg.setVisibility(View.VISIBLE);
        btnEmpReg.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEmpRegSuccess(String response) {
        Intent nextScr = new Intent(this, OTPActivity.class);
        nextScr.putExtra("OtpValue", response);
        startActivity(nextScr);
    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(RegType.this,message);
    }
}