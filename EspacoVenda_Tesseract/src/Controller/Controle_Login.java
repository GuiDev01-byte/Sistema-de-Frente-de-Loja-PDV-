package Controller;

import Modelo.Usuario;
import Service.Caixa;

public class Controle_Login {
    private Caixa caixa;

    public Controle_Login(Caixa caixa) {
        this.caixa = caixa;
    }
    //Metodo de Login
    public Usuario autenticar(String login, String senha) {
        Usuario u = caixa.buscarUsuario(login);
        if (u != null && u.getSenha().equals(senha)) {
            return u;
        }
        return null;
    }


    //Metodo para Cadastrar
    public boolean criarContaCliente(String login, String senha) {
        if (login.trim().isEmpty() || senha.trim().isEmpty()) {
            return false;
        }

        Usuario novoCliente = new Usuario(login, senha, "Cliente");
        return caixa.cadastrarUsuario(novoCliente);
    }
}
