package com.jurandir;

import com.jurandir.modules.Plano;

public class Main {

    public static void main(String[] args) {
        Plano p = new Plano(1000, 1005, 2);
        p.iniciar();
        System.out.println("Fim!");
    }
}
