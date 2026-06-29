package View;

import Service.Caixa;
import Modelo.Produto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TelaLista extends JPanel {

    private Caixa caixa;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaLista(Caixa caixa) {
        this.caixa = caixa;

        // Configuração do Painel (Mesmo padrão Azure das outras telas)
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(240, 255, 255));
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- 1. TÍTULO ---
        JLabel lblTitulo = new JLabel("Produtos Cadastrados");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- 2. CONFIGURAÇÃO DA TABELA ---
        // Definimos as colunas que vão aparecer
        String[] colunas = {"Código", "Nome do Produto", "Preço (R$)", "Estoque"};

        // O DefaultTableModel impede que o usuário edite as células dando dois cliques
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabela.setRowHeight(22);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabela.getTableHeader().setReorderingAllowed(false); // Impede de arrastar as colunas do lugar

        // Coloca a tabela dentro de um painel de rolagem (obrigatório para Swing)
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setMaximumSize(new Dimension(500, 300));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- 3. BOTÃO DE ATUALIZAR (Opcional, mas ótimo para testes) ---
        JButton btnAtualizar = new JButton("Atualizar Lista");
        btnAtualizar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAtualizar.setBackground(new Color(195, 215, 245));
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.setBorderPainted(false);
        btnAtualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAtualizar.setMaximumSize(new Dimension(150, 30));
        btnAtualizar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- EMPILHAMENTO DOS COMPONENTES ---
        this.add(Box.createVerticalGlue());
        this.add(lblTitulo);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(scrollPane);
        this.add(Box.createRigidArea(new Dimension(0, 15)));
        this.add(btnAtualizar);
        this.add(Box.createVerticalGlue());

        // --- AÇÕES ---
        btnAtualizar.addActionListener(e -> carregarDados());

        // Carrega os dados automaticamente assim que a tela abre
        carregarDados();
    }

    /**
     * Limpa a tabela e busca os dados atualizados diretamente do objeto Caixa
     */
    private void carregarDados() {
        // Limpa todas as linhas antigas da tabela para não duplicar
        modeloTabela.setRowCount(0);

        // Supondo que na sua classe Caixa o método se chame getListaProdutos()
        // ou que você tenha uma forma de listar os produtos. Ajuste o nome se necessário!
        ArrayList<Produto> produtos = caixa.getListaProdutos();

        if (produtos != null) {
            for (Produto p : produtos) {
                // Cria uma linha com os dados do produto
                Object[] linha = {
                        p.getCodigo(),
                        p.getNome(),
                        String.format("%.2f", p.getPreco()),
                        p.getEstoque()
                };
                // Adiciona a linha na tabela visual
                modeloTabela.addRow(linha);
            }
        }
    }
}