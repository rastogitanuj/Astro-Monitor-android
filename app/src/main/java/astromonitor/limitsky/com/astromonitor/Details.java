package astromonitor.limitsky.com.astromonitor;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;


public class Details extends ActionBarActivity {
    LineChartData graphData;
    LineChartView lineChartView;
    private TextView SourceName;
    private TextView SourceType;
    private TextView RA;
    private TextView today;
    private TextView dec;
    private TextView tenday;
    private TextView yesterday;
    private TextView mean;
    private TextView peak;
    private TextView days;
    private TextView lastday;
    private TextView heading;
    private ImageView back;
    LineChartData data;
    private static final String KEY_Y="flux";
    private static final String KEY_X="day";
    private static final String KEY_RESULTS="source";
    List<PointValue> values = new ArrayList<PointValue>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        SourceName=(TextView)findViewById(R.id.sourceName);
        SourceType=(TextView)findViewById(R.id.sourceType);
        RA=(TextView)findViewById(R.id.ra);
        today=(TextView)findViewById(R.id.today);
        heading=(TextView)findViewById(R.id.textView6);
        dec=(TextView)findViewById(R.id.dec);
        tenday=(TextView)findViewById(R.id.tendays);
        yesterday=(TextView)findViewById(R.id.yesterday);
        mean=(TextView)findViewById(R.id.mean);
        peak=(TextView)findViewById(R.id.peak);
        days=(TextView)findViewById(R.id.days);
        lastday=(TextView)findViewById(R.id.lastdays);
        back=(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
             overridePendingTransition(R.anim.animate4,R.anim.animate3);
            }
        });
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/robotoregular.ttf");
        SourceType.setText(getIntent().getExtras().getString("type").toString());
        SourceName.setText(getIntent().getExtras().getString("name").toString());
        if(getIntent().getExtras().getString("ra").toString().equals("-"))
            RA.setText(getIntent().getExtras().getString("ra").toString());
        else
            RA.setText(getIntent().getExtras().getString("ra").toString()+" J2000 Deg");

        if(getIntent().getExtras().getString("today").toString().equals("-"))
            today.setText(getIntent().getExtras().getString("today").toString());
        else
            today.setText(getIntent().getExtras().getString("today").toString()+" mCrab");

        if(getIntent().getExtras().getString("dec").toString().equals("-"))
            dec.setText(getIntent().getExtras().getString("dec").toString());
        else
            dec.setText(getIntent().getExtras().getString("dec").toString()+" J2000 Deg");

        if(getIntent().getExtras().getString("tendays").toString().equals("-"))
            tenday.setText(getIntent().getExtras().getString("tendays").toString());
        else
            tenday.setText(getIntent().getExtras().getString("tendays").toString()+" mCrab");

        if(getIntent().getExtras().getString("yesterday").toString().equals("-"))
            yesterday.setText(getIntent().getExtras().getString("yesterday").toString());
        else
            yesterday.setText(getIntent().getExtras().getString("yesterday").toString()+" mCrab");

        if(getIntent().getExtras().getString("mean").toString().equals("-"))
            mean.setText(getIntent().getExtras().getString("mean").toString());
        else
            mean.setText(getIntent().getExtras().getString("mean").toString()+" mCrab");

        if(getIntent().getExtras().getString("peak").toString().equals("-"))
            peak.setText(getIntent().getExtras().getString("peak").toString());
        else
            peak.setText(getIntent().getExtras().getString("peak").toString()+" mCrab");

        days.setText(getIntent().getExtras().getString("days").toString());
        lastday.setText(getIntent().getExtras().getString("lastday").toString());

        //set typeface
        yesterday.setTypeface(font);
        days.setTypeface(font);
        lastday.setTypeface(font);
        peak.setTypeface(font);
        mean.setTypeface(font);
        tenday.setTypeface(font);
        heading.setTypeface(font);
        dec.setTypeface(font);
        today.setTypeface(font);
        RA.setTypeface(font);
        SourceName.setTypeface(font);
        SourceType.setTypeface(font);
        lineChartView=(LineChartView)findViewById(R.id.chart);
        lineChartView.setMaxZoom(15);
        lineChartView.setInteractive(true);
        sendJsonRequest();
    }

    //function to send JSON request by adding it to the request queue(using JSON Volley)
    private void sendJsonRequest(){
        RequestQueue requestQueue= VolleySingleton.getInstance().getRequestQueue();
        String sourcename = (String) SourceName.getText();
        String finalsourcename = "";
        for (char a : sourcename.toCharArray()){
           if(a == '+')
               finalsourcename += 'p';
           else if(a == ' ');
           else
               finalsourcename += a;
        }
        JsonObjectRequest request=new JsonObjectRequest(com.android.volley.Request.Method.GET,"http://astromonitorapp.appspot.com/graphdata?sourcename="+finalsourcename,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){
                //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                parseJSONResponse(response);
                lineChartView.setLineChartData(data);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //lineChartView.setLineChartData(data);
                Toast.makeText(getApplicationContext(), "Sorry could not load results due to an error", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }

    //main code for parsing the JSON once we get the response.
    private void  parseJSONResponse(JSONObject response){
        if(response==null ||response.length()==0 ){
            return;
        }
        try {

            JSONArray results=response.getJSONArray(KEY_RESULTS);
            for(int i=0;i<results.length();i++){
                JSONObject currentSource=results.getJSONObject(i);
                values.add(new PointValue(Float.parseFloat(currentSource.getString(KEY_X)),Float.parseFloat(currentSource.getString(KEY_Y))));
            }
            Line line = new Line(values).setColor(Color.parseColor("#37474f")).setCubic(true);
            List<Line> lines = new ArrayList<Line>();
            lines.add(line);
            data = new LineChartData();
            data.setLines(lines);
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            axisX.setName("days(MGD units)");
            axisY.setName("Flux(mCrab)");
            axisX.setTextColor(Color.parseColor("#FF9800"));
            axisY.setTextColor(Color.parseColor("#FF9800"));
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
            data.setBaseValue(Float.NEGATIVE_INFINITY);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.animate4,R.anim.animate3);
    }
}
