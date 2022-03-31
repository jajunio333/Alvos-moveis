package com.jurandir.modules;

import com.jurandir.factory.FactoryAlvo;
import com.jurandir.factory.FactoryLancador;

public class Plano implements Runnable{
    private long largura;
    private long altura;
    private long tolerancia;
    private int numeroInteracoes;
    private Carregador carregador;

    public Plano(long largura, long altura, long tolerancia, int numeroInteracoes, Carregador carregador) {
        this.largura = largura;
        this.altura = altura;
        this.tolerancia = tolerancia;
        this.numeroInteracoes = numeroInteracoes;
        this.carregador = carregador;
    }

    @Override
    public void run() {

        while (numeroInteracoes > 0) {
            Alvo alvo = null;
            try {
                Thread.sleep(50);
                alvo = FactoryAlvo.criarNovoAlvo(largura, altura);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            FactoryLancador factoryLancador = new FactoryLancador(2, largura);
            new Monitoramento(factoryLancador.getListaLancador(), alvo, largura, altura, carregador);
            numeroInteracoes --;
        }
    }
}
