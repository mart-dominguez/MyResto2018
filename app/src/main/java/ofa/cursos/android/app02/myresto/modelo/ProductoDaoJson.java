package ofa.cursos.android.app02.myresto.modelo;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ProductoDaoJson implements ProductoDAO {

    private static List<ProductoMenu> MENU_PRODUCTOS;

    String FILENAME = "hello_file";
    String string = "hello world!";

    Context ctx;



    private void escribir(String datos){
        FileOutputStream fos = null;
        try {
            fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(datos.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ProductoMenu> listarMenu() {
        if(MENU_PRODUCTOS==null){
            MENU_PRODUCTOS = new ArrayList<>();
        }
        return MENU_PRODUCTOS;
    }

    private String leer(){
        FileInputStream fis = null;
        StringBuilder sb = new StringBuilder();
        try {
            fis = ctx.openFileInput(FILENAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public void cargarDatos(String[] datos) {
        MENU_PRODUCTOS = new ArrayList<>();
        for(String unaFila : datos){
            Log.d("APP_MY_RESTO",unaFila);
            StringTokenizer tokens = new StringTokenizer(unaFila, "|");
            ProductoMenu aux = new ProductoMenu();
            aux.setId(Integer.valueOf(tokens.nextToken()));
            aux.setNombre(tokens.nextToken());
            aux.setPrecio(Double.valueOf(tokens.nextToken()));
            MENU_PRODUCTOS.add(aux);
        }
    }
}