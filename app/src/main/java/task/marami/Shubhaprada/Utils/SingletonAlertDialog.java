package task.marami.Shubhaprada.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import task.marami.Shubhaprada.R;

public class SingletonAlertDialog {
    public static void alertDialogShow(Context context, String message)
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setTitle(R.string.title_activity_main);
        alertDialog.setCancelable(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
