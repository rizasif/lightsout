package lightsout.riz.com.lightsout;

import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

/**
 * Created by rizas on 1/10/2018.
 */

public class MyTileService extends TileService {
    public static final String TAG = "RizTag: " + MyTileService.class.getSimpleName();

    @Override
    public void onTileAdded() {
        Log.d(TAG, "onTileAdded: ");
    }

    @Override
    public void onStartListening() {

        Tile tile = getQsTile();
        Log.d(TAG, "onStartListening: "+tile.getLabel());
    }

    @Override
    public void onClick() {
        Log.d(TAG, "onClick: ");

        if (!isSecure()) {

            showDialog();

        } else {

            unlockAndRun(new Runnable() {
                @Override
                public void run() {

                    showDialog();
                }
            });
        }
    }

    private void showDialog() {

        showDialog(TileDialog.getDialog(this));
    }

}
