import java.util.ArrayList;

public class Paginas {
    private ArrayList<ArrayList<String>> paginas;
    private int quantidadePaginas;

    public Paginas(int tamanhoPagina, Tabela tabela){
        int totalPalavras = tabela.getTamanho();
        this.quantidadePaginas = totalPalavras / tamanhoPagina + (totalPalavras % tamanhoPagina == 0 ? 0 : 1);
        this.paginas = new ArrayList<>();

        ArrayList<Tupla> tuplas = tabela.getTabela();
        int indiceTupla = 0;

        for(int i = 0; i<quantidadePaginas; i++){
            this.paginas.add(new ArrayList<>());
            for(int j = 0; j< tamanhoPagina && indiceTupla < tuplas.size(); j++, indiceTupla++){
                paginas.get(i).add(tuplas.get(indiceTupla).getPalavra());
                tuplas.get(indiceTupla).setIndice(i+1);
            }
        }
    }
    public int getQuantidadePaginas(){
        return quantidadePaginas;
    }

    public void imprimir() {
        for (int i = 0; i < paginas.size(); i++) {
            System.out.println("Pagina " + (i + 1));
            for (String palavra : paginas.get(i)) {
                System.out.println(palavra);
            }
            System.out.println();
        }
    }
}
