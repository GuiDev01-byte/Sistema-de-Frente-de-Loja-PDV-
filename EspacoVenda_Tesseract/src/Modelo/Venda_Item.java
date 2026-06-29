package Modelo;

public class Venda_Item {

    private Produto produto;
    private int quantidade;
    private double Subtotal;

    public Venda_Item(Produto produto, int quantidade, double subTotal) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.Subtotal = subTotal;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getSubTotal() {
        return Subtotal;
    }

    public void setSubTotal(double subTotal) {
        Subtotal = subTotal;
    }
}