package ofa.cursos.android.app02.myresto;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import ofa.cursos.android.app02.myresto.modelo.Pedido;
import ofa.cursos.android.app02.myresto.modelo.ProductoDAO;
import ofa.cursos.android.app02.myresto.modelo.ProductoDAOMemory;
import ofa.cursos.android.app02.myresto.modelo.ProductoMenu;

public class DetallePedidoActivity extends AppCompatActivity {
    private ArrayAdapter<ProductoMenu> adaptadorLista;
    private ListView listaMenu;
    private ProductoDAO productoDao;

    private TextView txtCantidad;
    private Button btnMenosProducto;
    private Button btnMasProducto;
    private Button btnAgregarProducto;

    private Integer cantidadProducto;
    private ProductoMenu productoElegido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);
        productoDao = new ProductoDAOMemory();

        btnMasProducto = (Button) findViewById(R.id.btnMasProducto);
        btnMenosProducto = (Button) findViewById(R.id.btnMenosProducto);
        txtCantidad = (TextView) findViewById(R.id.detPedProductoCantidad);
        cantidadProducto = 0;
        txtCantidad.setText(cantidadProducto.toString());

        btnAgregarProducto = (Button) findViewById(R.id.btnAddProducto);
        btnAgregarProducto.setEnabled(false);

        String[] listaProductos = getResources().getStringArray(R.array.listaProductos);
        productoDao.cargarDatos(listaProductos);
        this.adaptadorLista = new ArrayAdapter<>(DetallePedidoActivity.this,android.R.layout.simple_list_item_single_choice,this.productoDao.listarMenu());
        listaMenu = (ListView) findViewById(R.id.listaProductos);
        listaMenu.setAdapter(this.adaptadorLista);
        listaMenu.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listaMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                productoElegido = adaptadorLista.getItem(position);
                Log.d("APP_MY_RESTO"," PRODUCTO "+productoElegido.toString()+"  CANTIDAD: "+cantidadProducto);
                Toast.makeText(DetallePedidoActivity.this," PRODUCTO "+productoElegido.toString()+"  CANTIDAD: "+cantidadProducto,Toast.LENGTH_LONG).show();
            }
        });

        btnMenosProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cantidadProducto>0) {
                    cantidadProducto--;
                    txtCantidad.setText(cantidadProducto.toString());
                    if(cantidadProducto==0) btnAgregarProducto.setEnabled(false);
                }
            }
        });
        btnMasProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cantidadProducto<10) {
                    cantidadProducto++;
                    btnAgregarProducto.setEnabled(true);
                    txtCantidad.setText(cantidadProducto.toString());
                }
            }
        });

        btnAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("APP_MY_RESTO"," PRODUCTOa "+productoElegido.toString()+"  CANTIDADdx: "+cantidadProducto);
                Toast.makeText(DetallePedidoActivity.this," PRODUCTaO "+productoElegido.toString()+"  CANTIDAD: "+cantidadProducto,Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra("producto", productoElegido);
                intent.putExtra("cantidad", cantidadProducto);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


}
