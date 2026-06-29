package View;

import Service.Caixa;

import javax.swing.*;
import java.awt.*;

public class TelaPDV extends JFrame {

    private Caixa caixa;
    private JPanel painelPrincipal;
    private JLabel lblUsuario;

    // Botões Bloqueados (requer Login para acessar)
    private JButton btnCadastroProduto;
    private JButton btnListaProduto;
    private JButton btnCarrinho;
    private JButton btnCompras;
    private JButton btnFinalizar;

    public TelaPDV() {
        this.caixa = new Caixa(); // Inicializa o banco de dados/serviço
        this.setTitle("Tesseract Store");
        this.setSize(550, 450);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.interfaceMenu();
    }

    public void interfaceMenu() {
        this.setLayout(new BorderLayout());

        painelPrincipal = new JPanel(); // Painel Principal
        painelPrincipal.setBackground(new Color(240, 255, 255)); // Azure
        painelPrincipal.setLayout(new BorderLayout());

        JPanel barraEsquerda = new JPanel(new BorderLayout());
        barraEsquerda.setPreferredSize(new Dimension(200, 600));

        JPanel menuLateral = new JPanel(); // Criação barra lateral
        menuLateral.setBackground(new Color(30, 64, 120)); // CornflowerBlue
        menuLateral.setLayout(new BoxLayout(menuLateral, BoxLayout.Y_AXIS));

        JPanel menuTopo = new JPanel(); // Criação barra topo
        menuTopo.setBackground(new Color(20, 45, 90)); // RoyalBlue
        menuTopo.setLayout(new BoxLayout(menuTopo, BoxLayout.Y_AXIS));
        menuTopo.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 0));

        JPanel menuRodape = new JPanel(); // Criação rodape
        menuRodape.setBackground(new Color(20, 45, 90));
        menuRodape.setLayout(new BoxLayout(menuRodape, BoxLayout.Y_AXIS));
        menuRodape.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        JPanel menuLogin = new JPanel();
        menuLogin.setBackground(new Color(20, 45, 90));
        menuLogin.setLayout(new BoxLayout(menuLogin, BoxLayout.X_AXIS));

        // Status do Usuario
        lblUsuario = new JLabel("Usuário: Não conectado");
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Segoe UI", Font.ITALIC | Font.BOLD, 13));
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuTopo.add(lblUsuario);

        // Instanciando os botões (SEM O "JButton" NA FRENTE NOS BOTÕES RESTRITOS!)
        JButton btnInicio = estelizarBotao("Início");
        btnCadastroProduto = estelizarBotao("Produto");
        btnListaProduto = estelizarBotao("Listar Produto");
        btnCarrinho = estelizarBotao("Carrinho");
        btnCompras = estelizarBotao("Compras");

        // Botões de Login/Sair/Cadastrar
        JButton btnLogar = estelizarBotao("Entrar");
        JButton btnCadastrar = estelizarBotao("Cadastrar");
        JButton btnSair = estelizarBotao("Sair");

        btnLogar.setBackground(new Color(127, 255, 212)); // Aquamarine
        btnCadastrar.setBackground(new Color(64, 224, 208)); // Turquoise
        btnSair.setBackground(new Color(255, 0, 0)); // Red

        Dimension tamRodape = new Dimension(85, 25);
        Dimension tamSair = new Dimension(100, 25);
        btnLogar.setMaximumSize(tamRodape);
        btnCadastrar.setMaximumSize(tamRodape);
        btnSair.setMaximumSize(tamSair);

        // Adicionar Botões ao Menu Lateral
        menuLateral.add(Box.createRigidArea(new Dimension(0, 30)));
        menuLateral.add(btnInicio);
        menuLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        menuLateral.add(btnCadastroProduto);
        menuLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        menuLateral.add(btnListaProduto);
        menuLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        menuLateral.add(btnCompras);
        menuLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        menuLateral.add(btnCarrinho);

        // Adicionar Botões Rodapé
        menuLogin.add(btnLogar);
        menuLogin.add(Box.createRigidArea(new Dimension(8, 8)));
        menuLogin.add(btnCadastrar);

        menuRodape.add(Box.createRigidArea(new Dimension(0, 8)));
        menuRodape.add(menuLogin);
        menuRodape.add(Box.createRigidArea(new Dimension(0, 8)));
        menuRodape.add(btnSair);

        // Ações de Troca de Tela
        btnInicio.addActionListener(e -> trocaTela(TelaInicio()));
        btnCadastroProduto.addActionListener(e -> trocaTela(TelaProduto()));
        btnListaProduto.addActionListener(e -> trocaTela(ListarProduto()));
        btnCompras.addActionListener(e -> trocaTela(TelaCompras()));
        btnCarrinho.addActionListener(e -> trocaTela(TelaCarrinho()));
        btnLogar.addActionListener(e -> trocaTela(TelaLogin()));
        btnCadastrar.addActionListener(e -> trocaTela(TelaCadastro()));

        btnSair.addActionListener(e -> {
            int fechar = JOptionPane.showConfirmDialog(this, "Deseja mesmo Sair?", "Sair", JOptionPane.YES_NO_OPTION);
            if (fechar == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Montagem da Janela
        barraEsquerda.add(menuTopo, BorderLayout.NORTH);
        barraEsquerda.add(menuLateral, BorderLayout.CENTER);
        barraEsquerda.add(menuRodape, BorderLayout.SOUTH);

        this.add(barraEsquerda, BorderLayout.WEST);
        this.add(painelPrincipal, BorderLayout.CENTER);

        // Abre a tela inicial e bloqueia o menu por padrão
        trocaTela(TelaInicio());
        configurarPermissoes("desconectado");
    }

    private JButton estelizarBotao(String text) {
        JButton botao = new JButton(text);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botao.setForeground(Color.BLACK);
        botao.setBackground(new Color(195, 215, 245));
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setMaximumSize(new Dimension(180, 30));
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        return botao;
    }

    private JPanel TelaInicio() {
        JPanel painelInicio = new JPanel(new GridBagLayout());
        painelInicio.setBackground(new Color(240, 255, 255));
        JLabel lblBoasVindas = new JLabel("Bem-vindo à Tesseract Store!");
        lblBoasVindas.setFont(new Font("Segoe UI", Font.BOLD, 16));
        painelInicio.add(lblBoasVindas);
        return painelInicio;
    }

    public void trocaTela(JPanel novaTela) {
        painelPrincipal.removeAll();
        painelPrincipal.add(novaTela, BorderLayout.CENTER);
        painelPrincipal.revalidate();
        painelPrincipal.repaint();
    }

    private JPanel TelaProduto() { return new TelaProduto(this.caixa); }
    private JPanel ListarProduto() { return new TelaLista(this.caixa); }
    private JPanel TelaCompras() { return new TelaCompras(this.caixa); }
    private JPanel TelaCarrinho() { return new TelaCarrinho(this.caixa); }
    private JPanel TelaLogin() { return new TelaLogin(this.caixa, this); }
    private JPanel TelaCadastro() { return new TelaCadastro(this.caixa, this); }


    public void trocarParaTelaLogin() {
        trocaTela(new TelaLogin(this.caixa, this));
    }

    // O CORAÇÃO DA ATUALIZAÇÃO - Agora com aviso no console
    public void atualizarStatusUsuario(String txtStatus, String cargo) {
        System.out.println("🚨 MENSAGEM DO SISTEMA: Liberando acesso para o cargo -> " + cargo);

        this.lblUsuario.setText(txtStatus);
        this.lblUsuario.revalidate();
        this.lblUsuario.repaint();

        configurarPermissoes(cargo);
    }

    private void configurarPermissoes(String cargo) {
        switch (cargo.toLowerCase()) {
            case "adm":
                btnCadastroProduto.setEnabled(true);
                btnListaProduto.setEnabled(true);
                btnCarrinho.setEnabled(true);
                btnCompras.setEnabled(true);
                break;

            case "vendedor":
                btnCadastroProduto.setEnabled(true);
                btnListaProduto.setEnabled(true);
                btnCarrinho.setEnabled(false); // Estava false no seu, liberei para ele poder vender!
                btnCompras.setEnabled(false);
                break;

            case "usuario":
                btnCadastroProduto.setEnabled(false);
                btnListaProduto.setEnabled(false);
                btnCompras.setEnabled(true);
                btnCarrinho.setEnabled(true);
                break;

            default:
                btnCadastroProduto.setEnabled(false);
                btnListaProduto.setEnabled(false);
                btnCarrinho.setEnabled(false);
                btnCompras.setEnabled(false);
                break;
        }
    }
}