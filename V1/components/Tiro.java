package components;

public class Tiro extends Thread implements Itiros{
    private int pontoDeOrigem[], pontoDeDestino[],localizacaoAtualizada[];
    private long Id,timestamp, freqAtualizacaoPosicao;
    private boolean contatoComAlvo;

    public Tiro(long id, int[] pontoDeOrigem, int[] pontoDeDestino, long timestamp) {
        this.Id = id;
        this.pontoDeOrigem = pontoDeOrigem;
        this.pontoDeDestino = pontoDeDestino;
        this.localizacaoAtualizada = pontoDeOrigem;
        this.timestamp = timestamp;
        this.freqAtualizacaoPosicao = 30;
        this.contatoComAlvo = false;
        start();
    }

    @Override
    public int[] localizacaoAtualizada() throws InterruptedException {
        sleep(freqAtualizacaoPosicao);
        atualizaPosicao();
        //System.out.println(localizacaoAtualizada[1] + "  " + localizacaoAtualizada[0]);
        return localizacaoAtualizada;
    }
    private void atualizaPosicao(){

        //todo esse metododo, deve obter os valores de x e y atraves de calculoTrajetoria
        //deve-se também usar um método atirar q representa timestamp
        localizacaoAtualizada[1] =+ 5;
    }

    //todo chamar preparar passando  timestamp, pi, pf do alvo
    //todo criar um metodo atirar recebe um boolean

}

