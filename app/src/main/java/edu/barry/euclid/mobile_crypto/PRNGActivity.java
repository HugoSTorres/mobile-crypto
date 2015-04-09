package edu.barry.euclid.mobile_crypto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.security.SecureRandom;

import java.math.BigInteger;
import java.util.Random;


public class PRNGActivity extends Activity {

    Button btnStandard;
    EditText txtTimes;
    TextView lblInfo;

    Battery battery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prng);

        //get GUI definitions
        this.btnStandard = (Button) findViewById(R.id.btnStandardPRNG);
        this.txtTimes = (EditText) findViewById(R.id.txtPRNGBenchSize);
        this.lblInfo = (TextView) findViewById(R.id.lblInfo);

        this.battery = new Battery(this);

        this.btnStandard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTests("STANDARD");
            }
        });
    }
    /**
     * Basically, just returns the value in the textfield.
     */
    private int getNumTimes(){
        int times;
        String timesStr = this.txtTimes.getText().toString();

        if (timesStr.matches("")) times = 1; // set a default of 1 time
        else times = Integer.parseInt(timesStr);

        return times;
    }

    /**
     * Runs the test based on name
     * @param name the name of the test: STANDARD for the standard Java SecureRandom generator
     */
    private void runTests(String name){
        int times = this.getNumTimes();
        byte[] bytes = new byte[16];

        float batteryPercentageBefore = battery.percentage();
        long timeBefore = System.currentTimeMillis();

        if (name.equals("STANDARD")) {
            SecureRandom generator = new SecureRandom();

            for (int i = 0; i < times; i++) {
                Log.d("STANDARD", "Generating number");
                generator.nextBytes(bytes);
            }
        }

        float batteryPercentageAfter = battery.percentage();
        long timeAfter = System.currentTimeMillis();

        float batteryUsed = batteryPercentageBefore - batteryPercentageAfter;
        long totalTime = timeAfter - timeBefore;

        String text = "It took " + Double.toString(totalTime/1000.0) + " seconds to run the standard Java SecureRandom generator"
                + Integer.toString(times) + " times, and it used " + Float.toString(batteryUsed) + "% of battery.";
        Toast.makeText(getApplicationContext(), text,
                Toast.LENGTH_LONG).show();
        lblInfo.setText(text);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_primality_tests, menu);
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
