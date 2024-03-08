public class Bucket {
    private Tupla[] tuplas;
    private int indice;
    private int tamanhoBucket;
    private Bucket proximoBucket;
    private int colisoes;
    private int overflows;
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
            Main.colisoes++;
            colisoes++;
        }
    }

    public void overflow(){
        Main.overflows++;
        overflows++;
        proximoBucket = new Bucket(tamanhoBucket, indice);
    }

    public int getIndice(){
        return indice;
    }

    public Bucket getProximoBucket() {
        return proximoBucket;
    }

    public int getOverflows(){
        return overflows;
    }

    public Tupla buscarTupla(String chaveDeBusca) {
        for (int i = 0; i < tuplas.length; i++) {
            if (tuplas[i] != null && tuplas[i].getPalavra().equals(chaveDeBusca)) {
                return tuplas[i];
            }
        }
        if (proximoBucket != null) {
            return proximoBucket.buscarTupla(chaveDeBusca);
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bucket " + indice + ":\n");
        try{
            for (Tupla tupla : tuplas) {
                sb.append(tupla.toString()).append("\n");
            }
        }catch (Exception e){
            //
        }

        return sb.toString();
    }
}