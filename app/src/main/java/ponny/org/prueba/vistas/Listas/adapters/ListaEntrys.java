package ponny.org.prueba.vistas.Listas.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import ponny.org.prueba.R;
import ponny.org.prueba.controlador.conexion.TareaConexionImagen;
import ponny.org.prueba.controlador.json.ControladorEntry;
import ponny.org.prueba.modelo.entidades.entry.Entry;
import ponny.org.prueba.modelo.entidades.entry.ImImage;
import ponny.org.prueba.vistas.Fondos;

/**
 * Created by Daniel on 10/06/2016.
 */
public class ListaEntrys extends BaseAdapter {
    Context mContext;
    Fondos fondos;
    List<Entry> listaEntry;
    int contador = 0;
    private ControladorEntry controladorEntry;
    private boolean isTablet;

    public ListaEntrys(Context mContext, ControladorEntry controladorEntry, boolean isTablet) {
        this.mContext = mContext;
        this.listaEntry = controladorEntry.getListaEntrys();
        this.controladorEntry = controladorEntry;
        this.isTablet = isTablet;
        fondos = new Fondos(mContext);
    }

    @Override
    public int getCount() {
        return listaEntry.size();
    }

    @Override
    public Object getItem(int position) {
        return listaEntry.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Entry entry = listaEntry.get(position);
        convertView = LayoutInflater.from(mContext).inflate(R.layout.lista_entrys, null);
        TextView autor = (TextView) convertView.findViewById(R.id.textViewAutorEntry);
        TextView name = (TextView) convertView.findViewById(R.id.textViewNameEntry);
        ImageView img = (ImageView) convertView.findViewById(R.id.imageViewIcoEntry);
        autor.setText(entry.getRights().getLabel());
        setName(name, entry);
        setImg(img, entry);
        contador++;
        darFondo(convertView);
        return convertView;
    }

    private void setImg(ImageView imageView, Entry entry) {

        ImImage imagen;
        if (!isTablet) {
            imagen = controladorEntry.mayorImagen(entry.getImImage());
        } else {
            imagen = controladorEntry.menorImagen(entry.getImImage());
        }
     //   Log.println(Log.ASSERT, "IMG", entry.getImImage().toString() + "");
     //   Log.println(Log.ASSERT, "IMG", imagen.getAttributes().getHeight());
        cargarimg(imageView, entry, imagen);
    }

    private void cargarimg(ImageView imageView, Entry entry, ImImage img) {
        TareaConexionImagen tarea = new TareaConexionImagen(mContext,
                imageView,
                entry.getImName().getLabel() + img.getAttributes().getHeight() + mContext.getResources().getString(R.string.ex_img));
        if (!tarea.validarConexion()) {
        //    Log.println(Log.ASSERT, "ejecutara", "hilo" + entry.getImName().getLabel() + mContext.getResources().getString(R.string.ex_img));
            tarea.execute(img.getLabel());
        }
    }

    private void setName(TextView name, Entry entry) {
        if (entry.getImName().getLabel().contains("'")) {
            name.setText(entry.getImName().getLabel().split(" ")[0]);
        } else {
            name.setText(entry.getImName().getLabel().split("-")[0]);
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
