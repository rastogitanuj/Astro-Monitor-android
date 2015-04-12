package astromonitor.limitsky.com.astromonitor;
/*
 Author:Nikit
 Handle all filter and sort related functions.
 */
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Filters extends ActionBarActivity {
    ImageView close_btn;
    TextView min_flux;
    TextView max_flux;
    TextView min_mean;
    TextView max_mean;
    Button sortby_flux;
    Button sortby_mean;
    Button sortby_status;
    Button sortby_sourceType;
    Button add_filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        //finding/instantiating views
        close_btn=(ImageView)findViewById(R.id.close);
        RangeSeekBar<Float> seekBar_mean = new RangeSeekBar<Float>(Float.parseFloat(getIntent().getExtras().getString("min_mean").toString()),Float.parseFloat(getIntent().getExtras().getString("max_mean").toString()),this);
        RangeSeekBar<Float> seekBar_flux = new RangeSeekBar<Float>(Float.parseFloat(getIntent().getExtras().getString("min_flux").toString()), Float.parseFloat(getIntent().getExtras().getString("max_flux").toString()),this);
        min_flux=(TextView)findViewById(R.id.min_flux);
        max_flux=(TextView)findViewById(R.id.max_flux);
        min_mean=(TextView)findViewById(R.id.min_mean);
        max_mean=(TextView)findViewById(R.id.max_mean);
        sortby_flux=(Button)findViewById(R.id.button);
        sortby_sourceType=(Button)findViewById(R.id.button2);
        sortby_mean=(Button)findViewById(R.id.button3);
        sortby_status=(Button)findViewById(R.id.button4);
        add_filter=(Button)findViewById(R.id.apply_filter);


        add_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("min_mean",min_mean.getText().subSequence(0,min_mean.length()-6));
                returnIntent.putExtra("max_mean",max_mean.getText().subSequence(0,max_mean.length()-6));
                returnIntent.putExtra("max_flux",max_flux.getText().subSequence(0,max_flux.length()-6));
                returnIntent.putExtra("min_flux",min_flux.getText().subSequence(0,min_flux.length()-6));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });


        //set min max values for the sliders
        min_flux.setText(seekBar_flux.getAbsoluteMinValue()+" mCrab");
        max_flux.setText(seekBar_flux.getAbsoluteMaxValue()+" mCrab");
        min_mean.setText(seekBar_mean.getAbsoluteMinValue()+" mCrab");
        max_mean.setText(seekBar_mean.getAbsoluteMaxValue()+" mCrab");



        //onclickListener
        sortby_flux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                sortby_mean.setBackgroundColor(Color.parseColor("#fff"));
//                sortby_mean.setTextColor(Color.parseColor("#37474f"));
//
//                sortby_flux.setBackgroundColor(Color.parseColor("#37474f"));
//                sortby_flux.setTextColor(Color.parseColor("#fff"));
//
//                sortby_sourceType.setBackgroundColor(Color.parseColor("#fff"));
//                sortby_sourceType.setTextColor(Color.parseColor("#37474f"));
//
//                sortby_status.setBackgroundColor(Color.parseColor("#fff"));
//                sortby_status.setTextColor(Color.parseColor("#37474f"));
            }
        });
        sortby_sourceType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sortby_mean.setBackgroundColor(Color.parseColor("#fff"));
//                sortby_mean.setTextColor(Color.parseColor("#37474f"));
//
//                sortby_flux.setBackgroundColor(Color.parseColor("#fff"));
//                sortby_flux.setTextColor(Color.parseColor("#37474f"));
//
//                sortby_sourceType.setBackgroundColor(Color.parseColor("#37474f"));
//                sortby_sourceType.setTextColor(Color.parseColor("#fff"));
//
//                sortby_status.setBackgroundColor(Color.parseColor("#fff"));
//                sortby_status.setTextColor(Color.parseColor("#37474f"));

            }
        });
        sortby_mean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sortby_mean.setBackgroundColor(Color.parseColor("#37474f"));
//                sortby_mean.setTextColor(Color.parseColor("#fff"));
//
//                sortby_flux.setBackgroundColor(Color.parseColor("#fff"));
//                sortby_flux.setTextColor(Color.parseColor("#37474f"));
//
//                sortby_sourceType.setBackgroundColor(Color.parseColor("#fff"));
//                sortby_sourceType.setTextColor(Color.parseColor("#37474f"));
//
//                sortby_status.setBackgroundColor(Color.parseColor("#fff"));
//                sortby_status.setTextColor(Color.parseColor("#37474f"));

            }
        });
        sortby_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                sortby_mean.setBackgroundColor(Color.parseColor("#fff"));
//                sortby_mean.setTextColor(Color.parseColor("#37474f"));
//
//                sortby_flux.setBackgroundColor(Color.parseColor("#fff"));
//                sortby_flux.setTextColor(Color.parseColor("#37474f"));
//
//                sortby_sourceType.setBackgroundColor(Color.parseColor("#fff"));
//                sortby_sourceType.setTextColor(Color.parseColor("#37474f"));
//
//                sortby_status.setBackgroundColor(Color.parseColor("#37474f"));
//                sortby_status.setTextColor(Color.parseColor("#fff"));
            }
        });
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.stillanim,R.anim.slidedown);
            }
        });
        seekBar_mean.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Float>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Float minValue, Float maxValue) {
                // handle changed range values
                Log.d("range-mean",minValue+" ,"+maxValue);
                min_mean.setText(String.format("%.2f", minValue) + " mCrab");
                max_mean.setText(String.format("%.2f", maxValue)+" mCrab");

            }
        });
        seekBar_flux.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Float>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Float minValue, Float maxValue) {
                // handle changed range values
                Log.d("range-mean",minValue+" ,"+maxValue);
                min_flux.setText(String.format("%.2f", minValue) + " mCrab");
                max_flux.setText(String.format("%.2f", maxValue) + " mCrab");
            }
        });

        // add RangeSeekBar to pre-defined layout
        ViewGroup layout_mean = (ViewGroup) findViewById(R.id.mean_slider);
        seekBar_mean.setPadding(30,30,30,30);
        layout_mean.addView(seekBar_mean);

        ViewGroup layout_flux = (ViewGroup) findViewById(R.id.flux_slider);
        seekBar_flux.setPadding(30,30,30,30);
        layout_flux.addView(seekBar_flux);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filters, menu);
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
}
