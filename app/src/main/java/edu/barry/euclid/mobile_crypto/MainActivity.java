package edu.barry.euclid.mobile_crypto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.security.Provider;
import java.security.Security;

public class MainActivity extends Activity {

    Button btnEncrypt, btnPrimality, btnPRNG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            for (Object entry : provider.keySet()) {
                String name = String.valueOf(entry);
                if (name.startsWith("Cipher")) {
                    Log.d("Cipher", "Supports: " + name.substring(7));
                }
            }
        }

        this.btnPrimality = (Button) findViewById(R.id.btnPrimality);
        this.btnEncrypt = (Button) findViewById(R.id.btnEnc);
        this.btnPRNG = (Button) findViewById(R.id.btnPRNG);

        this.btnEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), EncryptionActivity.class);
                startActivity(myIntent);

            }
        });

        this.btnPrimality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), PrimalityTestsActivity.class);
                startActivity(myIntent);

            }
        });

        this.btnPRNG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), PRNGActivity.class);
                startActivity(myIntent);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
