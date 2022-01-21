package task.marami.Shubhaprada.Utils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class VolleyErrorHandler {
    public static String onVolleyError(VolleyError error)
    {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
           return  "Slow Network Connection";
        } else if (error instanceof AuthFailureError) {
            return "Server Authentication Failure";
        } else if (error instanceof ServerError) {
            return "Server Busy Please Try Again After Some Time";
        } else if (error instanceof NetworkError) {
            return "Please Check Your NetWork Connection";
        }
        return "Please Try Again After Some Time";
    }
}
