package ponny.org.prueba.controlador.json;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ponny.org.prueba.modelo.entidades.Categoria;
import ponny.org.prueba.modelo.entidades.entry.Attributes;
import ponny.org.prueba.modelo.entidades.entry.Attributes_artist;
import ponny.org.prueba.modelo.entidades.entry.Attributes_category;
import ponny.org.prueba.modelo.entidades.entry.Attributes_price;
import ponny.org.prueba.modelo.entidades.entry.Attributes_releaseDate;
import ponny.org.prueba.modelo.entidades.entry.Category;
import ponny.org.prueba.modelo.entidades.entry.Entry;
import ponny.org.prueba.modelo.entidades.entry.ImArtist;
import ponny.org.prueba.modelo.entidades.entry.ImImage;
import ponny.org.prueba.modelo.entidades.entry.ImName;
import ponny.org.prueba.modelo.entidades.entry.ImPrice;
import ponny.org.prueba.modelo.entidades.entry.ImReleaseDate;

/**
 * Created by Daniel on 04/11/2016.
 */
public class ControladorEntry {
    private JSONArray array;
    private List<Entry> listaEntrys;
    private Gson gson;

    public ControladorEntry(JSONArray array) {
        this.array = array;
        gson = new Gson();
        listaEntrys = new ArrayList<>();
    }

    public ControladorEntry() {
        gson = new Gson();
    }

    /**
     * Lee el array y lo stranforma a objetos
     */
    public void llenarLista() {
        for (int i = 0; i < array.length(); i++) {

            try {
                Entry entry = gson.fromJson(array.get(i).toString(), Entry.class);
                entry.setImName(new ImName(array.getJSONObject(i).getJSONObject("im:name").getString("label")));
                entry.setCategory(cargarCategoria(array.getJSONObject(i)));
                entry.setImPrice(cargarPrice(array.getJSONObject(i)));
                entry.setImReleaseDate(cargarRealse(array.getJSONObject(i)));
                entry.setImArtist(cargarArtista(array.getJSONObject(i)));
                entry.setImImage(setImges(array.getJSONObject(i)));
                //          Log.println(Log.ASSERT, "INFO", entry.getImName().getLabel());
                listaEntrys.add(entry);

            } catch (JSONException e) {
                Log.println(Log.ASSERT, "INFO", e.toString());
                e.printStackTrace();
            }


        }
    }

    /**
     * @param objeto json a tratar
     * @return lsita de imagenes disponibles de la app
     * @throws JSONException
     */
    public List<ImImage> setImges(JSONObject objeto) throws JSONException {
        List<ImImage> lista = new ArrayList<>();
        JSONArray array = objeto.getJSONArray("im:image");
        for (int i = 0; i < array.length(); i++) {
            ImImage imagen = new ImImage();
            imagen.setLabel(array.getJSONObject(i).getString("label"));
            imagen.setAttributes(new Attributes(array.getJSONObject(i).getJSONObject("attributes").getString("height")));
            lista.add(imagen);
        }
        return lista;
    }

    /**
     * Devuelve la imagen mas pequeña
     *
     * @param lista
     * @return
     */
    public ImImage menorImagen(List<ImImage> lista) {
        ImImage aux = lista.get(0);
        for (int i = 1; i < lista.size(); i++) {
            if (Integer.parseInt(aux.getAttributes().getHeight()) <
                    Integer.parseInt(lista.get(i).getAttributes().getHeight())
                    )
                aux = lista.get(i);
        }
        return aux;
    }

    /**
     * Devbuelve la imagen mas grande
     *
     * @param lista
     * @return
     */
    public ImImage mayorImagen(List<ImImage> lista) {
        ImImage aux = lista.get(0);
        for (int i = 1; i < lista.size(); i++) {
            if (Integer.parseInt(aux.getAttributes().getHeight()) >
                    Integer.parseInt(lista.get(i).getAttributes().getHeight())
                    )
                aux = lista.get(i);
        }
        return aux;
    }

    public Entry getEntry(int i) {
        return listaEntrys.get(i);
    }

    public Entry getEntry(String entry) {
        return gson.fromJson(entry, Entry.class);
    }

    public List<Entry> getListaEntrys() {
        return listaEntrys;
    }

    private ImPrice cargarPrice(JSONObject object) throws JSONException {
        ImPrice price = new ImPrice();
        price.setLabel(object.getJSONObject("im:price").getString("label"));
        Attributes_price atributos = new Attributes_price();
        atributos.setAmount(object.getJSONObject("im:price").getJSONObject("attributes").getString("amount"));
        atributos.setCurrency(object.getJSONObject("im:price").getJSONObject("attributes").getString("currency"));
        price.setAttributes(atributos);
        return price;
    }

    private ImReleaseDate cargarRealse(JSONObject object) throws JSONException {
        ImReleaseDate realse = new ImReleaseDate();
        realse.setLabel(object.getJSONObject("im:releaseDate").getString("label"));
        Attributes_releaseDate atributos = new Attributes_releaseDate();
        atributos.setLabel(object.getJSONObject("im:releaseDate").getJSONObject("attributes").getString("label"));
        realse.setAttributes(atributos);
        return realse;
    }

    private Category cargarCategoria(JSONObject object) throws JSONException {
        Category category = new Category();
        Attributes_category atributos = new Attributes_category();
        atributos.setLabel(object.getJSONObject("category").getJSONObject("attributes").getString("label"));
        atributos.setImId(object.getJSONObject("category").getJSONObject("attributes").getString("im:id"));
        atributos.setTerm(object.getJSONObject("category").getJSONObject("attributes").getString("term"));
        atributos.setScheme(object.getJSONObject("category").getJSONObject("attributes").getString("scheme"));
        category.setAttributes(atributos);
        return category;
    }

    private ImArtist cargarArtista(JSONObject object) throws JSONException {
        ImArtist artis = new ImArtist();
        Attributes_artist artista = new Attributes_artist();
        artis.setLabel(object.getJSONObject("im:artist").getString("label"));
        artista.setHref(object.getJSONObject("im:artist").getJSONObject("attributes").getString("href"));
        artis.setAttributes(artista);
        return artis;
    }

    /**
     * Collection de categorias apartir de un hashmap
     * @return
     */
    private Collection<Categoria> cargarCateogria() {
        HashMap<String, Categoria> categoriaHashMap = new HashMap<>();
        for (Entry entry : getListaEntrys()) {
            categoriaHashMap.put(entry.getId().getLabel(), new Categoria(entry.getCategory(), menorImagen(entry.getImImage()).getLabel()));
        }
        return categoriaHashMap.values();
    }

    /**
     *
     * @return lista de categorias por url y nombre ordenado
     */
    public ArrayList<ArrayList<String>> cargarCategorias() {
        ArrayList<ArrayList<String>> contenido = new ArrayList<>();
        ArrayList<String> nombre = new ArrayList<>();
        ArrayList<String> url = new ArrayList<>();
        Iterator<Categoria> categoriaIterator = cargarCateogria().iterator();
        while (categoriaIterator.hasNext()) {

            nombre.add(categoriaIterator.next().getCategoria().getAttributes().getLabel());
            url.add(categoriaIterator.next().getImagen());

        }
        contenido.add(nombre);
        contenido.add(url);
        return contenido;
    }
}
