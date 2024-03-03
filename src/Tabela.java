import java.util.ArrayList;

public class Tabela {
    private ArrayList<Tupla> tuplas;
    public Tabela() {
        this.tuplas = new ArrayList<>();
    }
    public void adicionarTupla(Tupla novaTupla) {
        tuplas.add(novaTupla);
    }

    public int getTamanho() {
        return tuplas.size();
    }

    public ArrayList<Tupla> getTabela() {
        return tuplas;
    }

    public void imprimir(){
        for(int i = 0; i<tuplas.size(); i++){
            System.out.println(tuplas.get(i).getPalavra());
        }
    }

}
