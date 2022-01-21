package task.marami.Shubhaprada;

import android.content.Intent;

import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import task.marami.Shubhaprada.Interfaces.IAdminPin;
import task.marami.Shubhaprada.Presenters.ChangeAdminPinPresenter;
import task.marami.Shubhaprada.Utils.AdminPinEncription;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;

public class ChangeAdminPin extends AppCompatActivity implements
        View.OnClickListener,IAdminPin.AdminPinView{
    private EditText edtone,edttwo,edtthree,edtfour;
    private ProgressBar progressBar;
    ImageButton img_btn_clear;
    private Button img_btn_one,img_btn_two,img_btn_three,img_btn_four,img_btn_five,img_btn_six,img_btn_seven,img_btn_eight,img_btn_nine,img_btn_zero,img_btn_ok;
        ChangeAdminPinPresenter changeAdminPinPresenter;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_admin_pin);
        edtone=(EditText) findViewById(R.id.edtone);
        edttwo=(EditText) findViewById(R.id.edttwo);
        edtthree=(EditText) findViewById(R.id.edtthree);
        edtfour=(EditText) findViewById(R.id.edtfour);
         view = getWindow().getDecorView();
        edtone.setEnabled(false);
        edttwo.setEnabled(false);
        edtfour.setEnabled(false);
        edtthree.setEnabled(false);

        img_btn_one=(Button) findViewById(R.id.img_btn_one);
        img_btn_one.setOnClickListener(this);
        img_btn_two=(Button) findViewById(R.id.img_btn_two);
        img_btn_two.setOnClickListener(this);
        img_btn_three=(Button) findViewById(R.id.img_btn_three);
        img_btn_three.setOnClickListener(this);
        img_btn_four=(Button) findViewById(R.id.img_btn_four);
        img_btn_four.setOnClickListener(this);
        img_btn_five=(Button) findViewById(R.id.img_btn_five);
        img_btn_five.setOnClickListener(this);
        img_btn_six=(Button) findViewById(R.id.img_btn_six);
        img_btn_six.setOnClickListener(this);
        img_btn_seven=(Button) findViewById(R.id.img_btn_seven);
        img_btn_seven.setOnClickListener(this);
        img_btn_eight=(Button) findViewById(R.id.img_btn_eight);
        img_btn_eight.setOnClickListener(this);
        img_btn_nine=(Button) findViewById(R.id.img_btn_nine);
        img_btn_nine.setOnClickListener(this);
        img_btn_zero=(Button) findViewById(R.id.img_btn_zero);
        img_btn_zero.setOnClickListener(this);
        img_btn_ok=(Button) findViewById(R.id.img_btn_ok);
        img_btn_ok.setOnClickListener(this);
        img_btn_clear=(ImageButton) findViewById(R.id.img_btn_clear);
        img_btn_clear.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.prog_admin_pin);

        changeAdminPinPresenter=new ChangeAdminPinPresenter(this,this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_btn_one:
                changeAdminPinPresenter.onDataEnter("1");
                break;
            case R.id.img_btn_two:
                changeAdminPinPresenter.onDataEnter("2");
                break;
            case R.id.img_btn_three:
                changeAdminPinPresenter.onDataEnter("3");
                break;
            case R.id.img_btn_four:
                changeAdminPinPresenter.onDataEnter("4");
                break;
            case R.id.img_btn_five:
                changeAdminPinPresenter.onDataEnter("5");
                break;
            case R.id.img_btn_six:
                changeAdminPinPresenter.onDataEnter("6");
                break;
            case R.id.img_btn_seven:
                changeAdminPinPresenter.onDataEnter("7");
                break;
            case R.id.img_btn_eight:
                changeAdminPinPresenter.onDataEnter("8");
                break;
            case R.id.img_btn_nine:
                changeAdminPinPresenter.onDataEnter("9");
                break;
            case R.id.img_btn_zero:
                changeAdminPinPresenter.onDataEnter("0");
                break;
            case R.id.img_btn_ok:
                if(ConnectionDirectory.isConnected(this)) {
                    onValidation();
                }
                else
                {
                    Snackbar.make(v, Contents.No_Internet, Snackbar.LENGTH_LONG)
                            .setAction("Settings", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                                }
                            }).show();
                }
                break;
            case R.id.img_btn_clear:
                changeAdminPinPresenter.onClearData();
                break;
        }
    }
    @Override
    public void onStartProg() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopProg() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onEditOne(String val) {
        edtone.setText(val);
    }


    @Override
    public void onclearOne() {
        edtone.getText().clear();
    }

    @Override
    public void onEditTwo(String val) {
        edttwo.setText(val);
    }

    @Override
    public void onclearTwo() {
        edttwo.getText().clear();
    }

    @Override
    public void onEditThree(String val) {
        edtthree.setText(val);
    }

    @Override
    public void onclearThree() {
        edtthree.getText().clear();
    }

    @Override
    public void onEditFour(String val) {
        edtfour.setText(val);
    }

    @Override
    public void onclearFour() {
        edtfour.getText().clear();
    }

    @Override
    public void onSuccess() {
        startActivity(new Intent(this,PinChange.class));
        this.finish();
    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(ChangeAdminPin.this,message);
    }

    @Override
    public void onFiledPin() {
        img_btn_ok.setEnabled(false);
        onValidation();
    }

    void onValidation()
    {
        if(edtone.getText().toString().isEmpty()||edttwo.getText().toString().isEmpty()
                ||edtthree.getText().toString().isEmpty()||edtfour.getText().toString().isEmpty())
        {
            onError("Please Enter Pin Number");
        }
        else {
            String pin = AdminPinEncription.getValue(edtone.getText().toString())+
                    AdminPinEncription.getValue(edttwo.getText().toString())+
                    AdminPinEncription.getValue(edtthree.getText().toString())+
                    AdminPinEncription.getValue(edtfour.getText().toString());
            changeAdminPinPresenter.onAuthentication(pin);
        }
    }
}
