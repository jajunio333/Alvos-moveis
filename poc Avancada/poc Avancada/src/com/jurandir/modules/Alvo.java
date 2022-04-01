package com.jurandir.modules;

public class Alvo extends Thread {
    private static int count=0;
    private int id;
    private Posicao posicaoInicial;
    private Posicao posicaoCorrente;
    private Posicao posicaoFinal;
    private long timestamp;
    private long tempoPassado;
    private int velocidade;

    public Alvo(long timestamp, int velocidade, Posicao posicaoInicial, Posicao posicaoFinal) {
        super();
        this.id = count + 1;
        this.posicaoInicial = new Posicao(posicaoInicial.getX(), posicaoInicial.getY());
        this.posicaoCorrente = new Posicao(posicaoInicial.getX(), posicaoInicial.getY());
        this.timestamp = timestamp;
        this.velocidade = velocidade;
        this.posicaoFinal = new Posicao(posicaoFinal.getX(), posicaoFinal.getY());
        tempoPassado = 0L;
        count++;
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while (direcao() != 0) {
            try {
                Thread.sleep(timestamp);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            posicaoCorrente.setY(posicaoCorrente.getY() + direcao()*velocidade);
            tempoPassado += timestamp;
        }
    }

    public int direcao() {
       int direcao = Integer.parseInt("" + (posicaoFinal.getY() - posicaoCorrente.getY()) + "");
       return Integer.compare(direcao, 0);
    }

    public int getUnicId() {
        return id;
    }

    public Posicao getPosicaoCorrente() {
        return posicaoCorrente;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Posicao getPosicaoInicial() {
        return posicaoInicial;
    }

    public Posicao getPosicaoFinal() {
        return posicaoFinal;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public long getTempoPassado() {
        return tempoPassado;
    }

    public static int count() {
        return count;
    }
}
