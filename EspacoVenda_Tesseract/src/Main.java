//Boa Noite, Código feito com a ajuda de uma IA
//Aluno: Guilherme
//Adm = "adm" senha "123", Vendedor = "vendedor" senha "987"
//Necessário fazer login para rodar

import View.TelaPDV;

public class Main {
    static void main() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            TelaPDV tela = new TelaPDV();
            tela.setVisible(true);
        });
    }
}
