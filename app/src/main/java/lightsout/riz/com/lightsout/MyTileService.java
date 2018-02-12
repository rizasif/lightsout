package lightsout.riz.com.lightsout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by rizas on 1/10/2018.
 */

public class MyTileService extends TileService {
    public static final String TAG = "RizTag: " + MyTileService.class.getSimpleName();

    private static Tile tile;
    private static int ViewCount=0;
    private static final int MaxViews = 4;

    @Override
    public void onTileAdded() {
        Log.d(TAG, "onTileAdded: ");
        tile = getQsTile();
//        tile.setState(Tile.STATE_INACTIVE);
//        tile.updateTile();
        if (Settings.System.canWrite(getApplicationContext())) {
            UpdateStatus(false);
        }
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
        Log.d(TAG, "onStopListening");
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        Log.d(TAG, "onStartListening");

        tile = getQsTile();
        if (Settings.System.canWrite(getApplicationContext())) {
            UpdateStatus(false);
        }
    }

    @Override
    public void onClick() {
        Log.d(TAG, "onClick");

        tile = getQsTile();

        ViewCount++;
        if(ViewCount < MaxViews && tile.getState() != Tile.STATE_ACTIVE)
            showDialog(getResources().getString(R.string.on_click_message));

        if (!Settings.System.canWrite(getApplicationContext())) {
            Log.i(TAG, "Permission Required");
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            Toast.makeText(this, getResources().getString(R.string.permission_toast),
                    Toast.LENGTH_LONG).show();
        }else {
            if (!isSecure()) {
                try {
                    beginBrightnessAdjustment();
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                unlockAndRun(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            beginBrightnessAdjustment();
                        } catch (Settings.SettingNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    private void showDialog(String message) {
        showDialog(TileDialog.getDialog(this,getResources().getString(R.string.app_name)
                ,message, getDrawable(R.drawable.lightining)));
    }

    private void beginBrightnessAdjustment() throws Settings.SettingNotFoundException {
        BrightnessAdjustment.dropLight(getBaseContext());
        UpdateStatus(true);
    }

    private void UpdateStatus(){
        UpdateStatus(false);
    }

    private void UpdateStatus(boolean verbose){
        if(BrightnessAdjustment.isWakeLockActive()) {
            Log.d(TAG, "Setting State Active");
            BrightnessAdjustment.ForceStatus(true);
            tile.setState(Tile.STATE_ACTIVE);
            tile.updateTile();
            if(verbose)
                Toast.makeText(this, getString(R.string.toast_on), Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d(TAG, "Setting State Inactive");
            BrightnessAdjustment.ForceStatus(false);
            tile.setState(Tile.STATE_INACTIVE);
            tile.updateTile();
            if(verbose)
                Toast.makeText(this, getString(R.string.toast_off), Toast.LENGTH_SHORT).show();
        }
    }

}
