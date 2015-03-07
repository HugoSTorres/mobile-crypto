package edu.barry.euclid.mobile_crypto;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;

// ciphers
import javax.crypto.Cipher;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // AES
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // 3DES
            Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");

            // Blowfish
            Cipher c = Cipher.getInstance("Blowfish");
            Log.d("ALGORITHM", c.getAlgorithm());
        } catch (Exception e) {
            Log.d("ERROR", "cannot load cipher");
        }
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
