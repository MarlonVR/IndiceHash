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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Tabela:\n");
        for (Tupla tupla : tuplas) {
            sb.append(tupla.toString()).append("\n"); // Utiliza toString() de Tupla
        }
        return sb.toString();
    }

}
