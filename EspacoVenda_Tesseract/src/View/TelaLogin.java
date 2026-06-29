package View;

import Controller.Controle_Login;
import Modelo.Usuario;
import Service.Caixa;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaLogin extends JPanel {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private Controle_Login controleLogin;
    private TelaPDV telaPDV;
    private Caixa caixa;

    public TelaLogin(Caixa caixa, TelaPDV telaPDV) {
        this.telaPDV = telaPDV;
        this.caixa = caixa;
        this.controleLogin = new Controle_Login(caixa);

        // Configuração do Painel Principal (Fundo Azure)
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(240, 255, 255));
        this.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Tamanho padrão fixo para os campos ficarem bonitos e simétricos
        Dimension tamanhoCampo = new Dimension(200, 28);

        // --- 1. TÍTULO ---
        JLabel lblTitulo = new JLabel("Acesso ao Sistema");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- 2. CAMPO USUÁRIO ---
        JLabel lblUser = new JLabel("Usuário");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsuario.setMaximumSize(tamanhoCampo);
        txtUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- 3. CAMPO SENHA ---
        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblSenha.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtSenha = new JPasswordField();
        txtSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSenha.setMaximumSize(tamanhoCampo);
        txtSenha.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- 4. BOTÃO CONFIRMAR ---
        JButton btnLogar = new JButton("Entrar");
        btnLogar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogar.setForeground(Color.BLACK);
        btnLogar.setBackground(new Color(195, 215, 245));
        btnLogar.setFocusPainted(false);
        btnLogar.setBorderPainted(false);
        btnLogar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogar.setMaximumSize(new Dimension(200, 35)); // Um pouco mais alto que os inputs
        btnLogar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- CORAÇÃO DO LAYOUT (Empilhamento Espaçado) ---
        this.add(Box.createVerticalGlue()); // Empurra tudo para o centro vertical
        this.add(lblTitulo);
        this.add(Box.createRigidArea(new Dimension(0, 25))); // Espaço abaixo do título

        this.add(lblUser);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        this.add(txtUsuario);
        this.add(Box.createRigidArea(new Dimension(0, 15))); // Espaço entre blocos

        this.add(lblSenha);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        this.add(txtSenha);
        this.add(Box.createRigidArea(new Dimension(0, 25))); // Espaço antes do botão

        this.add(btnLogar);
        this.add(Box.createVerticalGlue()); // Empurra tudo para o centro vertical

        // --- AÇÃO DO BOTÃO ---
        btnLogar.addActionListener(e -> realizarLogin());
    }

    private void realizarLogin() {
        String usuarioDigitado = txtUsuario.getText().trim();
        String senhaDigitada = new String(txtSenha.getPassword());

        Usuario usuarioLogado = controleLogin.autenticar(usuarioDigitado, senhaDigitada);

        if (usuarioLogado != null) {
            // Transformamos em minúsculo para evitar qualquer erro de letra maiúscula
            this.caixa.setUsuarioLogado(usuarioLogado);
            String cargo = usuarioLogado.getCargo().toLowerCase();

            // Aceita tanto "adm" quanto "administrador"
            if (cargo.equals("adm") || cargo.equals("administrador")) {
                JOptionPane.showMessageDialog(this, "Acesso total concedido!", "Bem-vindo Administrador", JOptionPane.INFORMATION_MESSAGE);
                telaPDV.atualizarStatusUsuario("Usuário: " + usuarioLogado.getLogin() + " (ADM)", "adm");

            } else if (cargo.equals("vendedor")) {
                JOptionPane.showMessageDialog(this, "Painel de Vendas liberado!", "Bem-vindo Vendedor", JOptionPane.INFORMATION_MESSAGE);
                telaPDV.atualizarStatusUsuario("Usuário: " + usuarioLogado.getLogin() + " (Vendedor)", "vendedor");

            } else {
                // Se não for nenhum dos funcionários acima, é o cliente!
                JOptionPane.showMessageDialog(this, "Login realizado com sucesso!", "Bem-vindo", JOptionPane.INFORMATION_MESSAGE);
                telaPDV.atualizarStatusUsuario("Usuário: " + usuarioLogado.getLogin(), "usuario");
            }

            // Limpa os campos e fecha a tela de login
            txtUsuario.setText("");
            txtSenha.setText("");
            telaPDV.trocaTela(new JPanel()); // Volta para a tela inicial limpa

        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou Senha incorretos!", "Erro de Acesso", JOptionPane.ERROR_MESSAGE);
        }
    }
}