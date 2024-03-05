import java.util.ArrayList;

public class Pagina {
    private ArrayList<Tupla> pagina;
    private int indice;

    public Pagina(int indice) {
        this.indice = indice;
        this.pagina = new ArrayList<>();
    }

    public void adicionarPalavra(Tupla palavra) {
        pagina.add(palavra);
    }

    public ArrayList<Tupla> getPagina() {
        return pagina;
    }
    public int getIndice() {
        return indice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Tupla tupla : pagina) {
            sb.append(tupla.getPalavra()).append("\n");
        }
        return sb.toString();
    }
}
