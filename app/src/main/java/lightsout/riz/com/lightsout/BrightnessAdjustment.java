package lightsout.riz.com.lightsout;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by rizas on 1/10/2018.
 */

public class BrightnessAdjustment {
    public static final String TAG = "RizTag: " + BrightnessAdjustment.class.getSimpleName();

    public static void dropLight(Activity activity) throws Settings.SettingNotFoundException {
//        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
//        params.screenBrightness = 0;
//        activity.getWindow().setAttributes(params);

        ContentResolver cResolver = activity.getApplicationContext().getContentResolver();
        int brightnessMode = android.provider.Settings.System.getInt(cResolver,
                android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE);

        if (brightnessMode == android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
            android.provider.Settings.System.putInt(cResolver,
                    android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE,
                    android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        }
//        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, 0);
//        Settings.System.putInt(cResolver, Settings.System.SCREEN_OFF_TIMEOUT, 0);

        SensorManager sensorManager = (SensorManager) activity.getSystemService(SENSOR_SERVICE);
        Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        
        PowerManager pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);

        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, TAG);
        wl.acquire();

        Log.i(TAG, "System Brightness Set");
    }

}
