package ofa.cursos.android.app02.myresto.modelo;

import java.util.ArrayList;
import java.util.List;

public class PedidoDAOMemory implements  PedidoDAO{

    private static List<Pedido> REPOSITORIO_PEDIDOS = new ArrayList<>();

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
