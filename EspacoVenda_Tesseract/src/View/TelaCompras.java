package View;

import Controller.Controle_Venda;
import Service.Caixa;
import Modelo.Produto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TelaCompras extends JPanel {

    private Caixa caixa;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaCompras(Caixa caixa) {
        this.caixa = caixa;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(240, 255, 255));
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Área de Compras - Escolha seus Produtos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Configuração da Tabela
        String[] colunas = {"Código", "Nome do Produto", "Preço (R$)", "Estoque"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impede edição direta na tabela
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabela.setRowHeight(22);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 🌟 NOVIDADE: Renderizador para pintar a linha de vermelho se o estoque for ZERO
        tabela.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Pega o valor da coluna "Estoque" (índice 3)
                int estoque = Integer.parseInt(table.getValueAt(row, 3).toString());

                if (estoque == 0) {
                    c.setForeground(Color.RED); // Acabou! Fica vermelho
                    c.setFont(new Font("Segoe UI", Font.BOLD, 14));
                } else {
                    c.setForeground(Color.BLACK); // Normal
                    c.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }

                // Mantém o fundo azul se o usuário clicar na linha
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                } else {
                    c.setBackground(table.getBackground());
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setMaximumSize(new Dimension(500, 250));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão para abrir o quadro flutuante
        JButton btnEscolher = new JButton("Adicionar ao Carrinho");
        btnEscolher.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEscolher.setBackground(new Color(127, 255, 212)); // Aquamarine
        btnEscolher.setFocusPainted(false);
        btnEscolher.setMaximumSize(new Dimension(200, 35));
        btnEscolher.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEscolher.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Montagem da tela
        this.add(Box.createVerticalGlue());
        this.add(lblTitulo);
        this.add(Box.createRigidArea(new Dimension(0, 15)));
        this.add(scrollPane);
        this.add(Box.createRigidArea(new Dimension(0, 15)));
        this.add(btnEscolher);
        this.add(Box.createVerticalGlue());

        // Ação do Botão
        btnEscolher.addActionListener(e -> processarEscolha());

        carregarProdutos();
    }

    private void carregarProdutos() {
        modeloTabela.setRowCount(0);
        ArrayList<Produto> produtos = caixa.getListaProdutos();

        if (produtos == null || produtos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum produto cadastrado no momento. Volte mais tarde!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (Produto p : produtos) {
            Object[] linha = {
                    p.getCodigo(),
                    p.getNome(),
                    String.format("%.2f", p.getPreco()),
                    p.getEstoque()
            };
            modeloTabela.addRow(linha);
        }
    }

    private void processarEscolha() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, clique em um produto na lista primeiro!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Produto produtoEscolhido = caixa.getListaProdutos().get(linhaSelecionada);

        // Se o produto já estiver zerado, avisa antes mesmo de pedir quantidade
        if (produtoEscolhido.getEstoque() == 0) {
            JOptionPane.showMessageDialog(this, "Este produto está esgotado no momento!", "Estoque Vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String inputQtd = JOptionPane.showInputDialog(this,
                "Produto: " + produtoEscolhido.getNome() + "\nEstoque disponível: " + produtoEscolhido.getEstoque() +
                        "\n\nDigite a quantidade que deseja comprar:",
                "Quantidade", JOptionPane.QUESTION_MESSAGE);

        if (inputQtd != null && !inputQtd.trim().isEmpty()) {
            try {
                int quantidadeEscolhida = Integer.parseInt(inputQtd.trim());

                if (quantidadeEscolhida <= 0) {
                    JOptionPane.showMessageDialog(this, "A quantidade deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (quantidadeEscolhida > produtoEscolhido.getEstoque()) {
                    JOptionPane.showMessageDialog(this, "Desculpe, não temos essa quantidade em estoque!", "Estoque Insuficiente", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double valorTotal = produtoEscolhido.getPreco() * quantidadeEscolhida;

                String mensagemConfirmacao = String.format("Você escolheu %d x %s.\nValor Total: R$ %.2f\n\nConfirmar adição ao carrinho?",
                        quantidadeEscolhida, produtoEscolhido.getNome(), valorTotal);

                int confirmacao = JOptionPane.showConfirmDialog(this, mensagemConfirmacao, "Confirmar Compra", JOptionPane.YES_NO_OPTION);

                if (confirmacao == JOptionPane.YES_OPTION) {

                    // 🌟 NOVIDADE: Chama o Controle_Venda que você criou!
                    Controle_Venda controleVenda = new Controle_Venda(caixa);
                    boolean adicionado = controleVenda.adicionarItemAoCarrinho(produtoEscolhido.getCodigo(), quantidadeEscolhida);

                    if (adicionado) {
                        // Diminui o estoque da prateleira "na hora"
                        produtoEscolhido.setEstoque(produtoEscolhido.getEstoque() - quantidadeEscolhida);

                        JOptionPane.showMessageDialog(this, "Adicionado ao carrinho com sucesso!");

                        // Atualiza a tabela para refletir o novo estoque (e pintar de vermelho se zerou)
                        carregarProdutos();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao processar o carrinho ou usuário não logado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, digite apenas números inteiros!", "Erro de Digitação", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}