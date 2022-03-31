package com.jurandir.factory;

import com.jurandir.modules.Alvo;
import com.jurandir.modules.Posicao;


public class FactoryAlvo {

    public static Alvo criarNovoAlvo(long largura, long altura) {
        double random = Math.random();
        Alvo alvo;
        if (random >= 0.51) {
            alvo = new Alvo(100, 5, new Posicao(largura, altura), new Posicao(largura, 0));
        }
        else {
            alvo = new Alvo(100, 5, new Posicao(0, altura), new Posicao(0, 0));
        }
        return alvo;
    }

}
