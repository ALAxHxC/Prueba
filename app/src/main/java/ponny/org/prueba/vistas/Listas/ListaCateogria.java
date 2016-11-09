package ponny.org.prueba.vistas.Listas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;

import ponny.org.prueba.R;
import ponny.org.prueba.vistas.Listas.adapters.ListaCategorias;

/**
 * Activity que lsita categorias
 */
public class ListaCateogria extends AppCompatActivity {
    private ArrayList<String> name;
    private ArrayList<String> url;
    private ListaCategorias listaCategorias;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cateogria);
        cargarExtras();
    }

    private void cargarExtras() {
        name = getIntent().getStringArrayListExtra(getResources().getString(R.string.intenet_categoria));
        url = getIntent().getStringArrayListExtra(getResources().getString(R.string.intenet_urls));
        listaCategorias = new ListaCategorias(name, url, this);
        gridView = (GridView) findViewById(R.id.gridViewCategorias);
        gridView.setAdapter(listaCategorias);
        Log.println(Log.ASSERT, "INFO", name + "," + url);

    }

}
