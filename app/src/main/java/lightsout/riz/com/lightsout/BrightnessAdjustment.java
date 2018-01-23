package lightsout.riz.com.lightsout;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.util.Log;

/**
 * Created by rizas on 1/10/2018.
 */

public class BrightnessAdjustment {
    public static final String TAG = "RizTag: " + BrightnessAdjustment.class.getSimpleName();

    private static Status AppStatus = Status.Unknown;

    private static PowerManager.WakeLock wl;
    private static int brightnessValue;

    public static void dropLight(Context context) throws Settings.SettingNotFoundException {
        ContentResolver cResolver = context.getContentResolver();
        int brightnessMode = android.provider.Settings.System.getInt(cResolver,
                android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE);

        if(AppStatus == Status.OFF || AppStatus == Status.Unknown){
            brightnessValue = android.provider.Settings.System.getInt(cResolver,
                    Settings.System.SCREEN_BRIGHTNESS);
            TurnOn(brightnessMode, context, cResolver);
//            tile.setState(Tile.STATE_ACTIVE);
        } else {
            TurnOff(cResolver);
//            tile.setState(Tile.STATE_INACTIVE);
        }

        closePanel(context);

    }

    private static void TurnOn(int brightnessMode, Context context, ContentResolver cResolver){
//        if (brightnessMode == android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
//            android.provider.Settings.System.putInt(cResolver,
//                    android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE,
//                    android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
//        }
//
//        android.provider.Settings.System.putInt(cResolver,
//                Settings.System.SCREEN_BRIGHTNESS,
//                0);

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        wl = pm.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, TAG);
        wl.acquire();

        AppStatus = Status.ON;

        Log.i(TAG, "LightsOut Status ON");
    }

    private static void TurnOff(ContentResolver cResolver){
//        android.provider.Settings.System.putInt(cResolver,
//                Settings.System.SCREEN_BRIGHTNESS,
//                brightnessValue);
//
//        android.provider.Settings.System.putInt(cResolver,
//                android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE,
//                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);

        wl.release();

        AppStatus = Status.OFF;

        Log.i(TAG, "LightsOut Status OFF");
    }

    private static void closePanel(Context context){
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
    }

    public static void ForceStatus(boolean status_on){
        if(status_on){
            AppStatus=Status.ON;
        }
        else {
            AppStatus=Status.OFF;
        }
    }

    public static boolean isActive(){
        if(AppStatus == Status.OFF || AppStatus == Status.Unknown)
            return false;
        else
            return true;
    }

    public static  boolean isWakeLockActive(){
        if(wl == null || !wl.isHeld()){
            return false;
        }
        else {
            return true;
        }
    }

}
