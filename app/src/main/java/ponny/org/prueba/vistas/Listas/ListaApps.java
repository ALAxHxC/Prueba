package ponny.org.prueba.vistas.Listas;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import ponny.org.prueba.R;
import ponny.org.prueba.controlador.json.ControladorEntry;
import ponny.org.prueba.modelo.entidades.Categoria;
import ponny.org.prueba.modelo.entidades.entry.Entry;
import ponny.org.prueba.persistencia.JsonManager;
import ponny.org.prueba.vistas.AppActivity;
import ponny.org.prueba.vistas.Listas.adapters.ListaEntrys;
import ponny.org.prueba.vistas.VistaGenerica;

import static ponny.org.prueba.vistas.VistaGenerica.isTablet;

public class ListaApps extends AppCompatActivity {
    private JsonManager jsonManager;
    private String contenido;
    private JSONArray entrys;
    private ControladorEntry controladorEntry;
    private GridView lista;
    private ListaEntrys adapter;
    private final VistaGenerica vistaGenerica = new VistaGenerica(this);

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
        setContentView(R.layout.activity_lista_apps);
        vistaGenerica.setOrientacion();
        cargarPersistencia();
        cargarVistas();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_categorias, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.categorias:
                cargarCategorias();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void cargarVistas() {
        lista = (GridView) findViewById(R.id.gridViewLista);
        adapter = new ListaEntrys(this, controladorEntry, isTablet(this));
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cargarApp(i);
            }
        });
        lista.setAdapter(adapter);
    }

    private void cargarPersistencia() {
        jsonManager = new JsonManager();
        contenido = jsonManager.cargarJson(this);

        try {
            entrys = new JSONObject(contenido).getJSONObject(getString(R.string.feed)).getJSONArray(getString(R.string.entry));
            controladorEntry = new ControladorEntry(entrys);
            controladorEntry.llenarLista();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.println(Log.ASSERT, "JSON", contenido);
    }

    private void cargarApp(int i) {

        Intent intent = new Intent(this, AppActivity.class);
        Entry entry = controladorEntry.getEntry(i);
        intent.putExtra(getResources().getString(R.string.intent_name), entry.getImName().getLabel());
        intent.putExtra(getResources().getString(R.string.intent_sumary), entry.getSummary().getLabel());
        intent.putExtra(getResources().getString(R.string.intent_moneda), entry.getImPrice().getAttributes().getCurrency());
        intent.putExtra(getResources().getString(R.string.intent_price), entry.getImPrice().getAttributes().getAmount());
        intent.putExtra(getResources().getString(R.string.intent_categoria), entry.getCategory().getAttributes().getLabel());
        intent.putExtra(getResources().getString(R.string.intent_urlcategoria), entry.getCategory().getAttributes().getScheme());
        intent.putExtra(getResources().getString(R.string.intent_autor), entry.getImArtist().getLabel());
        intent.putExtra(getResources().getString(R.string.intent_url), entry.getLink().getAttributes().getHref());
        intent.putExtra(getResources().getString(R.string.intent_img), controladorEntry.menorImagen(entry.getImImage()).getLabel());
        intent.putExtra(getResources().getString(R.string.intent_imgname), entry.getImName().getLabel() + controladorEntry.mayorImagen(entry.getImImage()).getAttributes().getHeight() + getResources().getString(R.string.ex_img));
        intent.putExtra(getResources().getString(R.string.intent_release), entry.getImReleaseDate().getLabel());
        startActivity(intent);
    }

    private void cargarCategorias() {
        ArrayList<ArrayList<String>> lista = controladorEntry.cargarCategorias();
        Intent intent = new Intent(this, ListaCateogria.class);
        intent.putStringArrayListExtra(getResources().getString(R.string.intenet_categoria), lista.get(0));
        intent.putStringArrayListExtra(getResources().getString(R.string.intenet_urls), lista.get(1));
        startActivity(intent);

    }
}
