package ofa.cursos.android.app02.myresto;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

import ofa.cursos.android.app02.myresto.modelo.DetallePedido;
import ofa.cursos.android.app02.myresto.modelo.Pedido;
import ofa.cursos.android.app02.myresto.modelo.PedidoDAO;
import ofa.cursos.android.app02.myresto.modelo.PedidoDAOMemory;
import ofa.cursos.android.app02.myresto.modelo.ProductoDAO;
import ofa.cursos.android.app02.myresto.modelo.ProductoDAOMemory;
import ofa.cursos.android.app02.myresto.modelo.ProductoMenu;

public class MainActivity extends AppCompatActivity {

    private Button btnConfirmar;
    private Button btnAddProducto;
    private EditText txtNombre;
    private EditText txtDetalle;
    private Pedido pedidoActual;

    private PedidoDAO pedidoDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pedidoDao = new PedidoDAOMemory();

        pedidoActual = new Pedido();
        txtNombre = (EditText) findViewById(R.id.txtNombreCliente);
        txtDetalle = (EditText) findViewById(R.id.txtDetallePedido);



        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedidoActual.setNombre(txtNombre.getText().toString());
                Toast.makeText(MainActivity.this,"Pedido creado",Toast.LENGTH_LONG).show();
                Log.d("APP_MY_RESTO","Pedido confirmado!!!! ");
                Log.d("APP_MY_RESTO",pedidoActual.toString());

                pedidoDao.agregar(pedidoActual);

                // limpiar la variable para poder cargar un nuevo pedido
                pedidoActual = new Pedido();
                // limpiar el edit text en la pantalla
                txtNombre.setText("");

                Intent listaPedidosAct  = new Intent(MainActivity.this,ListaPedidosActivity.class);
                startActivity(listaPedidosAct );
            }
        });

        btnAddProducto = (Button) findViewById(R.id.btnAddProducto);
        btnAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listaMenu= new Intent(MainActivity.this,DetallePedidoActivity.class);
                startActivityForResult(listaMenu,999);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==999){
                int cantidad = data.getIntExtra("cantidad",0);
                ProductoMenu prod= (ProductoMenu) data.getSerializableExtra("producto");
                DetallePedido detalle = new DetallePedido();
                detalle.setCantidad(cantidad);
                detalle.setProductoPedido(prod);
                pedidoActual.addItemDetalle(detalle);
                txtDetalle.getText().append(prod.getNombre()+ " $"+(prod.getPrecio()*cantidad));
            }
        }

    }
}
