package View;

import Service.Caixa;
import Modelo.Produto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaProduto extends JPanel {

    private Caixa caixa;
    private JTextField txtCodigo;
    private JTextField txtNome;
    private JTextField txtPreco;
    private JTextField txtEstoque;

    public TelaProduto(Caixa caixa) {
        this.caixa = caixa;

        // Configuração do Painel Principal (Fundo Azure)
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(240, 255, 255));
        this.setBorder(new EmptyBorder(20, 40, 20, 40));

        Dimension tamanhoCampo = new Dimension(165, 28);

        // --- TÍTULO ---
        JLabel lblTitulo = new JLabel("Cadastro de Produto");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- CÓDIGO ---
        JLabel lblCodigo = new JLabel("Código Numérico:");
        lblCodigo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCodigo.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtCodigo = new JTextField();
        txtCodigo.setMaximumSize(tamanhoCampo);
        txtCodigo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- NOME ---
        JLabel lblNome = new JLabel("Nome do Produto:");
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtNome = new JTextField();
        txtNome.setMaximumSize(tamanhoCampo);
        txtNome.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- PREÇO ---
        JLabel lblPreco = new JLabel("Preço (R$):");
        lblPreco.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPreco.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtPreco = new JTextField();
        txtPreco.setMaximumSize(tamanhoCampo);
        txtPreco.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- ESTOQUE ---
        JLabel lblEstoque = new JLabel("Quantidade em Estoque:");
        lblEstoque.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblEstoque.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtEstoque = new JTextField();
        txtEstoque.setMaximumSize(tamanhoCampo);
        txtEstoque.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- BOTÃO SALVAR ---
        JButton btnSalvar = new JButton("Cadastrar Produto");
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalvar.setBackground(new Color(64, 224, 208)); // Turquoise
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorderPainted(false);
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalvar.setMaximumSize(new Dimension(200, 35));
        btnSalvar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- EMPILHAMENTO (Igual às outras telas) ---
        this.add(Box.createVerticalGlue());
        this.add(lblTitulo);
        this.add(Box.createRigidArea(new Dimension(0, 20)));

        this.add(lblCodigo);
        this.add(txtCodigo);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(lblNome);
        this.add(txtNome);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(lblPreco);
        this.add(txtPreco);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(lblEstoque);
        this.add(txtEstoque);
        this.add(Box.createRigidArea(new Dimension(0, 25)));

        this.add(btnSalvar);
        this.add(Box.createVerticalGlue());

        // --- AÇÃO DO BOTÃO ---
        btnSalvar.addActionListener(e -> cadastrarProduto());
    }

    private void cadastrarProduto() {
        try {
            // 1. Pega os valores da tela e faz as conversões
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            String nome = txtNome.getText().trim();

            // Troca vírgula por ponto para o Java entender o decimal corretamente
            String precoTexto = txtPreco.getText().trim().replace(",", ".");
            double preco = Double.parseDouble(precoTexto);

            int estoque = Integer.parseInt(txtEstoque.getText().trim());

            // 2. Validação simples para não aceitar nome vazio
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O nome do produto não pode ser vazio!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. Verifica se o código já existe (opcional, mas recomendado)
            if (caixa.buscarProdutoPorCodigo(codigo) != null) {
                JOptionPane.showMessageDialog(this, "Já existe um produto com este código!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 4. Cria o objeto e salva no nosso Caixa (banco de dados)
            Produto novoProduto = new Produto(codigo, nome, preco, estoque);
            caixa.cadastrarProduto(novoProduto);

            // 5. Feedback de sucesso e limpeza dos campos
            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            txtCodigo.setText("");
            txtNome.setText("");
            txtPreco.setText("");
            txtEstoque.setText("");

            // Coloca o foco de volta no primeiro campo para agilizar o próximo cadastro
            txtCodigo.requestFocus();

        } catch (NumberFormatException ex) {
            // Se o usuário digitar letras no lugar de números, cai aqui em vez de travar o programa
            JOptionPane.showMessageDialog(this, "Por favor, digite apenas números válidos em Código, Preço e Estoque.", "Erro de Preenchimento", JOptionPane.ERROR_MESSAGE);
        }
    }
}