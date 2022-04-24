package com.jurandir.modules;

import java.util.Stack;

public class Carregador extends Thread {

    private static int count = 0;
    private int id;
    private Stack<Tiro> tiros;
    private long tempoDeCarga;
    private boolean pronto;
    private int idLancador;

    public Carregador(long tempoDeCarga) {
        super();
        this.id = 1 + count;
        this.tempoDeCarga = tempoDeCarga;
        this.pronto = false;
        this.tiros = new Stack<>();
        count++;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(tempoDeCarga);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pronto = true;
    }

    public void acoplar(int idLancador) {
        if(!isAcoplado(idLancador)) {
            this.idLancador = idLancador;
            Thread t = new Thread(this);
            t.start();
        }
    }

    public synchronized void desacoplar() {
        this.idLancador = 0;
        this.pronto = false;
    }

    public long getTempoDeCarga() {
        return tempoDeCarga;
    }

    public int getUnicId() {
        return id;
    }

    public boolean getPronto(int idLancador) {
        return this.pronto && isAcoplado(idLancador);
    }

    private boolean isAcoplado(int idLancador) {
        return this.idLancador == idLancador;
    }

    public void carregarTiro(Tiro tiro, int idLancador) {
        if(getPronto(idLancador)) {
            tiros.push(tiro);
        }
    }

    public Tiro dispararTiro(int idLancador) {
        if (isAcoplado(idLancador)) {
            Tiro t = tiros.pop();
            t.disparar();
            if (tiros.isEmpty()) {
                pronto = false;
            }
            return t;
        }
        return null;
    }

    public static int count() {
        return count;
    }
}
