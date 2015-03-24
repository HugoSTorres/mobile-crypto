package edu.barry.euclid.mobile_crypto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.Random;


public class PrimalityTestsActivity extends Activity {

    Button btnRabin, btnStrassen;
    EditText txtTimes;
    TextView lblPrimality;

    Battery battery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primality_tests);

        //get GUI definitions
        this.btnRabin = (Button) findViewById(R.id.btnMRT);
        this.btnStrassen = (Button) findViewById(R.id.btnSST);
        this.txtTimes = (EditText) findViewById(R.id.txtTimes2RunPrimality);
        this.lblPrimality = (TextView) findViewById(R.id.lblPrimality);

        this.battery = new Battery(this);

        this.btnRabin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTests("MRT");

            }
        });
        this.btnStrassen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTests("SST");

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
     * @param name the name of the test: SST for Solovay Strassen and MRT for Miller Rabin
     */
    private void runTests(String name){
        int times = this.getNumTimes();

        float batteryPercentageBefore = battery.percentage();
        long timeBefore = System.currentTimeMillis();

        if (name.equals("MRT")) {
            MillerRabinPrimalityTest test = new MillerRabinPrimalityTest();
            for (int i = 0; i < times; i++) {
                test.isPrime(new BigInteger(256, new Random()));
            }
        } else if (name.equals("SST")){
            SolovayStrassenPrimalityTest test = new SolovayStrassenPrimalityTest();
            for (int i = 0; i < times; i++){
                test.isPrime((new Random()).nextInt());
            }
        }

        float batteryPercentageAfter = battery.percentage();
        long timeAfter = System.currentTimeMillis();

        float batteryUsed = batteryPercentageBefore - batteryPercentageAfter;
        long totalTime = timeAfter - timeBefore;

        String text = "It took " + Double.toString(totalTime/60.0) + " seconds to run " + (name.equals("MRT") ? "Miller-Rabin Test" : "Solovay-Strassen Test")
                + " algorithm " + Integer.toString(times) + " times, and it used " + Float.toString(batteryUsed) + "% of battery.";
        Toast.makeText(getApplicationContext(), text,
                Toast.LENGTH_LONG).show();
        lblPrimality.setText(text);

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
