package ponny.org.prueba.modelo.entidades;


import ponny.org.prueba.modelo.entidades.entry.Category;
import ponny.org.prueba.modelo.entidades.entry.ImImage;

/**
 * Created by Daniel on 10/06/2016.
 */
public class Categoria {
    private Category categoria;
    private String imagen;


    public Categoria(Category categoria, String imagen) {
        this.categoria = categoria;
        this.imagen = imagen;

    }

    public Category getCategoria() {
        return categoria;
    }

    public void setCategoria(Category categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
