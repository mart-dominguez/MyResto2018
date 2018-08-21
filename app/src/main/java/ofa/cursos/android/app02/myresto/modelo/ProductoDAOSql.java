package ofa.cursos.android.app02.myresto.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductoDAOSql implements ProductoDAO {

    private List<ProductoMenu> listaProductos;
    private Boolean flagListaCargada;

    public ProductoDAOSql(Context ctx){
        DatabaseManager.initializeInstance(new MyRestoOpenHelper(ctx));
        flagListaCargada = false;
        listaProductos= new ArrayList<>();
    }

    @Override
    public List<ProductoMenu> listarMenu() {
        if(!flagListaCargada) this.cargarDatos(null);
        return listaProductos;

    }

    @Override
    public void cargarDatos(String[] datos) {
        SQLiteDatabase dbConn = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = dbConn.rawQuery("SELECT _id,nombre,precio from PRODUCTO ",null);
        while (!cursor.isLast()){
            ProductoMenu aux = new ProductoMenu();
            aux.setPrecio(cursor.getDouble(cursor.getColumnIndex("precio")));
            aux.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
            aux.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            listaProductos.add(aux);
        }
        DatabaseManager.getInstance().closeDatabase();
        this.flagListaCargada= true;
    }
}
