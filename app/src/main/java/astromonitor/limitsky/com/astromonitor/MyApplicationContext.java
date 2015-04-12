package astromonitor.limitsky.com.astromonitor;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;

/**
 * Created by Nikit on 08-04-2015.
 This class is basically an application level class so all the variable declared and defined here can be used throughout the scope of application

 */
public class MyApplicationContext extends Application {

    private static MyApplicationContext appInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance=this;
    }

    public static MyApplicationContext getInstance(){

        return appInstance;
    }

    public static Context getAppContext(){
        return appInstance.getApplicationContext();
    }

}
