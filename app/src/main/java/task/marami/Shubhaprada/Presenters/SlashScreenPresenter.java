package task.marami.Shubhaprada.Presenters;

import android.content.Context;
import task.marami.Shubhaprada.Interfaces.ISlashScreen;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.StoragePrefer;

public class SlashScreenPresenter implements ISlashScreen.SlashScreenPresenter {
        Context context;
        ISlashScreen.SlashScreenView object;

public SlashScreenPresenter(Context context, ISlashScreen.SlashScreenView object) {
        this.context = context;
        this.object = object;
        }

@Override
public void onLoad() {
        StoragePrefer sp=new StoragePrefer(context);
        if(sp.getSprBoolean(Contents.PREF_KEY_AUTH_VALUE))
        {
        if(sp.getSprString(Contents.PTEF_KEY_USER_TYPE).equals("others")){
        object.onOthersHomeScr();
        }else {
        object.onHomeScr();
        }
        }
        else
        {
        // object.onHomeBeforeLogin();
        object.onLoginScr();
        }
        }
}
