package com.jurandir.modules;

import com.jurandir.factory.FactoryAlvo;
import com.jurandir.factory.FactoryLancador;

public class Plano extends Thread{
    private static final String PATH = "poc Avancada/files/";
    private long largura;
    private long altura;
    private long tamanhoAlvo;
    private int numeroInteracoes;
    private int numeroLancadores;

    public Plano(long largura, long altura, long tamanhoAlvo, int numeroInteracoes, int numeroLancadores) {
        this.largura = largura;
        this.altura = altura;
        this.tamanhoAlvo = tamanhoAlvo;
        this.numeroInteracoes = numeroInteracoes;
        this.numeroLancadores = numeroLancadores;
        Thread t = new Thread(this);
        t.start();
    }
    @Override
    public void run() {
        FactoryLancador factoryLancador = new FactoryLancador(numeroLancadores, largura);
        while (numeroInteracoes > 0) {
            Alvo alvo = null;
            try {
                Thread.sleep(10);
                alvo = FactoryAlvo.criarNovoAlvo(largura, altura, tamanhoAlvo);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            new Monitoramento(factoryLancador.getListaLancador(), alvo, largura, altura, tamanhoAlvo);
            numeroInteracoes --;
        }
    }
}
