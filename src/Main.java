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

        tabela.imprimir();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o tamanho das páginas:");
        Paginas paginas = new Paginas(scanner.nextInt(), tabela);
        paginas.imprimir();

    }

    public static int funcaoHash(String palavra, int quantidadeBuckets) {
        int hash = 0, primo = 31;
        for (int i = 0; i < palavra.length(); i++) {
            hash = (primo * hash + palavra.charAt(i)) % quantidadeBuckets;
        }
        return hash;
    }
}
