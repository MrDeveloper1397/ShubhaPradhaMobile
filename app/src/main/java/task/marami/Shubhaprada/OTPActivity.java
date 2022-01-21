package task.marami.Shubhaprada;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import task.marami.Shubhaprada.Interfaces.IOTPAuth;
import task.marami.Shubhaprada.Presenters.OTPPresenter;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;
import task.marami.Shubhaprada.Utils.StoragePrefer;

public class OTPActivity extends AppCompatActivity
        implements View.OnClickListener,IOTPAuth.OTPAuthView{
    EditText edt_no;
    TextView txt_number,TimeCounter,resend;
    LinearLayout otp_liner;
    String Serpin,usertype,apikey;
    OTPPresenter otpPresenter;
    ProgressBar progressBar;
    View view;
    StoragePrefer sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimary)));
        }
        view = getWindow().getDecorView();

        init();
        sp = new StoragePrefer(this);
        apikey = sp.getSprString(Contents.PREF_KEY_API_TOKEN);
        usertype = sp.getSprString(Contents.PTEF_KEY_USER_TYPE);
        otpPresenter=new OTPPresenter(this,this);

    }
    void init()
    {
        edt_no = (EditText) findViewById(R.id.edt_otp_no);
        TimeCounter = (TextView) findViewById(R.id.txt_otp_number_timecount);
        resend = (TextView) findViewById(R.id.txt_otp_resend);
        progressBar = (ProgressBar) findViewById(R.id.prog_otp_resnd);
        txt_number=(TextView) findViewById(R.id.txt_otp_number);
        otp_liner = (LinearLayout) findViewById(R.id.otp_liner);

        findViewById(R.id.btn_otp_manuval).setOnClickListener(this);
        findViewById(R.id.btn_otp_hide).setOnClickListener(this);
        resend.setOnClickListener(this);
        Serpin=getIntent().getStringExtra("OtpValue");
        timer();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_otp_manuval:
                otpPresenter.validation(edt_no.getText().toString(), Serpin);
                break;
            case R.id.btn_otp_hide:
                otpPresenter.validation(Serpin, Serpin);
                break;
            case R.id.txt_otp_resend:
                Log.d("response", Serpin);
                StoragePrefer sc=new StoragePrefer(this);
                Log.d("Apikey", sc.getSprString(Contents.PREF_KEY_API_TOKEN));
                otpPresenter.reSendOtp(Serpin,sc.getSprString(Contents.PREF_KEY_API_TOKEN));
                break;
        }
    }

    @Override
    public void onStartProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onGoHome() {
        sp.sprStoreString(Contents.PTEF_KEY_USER_TYPE, usertype);
        if (sp.getSprString(Contents.PTEF_KEY_USER_TYPE).equals("others")) {
            Intent main_scr = new Intent(this, OthersHomeScreen.class);
            (this).finish();
            main_scr.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(main_scr);
            finish();
        } else {
            Intent main_scr = new Intent(this, HomeActivity.class);
            (this).finish();
            main_scr.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(main_scr);
            finish();
        }
    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(OTPActivity.this,message);
    }

    @Override
    public void onResendSuccess() {
        resend.setText("Otp Sended Successfully");
        resend.setEnabled(false);
    }

    public void timer()
    {
        new CountDownTimer(90000, 1000) {

            public void onTick(long millisUntilFinished) {
                TimeCounter.setText("remaining: " + millisUntilFinished / 1000+ " Seconds");
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                TimeCounter.setVisibility(View.GONE);
                resend.setVisibility(View.VISIBLE);
                resend.setTextColor(Color.RED);
            }

        }.start();
    }
}
