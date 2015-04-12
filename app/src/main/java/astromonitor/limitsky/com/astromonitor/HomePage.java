package astromonitor.limitsky.com.astromonitor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.melnykov.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/*
    Author:Nikit
    Homepage that displays the list of active sources and also displays an option for filters.
    uses the JSON Volley library for JSON fetching from the server and FAB for material Design App.
 */

public class HomePage extends ActionBarActivity {
    private FloatingActionButton fab;
    private ListView galacticSourceList;
    GalacticSourceListAdapter adapter;
    //KEYS for JSON
    private static final String KEY_RESULTS="sources";
    private static final String KEY_SOURCE_NAME="sourcename";
    private static final String KEY_RIGHT_ESSENTIAL="RA";
    private static final String KEY_DECLINATION="Dec";
    private static final String KEY_SOURCE_TYPE="sourcetype";
    private static final String KEY_SOURCE_TODAY="Today";
    private static final String KEY_SOURCE_YESTERDAY="Yesterday";
    private static final String KEY_SOURCE_TENDAY="Tenday";
    private static final String KEY_SOURCE_MEAN="Mean";
    private static final String KEY_SOURCE_PEAK="Peak";
    private static final String KEY_SOURCE_DAYS="Days";
    private static final String KEY_SOURCE_LAST_DAY="Last_Day";
    private float global_min_mean=999999999;
    private float global_min_flux=999999999;
    private float global_max_mean=0;
    private float global_max_flux=0;


    private ArrayList<AstroSources> galacticSourceArrayList=new ArrayList<>();
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //find the views and initialize it
        fab=(FloatingActionButton)findViewById(R.id.fab);
        galacticSourceList=(ListView)findViewById(R.id.galactic_source_listView);

        galacticSourceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(HomePage.this,Details.class);
                intent.putExtra("name",galacticSourceArrayList.get(position).getSourceName());
                intent.putExtra("type",galacticSourceArrayList.get(position).getSourceType());
                intent.putExtra("ra",galacticSourceArrayList.get(position).getRightEssential());
                intent.putExtra("dec",galacticSourceArrayList.get(position).getDeclination());
                intent.putExtra("today",galacticSourceArrayList.get(position).getCurrentFlux());
                intent.putExtra("tendays",galacticSourceArrayList.get(position).getTenday());
                intent.putExtra("yesterday",galacticSourceArrayList.get(position).getYesterday());
                intent.putExtra("mean",galacticSourceArrayList.get(position).getMean());
                intent.putExtra("peak",galacticSourceArrayList.get(position).getPeak());
                intent.putExtra("days",galacticSourceArrayList.get(position).getDays());
                intent.putExtra("lastday",galacticSourceArrayList.get(position).getLastDays());
                startActivity(intent);
                overridePendingTransition(R.anim.animation,R.anim.animation2);
            }
        });
        //set onclickListeners for the views.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePage.this,Filters.class);
                intent.putExtra("max_flux",global_max_flux+"");
                intent.putExtra("max_mean",global_max_mean+"");
                intent.putExtra("min_flux",global_min_flux+"");
                intent.putExtra("min_mean",global_min_mean+"");
                startActivityForResult(intent, 0);
                overridePendingTransition(R.anim.slidebottomtop,R.anim.stillanim);//for smooth transitions
            }
        });
        fab.attachToListView(galacticSourceList);


        if(!NetworkConnection.haveNetworkConnection(this)){
            Toast.makeText(getApplicationContext(),"You don't seem to have an active network connection.",Toast.LENGTH_LONG).show();
        }
        else {
            pd = new ProgressDialog(this);
            pd.setCancelable(false);
            pd.setMessage("Loading Active Galactic Sources");
            pd.show();
            sendJsonRequest();


        }
    }

    //function to send JSON request by adding it to the request queue(using JSON Volley)
    private void sendJsonRequest(){
        RequestQueue requestQueue= VolleySingleton.getInstance().getRequestQueue();
        JsonObjectRequest request=new JsonObjectRequest(com.android.volley.Request.Method.GET,"http://astromonitorapp.appspot.com/transients?satellite=swift",new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();

                parseJSONResponse(response);
                adapter= new GalacticSourceListAdapter(HomePage.this,galacticSourceArrayList);

                galacticSourceList.setAdapter(adapter);
                pd.dismiss();

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"Sorry could not load results due to an error",Toast.LENGTH_LONG).show();
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
                AstroSources astroSources=new AstroSources();
                astroSources.setSourceName(currentSource.getString(KEY_SOURCE_NAME));
                astroSources.setSourceType(currentSource.getString(KEY_SOURCE_TYPE));
                astroSources.setDeclination(currentSource.getString(KEY_DECLINATION));
                astroSources.setRightEssential(currentSource.getString(KEY_RIGHT_ESSENTIAL));
                astroSources.setDays(currentSource.getString(KEY_SOURCE_DAYS));
                astroSources.setLastDays(currentSource.getString(KEY_SOURCE_LAST_DAY));
                astroSources.setMean(currentSource.getString(KEY_SOURCE_MEAN));
                astroSources.setPeak(currentSource.getString(KEY_SOURCE_PEAK));
                astroSources.setDays(currentSource.getString(KEY_SOURCE_DAYS));
                astroSources.setLastDays(currentSource.getString(KEY_SOURCE_LAST_DAY));
                astroSources.setTenday(currentSource.getString(KEY_SOURCE_TENDAY));
                astroSources.setCurrentFlux(currentSource.getString(KEY_SOURCE_TODAY));
                astroSources.setYesterday(currentSource.getString(KEY_SOURCE_YESTERDAY));
                Log.d("JSON",currentSource.getString(KEY_SOURCE_NAME));

                if(currentSource.getString(KEY_SOURCE_TODAY).equals("-") == false) {
                    if (Float.parseFloat(currentSource.getString(KEY_SOURCE_TODAY)) <= global_min_flux)
                        global_min_flux = Float.parseFloat(currentSource.getString(KEY_SOURCE_TODAY));
                    if (Float.parseFloat(currentSource.getString(KEY_SOURCE_TODAY)) >= global_max_flux)
                        global_max_flux = Float.parseFloat(currentSource.getString(KEY_SOURCE_TODAY));
                }

                if(currentSource.getString(KEY_SOURCE_MEAN).equals("-")==false) {
                    if (Float.parseFloat(currentSource.getString(KEY_SOURCE_MEAN)) <= global_min_mean)
                        global_min_mean = Float.parseFloat(currentSource.getString(KEY_SOURCE_MEAN));
                    if (Float.parseFloat(currentSource.getString(KEY_SOURCE_MEAN)) >= global_max_mean)
                        global_max_mean = Float.parseFloat(currentSource.getString(KEY_SOURCE_MEAN));
                }
                 galacticSourceArrayList.add(astroSources);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private class GalacticSourceListAdapter extends BaseAdapter {

        ArrayList<AstroSources> astroSourcelist;
        private Context context;
        //ArrayList<Itinerary> itineraryArrayList;
        public GalacticSourceListAdapter(Context c,ArrayList<AstroSources> asource) {
            this.context = c;
            astroSourcelist=(ArrayList<AstroSources>)asource.clone();
            //itineraryArrayList=(ArrayList<Itinerary>)itineraries.clone();
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return astroSourcelist.size();
        }

        public float calculatePercentage(int i){
            String speak = galacticSourceArrayList.get(i).getPeak();
            String stoday = galacticSourceArrayList.get(i).getCurrentFlux();
            if(speak.equals("-") || stoday.equals("-"))
                return 0.0f;
            else {
                float peak = Float.parseFloat(speak);
                float today = Float.parseFloat(stoday);
                float perc = (today/peak)*100;
                if(perc > 100.0)
                    return 100.0f;
                return perc;
            }
        }

        public String activeLevelText(float perc){
            if(perc > 80.0f){
                return "Highly Active";
            }
            else if(perc <= 80.0 && perc > 65.0){
                return "Active";
            }
            else if(perc <= 65.0 && perc > 35.0){
                return "Normal";
            }
            else{
                return "Low flux";
            }
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        private int lastPosition = -1;
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder=null;
            View row = null;
            if (convertView == null) {
                LayoutInflater inflator = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflator.inflate(R.layout.galactic_search_list_inflator, parent,
                        false);
                holder=new ViewHolder();
                holder.sourceName=(TextView)row.findViewById(R.id.sourceName);
                holder.sourceType=(TextView)row.findViewById(R.id.sourceType);
                holder.satelliteName=(TextView)row.findViewById(R.id.satelliteName);
                holder.flux=(TextView)row.findViewById(R.id.flux);
                holder.mean=(TextView)row.findViewById(R.id.mean);
                holder.activeLevel=(TextView)row.findViewById(R.id.activelevel);
                holder.activeLevelText=(TextView)row.findViewById(R.id.activeLevelText);

                row.setTag(holder);

            } else {
                row = convertView;
                holder = (ViewHolder) row.getTag();
            }

            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/robotoregular.ttf");
            holder.flux.setTypeface(font);
            holder.mean.setTypeface(font);
            holder.satelliteName.setTypeface(font);
            holder.sourceType.setTypeface(font);
            holder.sourceName.setTypeface(font);
            holder.sourceType.setText(astroSourcelist.get(position).getSourceType());
            holder.sourceName.setText(astroSourcelist.get(position).getSourceName());
            float activeLevel=calculatePercentage(position);
            holder.activeLevel.setText(String.format("%.2f",activeLevel)+"%");
            holder.activeLevelText.setText(activeLevelText(activeLevel));
            if(holder.activeLevelText.getText().equals("Highly Active")){
                holder.activeLevelText.setBackgroundColor(Color.parseColor("#f44336"));
            }
            else if(holder.activeLevelText.getText().equals("Active")){
                holder.activeLevelText.setBackgroundColor(Color.parseColor("#ffca28"));
            }
            else if(holder.activeLevelText.getText().equals("Low flux")){
                holder.activeLevelText.setBackgroundColor(Color.parseColor("#757575"));
            }
            else {
                holder.activeLevelText.setBackgroundColor(Color.parseColor("#1e88e5"));
            }


            if(astroSourcelist.get(position).getCurrentFlux().equals("-"))
            {
                holder.flux.setText(astroSourcelist.get(position).getCurrentFlux());
            }
            else
                holder.flux.setText(astroSourcelist.get(position).getCurrentFlux()+" mCrab");

            if(astroSourcelist.get(position).getMean().equals("-"))
                holder.mean.setText(astroSourcelist.get(position).getMean());
            else
                holder.mean.setText(astroSourcelist.get(position).getMean()+" mCrab");

            //Animation
            Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            row.startAnimation(animation);
            lastPosition = position;
            return row;
        }

    }
    class ViewHolder{
        TextView sourceName;
        TextView sourceType;
        TextView satelliteName;
        TextView flux;
        TextView mean;
        TextView activeLevel;
        TextView activeLevelText;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == 0 && resultCode == Activity.RESULT_OK) {
            //some code
            float min_mean= Float.parseFloat(data.getExtras().getString("min_mean"));
            float max_mean= Float.parseFloat(data.getExtras().getString("max_mean"));
            float min_flux= Float.parseFloat(data.getExtras().getString("min_flux"));
            float max_flux= Float.parseFloat(data.getExtras().getString("max_flux"));

        pd.show();
        ArrayList<AstroSources> filteredList=new ArrayList<>();
        for(int i=0;i<galacticSourceArrayList.size();i++){
            if(galacticSourceArrayList.get(i).getCurrentFlux().equals("-")==false && galacticSourceArrayList.get(i).getMean().equals("-")==false ){
                if(Float.parseFloat(galacticSourceArrayList.get(i).getMean())<=max_mean &&Float.parseFloat(galacticSourceArrayList.get(i).getMean())>=min_mean &&Float.parseFloat(galacticSourceArrayList.get(i).getCurrentFlux())<=max_flux &&Float.parseFloat(galacticSourceArrayList.get(i).getCurrentFlux())>=min_flux){
                 filteredList.add(galacticSourceArrayList.get(i));
                }
            }

        }
            pd.dismiss();

            if(filteredList.size()==0)
            {
                adapter=new GalacticSourceListAdapter(HomePage.this,galacticSourceArrayList);
                Toast.makeText(HomePage.this,"No results for the filters found",Toast.LENGTH_LONG).show();
            }
            else
                adapter=new GalacticSourceListAdapter(HomePage.this,filteredList);
            galacticSourceList.setAdapter(adapter);
        }



    }
}
