package ponny.org.prueba.controlador.conexion;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Daniel on 12/06/2016.
 *
 */
public class Internet {
    /**
     * Encargada de verificar si existe acceso a internet
     * @param mContext
     * @return
     */
    public static boolean SalidaInternet(Context mContext) {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
