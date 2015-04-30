package edu.barry.euclid.mobile_crypto;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class EncryptionActivity extends Activity {
    Button btnAES, btn3DES, btnBlowfish, btnRC4;
    EditText txtTimes;
    TextView lblEncryption;

    float preBattery, postBattery;
    long preTime, postTime;
    int times;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_encryption);
        this.btnAES = (Button) findViewById(R.id.btnAES);
        this.btn3DES = (Button) findViewById(R.id.btn3Des);
        this.btnBlowfish = (Button) findViewById(R.id.btnBlowfish);
        this.btnRC4 = (Button) findViewById(R.id.btnRC4);
        this.txtTimes = (EditText) findViewById(R.id.txtTimes2RunEncryption);
        this.lblEncryption = (TextView) findViewById(R.id.lblEncryption);

        this.btnAES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    performEncryption(EncryptionHandler.AES);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        this.btn3DES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    performEncryption(EncryptionHandler.TRIPLE_DES);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        this.btnBlowfish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    performEncryption(EncryptionHandler.BLOWFISH);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        this.btnRC4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    performEncryption(EncryptionHandler.RC4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

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
     * Performs the encryption algorithm
     * @param name the name of the encryption algorithm, can be: AES, 3DES, Blowfish
     */
    private void performEncryption(String name) throws InterruptedException {
        // This stuff needs to be outside of the EncryptionWorker because it needs to be passed in
        // through the parameters.
        this.times = this.getNumTimes();
        this.name = name; // We need this copy for the EncryptionWorker to output the right name in the Toast.



        EncryptionHandler algo = new EncryptionHandler(name);

        (new EncryptionWorker()).execute(new EncryptorParams(algo, times));
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

    // This is gonna handle the actual encryption so the main thread can handle the dialog.
    private class EncryptionWorker extends AsyncTask<EncryptorParams, Integer, Integer> {
        /* This is defined in here but wont' be handled in here. It will be handled in the
         * pre-execute/post-execute hooks below, which actually happen in the main thread. The
         * hooks are run in the main thread but are still lexically bound to this class so the
         * dialog can be made private since we won't use it anywhere but in the hooks.
         */
        private ProgressDialog dialog;

        @Override
        public void onPreExecute() {
            super.onPreExecute();

            preBattery = (new Battery(EncryptionActivity.this)).percentage();
            Log.d("BATTERY", "Before: " + String.valueOf(preBattery));

            preTime = System.currentTimeMillis();

            this.dialog = new ProgressDialog(EncryptionActivity.this);
            this.dialog.setTitle("Encrypting");
            this.dialog.setMessage("Please wait. This may take a while");

            this.dialog.show();
        }

        @Override
        public Integer doInBackground(EncryptorParams... params) {
            for (int i = 0; i < params[0].rounds; i++) {
                try {
                    params[0].encryptor.encrypt("Performs the encryption algorithm", "the name of the encryption algorithm, can be: AES, 3DES, Blowfish");
                } catch (Exception e) {
                    Log.e("EXCEPTION", e.getMessage());
                    return 1;
                }
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            this.dialog.dismiss();

            postBattery = (new Battery(EncryptionActivity.this)).percentage();
            Log.d("BATTERY", "After: " + String.valueOf(postBattery));


            postTime = System.currentTimeMillis();

            Log.d("dBattery", String.valueOf(preBattery) + " - " + String.valueOf(postBattery));
            float batteryUsed = preBattery - postBattery;
            Log.d("BATTERY", "Change: " + String.valueOf(batteryUsed));
            long totalTime = postTime - preTime;

            String text = "It took " + Double.toString(totalTime/1000.0) + " seconds to run " + name
                    + " algorithm " + Integer.toString(times) + " times, and it used " + Float.toString(batteryUsed) + "% of battery.";
            Toast.makeText(getApplicationContext(), text,
                    Toast.LENGTH_LONG).show();
            lblEncryption.setText(text);
        }
    }
}
