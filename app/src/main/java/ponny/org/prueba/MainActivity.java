package ponny.org.prueba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import ponny.org.prueba.persistencia.JsonManager;
import ponny.org.prueba.vistas.Listas.ListaApps;
import ponny.org.prueba.vistas.Mensaje;
import ponny.org.prueba.vistas.VistaGenerica;
import ponny.org.prueba.controlador.conexion.*;

public class MainActivity extends AppCompatActivity {
    private final VistaGenerica vistaGenerica = new VistaGenerica(this);
    private Mensaje mensajes;
    private JsonManager jsonManager;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        vistaGenerica.setOrientacion();
        Log.println(Log.ASSERT, "NUEVA COFIG", "NEUVA");
        //here you can handle orientation change
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        vistaGenerica.setOrientacion();
        cargarVistas();
        validadDatos();
    }

    /**
     * Genera un fullscreen en la acitividad
     **/
    public void setFullScreen() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    /**
     * Elementos activity
     */
    private void cargarVistas() {
        mensajes = new Mensaje(MainActivity.this);
        ImageView imagen = (ImageView) findViewById(R.id.imageViewInicio);
        imagen.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.splash, null));
        jsonManager = new JsonManager();
    }

    /**
     * valida si se debe descargar o usar cache
     **/
    private void validadDatos() {
        if (Internet.SalidaInternet(MainActivity.this)) {
            iniciarDescarga();
        } else if (cargarJson()) {
            Log.println(Log.INFO, "MAIN", "Encontro jsonManager");
            mensajes.toastmensaje(getString(R.string.funcion_cache));
            Splash();
        } else {
            mensajes.FinalizarApp(getString(R.string.error), getString(R.string.fallo_internet));
        }
    }

    /**
     * Envia a lista de apps
     **/

    private void Splash() {
        mensajes.toastmensaje(getResources().getString(R.string.caragando));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(getApplicationContext(), ListaApps.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        }, Toast.LENGTH_SHORT + 1000);

    }

    /**
     * Solicita descarga
     */
    private void iniciarDescarga() {
        TareaConexionJSON tarea = new TareaConexionJSON(this, mHandlerWS, new ProgressDialog(MainActivity.this), getString(R.string.url));
        tarea.execute();
    }

    /**
     * Handler web rest service
     */
    private final Handler mHandlerWS = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 2:

                    String respuesta = msg.getData().getString("result");
                    if (respuesta.startsWith("ERROR")) {
                        mensajes.toastmensaje(getResources().getString(R.string.fallo_internet));
                    } else {

                        guardarJson(respuesta);
                        Splash();
                    }
                    break;
            }
        }
    };

    /**
     * Valida si ya tiene almacenado el JSON
     *
     * @return validacion
     */
    private boolean cargarJson() {
        return jsonManager.validarJson(MainActivity.this);
    }

    /**
     * Guarda JsonManager
     **/
    private void guardarJson(String json) {
        JsonManager manager = new JsonManager();
        manager.guardarJson(json, MainActivity.this, mensajes);
    }

}
