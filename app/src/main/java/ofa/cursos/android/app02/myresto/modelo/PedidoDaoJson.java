package ofa.cursos.android.app02.myresto.modelo;

import android.content.Context;

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

    private List<Pedido> REPOSITORIO_PEDIDOS;

    public PedidoDaoJson(Context ctx){
        this.ctx = ctx;
        REPOSITORIO_PEDIDOS = new ArrayList<>();
        inicializar();
    }

    private void inicializar(){
        try {
            JSONArray datos = (JSONArray) new JSONTokener(this.leer()).nextValue();
            for(int i=0;i<datos.length();i++){
                JSONObject fila = datos.getJSONObject(i);
                Pedido unPedido = new Pedido();
                unPedido.setId(fila.getInt("id"));
                unPedido.setPagoAuotomatico(fila.getBoolean("pagoAutomatico"));
                unPedido.setEnviarNotificaciones(fila.getBoolean("envioNotificacion"));
                unPedido.setEnvioDomicilio(fila.getBoolean("envioDomicilio"));
                unPedido.setIncluyePropina(fila.getBoolean("incluyePropina"));
                unPedido.setBebidaXL(fila.getBoolean("bebidaXL"));
                unPedido.setPermiteCancelar(fila.getBoolean("permiteCancelar"));
                unPedido.setNombre(fila.getString("nombre"));
                unPedido.setEstado(Estado.valueOf(fila.getString("estado")));
                if(fila.getJSONArray("detalle").length()>0){
                    unPedido.setItemsPedidos(new ArrayList<DetallePedido>());
                    JSONArray detallePedido = fila.getJSONArray("detalle");
                    for(int j =0;j<detallePedido.length();j++){
                        DetallePedido detalleAux = new DetallePedido();
                        JSONObject filaDetalle = detallePedido.getJSONObject(i);
                        detalleAux.setCantidad(filaDetalle.getInt("cantidad"));
                        detalleAux.setId(filaDetalle.getInt("id"));
                        JSONObject elProducto = filaDetalle.getJSONObject("producto");
                        ProductoMenu prd = new ProductoMenu();
                        prd.setId(elProducto.getInt("id"));
                        prd.setNombre(elProducto.getString("nombre"));
                        prd.setPrecio(elProducto.getDouble("precio"));
                        detalleAux.setProductoPedido(prd);
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void escribir(String datos){
        JSONArray arregloPedidos = new JSONArray();
        for(Pedido p : this.REPOSITORIO_PEDIDOS){
            JSONObject unPedido = new JSONObject();
            try {
                unPedido.put("id",p.getId());
                unPedido.put("pagoAutomatico",p.isPagoAuotomatico());
                unPedido.put("envioNotificacion",p.isEnviarNotificaciones());
                unPedido.put("envioDomicilio",p.isEnvioDomicilio());
                unPedido.put("incluyePropina",p.isIncluyePropina());
                unPedido.put("bebidaXL",p.isBebidaXL());
                unPedido.put("permiteCancelar",p.isPermiteCancelar());
                unPedido.put("nombre",p.getNombre());
                unPedido.put("estado",p.getEstado().toString());
                JSONArray arregloDetalle = new JSONArray();
                for(DetallePedido dp : p.getItemsPedidos()){
                    JSONObject unDetallePedido = new JSONObject();
                    unDetallePedido.put("id",dp.getId());
                    unDetallePedido.put("cantidad",dp.getCantidad());
                    JSONObject prod = new JSONObject();
                    prod.put("id",dp.getProductoPedido().getId());
                    prod.put("nombre",dp.getProductoPedido().getNombre());
                    prod.put("precio",dp.getProductoPedido().getPrecio());
                    unDetallePedido.put("producto",prod);
                    arregloDetalle.put(unDetallePedido);
                }
                unPedido.put("detalle",arregloDetalle);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fos = null;
        try {
            fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(arregloPedidos.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    private void agregar(ProductoMenu p){
        JSONObject productoJson = new JSONObject();
        try {
            productoJson.put("id",p.getId());
            productoJson.put("nombre",p.getNombre());
            productoJson.put("precio",p.getPrecio());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void agregar(Pedido pedido) {
        REPOSITORIO_PEDIDOS.add(pedido);
    }

    @Override
    public void actualizar(Pedido pedido) {
        int indice = REPOSITORIO_PEDIDOS.indexOf(pedido);
        REPOSITORIO_PEDIDOS.set(indice,pedido);
    }

    @Override
    public void eliminar(Pedido pedido) {
        REPOSITORIO_PEDIDOS.remove(pedido);
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