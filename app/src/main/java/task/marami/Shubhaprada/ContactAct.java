package task.marami.Shubhaprada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import task.marami.Shubhaprada.Interfaces.IContactUs;
import task.marami.Shubhaprada.Presenters.ContactUsPresernter;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;

public class ContactAct extends AppCompatActivity implements IContactUs.view {
TextView tv1,tv2,tv3,tv4,tv5,tvcompanyname;
ContactUsPresernter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        tv1 = findViewById(R.id.textView1);
        tv2 = findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView3);
        tv4 = findViewById(R.id.textView4);
        tv5 = findViewById(R.id.textView5);

        tvcompanyname = findViewById(R.id.tvCompanyName);
        presenter = new ContactUsPresernter(this, this);
        if(ConnectionDirectory.isConnected(getApplicationContext())) {
            presenter.onLoadCompanyAddress();
        }else
        {
            Snackbar.make(getCurrentFocus(), Contents.No_Internet, Snackbar.LENGTH_LONG)
                    .setAction("Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                        }
                    }).show();
        }
    }

    @Override
    public void onStartprog() {

    }

    @Override
    public void onStopProg() {

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void getCompanyAddress(JSONObject response) {
        try {
            tvcompanyname.setText(response.getString("Names"));
            String address = response.getString("Address1");
            List<String> addresslist = Arrays.asList(address.split(","));
            tv1.setText(addresslist.get(0));
            tv2.setText(addresslist.get(1));
            tv3.setText(addresslist.get(2));
            tv4.setText(addresslist.get(3));
            tv5.setText(addresslist.get(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }*/
}