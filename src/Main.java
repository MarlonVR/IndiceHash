import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static int colisoes = 0;
    public static int overflows = 0;
    public static void main(String[] args) {
        Tabela tabela = new Tabela();

        try {
            File arquivo = new File("palavras.txt");
            Scanner scanner = new Scanner(arquivo);

            while (scanner.hasNextLine()) {
                tabela.adicionarTupla(new Tupla(scanner.nextLine()));
            } scanner.close();

        } catch (FileNotFoundException e) {/**/}

        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o tamanho das páginas:");

        int tamanhoPagina = scanner.nextInt();
        ArrayList<Pagina> paginas = criarPaginas(tabela, tamanhoPagina);
        //imprimirPaginas(paginas);

        Bucket[] buckets = criarBuckets(tabela, paginas);
        //imprimirBuckets(buckets);
        calcularTaxas((double)tabela.getTamanho());


        tableScan(paginas, 20);
        String palavra = "aahing";
        System.out.println("Palavra encontrada na pagina: " + buscaHash(buckets, funcaoHash(palavra, buckets.length), palavra));

    }

    public static int funcaoHash(String palavra, int quantidadeBuckets) {
        int hash = 0, primo = 31;
        for (int i = 0; i < palavra.length(); i++) {
            hash = (primo * hash + palavra.charAt(i)) % quantidadeBuckets;
        }
        return hash;
    }

    private static ArrayList<Pagina> criarPaginas(Tabela tabela, int tamanhoPagina) {
        ArrayList<Pagina> paginas = new ArrayList<>();

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
        return paginas;
    }

    private static Bucket[] criarBuckets(Tabela tabela, ArrayList<Pagina> paginas){
        int tamanhoBucket = 100;
        int totalTuplas = tabela.getTamanho();
        int quantidadeBuckets = totalTuplas / tamanhoBucket + ( totalTuplas % tamanhoBucket == 0 ? 0 : 1);

        Bucket[] buckets = new Bucket[quantidadeBuckets];

        for(int i = 0; i<buckets.length; i++){
            buckets[i] = new Bucket(tamanhoBucket, (i+1));
        }

        for(int i = 0; i<paginas.size(); i++){
            ArrayList<Tupla> pagina = paginas.get(i).getPagina();
            for(int j = 0; j<pagina.size(); j++){
                int hash = funcaoHash(pagina.get(j).getPalavra(), quantidadeBuckets);
                buckets[hash].adicionarTupla(pagina.get(j));
            }
        }

        return buckets;
    }

    private static void tableScan(ArrayList<Pagina> paginas, int registrosParaLer) {
        int registrosLidos = 0;
        int paginasLidas = 0;

        for (int i = 0; i < paginas.size() && registrosLidos < registrosParaLer; i++, paginasLidas++) {
            ArrayList<Tupla> tuplas = paginas.get(i).getPagina();
            for (int j = 0; j < tuplas.size() && registrosLidos < registrosParaLer; j++) {
                System.out.println(tuplas.get(j).getPalavra());
                registrosLidos++;
            }
        }

        System.out.printf("Registros lidos: %d\nPáginas acessadas: %d\n", registrosLidos, paginasLidas);
    }

    private static int buscaHash(Bucket[] buckets, int hash, String chaveDeBusca){
        return buckets[hash].buscarTupla(chaveDeBusca).getIndice();
    }


    private static void imprimirPaginas(ArrayList<Pagina> paginas){
        for (int i = 0; i < paginas.size(); i++) {
            System.out.println("Pagina " + paginas.get(i).getIndice());
            System.out.println(paginas.get(i).toString());
        }
    }

    private static void imprimirBuckets(Bucket[] buckets){
        for(int i = 0; i<buckets.length; i++){
            System.out.println(buckets[i].toString());

            Bucket bucketDoOverflow = buckets[i].getProximoBucket();
            while(bucketDoOverflow != null) {
                System.out.println(bucketDoOverflow);
                bucketDoOverflow = bucketDoOverflow.getProximoBucket();
            }
        }
    }

    private static void calcularTaxas(double totalTuplas) {
        System.out.println("Colisoes totais: " + colisoes);
        System.out.println("Overflows totais: " + overflows);
        System.out.printf("\nTaxa de Colisões: %.2f%%\n", ((double) colisoes / totalTuplas) * 100.0);
        System.out.printf("Taxa de Overflows: %.2f%%\n", ((double) overflows / totalTuplas) * 100.0);

    }
}