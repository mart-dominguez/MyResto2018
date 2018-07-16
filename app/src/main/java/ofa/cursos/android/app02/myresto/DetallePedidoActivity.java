package ofa.cursos.android.app02.myresto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ofa.cursos.android.app02.myresto.modelo.Pedido;
import ofa.cursos.android.app02.myresto.modelo.ProductoDAO;
import ofa.cursos.android.app02.myresto.modelo.ProductoDAOMemory;
import ofa.cursos.android.app02.myresto.modelo.ProductoMenu;

public class DetallePedidoActivity extends AppCompatActivity {
    private ArrayAdapter<ProductoMenu> adaptadorLista;
    private ListView listaMenu;
    private ProductoDAO productoDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);
        productoDao = new ProductoDAOMemory();

        String[] listaProductos = getResources().getStringArray(R.array.listaProductos);
        productoDao.cargarDatos(listaProductos);
        this.adaptadorLista = new ArrayAdapter<>(DetallePedidoActivity.this,android.R.layout.simple_list_item_single_choice,this.productoDao.listarMenu());
        listaMenu = (ListView) findViewById(R.id.listaProductos);
        listaMenu.setAdapter(this.adaptadorLista);
    }
}
