package ofa.cursos.android.app02.myresto.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PedidoDAOSql implements PedidoDAO {


    public PedidoDAOSql(Context ctx){
        DatabaseManager.initializeInstance(new MyRestoOpenHelper(ctx));
    }


    @Override
    public void agregar(Pedido pedido) {
        SQLiteDatabase dbConn = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = dbConn.rawQuery("SELECT _id,nombre,precio from PRODUCTO ",null);
        // INSERT
    }

    @Override
    public void eliminar(Pedido pedido) {

    }

    @Override
    public void actualizar(Pedido pedido) {

    }

    @Override
    public List<Pedido> listarTodos() {
        return null;
    }

    @Override
    public Pedido buscarPorId(Integer id) {
        return null;
    }
}
