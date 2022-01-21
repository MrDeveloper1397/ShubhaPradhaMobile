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

import task.marami.Shubhaprada.Interfaces.IPinChange;
import task.marami.Shubhaprada.Presenters.PinChangePresenter;
import task.marami.Shubhaprada.Utils.AdminPinEncription;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;

public class PinChange extends AppCompatActivity
        implements View.OnClickListener, IPinChange.PinChangeView{
    private EditText edtone,edttwo,edtthree,edtfour;
    private EditText reedtone,reedttwo,reedtthree,reedtfour;
    ImageButton img_btn_clear;
    private Button img_btn_one,img_btn_two,img_btn_three,img_btn_four,img_btn_five,img_btn_six,img_btn_seven,img_btn_eight,img_btn_nine,img_btn_zero,img_btn_ok;
    private ProgressBar progressBar;
    PinChangePresenter pinChangePresenter;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_change);
        edtone=(EditText) findViewById(R.id.edtone);
        edttwo=(EditText) findViewById(R.id.edttwo);
        edtthree=(EditText) findViewById(R.id.edtthree);
        edtfour=(EditText) findViewById(R.id.edtfour);
        view = getWindow().getDecorView();
        edtone.setEnabled(false);
        edttwo.setEnabled(false);
        edtfour.setEnabled(false);
        edtthree.setEnabled(false);

        reedtone=(EditText) findViewById(R.id.reedtone);
        reedttwo=(EditText) findViewById(R.id.reedttwo);
        reedtthree=(EditText) findViewById(R.id.reedtthree);
        reedtfour=(EditText) findViewById(R.id.reedtfour);

        reedtone.setEnabled(false);
        reedttwo.setEnabled(false);
        reedtfour.setEnabled(false);
        reedtthree.setEnabled(false);

        img_btn_one = (Button) findViewById(R.id.img_btn_one);
        img_btn_one.setOnClickListener(this);
        img_btn_two = (Button) findViewById(R.id.img_btn_two);
        img_btn_two.setOnClickListener(this);
        img_btn_three = (Button) findViewById(R.id.img_btn_three);
        img_btn_three.setOnClickListener(this);
        img_btn_four = (Button) findViewById(R.id.img_btn_four);
        img_btn_four.setOnClickListener(this);
        img_btn_five = (Button) findViewById(R.id.img_btn_five);
        img_btn_five.setOnClickListener(this);
        img_btn_six = (Button) findViewById(R.id.img_btn_six);
        img_btn_six.setOnClickListener(this);
        img_btn_seven = (Button) findViewById(R.id.img_btn_seven);
        img_btn_seven.setOnClickListener(this);
        img_btn_eight = (Button) findViewById(R.id.img_btn_eight);
        img_btn_eight.setOnClickListener(this);
        img_btn_nine = (Button) findViewById(R.id.img_btn_nine);
        img_btn_nine.setOnClickListener(this);
        img_btn_zero = (Button) findViewById(R.id.img_btn_zero);
        img_btn_zero.setOnClickListener(this);
        img_btn_ok = (Button) findViewById(R.id.img_btn_ok);
        img_btn_ok.setOnClickListener(this);
        img_btn_clear=(ImageButton) findViewById(R.id.img_btn_clear);
        img_btn_clear.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.prog_pin_change);
        pinChangePresenter=new PinChangePresenter(this,this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_btn_one:
                pinChangePresenter.onDataEnter("1");
                break;
            case R.id.img_btn_two:
                pinChangePresenter.onDataEnter("2");
                break;
            case R.id.img_btn_three:
                pinChangePresenter.onDataEnter("3");
                break;
            case R.id.img_btn_four:
                pinChangePresenter.onDataEnter("4");
                break;
            case R.id.img_btn_five:
                pinChangePresenter.onDataEnter("5");
                break;
            case R.id.img_btn_six:
                pinChangePresenter.onDataEnter("6");
                break;
            case R.id.img_btn_seven:
                pinChangePresenter.onDataEnter("7");
                break;
            case R.id.img_btn_eight:
                pinChangePresenter.onDataEnter("8");
                break;
            case R.id.img_btn_nine:
                pinChangePresenter.onDataEnter("9");
                break;
            case R.id.img_btn_zero:
                pinChangePresenter.onDataEnter("0");
                break;
            case R.id.img_btn_ok:
                if(ConnectionDirectory.isConnected(this))
                {
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
                pinChangePresenter.onClearData();
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
        startActivity(new Intent(this,AdminMenu.class));
        this.finish();
    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(PinChange.this,message);
    }

    @Override
    public void onEditreOne(String val) {
        reedtone.setText(val);
    }

    @Override
    public void onclearreOne() {
        reedtone.getText().clear();
    }

    @Override
    public void onEditreTwo(String val) {
        reedttwo.setText(val);
    }

    @Override
    public void onclearreTwo() {
        reedttwo.getText().clear();
    }

    @Override
    public void onEditreThree(String val) {
        reedtthree.setText(val);
    }

    @Override
    public void onclearreThree() {
        reedtthree.getText().clear();
    }

    @Override
    public void onEditreFour(String val) {
        reedtfour.setText(val);
    }

    @Override
    public void onclearreFour() {
        reedtfour.getText().clear();
    }
    void onValidation()
    {
        if(edtone.getText().toString().isEmpty()||edttwo.getText().toString().isEmpty()
                ||edtthree.getText().toString().isEmpty()||edtfour.getText().toString().isEmpty()||reedtone.getText().toString().isEmpty()||
                reedttwo.getText().toString().isEmpty()
                ||reedtthree.getText().toString().isEmpty()||reedtfour.getText().toString().isEmpty())
        {
            onError("Please Enter Pin Number");
        }
        else {
            String repin=reedtone.getText().toString()+reedttwo.getText().toString()+reedtthree.getText().toString()+reedtfour.getText().toString();
            String pin = edtone.getText().toString()+edttwo.getText().toString()+edtthree.getText().toString()+edtfour.getText().toString();
            String enpin = AdminPinEncription.getValue(edtone.getText().toString())+
                    AdminPinEncription.getValue(edttwo.getText().toString())+
                    AdminPinEncription.getValue(edtthree.getText().toString())+
                    AdminPinEncription.getValue(edtfour.getText().toString());
            if(pin.equals(repin))
            {
                pinChangePresenter.onStoreNewPin(enpin);
            }
            else
            {
                pinChangePresenter.reclear();
                onError("Pin Number MissMatch");
            }
        }
    }
}
