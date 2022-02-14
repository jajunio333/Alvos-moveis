package components;

public class AlvosMoveis extends Thread implements IalvosMoveis{
    private int pontoDeOrigem[], pontoDeDestino[],localizacaoAtualizada[];
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

    public void setId(long id) {
        Id = id;
    }

    public int[] getPontoDeOrigem() {
        return pontoDeOrigem;
    }

    public void setPontoDeOrigem(int[] pontoDeOrigem) {
        this.pontoDeOrigem = pontoDeOrigem;
    }

    public int[] getPontoDeDestino() {
        return pontoDeDestino;
    }

    public void setPontoDeDestino(int[] pontoDeDestino) {
        this.pontoDeDestino = pontoDeDestino;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int[] localizacaoAtualizada() throws InterruptedException {
        sleep(freqAtualizacaoPosicao);
        atualizaPosicao();
        //System.out.println(localizacaoAtualizada[1] + "  " + localizacaoAtualizada[0]);
        return localizacaoAtualizada;
    }

    private void atualizaPosicao(){
        localizacaoAtualizada[1] =+ 5;
    }
}


