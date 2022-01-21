package task.marami.Shubhaprada.Utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton sInstance;
    private RequestQueue requestQueue;
    private static Context sContext;

    private MySingleton(Context context)
    {
        this.sContext=context;
        requestQueue=getRequestQueue();
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(sContext.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getsInstance(Context context)
    {
        if(sInstance==null)
        {
            sInstance=new MySingleton(context);
        }
        return sInstance;
    }
    public<T> void addRequest(Request<T> request)
    {
        requestQueue.add(request);
    }
}
