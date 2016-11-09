package ponny.org.prueba.vistas.Listas.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ponny.org.prueba.R;
import ponny.org.prueba.controlador.conexion.TareaConexionImagen;
import ponny.org.prueba.vistas.Fondos;

/**
 * Created by Daniel on 08/11/2016.
 * Lista las categorias
 */
public class ListaCategorias extends BaseAdapter {
    private ArrayList<String> titulos;
    private ArrayList<String> urls;
    private Context mContext;
    int contador = 0;
    Fondos fondos;

    public ListaCategorias(ArrayList<String> titulo, ArrayList<String> url, Context mContext) {
        this.titulos = titulo;
        this.urls = url;
        this.mContext = mContext;
        fondos = new Fondos(mContext);
        Log.println(Log.ASSERT, "INFO", titulos.size() + "," + urls.size());
    }

    @Override
    public int getCount() {
        return titulos.size();
    }

    @Override
    public Object getItem(int i) {
        return titulos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        convertView = LayoutInflater.from(mContext).inflate(R.layout.lista_categoria, null);
        TextView titulo = (TextView) convertView.findViewById(R.id.textViewTitulo);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewCategoria);
        cargarimg(imageView, titulos.get(i), urls.get(i));
        titulo.setText(titulos.get(i));
        darFondo(convertView);
        return convertView;
    }

    private void cargarimg(ImageView imageView, String name, String url) {
        TareaConexionImagen tarea = new TareaConexionImagen(mContext,
                imageView,
                name + mContext.getResources().getString(R.string.ex_img));
        if (!tarea.validarConexion()) {
            //    Log.println(Log.ASSERT, "ejecutara", "hilo" + entry.getImName().getLabel() + mContext.getResources().getString(R.string.ex_img));
            tarea.execute(url);
        }
    }

    private void darFondo(View view) {
        if (contador == fondos.getFondos().size()) {
            contador = 0;
        }
        if (contador < fondos.getFondos().size()) {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackgroundDrawable(fondos.getFondos().get(contador));
            } else {
                view.setBackground(fondos.getFondos().get(contador));
            }
        }
    }

}
