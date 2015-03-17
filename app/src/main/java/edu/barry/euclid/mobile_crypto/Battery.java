package edu.barry.euclid.mobile_crypto;

import android.content.IntentFilter;
import android.content.Intent;
import android.app.Activity;
import android.os.BatteryManager;

/**
 * Created by hugo on 3/16/15.
 *
 * This wraps all the battery functionality we need. It needs an activity to use for registering
 * receivers. It needs to be passed in as a parameter upon instantiation.
 *
 * It exposes the battery properties cleanly without exposing the underlying Intent used.
 */
public class Battery {
    Intent batteryStatus;

    public Battery(Activity activity) {
        batteryStatus =
                activity.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    /**
     * @return the battery percentage calculated using the raw level and scale information, or -1 if
     * there was an error
     */
    public float percentage() {
        int level = this.getLevel();
        float scale = (float) this.getScale();

        if (level == -1 || scale == -1) return -1;
        return level / scale;
    }

    /**
     * @return the raw battery level, or -1 if there was an error
     */
    public int getLevel() {
        return batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    }

    /**
     * @return the raw scale, or -1 if there was an error
     */
    public int getScale() {
        return batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
    }
}
