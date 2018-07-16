package ofa.cursos.android.app02.myresto.modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class DetallePedido implements Parcelable{
    private int id;
    private ProductoMenu productoPedido;
    private int cantidad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductoMenu getProductoPedido() {
        return productoPedido;
    }

    public void setProductoPedido(ProductoMenu productoPedido) {
        this.productoPedido = productoPedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
