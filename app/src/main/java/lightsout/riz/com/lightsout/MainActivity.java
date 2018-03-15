package lightsout.riz.com.lightsout;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    public static final String TAG = "RizTag: " + MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Settings.System.canWrite(getApplicationContext())) {
            Log.i(TAG, "Permission Required");
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 200);
            Toast.makeText(this, getResources().getString(R.string.permission_toast),
                    Toast.LENGTH_LONG).show();
        }
        else {
            Log.i(TAG, "Permission Available");
//            try {
//                BrightnessAdjustment.dropLight(this);
//            } catch (Settings.SettingNotFoundException e) {
//                e.printStackTrace();
//            }
        }

        Log.i(TAG, "End onCreate");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            BrightnessAdjustment.dropLight(this);
//        } catch (Settings.SettingNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    private void requestPermission(){
        // Here, thisActivity is the current activity
        if (this.checkSelfPermission(Manifest.permission.WRITE_SETTINGS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (this.shouldShowRequestPermissionRationale(
                    Manifest.permission.WRITE_SETTINGS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.i(TAG, "Explanation Required");

            } else {

                // No explanation needed, we can request the permission.
                this.requestPermissions(
                        new String[]{Manifest.permission.WRITE_SETTINGS},
                        992);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 992: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                    Log.i(TAG, "Permission Granted by User");
//                    try {
//                        BrightnessAdjustment.dropLight(this);
//                    } catch (Settings.SettingNotFoundException e) {
//                        e.printStackTrace();
//                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.i(TAG, "Permission Denied by User");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
