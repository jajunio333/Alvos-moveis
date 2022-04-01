package com.jurandir.modules;

import java.util.List;

public class Tiro implements Runnable {
    private int id;
    private Posicao posicaoCorrente;
    private long timestamp;
    private List<Posicao> trajetoria;
    private static int count = 0;

    private Tiro(Posicao posicaoInicial, long timestamp, List<Posicao> trajetoria) {
        this.id = 1 + count;
        this.timestamp = timestamp;
        this.posicaoCorrente = posicaoInicial;
        this.trajetoria = trajetoria;
        count++;
    }

    public static Tiro criarTiro(Posicao posicaoInicial, long timestamp, List<Posicao> trajetoria) {
        return new Tiro(posicaoInicial, timestamp, trajetoria);
    }

    public void disparar() {
        Thread t = new Thread(this);
        t.start();
    }
    
    @Override
    public void run() {
        for (Posicao p :trajetoria) {
            try {
                Thread.sleep(timestamp);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mover(p);
        }
    }

    private void mover(Posicao p) {
        posicaoCorrente = p;
    }

    public int getUnicId() {
        return id;
    }

    public Posicao getPosicaoCorrente() {
        return posicaoCorrente;
    }

    public static int count() {
        return count;
    }
}
