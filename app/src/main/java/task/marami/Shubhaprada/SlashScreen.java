package task.marami.Shubhaprada;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import task.marami.Shubhaprada.Interfaces.ISlashScreen;
import task.marami.Shubhaprada.Presenters.SlashScreenPresenter;

public class SlashScreen extends AppCompatActivity implements ISlashScreen.SlashScreenView {
    SlashScreenPresenter slashScreenPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash_screen);
        slashScreenPresenter = new SlashScreenPresenter(this, this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                slashScreenPresenter.onLoad();
            }
        }, 4000);
    }

    @Override
    public void onHomeScr() {
        startActivity(new Intent(this, HomeActivity.class));
        this.finish();
    }

    @Override
    public void onLoginScr() {
        startActivity(new Intent(this, RegType.class));
        this.finish();
    }
    @Override
    public void onOthersHomeScr() {
        startActivity(new Intent(this, OthersHomeScreen.class));
        this.finish();
    }
}
