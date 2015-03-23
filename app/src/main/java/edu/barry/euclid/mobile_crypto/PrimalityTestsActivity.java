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


public class PrimalityTestsActivity extends Activity {

    Button btnRabin, btnStrassen;
    EditText txtTimes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primality_tests);

        //get GUI definitions
        this.btnRabin = (Button) findViewById(R.id.btnMRT);
        this.btnStrassen = (Button) findViewById(R.id.btnSST);
        this.txtTimes = (EditText) findViewById(R.id.txtTimes2RunPrimality);

        this.btnRabin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int times = getNumTimes();

            }
        });
        this.btnStrassen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int times = getNumTimes();

            }
        });
    }
    /**
     * Basically, just returns the value in the textfield.
     */
    private int getNumTimes(){
        int times;
        if (this.txtTimes.getText().toString().matches("")) times = 1; // set a default of 1 time
        else times = Integer.getInteger(this.txtTimes.getText().toString());

        return times;
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
