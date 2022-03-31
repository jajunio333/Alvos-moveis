package com.jurandir.modules;

import java.util.List;

public class Monitoramento implements Runnable{

    private List<Lancador> listLancador;
    private Alvo alvo;
    private long largura;
    private long altura;
    private Carregador carregador;

    public Monitoramento(List<Lancador> listLancador, Alvo alvo, long largura, long altura, Carregador carregador) {
        this.listLancador = listLancador;
        this.alvo = alvo;
        this.largura = largura;
        this.altura = altura;
        this.carregador = carregador;
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        //TODO: selecionar um lancador para dar o tiro;
        //TODO: iniciar lancamento com o alvo; lancador.iniciar(carregador, alvo, largura, altura);
        boolean monitorar = lancador.getCurrentTiro() != null;
        System.out.println("Iniciando monitoramento!" + " Monitorar: " + monitorar);
        while (monitorar) {
            System.out.println("Posição do Tiro de id" +   lancador.getCurrentTiro().getUnicId() + ": x=" + lancador.getCurrentTiro().getPosicaoCorrente().getX() + ", y=" + lancador.getCurrentTiro().getPosicaoCorrente().getY());
            System.out.println("Posição do Tiro1 de id" +   lancador1.getCurrentTiro().getUnicId() + ": x=" + lancador1.getCurrentTiro().getPosicaoCorrente().getX() + ", y=" + lancador1.getCurrentTiro().getPosicaoCorrente().getY());
            System.out.println("Posição do Alvo de id" +   alvo.getUnicId() + ": x=" + alvo.getPosicaoCorrente().getX() + ", y=" + alvo.getPosicaoCorrente().getY());
            if(Math.abs(alvo.getPosicaoCorrente().getX() - lancador.getCurrentTiro().getPosicaoCorrente().getX()) <= tolerancia &&
                    Math.abs(alvo.getPosicaoCorrente().getY() - lancador.getCurrentTiro().getPosicaoCorrente().getY()) <= tolerancia) {
                System.out.println("Alvo com id " + alvo.getUnicId() + " foi atingido pelo tiro com id " + lancador.getCurrentTiro().getUnicId() + " com tolerancia de " + tolerancia + " unidades" );
                monitorar = false;
            }
            if(Math.abs(alvo.getPosicaoCorrente().getX() - lancador1.getCurrentTiro().getPosicaoCorrente().getX()) <= tolerancia &&
                    Math.abs(alvo.getPosicaoCorrente().getY() - lancador1.getCurrentTiro().getPosicaoCorrente().getY()) <= tolerancia) {
                System.out.println("Alvo com id " + alvo.getUnicId() + " foi atingido pelo tiro com id " + lancador1.getCurrentTiro().getUnicId() + " com tolerancia de " + tolerancia + " unidades" );
                monitorar = false;
            }
            else if(alvo.getPosicaoCorrente().getY() < 0 ||
                    lancador.getCurrentTiro().getPosicaoCorrente().getX() > largura  ||
                    lancador.getCurrentTiro().getPosicaoCorrente().getX() < largura ||
                    lancador.getCurrentTiro().getPosicaoCorrente().getY() > altura) {
                System.out.println("Alvo ou tiro sairam dos limtes do plano");
                monitorar = false;
            }
            try {
                Thread.sleep(1); // tempo para nova conferencia
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Fim Execução!");
    }
}
