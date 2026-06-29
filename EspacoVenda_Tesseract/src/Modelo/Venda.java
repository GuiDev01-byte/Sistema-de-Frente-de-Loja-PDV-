package Modelo;

import java.util.ArrayList;

public class Venda {

    private ArrayList<Venda_Item> itens;

    public Venda() {
        itens = new ArrayList<>();
    }

    public void adicionarItem(Venda_Item item) {
        itens.add(item);
    }

    public double calcularTotal() {
        double total = 0;

        for (Venda_Item item : itens) {
            total += item.getSubTotal();
        }

        return total;
    }

    public ArrayList<Venda_Item> getItens() {
        return itens;
    }
}
