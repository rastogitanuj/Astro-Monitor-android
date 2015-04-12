package astromonitor.limitsky.com.astromonitor;

/*
Author Nikit
Splash screen that displays the app screen layout
 */
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class SplashScreen extends ActionBarActivity {
    TextView MainHeading;
    TextView SubHeading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //create the intent for the new activity
        final Intent intent= new Intent(this,HomePage.class);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/robotolight.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/robotoregular.ttf");
        MainHeading=(TextView)findViewById(R.id.textView);
        SubHeading=(TextView)findViewById(R.id.textView2);
        MainHeading.setTypeface(font);
        SubHeading.setTypeface(font2);
        //creating a thread to maintain the delay
        Thread th = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                }
            }
        };
        th.start();//start the thread
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
