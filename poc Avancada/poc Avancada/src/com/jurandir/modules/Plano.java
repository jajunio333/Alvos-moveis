package com.jurandir.modules;

import com.jurandir.factory.FactoryAlvo;
import com.jurandir.factory.FactoryLancador;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Plano {
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
    }

    public void run() {
        limparArquivos();
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

    private void limparArquivos() {
        //deletar arquivos da pasta files, se existir.
    }
}
