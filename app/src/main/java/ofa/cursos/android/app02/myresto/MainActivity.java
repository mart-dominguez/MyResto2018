package ofa.cursos.android.app02.myresto;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Arrays;

import ofa.cursos.android.app02.myresto.modelo.DetallePedido;
import ofa.cursos.android.app02.myresto.modelo.Estado;
import ofa.cursos.android.app02.myresto.modelo.Pedido;
import ofa.cursos.android.app02.myresto.modelo.PedidoDAO;
import ofa.cursos.android.app02.myresto.modelo.PedidoDAOMemory;
import ofa.cursos.android.app02.myresto.modelo.PedidoDaoJson;
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
    private TextView tvMontoTotal;
    private RadioGroup rgTipoPedido;
    private CheckBox cbIncluirPropina;
    private CheckBox cbBebidaXL;
    private CheckBox cbPermiteCancel;
    private Switch swNotiifcar;
    private ToggleButton toglePagaEntrega;

    private Boolean flagUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // pedidoDao = new PedidoDAOMemory();
        pedidoDao = new PedidoDaoJson(this);
        pedidoActual = new Pedido();
        txtNombre = (EditText) findViewById(R.id.txtNombreCliente);
        txtDetalle = (EditText) findViewById(R.id.txtDetallePedido);
        tvMontoTotal = (TextView) findViewById(R.id.lblMontoTotal);

        rgTipoPedido = (RadioGroup) findViewById(R.id.rgTipoPedido);
        cbIncluirPropina = (CheckBox) findViewById(R.id.cbIncluirPropina);
        cbBebidaXL = (CheckBox) findViewById(R.id.cbBebidaXL);
        cbPermiteCancel = (CheckBox) findViewById(R.id.cbPermitirCancelar);
        swNotiifcar = (Switch) findViewById(R.id.swtNotificaciones);
        toglePagaEntrega = (ToggleButton) findViewById(R.id.toggleButton);

        flagUpdate = false;
        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedidoActual.setEstado(Estado.CONFIRMADO);
                pedidoActual.setNombre(txtNombre.getText().toString());
                pedidoActual.setBebidaXL(cbBebidaXL.isChecked());
                pedidoActual.setIncluyePropina(cbIncluirPropina.isChecked());
                pedidoActual.setEnvioDomicilio(rgTipoPedido.getCheckedRadioButtonId()==R.id.radBtnDelivery);
                pedidoActual.setEnviarNotificaciones(swNotiifcar.isChecked());
                pedidoActual.setPagoAuotomatico(!toglePagaEntrega.isChecked());
                Toast.makeText(MainActivity.this,"Pedido creado",Toast.LENGTH_LONG).show();
                Log.d("APP_MY_RESTO","Pedido confirmado!!!! ");
                Log.d("APP_MY_RESTO",pedidoActual.toString());

                if( flagUpdate ){
                    pedidoDao.actualizar(pedidoActual);
                    flagUpdate= false;
                }else{
                    pedidoDao.agregar(pedidoActual);
                }


                // limpiar la variable para poder cargar un nuevo pedido
                pedidoActual = new Pedido();
                // limpiar el edit text en la pantalla
                txtNombre.setText("");
                txtDetalle.setText("");
                tvMontoTotal.setText("$0.0");
                cbBebidaXL.setChecked(false);
                cbIncluirPropina.setChecked(false);
                swNotiifcar.setChecked(false);
                RadioButton rbDelivery = (RadioButton) findViewById(R.id.radBtnDelivery);
                RadioButton rbMesa = (RadioButton) findViewById(R.id.radBtnMesa);
                    rbDelivery.setChecked(false);
                    rbMesa.setChecked(false);
                toglePagaEntrega.setChecked(false);
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

        if(getIntent().getExtras()!=null){
            int idPedidoSeleccionado = getIntent().getExtras().getInt("idPedido",-1);
            if(idPedidoSeleccionado>0){
                this.loadPedido(idPedidoSeleccionado);
                flagUpdate = true;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("APP_MY_RESTO"," resultCode "+resultCode+"  requestCode: "+requestCode+ " OK("+RESULT_OK+")");

        if(resultCode==RESULT_OK){
            if(requestCode==999){
                int cantidad = data.getIntExtra("cantidad",0);
                ProductoMenu prod= (ProductoMenu) data.getParcelableExtra("producto");
                DetallePedido detalle = new DetallePedido();
                detalle.setCantidad(cantidad);
                detalle.setProductoPedido(prod);
                pedidoActual.addItemDetalle(detalle);
                txtDetalle.getText().append(prod.getNombre()+ " $"+(prod.getPrecio()*cantidad)+"\r\n");
                double totalOrden = 0.0;
                for(DetallePedido det :  pedidoActual.getItemsPedidos()){
                    totalOrden += det.getCantidad()*det.getProductoPedido().getPrecio();
                }
                tvMontoTotal.setText("$"+totalOrden);
            }
        }

    }

    private void loadPedido(int id){
        flagUpdate = true;
        pedidoActual= this.pedidoDao.buscarPorId(id);
        txtNombre.setText(pedidoActual.getNombre());
        cbBebidaXL.setChecked(pedidoActual.isBebidaXL());
        cbIncluirPropina.setChecked(pedidoActual.isIncluyePropina());
        swNotiifcar.setChecked(pedidoActual.isEnviarNotificaciones());
        RadioButton rbDelivery = (RadioButton) findViewById(R.id.radBtnDelivery);
        RadioButton rbMesa = (RadioButton) findViewById(R.id.radBtnMesa);
        if(pedidoActual.isEnvioDomicilio()){
            rbDelivery.setChecked(true);
            rbMesa.setChecked(false);
        }else{
            rbDelivery.setChecked(false);
            rbMesa.setChecked(true);
        }
        toglePagaEntrega.setChecked(!pedidoActual.isPagoAuotomatico());
        double totalOrden = 0.0;
        for(DetallePedido det :  pedidoActual.getItemsPedidos()){
            txtDetalle.getText().append(det.getProductoPedido().getNombre()+ " $"+(det.getProductoPedido().getPrecio()*det.getCantidad())+"\r\n");
            totalOrden += det.getCantidad()*det.getProductoPedido().getPrecio();
        }
        tvMontoTotal.setText("$"+totalOrden);
    }
}
