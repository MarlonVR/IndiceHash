import java.util.ArrayList;

public class Bucket {
    private Tupla[] tuplas;
    private int indice;
    private int tamanhoBucket;
    private Bucket proximoBucket;
    public Bucket(int tamanhoBucket, int indice) {
        this.tamanhoBucket = tamanhoBucket;
        this.indice = indice;
        this.tuplas = new Tupla[tamanhoBucket];
        this.proximoBucket = null;
    }
    public void adicionarTupla(Tupla novaTupla) {
        for (int i = 0; i < tuplas.length; i++) {
            if (tuplas[i] == null) {
                tuplas[i] = novaTupla;
                return;
            }
        }
        if(proximoBucket == null){
            overflow();
            adicionarTupla(novaTupla);
        }
        else {
            proximoBucket.adicionarTupla(novaTupla);
        }
    }

    public void overflow(){
        proximoBucket = new Bucket(tamanhoBucket, indice);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bucket:\n");
        for (Tupla tupla : tuplas) {
            sb.append(tupla.toString()).append("\n");
        }
        return sb.toString();
    }
}
