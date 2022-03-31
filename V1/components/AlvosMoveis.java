package components;

public class AlvosMoveis extends Thread implements IalvosMoveis{
    private final int pontoDeOrigem[], pontoDeDestino[],localizacaoAtualizada[];
    private long Id,timestamp, freqAtualizacaoPosicao;
    private boolean chegadaDestino, atingido;

    public AlvosMoveis(long id, int[] pontoDeOrigem, int[] pontoDeDestino, long timestamp) {
        this.Id = id;
        this.pontoDeOrigem = pontoDeOrigem;
        this.pontoDeDestino = pontoDeDestino;
        this.localizacaoAtualizada = pontoDeOrigem;
        this.timestamp = timestamp;
        this.freqAtualizacaoPosicao = 30;
        this.chegadaDestino = false;
        this.atingido = false;
        start();
    }

    @Override
    public long getId() {
        return Id;
    }

    public int[] getPontoDeOrigem() {
        return pontoDeOrigem;
    }

    public int[] getPontoDeDestino() {
        return pontoDeDestino;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {this.timestamp = timestamp;}

    public void setAtingido(boolean atiangido){this.atingido = atiangido;}

    public long getFreqAtualizacaoPosicao() {
        return freqAtualizacaoPosicao;
    }

    public void setFreqAtualizacaoPosicao(long freqAtualizacaoPosicao) {
        this.freqAtualizacaoPosicao = freqAtualizacaoPosicao;
    }

    @Override
    public void run(){
        moverAteFim();
    }

    private void moverAteFim() {
        if (localizacaoAtualizada[1] < pontoDeDestino[1]) {
            atualizaPosicao();
            chegadaDestino = false;
            try {
                Thread.sleep(freqAtualizacaoPosicao);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            moverAteFim();
        } else {
            chegadaDestino = true;
        }
    }

    private void atualizaPosicao(){
        localizacaoAtualizada[1] =- 5;
    }

    @Override
    public int[] localizacaoAtualizada() {
        return localizacaoAtualizada;
    }
}


