import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Tabela tabela;
    private static ArrayList<Pagina> paginas;
    private static Bucket[] buckets;
    public static int colisoes = 0;
    public static int overflows = 0;
    public static void main(String[] args) {
        tabela = lerDados();
        SwingUtilities.invokeLater(Main::interfaceGrafica);
    }
    public static int funcaoHash(String palavra, int quantidadeBuckets) {
        int hash = 0, primo = 31;
        for (int i = 0; i < palavra.length(); i++) {
            hash = (primo * hash + palavra.charAt(i)) % quantidadeBuckets;
        }
        return hash;
    }

    public static Tabela lerDados(){
        Tabela tabela = new Tabela();
        try {
            File arquivo = new File("palavras.txt");
            Scanner scanner = new Scanner(arquivo);
            while (scanner.hasNextLine()) {
                tabela.adicionarTupla(new Tupla(scanner.nextLine()));
            } scanner.close();
        } catch (FileNotFoundException e) {/**/}
        return tabela;
    }
    private static void criarPaginas(int tamanhoPagina) {
        paginas = new ArrayList<>();

        int totalPalavras = tabela.getTamanho();
        int quantidadePaginas = totalPalavras / tamanhoPagina + (totalPalavras % tamanhoPagina == 0 ? 0 : 1);

        ArrayList<Tupla> tuplas = tabela.getTabela();
        int indiceTupla = 0;

        for (int i = 0; i < quantidadePaginas; i++) {
            Pagina novaPagina = new Pagina(i+1);
            for (int j = 0; j < tamanhoPagina && indiceTupla < tuplas.size(); j++, indiceTupla++) {
                Tupla tupla = tuplas.get(indiceTupla);
                tupla.setIndice(i+1);
                novaPagina.adicionarPalavra(tupla);
            }
            paginas.add(novaPagina);
        }
    }

    private static void criarBuckets(){
        int tamanhoBucket = 100;
        int totalTuplas = tabela.getTamanho();
        int quantidadeBuckets = totalTuplas / tamanhoBucket + ( totalTuplas % tamanhoBucket == 0 ? 0 : 1);

        buckets = new Bucket[quantidadeBuckets];

        for(int i = 0; i<buckets.length; i++){
            buckets[i] = new Bucket(tamanhoBucket, (i+1));
        }

        for(int i = 0; i<paginas.size(); i++){
            ArrayList<Tupla> pagina = paginas.get(i).getPagina();
            for(int j = 0; j<pagina.size(); j++){
                int hash = funcaoHash(pagina.get(j).getPalavra(), quantidadeBuckets);
                buckets[hash].adicionarDado(pagina.get(j));
            }
        }
    }

    private static void tableScan(int registrosParaLer, StringBuilder scanStr) {
        int registrosLidos = 0;
        int paginasLidas = 0;

        for (int i = 0; i < paginas.size() && registrosLidos < registrosParaLer; i++) {
            ArrayList<Tupla> tuplas = paginas.get(i).getPagina();
            paginasLidas++;
            for (int j = 0; j < tuplas.size() && registrosLidos < registrosParaLer; j++) {
                scanStr.append(tuplas.get(j).getPalavra()).append("\n");
                registrosLidos++;
            }
        }

        scanStr.append("\nRegistros lidos: ").append(registrosLidos)
                .append("\nPáginas acessadas: ").append(paginasLidas).append("\n");
    }

    private static String buscaHash(int hash, String chaveDeBusca) {
        if (buckets[hash] != null) {
            Tupla dadoEncontrado = buckets[hash].buscarDado(chaveDeBusca);
            if (dadoEncontrado != null) {
                return "Palavra encontrada: " + dadoEncontrado.getPalavra() + "\n" +
                        "Localizada na página: " + dadoEncontrado.getIndice();
            }
        }
        return "Palavra não encontrada.";
    }

    private static void mostrarInformacoesEmPopUp(JFrame frame, String titulo, String informacoes) {
        JTextArea textArea = new JTextArea(15, 50);
        textArea.setText(informacoes);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        JOptionPane.showMessageDialog(frame, scrollPane, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    private static void interfaceGrafica() {
        JFrame frame = new JFrame("Tabela Hash GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel tamanhoPaginasLabel = new JLabel("Tamanho das páginas:");
        JTextField tamanhoPaginaField = new JTextField(6);
        JButton criarBancoBtn = new JButton("Criar Banco de Dados");
        JLabel tableScanLabel = new JLabel("Registros para o Table Scan:");
        JTextField tableScanField = new JTextField(6);
        JButton tableScanBtn = new JButton("Table Scan");
        JLabel buscarPalavraLabel = new JLabel("Buscar palavra:");
        JTextField buscarPalavraField = new JTextField(10);
        JButton buscarPalavraBtn = new JButton("Buscar");
        JButton tabelaBtn = new JButton("Tabela");
        JButton paginasBtn = new JButton("Páginas");
        JButton bucketsBtn = new JButton("Buckets");
        JButton taxasBtn = new JButton("Taxa de colisão e overflow");
        frame.add(tamanhoPaginasLabel);
        frame.add(tamanhoPaginaField);
        frame.add(criarBancoBtn);
        frame.add(tableScanLabel);
        frame.add(tableScanField);
        frame.add(tableScanBtn);
        frame.add(tabelaBtn);
        frame.add(paginasBtn);
        frame.add(bucketsBtn);
        frame.add(taxasBtn);
        frame.add(buscarPalavraLabel);
        frame.add(buscarPalavraField);
        frame.add(buscarPalavraBtn);

        criarBancoBtn.addActionListener(e -> {
            try {
                int tamanhoPagina = Integer.parseInt(tamanhoPaginaField.getText());
                criarPaginas(tamanhoPagina);
                criarBuckets();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira um número válido para o tamanho das páginas.");
            }
        });

        tabelaBtn.addActionListener(e -> mostrarInformacoesEmPopUp(frame, "Tabela", tabela.toString()));

        paginasBtn.addActionListener(e -> {
            if (paginas == null || paginas.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Primeiro crie o banco de dados.");
                return;
            }
            StringBuilder paginasStr = new StringBuilder();
            for (Pagina pagina : paginas) {
                paginasStr.append("Página " + pagina.getIndice() + "\n" + pagina.toString() + "\n\n");
            }
            mostrarInformacoesEmPopUp(frame, "Páginas", paginasStr.toString());
        });

        bucketsBtn.addActionListener(e -> {
            if (buckets == null || buckets.length == 0) {
                JOptionPane.showMessageDialog(frame, "Primeiro crie o banco de dados.");
                return;
            }
            StringBuilder bucketsStr = new StringBuilder();
            for (Bucket bucket : buckets) {
                bucketsStr.append(bucket.toString()).append("\n");
                Bucket bucketDoOverflow = bucket.getProximoBucket();
                while (bucketDoOverflow != null) {
                    bucketsStr.append(bucketDoOverflow.toString()).append("\n");
                    bucketDoOverflow = bucketDoOverflow.getProximoBucket();
                }
            }
            mostrarInformacoesEmPopUp(frame, "Buckets", bucketsStr.toString());
        });

        tableScanBtn.addActionListener(e -> {
            if (paginas == null || paginas.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Primeiro crie o banco de dados.");
                return;
            }
            try {
                int registrosParaLer = Integer.parseInt(tableScanField.getText());
                StringBuilder scanStr = new StringBuilder();
                tableScan(registrosParaLer, scanStr);
                mostrarInformacoesEmPopUp(frame, "Table Scan", scanStr.toString());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira um número válido para registros do Table Scan.");
            }
        });

        taxasBtn.addActionListener(e -> {
            if (buckets == null || buckets.length == 0 || paginas == null || paginas.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Primeiro crie o banco de dados e realize operações para calcular taxas.");
                return;
            }
            int totalTuplas = paginas.stream().mapToInt(p -> p.getPagina().size()).sum();
            double taxaColisoes = ((double) colisoes / totalTuplas) * 100.0;
            double taxaOverflows = ((double) overflows / totalTuplas) * 100.0;

            StringBuilder taxasStr = new StringBuilder();
            taxasStr.append("Colisões totais: ").append(colisoes).append("\n")
                    .append("Overflows totais: ").append(overflows).append("\n")
                    .append(String.format("\nTaxa de Colisões: %.2f%%\n", taxaColisoes))
                    .append(String.format("Taxa de Overflows: %.2f%%\n", taxaOverflows));

            mostrarInformacoesEmPopUp(frame, "Taxas de Colisão e Overflow", taxasStr.toString());
        });

        buscarPalavraBtn.addActionListener(e -> {
            String palavraBuscada = buscarPalavraField.getText().trim();
            if (palavraBuscada.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira uma palavra para buscar.");
                return;
            }
            if (buckets == null || buckets.length == 0) {
                JOptionPane.showMessageDialog(frame, "Primeiro crie o banco de dados.");
                return;
            }
            String resultadoBusca = buscaHash(funcaoHash(palavraBuscada, buckets.length), palavraBuscada);
            mostrarInformacoesEmPopUp(frame, "Resultado da Busca", resultadoBusca);
        });


        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }




}