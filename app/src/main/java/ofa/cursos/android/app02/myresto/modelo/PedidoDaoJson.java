package ofa.cursos.android.app02.myresto.modelo;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PedidoDaoJson implements  PedidoDAO{

    String FILENAME = "pedido";
    Context ctx;

    private static List<Pedido> REPOSITORIO_PEDIDOS = new ArrayList<>();

    public PedidoDaoJson(Context ctx){
        this.ctx = ctx;
        cargarLista();
    }

    private void cargarLista(){
        try {
            JSONArray datos = (JSONArray) new JSONTokener(this.leerDeArchivo()).nextValue();
            REPOSITORIO_PEDIDOS.clear();
            for(int i=0;i<datos.length();i++){
                JSONObject fila = datos.getJSONObject(i);
                Pedido unPedido = new Pedido();
                unPedido.loadFromJson(fila);
                Log.d("APP_MY_RESTO",unPedido.toString());
                REPOSITORIO_PEDIDOS.add(unPedido);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void guardarLista(){
        JSONArray arregloPedidos = new JSONArray();
        for(Pedido p : this.REPOSITORIO_PEDIDOS){
            arregloPedidos.put(p.toJson());
        }
        escribirEnArchivo(arregloPedidos);
    }

    private void escribirEnArchivo(JSONArray arregloPedidos){
        FileOutputStream fos = null;
        try {
            fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            Log.d("APP_MY_RESTO",arregloPedidos.toString());
            fos.write(arregloPedidos.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String leerDeArchivo(){
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
    public void agregar(Pedido pedido) {
        REPOSITORIO_PEDIDOS.add(pedido);
        guardarLista();
    }

    @Override
    public void actualizar(Pedido pedido) {
        int indice = REPOSITORIO_PEDIDOS.indexOf(pedido);
        REPOSITORIO_PEDIDOS.set(indice,pedido);
        guardarLista();
    }

    @Override
    public void eliminar(Pedido pedido) {
        REPOSITORIO_PEDIDOS.remove(pedido);
        guardarLista();
    }

    @Override
    public List<Pedido> listarTodos() {
        return REPOSITORIO_PEDIDOS;
    }


    @Override
    public Pedido buscarPorId(Integer id) {
        for(Pedido unPedido: REPOSITORIO_PEDIDOS){
            if(unPedido.getId().equals(id)) return unPedido;
        }
        return null;
    }


}