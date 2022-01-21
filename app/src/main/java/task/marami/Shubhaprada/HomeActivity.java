package task.marami.Shubhaprada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import task.marami.Shubhaprada.Fragments.HomeContent;
import task.marami.Shubhaprada.Fragments.LayoutList;
import task.marami.Shubhaprada.Fragments.Profile;
import task.marami.Shubhaprada.Fragments.VacantList;
import task.marami.Shubhaprada.Interfaces.ILogout;
import task.marami.Shubhaprada.Presenters.LogoutPresenter;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;
import task.marami.Shubhaprada.Utils.StoragePrefer;

public class HomeActivity extends AppCompatActivity implements ILogout.view {
    private HomeContent home;
    private LayoutList layoutList;
    private Profile profile;
    private VacantList vacantList;
    String prename;
    StoragePrefer sp;
    BottomNavigationView navView;
    LogoutPresenter presenter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    //  setTitle("Home");
                    prename = "Home";
                    item.setIcon(R.drawable.ic_bottom_home);
                    home = HomeContent.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, home)
                            .commit();
                    return true;
                case R.id.nav_profile:
                    //  setTitle("Profile");
                    prename = "profile";
                    item.setIcon(R.drawable.ic_profile);
                    profile = Profile.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, profile)
                            .commit();
                    return true;
                case R.id.nav_layouts:
                    // setTitle("Layouts");
                    prename = "projects";
                    item.setIcon(R.drawable.ic_slide_layouts);
                    layoutList = layoutList.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, layoutList)
                            .commit();
                    return true;
                case R.id.nav_vacantList:
                    // setTitle("VacantList");
                    prename = "VacantList";
                    item.setIcon(R.drawable.ic_slide_layouts);
                    vacantList = VacantList.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, vacantList)
                            .commit();
                    return true;
                case R.id.nav_more:
                    prename = "More";
                    PopupMenu popup = new PopupMenu(HomeActivity.this, findViewById(R.id.nav_more));
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.more_options_menu, popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.menu_share:
                                    try {
                                        Intent i = new Intent(Intent.ACTION_SEND);
                                        i.setType("text/plain");
                                        i.putExtra(Intent.EXTRA_SUBJECT, "Assured Properties.");
                                        String sAux = "\nLet me recommend you this application\n\n";
                                        sAux = sAux + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n";
                                        i.putExtra(Intent.EXTRA_TEXT, sAux);
                                        startActivity(Intent.createChooser(i, "choose one"));
                                    } catch (Exception e) {
                                    }
                                    return true;

                                case R.id.menu_contact:
                                    startActivity(new Intent(HomeActivity.this, ContactAct.class));
                                    return true;
                                case R.id.menu_logout:

                                    sp.sprStoreString(Contents.PREF_KEY_API_TOKEN,"");
                                    sp.sprStoreString(Contents.PTEF_KEY_USER_TYPE,"");
                                    sp.sprStoreBoolean(Contents.PREF_KEY_AUTH_VALUE,false);
                                    startActivity(new Intent(HomeActivity.this,RegType.class));
                                    finish();
                                    return true;
                            }
                            return true;
                        }
                    });
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sp = new StoragePrefer(this);
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        prename = "Home";
        presenter = new LogoutPresenter(this, this);
        home = HomeContent.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, home)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_action_menu, menu);
        if (sp.getSprString(Contents.PTEF_KEY_USER_TYPE).equals("user")) {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_reservation:
                startActivity(new Intent(this, PlotReservation.class));
                return true;
            case R.id.menu_admin:
                startActivity(new Intent(this, AdminPin.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (!prename.equals("Home")) {
            home = new HomeContent();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, home).commit();
            prename = "Home";
        } else {
            final AlertDialog User_alert = new AlertDialog.Builder(HomeActivity.this).create();
            User_alert.setTitle("Alert Message");
            User_alert.setMessage("Do You Want To Exit ?");
            User_alert.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveTaskToBack(true);
                            finish();
                        }
                    });
            User_alert.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            User_alert.show();
        }
    }

    @Override
    public void onStartProg() {

    }

    @Override
    public void onStopProg() {

    }

    @Override
    public void OnError(String msg) {
        SingletonAlertDialog.alertDialogShow(HomeActivity.this,msg);
    }

    @Override
    public void onSuccess() {
        sp.sprStoreString(Contents.PREF_KEY_API_TOKEN,"");
        sp.sprStoreString(Contents.PTEF_KEY_USER_TYPE,"");
        sp.sprStoreBoolean(Contents.PREF_KEY_AUTH_VALUE,false);
        startActivity(new Intent(this,RegType.class));
        this.finish();
    }
}