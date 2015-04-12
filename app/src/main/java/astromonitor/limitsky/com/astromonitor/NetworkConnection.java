package astromonitor.limitsky.com.astromonitor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Nikit on 30-03-2015.
 This class contains all the basic funtionalities required for the network connection.
 */
public class NetworkConnection {
/*
    function to check both WIFI and Mobile Connection.
    Fetch the network info from the mobile.

 */
    public static boolean haveNetworkConnection(Context ctx) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
/*
    function to handle poor network connection
    Try connecting to google. if ping is successful within 3 seconds then active else inactive.
*/
    public   static  boolean   checkActiveNetworkConnection() {
        try {
            HttpURLConnection urlc = (HttpURLConnection)(new URL("http://www.google.com").openConnection());
            urlc.setRequestProperty("User-Agent", "Test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(3000);
            urlc.connect();
            return (urlc.getResponseCode() == 200);

        } catch (IOException e) {
        //log("IOException in connectGoogle())");
            return false;
        }
    }


}
