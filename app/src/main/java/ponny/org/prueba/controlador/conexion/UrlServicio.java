package ponny.org.prueba.controlador.conexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by daniel on 09/06/2016.
 * Proceso logico que descarga el JSON
 */
public class UrlServicio {
    private String url;

    public UrlServicio(String url) {
        this.url = url;
    }

    private String respuesta = "";

    public void getJSON() {
        HttpURLConnection c = null;
        try {
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.connect();
            respuesta = getRespuestaConnection(c);

        } catch (Exception ex) {
            respuesta = "ERROR," + ex.toString();
            ex.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    respuesta = "ERROR," + ex.toString();
                    ex.printStackTrace();
                    //disconnect error
                }
            }
        }

    }

    private String getRespuestaConnection(HttpURLConnection connection) throws IOException {
        int status = connection.getResponseCode();
        switch (status) {
            case 200:
            case 201:
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                return sb.toString();
            default:
                return null;
        }
    }

    public String getRespuesta() {
        return respuesta;
    }
}
