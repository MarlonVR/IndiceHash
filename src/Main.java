import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Tabela tabela = new Tabela();

        try {
            File arquivo = new File("palavras.txt");
            Scanner scanner = new Scanner(arquivo);

            for(int i = 0; i<20; i++){
                if(scanner.hasNextLine()){
                    tabela.adicionarTupla(new Tupla(scanner.nextLine()));
                }
            }
            scanner.close();

            /*while (scanner.hasNextLine()) {
                tabela.adicionarTupla(new Tupla(scanner.nextLine()));
            } scanner.close();*/

        } catch (FileNotFoundException e) {/**/}

        System.out.println(tabela.toString());
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o tamanho das páginas:");

        int tamanhoPagina = scanner.nextInt();
        ArrayList<Pagina> paginas = new ArrayList<>();

        int totalPalavras = tabela.getTamanho();
        int quantidadePaginas = totalPalavras / tamanhoPagina + (totalPalavras % tamanhoPagina == 0 ? 0 : 1);

        ArrayList<Tupla> tuplas = tabela.getTabela();
        int indiceTupla = 0;

        for (int i = 0; i < quantidadePaginas; i++) {
            Pagina novaPagina = new Pagina();
            for (int j = 0; j < tamanhoPagina && indiceTupla < tuplas.size(); j++, indiceTupla++) {
                novaPagina.adicionarPalavra(tuplas.get(indiceTupla));
            }
            paginas.add(novaPagina);
        }

        for (int i = 0; i < paginas.size(); i++) {
            System.out.println("Pagina " + (i+1));
            System.out.println(paginas.get(i).toString());
        }

    }

    public static int funcaoHash(String palavra, int quantidadeBuckets) {
        int hash = 0, primo = 31;
        for (int i = 0; i < palavra.length(); i++) {
            hash = (primo * hash + palavra.charAt(i)) % quantidadeBuckets;
        }
        return hash;
    }
}
