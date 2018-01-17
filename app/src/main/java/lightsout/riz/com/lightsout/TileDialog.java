package lightsout.riz.com.lightsout;
/**
 * Created by rizas on 1/10/2018.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.view.ContextThemeWrapper;

public class TileDialog {
    public static AlertDialog getDialog(Context context, String title, String message, Drawable icon) {

        AlertDialog.Builder builder =  new AlertDialog.Builder(context);
        builder.setIcon(icon);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        return builder.create();
    }
}
