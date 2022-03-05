package components;

public class Lancador extends  Thread{
    private int posicaoLancador[];
    private Carregador carregador;

    public Lancador(Carregador carregador, int posicaoLancador[]) {
        this.posicaoLancador = posicaoLancador;
        this.carregador = carregador;
    }

    public int carregar() throws InterruptedException {
        int tempoGasto = 0;
        while (carregador.verificaMunicao()){
            sleep(30);
            carregador.retiraMunicao();
            tempoGasto += 30;
        }
        return tempoGasto;
    }
    public int[] preparar(int timestamp, int[]  pontoInicialAlvo) throws InterruptedException {
        int tempoGasto = 30;
        sleep(tempoGasto);//mira
        return calculoTrajetoria(tempoGasto + timestamp, pontoInicialAlvo);
    }
    public int[]calculoTrajetoria(int timestamp,int[]  pontoAtualTiro) {

    }
    public boolean atirar(){
        return true;
    }
}
