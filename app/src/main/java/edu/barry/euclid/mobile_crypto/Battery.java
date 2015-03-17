package edu.barry.euclid.mobile_crypto;

import android.content.IntentFilter;
import android.content.Intent;
import android.app.Activity;
import android.os.BatteryManager;

/**
 * Created by hugo on 3/16/15.
 */
public class Battery {
    Intent batteryStatus;

    public Battery(Activity activity) {
        batteryStatus =
                activity.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    public float percentage() {
        int level = this.getLevel();
        float scale = (float) this.getScale();

        return level / scale;
    }

    public int getLevel() {
        return batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    }

    public int getScale() {
        return batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
    }
}
