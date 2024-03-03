import java.util.ArrayList;

public class Paginas {
    private ArrayList<ArrayList<String>> paginas;
    private int quantidadePaginas;
    private int palavrasPorPagina;

    public Paginas(int quantidadePaginas, Tabela tabela){
        this.quantidadePaginas = quantidadePaginas;
        this.palavrasPorPagina = tabela.getTamanho() / quantidadePaginas;
        this.paginas = new ArrayList<>();

        ArrayList<Tupla> tupla = tabela.getTabela();
        int indiceTabela = 0;

        // preencher pagina
        for(int i = 0; i<quantidadePaginas; i++){
            this.paginas.add(new ArrayList<>());
            for(int j = 0; j<palavrasPorPagina; j++, indiceTabela++){
                paginas.get(i).add(tupla.get(indiceTabela).getPalavra());
                tupla.get(indiceTabela).setIndice(j+1);
            }
        }

        int palavrasFora = tabela.getTamanho() - indiceTabela;

        //distribuindo as palavras q ficaram de fora
        for (int i = 0; palavrasFora > 0; i = (i + 1) % quantidadePaginas, palavrasFora--) {
            paginas.get(i).add(tupla.get(indiceTabela++).getPalavra());
        }


    }
    public int getQuantidadePaginas(){
        return quantidadePaginas;
    }
    public int getPalavrasPorPagina(){
        return palavrasPorPagina;
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
