package ponny.org.prueba.controlador.conexion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Daniel on 12/06/2016.
 * Realiza la descarga de imagenes
 */
public class TareaConexionImagen extends AsyncTask<String, String, Bitmap> {
    private Context mContext;
    private ImageView img;
    private String namearchivo;
    private final boolean internet;

    public TareaConexionImagen(Context mContext, ImageView img, String namearchivo) {
        this.mContext = mContext;
        this.img = img;
        this.namearchivo = namearchivo;
        internet = Internet.SalidaInternet(mContext);

    }

    public boolean validarConexion() {
        if (internet) {

            EliminaExistente(namearchivo);
            return false;
        }
        if (existe(namearchivo)) {
            try {

                cargarDescarga();

                return true;
            } catch (Exception ex) {
                return true;
            }

        } else {
            return true;
        }

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected Bitmap doInBackground(String... args) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap image) {
        img.setImageBitmap(image);
        guardarImagen(image, namearchivo);

    }

    private void cargarDescarga() {
        img.setImageBitmap(CargarImagen(namearchivo));
    }

    private boolean existe(String nombre) {
        File f = new File(mContext.getApplicationContext().getCacheDir(), nombre);
        return f.exists();

    }

    private boolean EliminaExistente(String nombre) {
        File f = new File(mContext.getApplicationContext().getCacheDir(), nombre);
        return f.delete();

    }

    private Bitmap CargarImagen(String nombre) {
        try {
            File f = new File(mContext.getApplicationContext().getCacheDir(), nombre);
            Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
            return bitmap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    private void guardarImagen(Bitmap map, String nombre) {
        //create a file to write bitmap data
        File f = new File(mContext.getApplicationContext().getCacheDir(), nombre);
        if (f.exists()) {

            return;
        }
        try {
            f.createNewFile();

//Convert bitmap to byte array
            Bitmap bitmap = map;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();


        } catch (IOException e) {
            Log.println(Log.ASSERT, "FILE", e.toString());
            e.printStackTrace();
        }

    }
}
