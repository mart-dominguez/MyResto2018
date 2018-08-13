package ofa.cursos.android.app02.myresto.modelo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOHttp implements ProductoDAO {

    @Override
    public List<ProductoMenu> listarMenu() {
        ArrayList<ProductoMenu> resultado = new ArrayList<>();
        HttpURLConnection urlConnection = null;
        DataOutputStream printout =null;
            try {
                URL url = new URL("http://10.0.2.2:5000/productos");
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                InputStreamReader isw = new InputStreamReader(in);
                StringBuilder sb = new StringBuilder();
                int data = isw.read();
                while (data != -1) {
                    char current = (char) data;
                    sb.append(current);
                    data = isw.read();
                }
                Log.d("APP_MY_RESTO",sb.toString());
                JSONArray listaProducto = (JSONArray) new JSONTokener(sb.toString()).nextValue();
                for(int i=0;i<listaProducto.length();i++){
                    JSONObject fila = listaProducto.getJSONObject(i);
                    ProductoMenu unProducto= new ProductoMenu();
                    unProducto.loadFromJson(fila);
                    Log.d("APP_MY_RESTO",unProducto.toString());
                    resultado.add(unProducto);
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(printout!=null) try {
                    printout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(urlConnection !=null)urlConnection.disconnect();
            }
            return resultado;
    }

    @Override
    public void cargarDatos(String[] datos) {

    }
}
