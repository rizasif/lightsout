package lightsout.riz.com.lightsout; /**
 * Created by rizas on 1/10/2018.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.Random;

import lightsout.riz.com.lightsout.Constants;

public class TileDialog {
    public static AlertDialog getDialog(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Fact");
        Random random = new Random();
        builder.setMessage(Constants.facts[random.nextInt(6)]);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        return builder.create();
    }
}
