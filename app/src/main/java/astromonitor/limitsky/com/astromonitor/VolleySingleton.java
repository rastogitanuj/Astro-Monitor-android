package astromonitor.limitsky.com.astromonitor;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Nikit on 08-04-2015.
 */
public class VolleySingleton {
    private static VolleySingleton volleyInstance=null;
    private RequestQueue requestQueue;
    private VolleySingleton(){

        requestQueue= Volley.newRequestQueue(MyApplicationContext.getAppContext());

    }
    public static VolleySingleton getInstance(){

        if(volleyInstance==null){
            volleyInstance=new VolleySingleton();
        }
        return volleyInstance;
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }


}
