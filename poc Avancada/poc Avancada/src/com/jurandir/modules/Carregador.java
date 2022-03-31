package com.jurandir.modules;

import java.util.Stack;

public class Carregador extends Thread {

    private static int count = 0;
    private int id;
    private Stack<Tiro> tiros;
    private long tempoDeCarga;
    private boolean pronto;
    private int idLancador;

    public Carregador(int id, long tempoDeCarga) {
        super();
        this.id = id;
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

    public void acoplar(int idLancador) { //Talvez remover synchronized
        if(!isAcoplado(idLancador)) {
            this.idLancador = idLancador;
            Thread t = new Thread(this);
            t.start();
        }
    }

    public synchronized void desacoplar() { //Talvez remover synchronized
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

    public void dispararTiro(int idLancador) {
        if (isAcoplado(idLancador)) {
            Tiro t = tiros.pop();
            t.disparar();
        }
        if(tiros.isEmpty()) {
            pronto = false;
        }
    }

    public static int count() {
        return count;
    }
}
