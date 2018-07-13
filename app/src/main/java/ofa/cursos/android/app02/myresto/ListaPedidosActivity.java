package ofa.cursos.android.app02.myresto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import ofa.cursos.android.app02.myresto.modelo.Pedido;
import ofa.cursos.android.app02.myresto.modelo.PedidoDAO;
import ofa.cursos.android.app02.myresto.modelo.PedidoDAOMemory;

public class ListaPedidosActivity extends AppCompatActivity {

    private ArrayAdapter<Pedido> adaptadorLista;
    private PedidoDAO pedidoDAO;
    private Button btnNuevoPedido;
    private ListView listaPedidos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);
        this.pedidoDAO = new PedidoDAOMemory();
        this.adaptadorLista = new ArrayAdapter<>(ListaPedidosActivity.this,android.R.layout.simple_list_item_1,pedidoDAO.listarTodos());
        this.btnNuevoPedido = (Button) findViewById(R.id.btnNuevosPedidos);
        this.listaPedidos = (ListView) findViewById(R.id.listaPedidos);

        this.listaPedidos.setAdapter(this.adaptadorLista);

        this.btnNuevoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevoPedidoIntent = new Intent(ListaPedidosActivity.this,MainActivity.class);
                startActivity(nuevoPedidoIntent);
            }
        });

    }
}
