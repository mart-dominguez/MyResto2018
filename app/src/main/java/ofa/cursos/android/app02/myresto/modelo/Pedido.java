package ofa.cursos.android.app02.myresto.modelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pedido {
    private Integer id;
    private String nombre;
    private String pedido;
    private boolean envioDomicilio;
    private boolean bebidaXL;
    private boolean permiteCancelar;
    private boolean incluyePropina;
    private boolean enviarNotificaciones;
    private boolean pagoAuotomatico;
    private List<DetallePedido> itemsPedidos;
    private Estado estado;
    private static int idGenerator = 0;

    public Pedido(){
        this.id = ++Pedido.idGenerator;
        this.itemsPedidos = new ArrayList<>();
    }

    @Override
    public String toString() {
        return nombre+ " "+ estado;
    }

    public void addItemDetalle(DetallePedido prd){
        this.itemsPedidos.add(prd);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public boolean isEnvioDomicilio() {
        return envioDomicilio;
    }

    public void setEnvioDomicilio(boolean envioDomicilio) {
        this.envioDomicilio = envioDomicilio;
    }

    public boolean isBebidaXL() {
        return bebidaXL;
    }

    public void setBebidaXL(boolean bebidaXL) {
        this.bebidaXL = bebidaXL;
    }

    public boolean isPermiteCancelar() {
        return permiteCancelar;
    }

    public void setPermiteCancelar(boolean permiteCancelar) {
        this.permiteCancelar = permiteCancelar;
    }

    public boolean isIncluyePropina() {
        return incluyePropina;
    }

    public void setIncluyePropina(boolean incluyePropina) {
        this.incluyePropina = incluyePropina;
    }

    public boolean isEnviarNotificaciones() {
        return enviarNotificaciones;
    }

    public void setEnviarNotificaciones(boolean enviarNotificaciones) {
        this.enviarNotificaciones = enviarNotificaciones;
    }

    public boolean isPagoAuotomatico() {
        return pagoAuotomatico;
    }

    public void setPagoAuotomatico(boolean pagoAuotomatico) {
        this.pagoAuotomatico = pagoAuotomatico;
    }

    public List<DetallePedido> getItemsPedidos() {
        return itemsPedidos;
    }

    public void setItemsPedidos(List<DetallePedido> itemsPedidos) {
        this.itemsPedidos = itemsPedidos;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void preparar(){
        if(this.estado==Estado.CONFIRMADO){
            this.estado = Estado.EN_PREPARACION;
        }
    }

    public void enviar(){
        if(this.estado==Estado.EN_PREPARACION){
            this.estado = Estado.EN_ENVIO;
        }
    }

    public void entregar(){
        if(this.estado==Estado.EN_ENVIO){
            this.estado = Estado.ENTREGADO;
        }
    }

    public JSONObject toJson(){
        JSONObject unPedido = new JSONObject();
        try {
            unPedido.put("id",this.getId());
            unPedido.put("pagoAutomatico",this.isPagoAuotomatico());
            unPedido.put("envioNotificacion",this.isEnviarNotificaciones());
            unPedido.put("envioDomicilio",this.isEnvioDomicilio());
            unPedido.put("incluyePropina",this.isIncluyePropina());
            unPedido.put("bebidaXL",this.isBebidaXL());
            unPedido.put("permiteCancelar",this.isPermiteCancelar());
            unPedido.put("nombre",this.getNombre());
            unPedido.put("estado",this.getEstado().toString());
            JSONArray arregloDetalle = new JSONArray();
            for(DetallePedido dp : this.getItemsPedidos()){
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
        return unPedido;
    }

    public void loadFromJson(JSONObject fila ){
        try {
            this.setId(fila.getInt("id"));
            this.setPagoAuotomatico(fila.getBoolean("pagoAutomatico"));
            this.setEnviarNotificaciones(fila.getBoolean("envioNotificacion"));
            this.setEnvioDomicilio(fila.getBoolean("envioDomicilio"));
            this.setIncluyePropina(fila.getBoolean("incluyePropina"));
            this.setBebidaXL(fila.getBoolean("bebidaXL"));
            this.setPermiteCancelar(fila.getBoolean("permiteCancelar"));
            this.setNombre(fila.getString("nombre"));
            this.setEstado(Estado.valueOf(fila.getString("estado")));
            if(fila.getJSONArray("detalle").length()>0){
                this.setItemsPedidos(new ArrayList<DetallePedido>());
                JSONArray detallePedido = fila.getJSONArray("detalle");
                for(int j =0;j<detallePedido.length();j++){
                    DetallePedido detalleAux = new DetallePedido();
                    JSONObject filaDetalle = detallePedido.getJSONObject(j);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
