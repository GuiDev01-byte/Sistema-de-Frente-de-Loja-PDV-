package Service;

import Modelo.Produto;
import Modelo.Usuario;
import Modelo.Venda;
import java.util.ArrayList;

public class Caixa  {

    private ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    private ArrayList<Produto> listaProdutos;
    private ArrayList<Venda> historicoVendas;

    private Usuario usuarioLogado;

    public Caixa() {
        this.listaProdutos = new ArrayList<>();
        this.historicoVendas = new ArrayList<>();

        listaUsuarios.add(new Usuario("adm", "123", "administrador"));
        listaUsuarios.add(new Usuario("vendedor", "987", "vendedor"));
    }

    public boolean cadastrarUsuario(Usuario novoUsuario) {
        if (buscarUsuario(novoUsuario.getLogin()) != null) {
            return false;
        }
        this.listaUsuarios.add(novoUsuario);
        return true;
    }

    public Usuario buscarUsuario(String login) {
        for (Usuario u : listaUsuarios) {
            if (u.getLogin().equalsIgnoreCase(login)) {
                return u;
            }
        }
        return null;
    }

    public void cadastrarProduto(Produto p) {
        this.listaProdutos.add(p);
    }

    public ArrayList<Produto> getListaProdutos() {
        return this.listaProdutos;
    }

    public Produto buscarProdutoPorCodigo(int codigo) {
        for (Produto p : listaProdutos) {
            if (p.getCodigo() == codigo) {
                return p;
            }
        }
        return null;
    }

    //Métodos para as Vendas
    public void registrarVenda(Venda v) {
        this.historicoVendas.add(v);
    }

    public ArrayList<Venda> getListaVendas() {
        return this.historicoVendas;
    }

    public void setUsuarioLogado(Usuario u) { this.usuarioLogado = u; }
    public Usuario getUsuarioLogado() { return this.usuarioLogado; }

}
