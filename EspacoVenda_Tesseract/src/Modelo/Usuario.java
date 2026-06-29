package Modelo;

public class Usuario {
    private String login;
    private String senha;
    private String cargo;

    private Venda carrinho = new Venda();

    public Usuario(String login, String senha, String cargo) {
        this.login = login;
        this.senha = senha;
        this.cargo = cargo.toLowerCase();
    }

    public String getLogin() {
        return login;
    }
    public String getSenha() {
        return senha;
    }
    public String getCargo() {
        return cargo;
    }

    public Venda getCarrinho() {
        return  this.carrinho;
    }

    public void limparCarrinho() {
        this.carrinho = new Venda();
    }
}
