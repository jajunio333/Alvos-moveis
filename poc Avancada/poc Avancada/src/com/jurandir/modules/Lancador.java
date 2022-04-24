package com.jurandir.modules;

import RD.Rec;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lancador extends Thread{
    private static int count = 0;
    private int id;
    private Posicao posicaoCorrente;
    private long timestampTiro;
    private int velocidadeTiro;
    private Carregador carregador;
    private boolean isReady;

    public Lancador(Posicao posicaoCorrente, long timestampTiro, int velocidadeTiro, Carregador carregador) {
        this.id = 1+count;
        this.posicaoCorrente = posicaoCorrente;
        this.timestampTiro = timestampTiro;
        this.velocidadeTiro = velocidadeTiro;
        this.carregador = carregador;
        isReady = true;
        count++;
        Thread t = new Thread(this);
        t.start();
    }

    public Tiro iniciar(Alvo alvo, long largura, long altura) {
        isReady = false;
        Tiro tiro;
        boolean acopladoCarregador = false;
        while (!acopladoCarregador) {
            carregador.acoplar(this.id);
            acopladoCarregador = carregador.getPronto(this.id);
            try {
                Thread.sleep(1); // tempo para nova conferencia
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        tiro = calcularTiro(alvo, largura, altura, carregador);
        if(tiro != null) {
            carregador.carregarTiro(tiro, this.id);
            tiro = carregador.dispararTiro(this.id);
        } else {
            System.out.println("Não é possível acertar o alvo com os parametros informados!");
        }
        carregador.desacoplar();
        isReady = true;
        return tiro;
    }

    private Tiro calcularTiro(Alvo alvo, long largura, long altura, Carregador carregador) {
        Posicao posicaoAlvoCorrenteParaFutura = new Posicao(0, 0);
        posicaoAlvoCorrenteParaFutura.setX(alvo.getPosicaoInicial().getX());
        int tempo = 1;
        Posicao pontoColisao = new Posicao(-1, -1);
        boolean pontoColisaoEncontrado = false;
        double distancia = 0;
        long tempoColisaoCiclo = 0;
        long posicaoInicialNova = alvo.getPosicaoCorrente().getY(); //OBTEM A POSIÇÃO DO ALVO COMO UM RADAR
        while (!pontoColisaoEncontrado) {
            posicaoAlvoCorrenteParaFutura.setY(posicaoInicialNova +
                    (long) alvo.direcao() *alvo.getVelocidade()*tempo);

            distancia = Math.sqrt(Math.pow(Math.abs(posicaoAlvoCorrenteParaFutura.getY() - this.posicaoCorrente.getY()),2) +
                    Math.pow(Math.abs(posicaoAlvoCorrenteParaFutura.getX() - this.posicaoCorrente.getX()),2));
            double angle = Math.atan2((posicaoAlvoCorrenteParaFutura.getY()-this.posicaoCorrente.getY()),
                    (posicaoAlvoCorrenteParaFutura.getX()-this.posicaoCorrente.getX()));
            tempoColisaoCiclo = Math.round(distancia/(velocidadeTiro));
            if(tempoColisaoCiclo*timestampTiro == (tempo*(alvo.getTimestamp()))) {
                pontoColisaoEncontrado = true;
                pontoColisao.setY(posicaoAlvoCorrenteParaFutura.getY());
                pontoColisao.setX(posicaoAlvoCorrenteParaFutura.getX());
                System.out.println("Ponto de colisão encontrado: x = " + posicaoAlvoCorrenteParaFutura.getX() + " y = " + posicaoAlvoCorrenteParaFutura.getY());
            }
            else if(posicaoAlvoCorrenteParaFutura.getY() - alvo.getPosicaoFinal().getY() < 0) {
                pontoColisaoEncontrado = true;
            }
            tempo++;
        }
        if(pontoColisao.getY() != -1) {
            List<Posicao> trajetoria = new ArrayList<>();
            long posY = this.posicaoCorrente.getY();
            long posX = this.posicaoCorrente.getX();
            int j = 1;
            double angle = Math.atan2((pontoColisao.getY()-this.posicaoCorrente.getY()),
                    (pontoColisao.getX()-this.posicaoCorrente.getX()));
            while (posY < pontoColisao.getY() || (posX > 0 && posX < largura)) {       // AGORA ADICIONA A VELOCIDADE MODIFICADA PELA RD
                    posY = Math.round(this.posicaoCorrente.getY() + velocidadeTiroModificada()*Math.sin(angle)*j);
                    posX = Math.round(this.posicaoCorrente.getX() + velocidadeTiroModificada()*Math.cos(angle)*j);
                    trajetoria.add(new Posicao(posX, posY));
                    j++;

            }
            return Tiro.criarTiro(new Posicao(posicaoCorrente.getX(), posicaoCorrente.getY()), timestampTiro, trajetoria);
        }
        return null;
    }

    public double velocidadeTiroModificada(){
        Random gerador = new Random();
        List<Double> F1aF4 = new ArrayList<>();
        List<Double> S1aS4 = new ArrayList<>();
        F1aF4.add(10.0); // F1
        F1aF4.add(5.0); // F2
        F1aF4.add(gerador.nextDouble() + 5.0); // RUIDO ADICIONADO A F3 (NO CASO F3 É A VELOCIDADE DO TIRO)
        F1aF4.add(10.0); // F4

        //GERADOR DE DESVIOS ALEATORIOS
        for (int i = 0; i < 4; i++) {
            S1aS4.add(gerador.nextDouble()/1.0); //
        }
        return new Rec().RecDados(F1aF4.get(0), F1aF4.get(1), F1aF4.get(2), F1aF4.get(3),
                                  S1aS4.get(0), S1aS4.get(1), 1, S1aS4.get(3));

    }

    public boolean isReady() {
        return isReady;
    }

    public long getId() {
        return id;
    }

}
