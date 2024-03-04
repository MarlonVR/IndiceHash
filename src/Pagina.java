import java.util.ArrayList;

public class Pagina {
    private ArrayList<Tupla> palavras;

    public Pagina() {
        this.palavras = new ArrayList<>();
    }

    public void adicionarPalavra(Tupla palavra) {
        palavras.add(palavra);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Tupla tupla : palavras) {
            sb.append(tupla.getPalavra()).append("\n"); // Supondo que Tupla tenha o método getPalavra()
        }
        return sb.toString();
    }
}
