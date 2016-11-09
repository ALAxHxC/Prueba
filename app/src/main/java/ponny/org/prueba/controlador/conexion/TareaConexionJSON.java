package ponny.org.prueba.controlador.conexion;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import ponny.org.prueba.R;


/**
 * Created by daniel on 09/06/2016.
 * Realiza la descarga del JSON
 */
public class TareaConexionJSON extends AsyncTask<Void, Void, String> {
    private UrlServicio conexion;
    private Context mContext;
    private ProgressDialog dialog;
    private Handler mHandler;
    private Message msg;
    private Bundle bundle;

    public TareaConexionJSON(Context context, Handler mHandler, ProgressDialog dialog,String url) {
        mContext = context;
        this.mHandler = mHandler;
        this.dialog = dialog;
        conexion = new UrlServicio(url);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        msg = mHandler.obtainMessage(1);
        bundle = new Bundle();
        bundle.putString("result", "Envio peticion");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
        iniciarDialogo();
        //   dynamictext = (TextView) findViewById(R.id.dynamictext);
    }

    @Override
    protected String doInBackground(Void... params) {

        conexion.getJSON();
        return null;
    }

    @Override
    protected void onPostExecute(String strings) {
        cancelarDialogo();
        String salida = conexion.getRespuesta();
        msg = mHandler.obtainMessage(2);
        bundle = new Bundle();
        bundle.putString("result", salida);
        msg.setData(bundle);
        mHandler.sendMessage(msg);

    }

    private void iniciarDialogo() {
        dialog = new ProgressDialog(mContext);
        dialog.setMessage(mContext.getResources().getString(R.string.Descargando));
        dialog.setCancelable(false);
        dialog.show();

    }

    private void cancelarDialogo() {
        if (dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (IllegalArgumentException ex) {
                return;
            }
        }

    }


}


