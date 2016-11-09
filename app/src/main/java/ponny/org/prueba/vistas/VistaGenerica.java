package ponny.org.prueba.vistas;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

/**
 * Created by Daniel on 03/11/2016.
 */
public class VistaGenerica {
    private Activity context;

    public VistaGenerica(Activity context) {
        this.context = context;
    }

   public void setOrientacion() {
        if (isTablet(context)) {
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

    }

    public static boolean isTablet(Activity context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
