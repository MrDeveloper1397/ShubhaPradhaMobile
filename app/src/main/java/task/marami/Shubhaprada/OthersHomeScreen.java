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
import task.marami.Shubhaprada.Fragments.VacantList;
import task.marami.Shubhaprada.Interfaces.ILogout;
import task.marami.Shubhaprada.Presenters.LogoutPresenter;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;
import task.marami.Shubhaprada.Utils.StoragePrefer;

public class OthersHomeScreen extends AppCompatActivity  implements ILogout.view {
    private HomeContent homeContent;
    private LayoutList layoutList;
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
                case R.id.nav_home_new:
                    prename = "Home";
                    item.setIcon(R.drawable.ic_bottom_home);
                    homeContent = HomeContent.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container_new, homeContent)
                            .commit();
                    return true;
                case R.id.nav_layouts_new:
                    prename = "projects";
                    item.setIcon(R.drawable.ic_slide_layouts);
                    layoutList = layoutList.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container_new, layoutList)
                            .commit();
                    return true;
                case R.id.nav_vacantList_new:
                    prename = "VacantList";
                    item.setIcon(R.drawable.ic_slide_layouts);
                    vacantList = VacantList.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container_new, vacantList)
                            .commit();
                    return true;
                case R.id.nav_more_new:
                    prename = "More";
                    PopupMenu popup = new PopupMenu(OthersHomeScreen.this, findViewById(R.id.nav_more_new));
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
                                        //e.toString();
                                    }
                                    return true;

                                case R.id.menu_contact:
                                    startActivity(new Intent(OthersHomeScreen.this, ContactAct.class));
                                    return true;
                                case R.id.menu_logout:
                                    onSuccess();
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
        setContentView(R.layout.activity_others_home_screen);
        sp = new StoragePrefer(this);
        navView = findViewById(R.id.nav_view_new);
        String usertype = sp.getSprString(Contents.PTEF_KEY_USER_TYPE);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        prename = "Home";
        presenter = new LogoutPresenter(this, this);
        homeContent = HomeContent.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container_new, homeContent)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_action_menu, menu);
        if (sp.getSprString(Contents.PTEF_KEY_USER_TYPE).equals("others")) {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (!prename.equals("Home")) {
            homeContent = new HomeContent();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container_new, homeContent).commit();
            prename = "Home";
        } else {
            final AlertDialog User_alert = new AlertDialog.Builder(OthersHomeScreen.this).create();
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
        SingletonAlertDialog.alertDialogShow(OthersHomeScreen.this,msg);
    }

    @Override
    public void onSuccess() {
        sp.sprStoreString(Contents.PREF_KEY_API_TOKEN, "");
        sp.sprStoreString(Contents.PTEF_KEY_USER_TYPE, "");
        sp.sprStoreBoolean(Contents.PREF_KEY_AUTH_VALUE, false);
        startActivity(new Intent(this, RegType.class));
        this.finish();
    }
}