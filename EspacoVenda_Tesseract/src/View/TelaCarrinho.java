package View;

import Controller.Controle_Venda;
import Modelo.Venda;
import Modelo.Venda_Item;
import Modelo.Produto;
import Service.Caixa;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaCarrinho extends JPanel {
    private Caixa caixa;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JLabel lblTotal;
    private JButton btnFinalizar, btnCancelar;
    private double precoTotalGeral = 0;

    public TelaCarrinho(Caixa caixa) {
        this.caixa = caixa;

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(240, 255, 255));
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        // TOPO: Título Fixo
        JLabel lblTitulo = new JLabel("Seu Carrinho de Compras", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        this.add(lblTitulo, BorderLayout.NORTH);

        // CENTRO: Tabela de Itens (Colunas fixas para o Carrinho)
        String[] colunas = {"Produto", "Qtd", "Preço Unitário (R$)", "Subtotal (R$)"};

        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modeloTabela);
        tabela.setRowHeight(25);
        this.add(new JScrollPane(tabela), BorderLayout.CENTER);

        // SUL: Painel de Controle (Total e Botões)
        JPanel painelSul = new JPanel(new BorderLayout(0, 10));
        painelSul.setBackground(new Color(240, 255, 255));
        painelSul.setBorder(new EmptyBorder(15, 0, 0, 0));

        lblTotal = new JLabel("VALOR TOTAL: R$ 0,00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        painelSul.add(lblTotal, BorderLayout.NORTH);

        // Painel de Botões com FlowLayout alinhado à direita
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        painelBotoes.setBackground(new Color(240, 255, 255));

        // Adicionando os botões diretamente (sem restrição de cargo)
        btnCancelar = criarBotao("Cancelar", new Color(220, 53, 69));
        btnCancelar.addActionListener(e -> processarCancelamento());

        btnFinalizar = criarBotao("Comprar", new Color(60, 179, 113));
        btnFinalizar.addActionListener(e -> processarFinalizacao());

        painelBotoes.add(btnCancelar); // Fica na esquerda
        painelBotoes.add(btnFinalizar); // Fica na direita

        painelSul.add(painelBotoes, BorderLayout.SOUTH);
        this.add(painelSul, BorderLayout.SOUTH);

        carregarCarrinho();
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBackground(cor);
        b.setForeground(Color.WHITE);
        b.setPreferredSize(new Dimension(100, 30)); // Tamanho padronizado
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void carregarCarrinho() {
        modeloTabela.setRowCount(0);
        precoTotalGeral = 0;

        // Proteção extra caso não tenha usuário logado
        if (caixa.getUsuarioLogado() != null && caixa.getUsuarioLogado().getCarrinho() != null) {
            Venda v = caixa.getUsuarioLogado().getCarrinho();
            for (Venda_Item i : v.getItens()) {
                modeloTabela.addRow(new Object[]{
                        i.getProduto().getNome(),
                        i.getQuantidade(),
                        String.format("%.2f", i.getProduto().getPreco()),
                        String.format("%.2f", i.getSubTotal())
                });
                precoTotalGeral += i.getSubTotal();
            }
        }
        lblTotal.setText(String.format("VALOR TOTAL: R$ %.2f", precoTotalGeral));
    }

    private void processarCancelamento() {
        if (modeloTabela.getRowCount() == 0) return;
        int opt = JOptionPane.showConfirmDialog(this,
                "Deseja cancelar? Os itens voltarão para o estoque.", "Cancelar", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            Venda v = caixa.getUsuarioLogado().getCarrinho();
            for (Venda_Item i : v.getItens()) {
                Produto p = i.getProduto();
                p.setEstoque(p.getEstoque() + i.getQuantidade());
            }
            caixa.getUsuarioLogado().limparCarrinho();
            carregarCarrinho();
            JOptionPane.showMessageDialog(this, "Compra cancelada com sucesso!");
        }
    }

    private void processarFinalizacao() {
        if (modeloTabela.getRowCount() == 0) return;

        double perc = 0;
        if (precoTotalGeral >= 250) perc = 0.25;
        else if (precoTotalGeral >= 100) perc = 0.10;
        else if (precoTotalGeral >= 50) perc = 0.05;

        double desc = precoTotalGeral * perc;
        double total = precoTotalGeral - desc;

        String msg = String.format("Resumo da Compra:\nTotal: R$ %.2f\nDesconto (%.0f%%): R$ %.2f\nFinal: R$ %.2f\n\nConfirmar?",
                precoTotalGeral, perc*100, desc, total);
        if (JOptionPane.showConfirmDialog(this, msg, "Checkout", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            new Controle_Venda(caixa).finalizarVendaAtual();
            JOptionPane.showMessageDialog(this, "Venda realizada com sucesso!");
            carregarCarrinho();
        }
    }
}