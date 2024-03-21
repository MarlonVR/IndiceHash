public class Bucket {
    private Tupla[] dados;
    private int indice;
    private int tamanhoBucket;
    private Bucket proximoBucket;
    private int colisoes;
    private int overflows;
    public Bucket(int tamanhoBucket, int indice) {
        this.tamanhoBucket = tamanhoBucket;
        this.indice = indice;
        this.dados = new Tupla[tamanhoBucket];
        this.proximoBucket = null;
    }
    public void adicionarDado(Tupla novoDado) {
        for (int i = 0; i < dados.length; i++) {
            if (dados[i] == null) {
                dados[i] = novoDado;
                return;
            }
        }
        if(proximoBucket == null){
            overflow(novoDado);
        }
        else {
            proximoBucket.adicionarDado(novoDado);
            Main.colisoes++;
            colisoes++;
        }
    }

    public void overflow(Tupla novoDado){
        Main.overflows++;
        overflows++;
        proximoBucket = new Bucket(tamanhoBucket, indice);
        adicionarDado(novoDado);
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

    public Tupla buscarDado(String chaveDeBusca) {
        for (int i = 0; i < dados.length; i++) {
            if (dados[i] != null && dados[i].getPalavra().equals(chaveDeBusca)) {
                return dados[i];
            }
        }
        if (proximoBucket != null) {
            return proximoBucket.buscarDado(chaveDeBusca);
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bucket " + indice + ":\n");
        try{
            for (Tupla tupla : dados) {
                sb.append(tupla.toString()).append("\n");
            }
        }catch (Exception e){
            //
        }

        return sb.toString();
    }
}