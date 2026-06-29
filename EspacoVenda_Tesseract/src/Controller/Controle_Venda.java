package Controller;

import Modelo.Produto;
import Modelo.Venda;
import Modelo.Venda_Item;
import Service.Caixa;

public class Controle_Venda {
    private Caixa caixa;

    public Controle_Venda(Caixa caixa) {
        this.caixa = caixa;
    }

    // Método auxiliar para pegar o carrinho do cara que está logado no momento
    public Venda getVendaAtual() {
        if (caixa.getUsuarioLogado() != null) {
            return caixa.getUsuarioLogado().getCarrinho(); // Puxa a venda aberta do usuário!
        }
        return null;
    }

    // Adicionar Item ao Carrinho (Ligeiramente modificado para usar o carrinho do usuário)
    public boolean adicionarItemAoCarrinho(int codigoProduto, int quantidade) {
        Produto p = caixa.buscarProdutoPorCodigo(codigoProduto);
        Venda carrinhoAtual = getVendaAtual();

        // Ele ainda verifica se tem estoque suficiente antes de deixar colocar no carrinho!
        if (carrinhoAtual != null && p != null && p.getEstoque() >= quantidade) {
            double subTotal = p.getPreco() * quantidade;
            Venda_Item item = new Venda_Item(p, quantidade, subTotal);

            carrinhoAtual.adicionarItem(item);
            return true;
        }
        return false;
    }

    // Finalizar a Venda
    public void finalizarVendaAtual() {
        Venda carrinhoAtual = getVendaAtual();

        if (carrinhoAtual != null) {

            // 🌟 CORREÇÃO: O laço 'for' VOLTOU!
            // Agora o estoque diminui DE VERDADE apenas no momento de pagar!
            for (Venda_Item item : carrinhoAtual.getItens()) {
                item.getProduto().baixarEstoque(item.getQuantidade());
            }

            // 1. Salva a venda no histórico da loja
            caixa.registrarVenda(carrinhoAtual);

            // 2. Reseta o carrinho APENAS do usuário que acabou de comprar
            caixa.getUsuarioLogado().limparCarrinho();
        }
    }
}