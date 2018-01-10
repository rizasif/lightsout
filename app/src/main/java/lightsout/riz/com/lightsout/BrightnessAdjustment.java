package lightsout.riz.com.lightsout;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by rizas on 1/10/2018.
 */

public class BrightnessAdjustment {
    public static final String TAG = "RizTag: " + BrightnessAdjustment.class.getSimpleName();

    public static void dropLight(Activity activity){
//        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
//        params.screenBrightness = 0;
//        activity.getWindow().setAttributes(params);

        ContentResolver cResolver = activity.getApplicationContext().getContentResolver();
        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, 0);
    }

}
