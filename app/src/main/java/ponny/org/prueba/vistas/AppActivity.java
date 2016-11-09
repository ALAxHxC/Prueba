package ponny.org.prueba.vistas;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ponny.org.prueba.R;
import ponny.org.prueba.controlador.conexion.TareaConexionImagen;
import ponny.org.prueba.controlador.json.ControladorEntry;
import ponny.org.prueba.modelo.entidades.entry.Entry;
import ponny.org.prueba.modelo.entidades.entry.ImImage;

public class AppActivity extends AppCompatActivity {
    private final VistaGenerica vistaGenerica = new VistaGenerica(this);
    private TextView name, sumary, realse, categoria, autor, precio, moneda, title;
    private ImageView imagen;

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
        setContentView(R.layout.activity_app);
        vistaGenerica.setOrientacion();
        cargarVista();
        cargarEntry();
    }


    public void cargarVista() {
        getSupportActionBar().setTitle(getResources().getString(R.string.resumen_app));
        name = (TextView) findViewById(R.id.textViewEntryName);
        precio = (TextView) findViewById(R.id.textViewPriceI);
        moneda = (TextView) findViewById(R.id.textViewAtributeP);
        realse = (TextView) findViewById(R.id.textViewRealseDate);
        sumary = (TextView) findViewById(R.id.textViewSumaryI);
        categoria = (TextView) findViewById(R.id.textViewCategoria);
        autor = (TextView) findViewById(R.id.textViewAutorI);
        title = (TextView) findViewById(R.id.textViewRealseAtributes);
    }

    public void cargarEntry() {
        Bundle bundle = getIntent().getExtras();
        name.setText(bundle.getString(getResources().getString(R.string.intent_name)));
        sumary.setText(bundle.getString(getResources().getString(R.string.intent_sumary)));
        moneda.setText(bundle.getString(getResources().getString(R.string.intent_moneda)));
        precio.setText(bundle.getString(getResources().getString(R.string.intent_price)));
        realse.setText(bundle.getString(getResources().getString(R.string.intent_release)));
        categoria.setText(bundle.getString(getResources().getString(R.string.intent_categoria)));
        autor.setText(bundle.getString(getResources().getString(R.string.intent_autor)));


        cargarImagen(bundle.getString(getResources().getString(R.string.intent_img)), bundle.getString(getResources().getString(R.string.intent_imgname)));
        cargarUrl(imagen, bundle.getString(getResources().getString(R.string.intent_url)));
        cargarUrl(categoria, bundle.getString(getResources().getString(R.string.intent_urlcategoria)));
    }

    private void cargarImagen(String url, String name) {
        imagen = (ImageView) findViewById(R.id.imageViewEntry);

        TareaConexionImagen tarea = new TareaConexionImagen(this, imagen, name);

        if (!tarea.validarConexion()) {
            Log.println(Log.ASSERT, "ejecutara", "hilo");
            tarea.execute(url);
        }

    }

    /**
     * habilita cargar una pagina web al hacer click sobre una vista
     *
     * @param view
     * @param url
     */
    private void cargarUrl(View view, final String url) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

    }

}
