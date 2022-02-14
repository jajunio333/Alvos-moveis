package main;

import components.AlvosMoveis;


public class Testes {
    public static void main(String[] args) throws InterruptedException {
        AlvosMoveis teste1 = new AlvosMoveis(1, new int[]{1, 1},new int[]{1, 1},0);
       teste1.localizacaoAtualizada();
    }
}