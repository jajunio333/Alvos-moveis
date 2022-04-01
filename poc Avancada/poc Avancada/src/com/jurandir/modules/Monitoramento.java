package com.jurandir.modules;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Monitoramento implements Runnable{

    private static final String PATH = "poc Avancada/files/";

    private long id;
    private List<Lancador> listLancador;
    private Alvo alvo;
    private long largura;
    private long altura;
    private long tamanhoAlvo;
    private StringBuilder stringBuilder;
    private static long count;

    public Monitoramento(List<Lancador> listLancador, Alvo alvo, long largura, long altura, long tamanhoAlvo) {
        this.id = 1 + count;
        this.listLancador = listLancador;
        this.alvo = alvo;
        this.largura = largura;
        this.altura = altura;
        this.tamanhoAlvo = tamanhoAlvo;
        this.stringBuilder = new StringBuilder();
        count ++;
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        appendEvent("Iniciando monitoramento com id " + this.id);
        Lancador lancador = SelecionarLancador();
        lancador.iniciar(alvo, largura, altura);
        boolean monitorar = lancador.getCurrentTiro() != null;
        if (!monitorar) {
            appendEvent("Lancador id: " + lancador.getId() + " não consegue acertar o alvo de id: " + alvo.getUnicId());
        }
        while (monitorar) {
            appendEvent("Lancador id: " + lancador.getId() + "; Posição do Tiro de id" +   lancador.getCurrentTiro().getUnicId() + ": x=" + lancador.getCurrentTiro().getPosicaoCorrente().getX() + ", y=" + lancador.getCurrentTiro().getPosicaoCorrente().getY());
            appendEvent("Posição do Alvo de id" +   alvo.getUnicId() + ": x=" + alvo.getPosicaoCorrente().getX() + ", y=" + alvo.getPosicaoCorrente().getY());
            if(Math.abs(alvo.getPosicaoCorrente().getX() - lancador.getCurrentTiro().getPosicaoCorrente().getX()) <= tamanhoAlvo &&
                    Math.abs(alvo.getPosicaoCorrente().getY() - lancador.getCurrentTiro().getPosicaoCorrente().getY()) <= tamanhoAlvo) {
                appendEvent("Monitoramento de id " + this.id + ":\nAlvo com id " + alvo.getUnicId() + " foi atingido pelo tiro com id " + lancador.getCurrentTiro().getUnicId() + " e lancador de id " + lancador.getId() + " com tamanhoAlvo de " + tamanhoAlvo + " unidades na posição x = "
                        + alvo.getPosicaoCorrente().getX() + " y = " + alvo.getPosicaoCorrente().getY());
                monitorar = false;
            }
            else if(alvo.getPosicaoCorrente().getY() + tamanhoAlvo < 0 ||
                    lancador.getCurrentTiro().getPosicaoCorrente().getX() - tamanhoAlvo > largura  ||
                    lancador.getCurrentTiro().getPosicaoCorrente().getX() + tamanhoAlvo < 0 ||
                    lancador.getCurrentTiro().getPosicaoCorrente().getY() - tamanhoAlvo > altura) {

                appendEvent("Alvo de id " + alvo.getUnicId() + " ou tiro de id " + lancador.getCurrentTiro().getUnicId() + " sairam dos limtes do plano");
                monitorar = false;
            }
            try {
                Thread.sleep(1); // tempo para nova conferencia
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        appendEvent("Fim Execução!");
        gravarArquivo();
    }

    private void gravarArquivo() {
        try {
            File myObj = new File(PATH+"Monitoramento_" + this.id + ".txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter myWriter = new FileWriter(PATH+"Monitoramento_" + this.id + ".txt");
            myWriter.write(stringBuilder.toString());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void appendEvent(String event) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        stringBuilder.append(sdf.format(new Date())).append("    ").append(event).append("\n");
        System.out.println(sdf.format(new Date()) + "    " + event);
    }

    private Lancador SelecionarLancador() {
        boolean lancadorSelecionado = false;
        int quantidadeLancadores = listLancador.size();
        int contador = 0;
        Lancador lancador = null;
        while(!lancadorSelecionado) {
            lancadorSelecionado = listLancador.get(contador).isReady();
            if (lancadorSelecionado) {
                lancador = listLancador.get(contador);
            }
            contador ++;
            if (contador == quantidadeLancadores) {
                contador = 0;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Erro thread selecionar lancador: " + e.getMessage());
            }
        }
        return lancador;
    }
}
