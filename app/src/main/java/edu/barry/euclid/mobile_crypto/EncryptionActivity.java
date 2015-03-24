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

import java.util.Random;


public class EncryptionActivity extends Activity {

    Button btnAES, btn3DES, btnBlowfish;
    EditText txtTimes;

    Battery battery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);
        this.btnAES = (Button) findViewById(R.id.btnAES);
        this.btn3DES = (Button) findViewById(R.id.btn3Des);
        this.btnBlowfish = (Button) findViewById(R.id.btnBlowfish);
        this.txtTimes = (EditText) findViewById(R.id.txtTimes2RunEncryption);

        this.battery = new Battery(this);

        this.btnAES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performEncryption(EncryptionHandler.AES);

            }
        });

        this.btn3DES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performEncryption(EncryptionHandler.TRIPLE_DES);

            }
        });

        this.btnBlowfish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performEncryption(EncryptionHandler.BLOWFISH);

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

    /**
     * Performs the encryption algorithm
     * @param name the name of the encryption algorithm, can be: AES, 3DES, Blowfish
     */
    private void performEncryption(String name){
        int times = this.getNumTimes();

        float batteryPercentageBefore = battery.percentage();
        long timeBefore = System.currentTimeMillis();

        EncryptionHandler algo = new EncryptionHandler(name);

        for (int i = 0; i < times; i++) {
            try {
                algo.encrypt("Performs the encryption algorithm", "Inflate the menu; this adds items to the action bar if it is present.");
            } catch(Exception e){
                Log.e("EXCEPTION", "Encryption handler");
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_encryption, menu);
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
