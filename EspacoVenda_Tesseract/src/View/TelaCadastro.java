package View;

import Controller.Controle_Login;
import Service.Caixa;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaCadastro extends JPanel {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmarSenha;
    private Controle_Login controleLogin;
    private TelaPDV telaPDV;

    public TelaCadastro(Caixa caixa, TelaPDV telaPDV) {
        this.telaPDV = telaPDV;
        this.controleLogin = new Controle_Login(caixa);

        // Configuração do Painel Principal (Fundo Azure)
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(240, 255, 255));
        this.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Tamanho padrão fixo para manter a simetria visual
        Dimension tamanhoCampo = new Dimension(200, 28);

        // --- 1. TÍTULO ---
        JLabel lblTitulo = new JLabel("Criar Nova Conta");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- 2. CAMPO USUÁRIO ---
        JLabel lblUser = new JLabel("Nome do Cliente");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsuario.setMaximumSize(tamanhoCampo);
        txtUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- 3. CAMPO SENHA ---
        JLabel lblSenha = new JLabel("Digite a Senha");
        lblSenha.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblSenha.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtSenha = new JPasswordField();
        txtSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSenha.setMaximumSize(tamanhoCampo);
        txtSenha.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- 4. CAMPO CONFIRMAR SENHA ---
        JLabel lblConfirmar = new JLabel("Confirme a Senha");
        lblConfirmar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtConfirmarSenha = new JPasswordField();
        txtConfirmarSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtConfirmarSenha.setMaximumSize(tamanhoCampo);
        txtConfirmarSenha.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- 5. BOTÃO CADASTRAR ---
        JButton btnSalvar = new JButton("Cadastrar");
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalvar.setForeground(Color.BLACK);
        btnSalvar.setBackground(new Color(64, 224, 208)); // Mesma cor Turquoise do botão do menu
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorderPainted(false);
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalvar.setMaximumSize(new Dimension(200, 35));
        btnSalvar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- EMPILHAMENTO DOS COMPONENTES (Igual à de Login) ---
        this.add(Box.createVerticalGlue());
        this.add(lblTitulo);
        this.add(Box.createRigidArea(new Dimension(0, 20)));

        this.add(lblUser);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        this.add(txtUsuario);
        this.add(Box.createRigidArea(new Dimension(0, 12)));

        this.add(lblSenha);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        this.add(txtSenha);
        this.add(Box.createRigidArea(new Dimension(0, 12)));

        this.add(lblConfirmar);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        this.add(txtConfirmarSenha);
        this.add(Box.createRigidArea(new Dimension(0, 20)));

        this.add(btnSalvar);
        this.add(Box.createVerticalGlue());

        // --- AÇÃO DO BOTÃO ---
        btnSalvar.addActionListener(e -> realizarCadastro());
    }

    private void realizarCadastro() {
        String usuarioDigitado = txtUsuario.getText().trim();
        String senhaDigitada = new String(txtSenha.getPassword());
        String confirmacaoSenha = new String(txtConfirmarSenha.getPassword());

        // Validação 1: Verificar se os campos estão em branco
        if (usuarioDigitado.isEmpty() || senhaDigitada.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro no Cadastro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validação 2: Verificar se as senhas coincidem
        if (!senhaDigitada.equals(confirmacaoSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem!", "Erro no Cadastro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Chame a lógica do Controller que criamos!
        boolean sucesso = controleLogin.criarContaCliente(usuarioDigitado, senhaDigitada);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Conta criada com sucesso!\nAgora você já pode fazer login.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            // Redireciona o usuário direto para a tela de Login para ele testar o novo acesso
            telaPDV.trocarParaTelaLogin();
        } else {
            JOptionPane.showMessageDialog(this, "Este nome de usuário já está sendo usado!", "Erro no Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }
}