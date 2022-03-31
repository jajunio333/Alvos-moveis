package com.jurandir.modules;

import java.util.ArrayList;
import java.util.List;

public class Lancador {
    private static int count = 0;
    private int id;
    private Posicao posicaoCorrente;
    private Tiro tiro;
    private long timestampTiro;
    private int velocidadeTiro;
    private boolean isReady;

    public Lancador(Posicao posicaoCorrente, long timestampTiro, int velocidadeTiro) {
        this.id = 1+count;
        this.posicaoCorrente = posicaoCorrente;
        this.timestampTiro = timestampTiro;
        this.velocidadeTiro = velocidadeTiro;
        isReady = false;
        count++;
    }

    public void iniciar(Carregador carregador, Alvo alvo, long largura, long altura) {
        isReady = false;
        boolean acopladoCarregador = false;
        carregador.acoplar(this.id);
        while (!acopladoCarregador) {
            acopladoCarregador = carregador.getPronto(this.id);
            try {
                Thread.sleep(1); // tempo para nova conferencia
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        this.tiro = calcularTiro(alvo, largura, altura, carregador);
        if(this.tiro != null) {
            carregador.carregarTiro(this.tiro, this.id);
            carregador.dispararTiro(this.id);
        } else {
            System.out.println("Não é possível acertar o alvo com os parametros informados!");
        }
        isReady = true;
    }

    private Tiro calcularTiro(Alvo alvo, long largura, long altura, Carregador carregador) {
        Posicao posicaoAlvoCorrenteParaFutura = new Posicao(0, 0);
        posicaoAlvoCorrenteParaFutura.setX(alvo.getPosicaoInicial().getX());
        int tempo = 1;
        Posicao pontoColisao = new Posicao(-1, -1);
        boolean pontoColisaoEncontrado = false;
        double distancia = 0;
        long tempoColisaoCiclo = 0;
        long posicaoInicialNova = alvo.getPosicaoInicial().getY() +
                alvo.direcao()*alvo.getVelocidade()*(carregador.getTempoDeCarga()/alvo.getTimestamp());
        while (!pontoColisaoEncontrado) {
            posicaoAlvoCorrenteParaFutura.setY(posicaoInicialNova +
                    alvo.direcao()*alvo.getVelocidade()*tempo);

            distancia = Math.sqrt(Math.pow(Math.abs(posicaoAlvoCorrenteParaFutura.getY() - this.posicaoCorrente.getY()),2) +
                    Math.pow(Math.abs(posicaoAlvoCorrenteParaFutura.getY() - this.posicaoCorrente.getY()),2));
            double angle = Math.atan2((posicaoAlvoCorrenteParaFutura.getY()-this.posicaoCorrente.getY()),
                    (posicaoAlvoCorrenteParaFutura.getX()-this.posicaoCorrente.getX()));
            tempoColisaoCiclo = Math.round(distancia/(velocidadeTiro*Math.sin(angle)));
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
            while (posY < pontoColisao.getY() || (posX > 0 && posX < largura)) {
                posY = Math.round(this.posicaoCorrente.getY() + velocidadeTiro*Math.sin(angle)*j);
                posX = Math.round(this.posicaoCorrente.getX() + velocidadeTiro*Math.cos(angle)*j);
                trajetoria.add(new Posicao(posX, posY));
                j++;
            }
            return new Tiro(1+Tiro.count(), new Posicao(posicaoCorrente.getX(), posicaoCorrente.getY()), timestampTiro, trajetoria);
        }
        return null;
    }

    public static int count() {
        return count;
    }

    public Tiro getCurrentTiro() {
        return tiro;
    }

    public boolean isReady() {
        return isReady;
    }
}
